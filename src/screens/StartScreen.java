package screens;

import java.awt.Graphics;

import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import sprites.ImageSprite;
import sprites.MessageSprite;
import utils.ColorPack;
import utils.ImageLoader;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class StartScreen extends Screen {
	int counter = 0;
	private ImageSprite image;
	private MessageSprite message;
	
	@Override
	public void keyPressed(int key) {
		System.out.println("on" + key);
		if(key == InputHandler.PLAYKEY0) {
			setNextScreen(new ModeSelect(getGameObject()));
			getGameObject().getOverlay().getMiddleBottom().setText(" ");
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
	
	public StartScreen(GameObject gameObject) {
		super(gameObject);
		setNextScreen(new TitleScreen(gameObject));
		
		message = new MessageSprite(0, (int)(getScreenHeight() * 0.8), "PRESS START TO PLAY");
		
		image = new ImageSprite(getScreenWidth() / 2, (int)(getScreenHeight() * 0.4), ImageLoader.loadImageFromResource("src/res/images/konami_logo.png"));
		image.setScreenSize(getScreenWidth(), getScreenHeight());
		image.setSize(0.7f);
		image.setOpacity(0);
		
		System.out.println("Songs Installed: " + getGameObject().getSongFiles().length);
	}

	@Override
	public void update()  {
		counter++;
		if(counter == 200) {
			image.fadeIn();
			
		}
		
		if(counter == 400) {
			message.fadeIn();
		}
		
		if(counter == 500) {
			image.fadeOut();
		}
		if(counter == 600) {
			moveScreen();
		}
		
		message.setScreenSize(getScreenWidth(), getScreenHeight());
		message.update();
		
		image.setScreenSize(getScreenWidth(), getScreenHeight());
		image.update();
		
		
		
	}

	@Override
	public void draw(Graphics context) {
		// Draw in the background color
		context.setColor(ColorPack.WHITE);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());
	
		image.draw(context);
		
		//message.draw(context);
	}


}
