package engine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import input.InputHandler;
import screens.Overlay;
import screens.StartScreen;

/**
 * This is the initial game engine class which handles the running of the game
 * @author bobbydilley
 *
 */
public class Engine extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private int width = 800;
	private int height = 600;
	private boolean fullScreen;
	private String name = "Game";
	private boolean running = false;
	private int tickCount = 0;
	private GameObject gameObject = new GameObject(width, height);
	private Screen screen = new StartScreen(gameObject);
	private int opac = 255;
	private boolean changing = false;
	private Overlay overlay = new Overlay(gameObject);
	private boolean setRender = false;
	
	private InputHandler inputHandler;
	
	
	/**
	 * The Initial engine constructor which will start the engine
	 */
	public Engine(String name, boolean fullScreen) {
		
		this.fullScreen = fullScreen;
		this.name = name;
		this.gameObject.setOverlay(overlay);
		
		// Make sure the window is not movable
		setMinimumSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));

		// Create the JFrame
		frame = new JFrame(name);

		// Set the close operation and layout
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// Add our canvas to the JFrame
		frame.add(this, BorderLayout.CENTER);
		
		// Make sure the frame is setup for input
		frame.setFocusable(true);
		frame.setFocusTraversalKeysEnabled(false);
		
		// Create the input handler
		inputHandler = new InputHandler();
		gameObject.setInputHandler(inputHandler);
		
		// Put some default keys in
		inputHandler.storePlayKey('q');
		inputHandler.storePlayKey('w');
		inputHandler.storePlayKey('e');
		inputHandler.storePlayKey('r');
		inputHandler.storePowerKey('l');
		inputHandler.storeMuteKey('m');
		
		this.addKeyListener(inputHandler);
				
		inputHandler.setScreen(screen);		
		

		// Sets the frame so the sizes are above / at the correct size
		frame.pack();

		// Stop the frame being resized
		frame.setResizable(false);
		
		// Centre the frame on your computer
		frame.setLocationRelativeTo(null);

		// Test to see if we can support full screen mode
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		
		// If full screen is set, jump into full screen mode now
		if(device.isDisplayChangeSupported() && fullScreen) {
			device.setDisplayMode(new DisplayMode(width, height, 32, DisplayMode.REFRESH_RATE_UNKNOWN));
			device.setFullScreenWindow(frame); 
		}
		
		
		// Show the frame
		frame.setVisible(true);
	}

	/**
	 * This will start the thread running the engine
	 */
	public void start() {
		running = true;
		new Thread(this).start();
	}

	/**
	 * This will stop the thread running the engine
	 */
	public void stop() {
		running = false;
	}

	/**
	 * This is called when the thread has started
	 */
	public void run() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
				
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000/60D;
		
		int ticks = 0;
		int frames = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			
			// Set to true to limit to 60fps for testing
			boolean shouldRender = false;
			
			while(delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			// Limit rendering
			/*try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			if(shouldRender) {
				frames++;
				render();
			}
			
			
			if(System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println("Ticks: " + ticks + ", Frames: " + frames);
				frames = 0;
				ticks = 0;
			}
			
		}
	}
	
	/**
	 * This is called every time a tick update event fires
	 */
	public void tick() {
		tickCount++;
		
		if(screen.shouldMoveScreen()) {
			changing = true;
		}
		
		if(changing && opac >= 550) {
			Screen nextScreen = screen.getNextScreen();
			screen.dispose();
			screen = nextScreen;
			
			// For effect have a little fake load time
			changing = false;
			System.out.println("Screen Moved");
		}
		
		overlay.update();
		screen.update();
	}
	
	/**
	 * This is called every time a render update event fires
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		// Change Graphics
		if(setRender) {
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		}
		
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		
				
		screen.draw(g);
		
		
		// This is a layer over the top which is used to fade between scenes
		g.setColor(new Color(0, 0, 0, Math.max(Math.min(opac, 255), 0)));
		
		// Sorts the fading of the scenes
		if(opac > 0 && !changing) {
			opac-=4;
		}
		
		if(opac < 550 && changing) {
			opac+=17;
		}
		
		
				
		// Adds the rectangle over the top
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Draw the overlay
		overlay.draw(g);
		
		// Remove and dispose of them
		g.dispose();
		bs.show();
				
	}
}
