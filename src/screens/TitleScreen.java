package screens;

import java.awt.Color;
import java.awt.Graphics;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import audio.Player;
import audio.SoundHandler;
import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import sprites.DotSpriteBackground;
import sprites.FancyCenterTextSprite;
import sprites.ImageGrad;
import sprites.ImageSprite;
import sprites.MessageSprite;
import sprites.SystemBox;
import sprites.SystemText;
import utils.ColorPack;
import utils.ImageLoader;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class TitleScreen extends Screen {

	private SystemText textSprite;
	private DotSpriteBackground dotBackground;
	private ImageSprite b;
	private ImageSprite be;
	private ImageSprite bea;
	private ImageSprite beat;
	private ImageSprite beatn;
	private ImageSprite beatne;
	private ImageSprite beatnet;
	private ImageSprite beatnetf;
	private ImageSprite beatnetfi;
	private ImageGrad imageGrad;
	private int mopac = 0;
	
	
	
	private MessageSprite message;
	private MessageSprite message2;
	
	private SystemBox box;
	private FancyCenterTextSprite title;
	int count = -300;
	
	int lineXPos = 0;
	int lineOpac = 255;
	private SoundHandler fx;
	String[] fxlist = {"titleshort.wav", "bang.wav"};
	
	
	@Override
	public void keyPressed(int key) {
		System.out.println("on" + key);
		if(key == InputHandler.PLAYKEY0) {
			getGameObject().getOverlay().getMiddleBottom().setText(" ");
			fx.stopAll();
			fx.playEffect("bang.wav");
			moveScreen();
		} else if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute pressed");
		}
	}
	
	@Override
	public void keyReleased(int key) {
		System.out.println("off" + key);
		if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute unpressed");
		}
	} 
	
	
	public TitleScreen(GameObject gameObject) {
		super(gameObject);
		fx = new SoundHandler();
		
		fx.fillEffects(fxlist);
		
		textSprite = new SystemText(10, 10, "HELLO");
		//textSprite.setText("K44:K:C:A:2012072391");
		try {
			textSprite.setText("ID:" + Inet4Address.getLocalHost().getHostAddress().replaceAll("\\.", ":"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setNextScreen(new ModeSelect(gameObject));
		
		message = new MessageSprite(0, (int)(getScreenHeight() * 0.71), "PRESS 'Q' TO START THE GAME!");
		message2 = new MessageSprite(0, (int)(getScreenHeight() * 0.79), "NETWORK PLAY AVAILABLE!");
		message2.white();
		
		
		box = new SystemBox();
		box.setScreenSize(getScreenWidth(), getScreenHeight());

		imageGrad = new ImageGrad();
		imageGrad.setOpacity(0);
		
		dotBackground = new DotSpriteBackground(10, 10, 20, 30, true, getScreenWidth(), getScreenHeight());
		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());
		
		beatnetf = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/beatnet_full.png"));
		beatnetf.setScreenSize(getScreenWidth(), getScreenHeight());
		beatnetf.setOpacity(0);
		
		beatnetfi = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/beatnet_full.png"));
		beatnetfi.setScreenSize(getScreenWidth(), getScreenHeight());
		beatnetfi.setOpacity(0);
		beatnetfi.setSize(1.01f);
		
		beatnet = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/beatnet.png"));
		beatnet.setScreenSize(getScreenWidth(), getScreenHeight());
		beatnet.setOpacity(0);
		
		beatne = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/beatne.png"));
		beatne.setScreenSize(getScreenWidth(), getScreenHeight());
		beatne.setOpacity(0);
		
		beatn = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/beatn.png"));
		beatn.setScreenSize(getScreenWidth(), getScreenHeight());
		beatn.setOpacity(0);
		
		beat = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/beat.png"));
		beat.setScreenSize(getScreenWidth(), getScreenHeight());
		beat.setOpacity(0);
		
		bea = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/bea.png"));
		bea.setScreenSize(getScreenWidth(), getScreenHeight());
		bea.setOpacity(0);
		
		be = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/be.png"));
		be.setScreenSize(getScreenWidth(), getScreenHeight());
		be.setOpacity(0);
		
		b = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/b.png"));
		b.setScreenSize(getScreenWidth(), getScreenHeight());
		b.setOpacity(0);
		
		title = new FancyCenterTextSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), "beatnet");
		title.setScreenSize(getScreenWidth(), getScreenHeight());
		
		// Set the x Positoin of the line
		lineXPos = -getScreenWidth() - 1;
	}

	@Override
	public void update() {

		if(count == -190) {
			fx.playEffect("titleshort.wav");
		}
		
		imageGrad.setScreenSize(getScreenWidth(), getScreenHeight());
		imageGrad.update();
		
		textSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		textSprite.update();
		
		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());
		dotBackground.update();
		
		beatnetfi.setScreenSize(getScreenWidth(), getScreenHeight());
		beatnetfi.update();
		
		
		beatnetf.setScreenSize(getScreenWidth(), getScreenHeight());
		beatnetf.update();
		
		beatnet.setScreenSize(getScreenWidth(), getScreenHeight());
		beatnet.update();
		
		beatne.setScreenSize(getScreenWidth(), getScreenHeight());
		beatne.update();
		
		beatn.setScreenSize(getScreenWidth(), getScreenHeight());
		beatn.update();
		
		beat.setScreenSize(getScreenWidth(), getScreenHeight());
		beat.update();
		
		bea.setScreenSize(getScreenWidth(), getScreenHeight());
		bea.update();
		
		be.setScreenSize(getScreenWidth(), getScreenHeight());
		be.update();
		
		b.setScreenSize(getScreenWidth(), getScreenHeight());
		b.update();
		
		title.setScreenSize(getScreenWidth(), getScreenHeight());
		title.update();
		
		message.setScreenSize(getScreenWidth(), getScreenHeight());
		message.update();
		
		message2.setScreenSize(getScreenWidth(), getScreenHeight());
		message2.update();
		
		count++;
		if(count == 800) {
			setNextScreen(new StartScreen(getGameObject()));
			moveScreen();
		}
		
		
		if(count > 300 && lineXPos < 0) {
			lineXPos += (int)(-0.2 * lineXPos);
		}
		
		
		
		if(count == 310) {b.fadeIn();}
		if(count == 320) {be.fadeIn();}
		if(count == 330) {bea.fadeIn();}
		if(count == 340) {beat.fadeIn();}
		if(count == 350) {beatn.fadeIn();}
		if(count == 360) {beatne.fadeIn();}
		if(count == 370) {beatnet.fadeIn();}
		
		if(count == 380) {
			b.setOpacity(0);
			be.setOpacity(0);
			bea.setOpacity(0);
			beat.setOpacity(0);
			beatn.setOpacity(0);
			beatne.setOpacity(0);
			beatnet.fadeOutQuick();
			mopac = 255;
		}
		
		if(count > 380 && lineOpac > 0) {
			lineOpac -= Math.max(0.1, (int)(lineOpac * 0.1));
		}
		
		if(mopac > 0) {
			mopac -= Math.max(0.1, (int)(mopac * 0.05));
		}
		
		if(count == 380) {
			if(beatnetfi.getOpacity() == 0) {
				beatnetfi.setOpacity(1f);
				beatnetf.fadeIn();
			}
			beatnetfi.impress();
			imageGrad.fadeIn();
		}
		
		if(count == 470) {
			message.fadeIn();
		}
		
		if(count == 480) {
			message2.fadeIn();
		}
	
		
		
		
	}

	@Override
	public void draw(Graphics context) {

		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());

		imageGrad.draw(context);
		
		dotBackground.draw(context);

		textSprite.draw(context);
		
		
		if(count > 600) {
			//box.draw(context);
			
		}
		
		beatnetfi.draw(context);
		beatnetf.draw(context);
		beatnet.draw(context);
		beatne.draw(context);
		beatn.draw(context);
		beat.draw(context);
		bea.draw(context);
		be.draw(context);
		b.draw(context);
		
	
		
		// Draw the middle line
		if(lineOpac > 1) {
			context.setColor(new Color(255, 255, 255, lineOpac));
			
			int ypos = 286;
			context.drawLine(0 + lineXPos, ypos, getScreenWidth() + lineXPos, ypos);
			
		}
		
		
		
		if(mopac > 1) {
			context.setColor(new Color(255, 255, 255, mopac));
			context.fillRect(0, 0, getScreenWidth(), getScreenHeight());
		}
		
		message.draw(context);
		message2.draw(context);
		
		
	}

}
