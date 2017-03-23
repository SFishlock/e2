package screens;

import java.awt.Graphics;

import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import sprites.SystemText;
import utils.ColorPack;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class ExampleScreen extends Screen {

	private SystemText textSprite; // An example text sprite
	private int count = 0; // A variable to count on the screen

	@Override
	public void keyPressed(int key) {
		System.out.println("on" + key);
		// Default keys are being set in the engine class currently
		// There are representations of all the keys, PLAYER0 is set to Q
		if (key == InputHandler.PLAYKEY0) {
			// This will set the bottom text
			getGameObject().getOverlay().getMiddleBottom().setText("PRESS START");

			// This will move screen
			moveScreen();
		}
	}

	@Override
	public void keyReleased(int key) {
		System.out.println("off" + key);
	}

	public ExampleScreen(GameObject gameObject) {
		super(gameObject);
		// Constructor for the SystemText object
		textSprite = new SystemText(10, 10, "PRESS 'Q' TO START GAME");

		// You can set the next screen whereever you like
		setNextScreen(new PlayScreen(gameObject));

	}

	@Override
	public void update() {
		// You should call setScreenSize and update on all sprites
		textSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		textSprite.update();

		// Increase the variable as the time goes up
		count++;

		// If we are in the screen for 60 updates, move to the start screen.
		if (count == 600) {
			// You can set the next screen right before moving, although may
			// cause a few m/s of lag
			setNextScreen(new PlayScreen(getGameObject()));

			// Run this to actually move the screen
			moveScreen();
		}

	}

	@Override
	public void draw(Graphics context) {

		// We use this to draw a dark background
		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());

		// This is how you draw the sprites
		textSprite.draw(context);

	}

}
