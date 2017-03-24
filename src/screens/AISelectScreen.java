package screens;

import java.awt.Graphics;

import audio.SoundHandler;
import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import sprites.DotSpriteBackground;
import sprites.ImageGrad;
import sprites.SystemTextCenterFloat;
import utils.ColorPack;

public class AISelectScreen extends Screen {
	

	private SoundHandler fx;
	String[] fxlist = { "move.wav", "bang.wav", "titlesongquiet.wav" };
	int level = 1;
	private SystemTextCenterFloat levelTextSprite;
	private SystemTextCenterFloat aiTextSprite;
	private SystemTextCenterFloat diffTextSprite;
	private String diffText;
	private DotSpriteBackground dotBackground;
	private ImageGrad imageGrad;

	@Override
	public void keyPressed(int key) {
		System.out.println("on" + key);
	
		if (key == InputHandler.PLAYKEY0) {
			fx.playEffect("bang.wav");
			getGameObject().setAiLevelText(diffText);
			getGameObject().setAiLevel(10 - level);
			moveScreen();
		}
		
		if (key == InputHandler.PLAYKEY3) {
			if (level == 10) {
				level = 1;
			} else {
				level++;
			}
			fx.playEffect("move.wav");
		}

		if (key == InputHandler.PLAYKEY2) {
			if (level == 1) {
				level = 10;
			} else {
				level--;
			}
			fx.playEffect("move.wav");
		} else if (key == InputHandler.MUTEKEY) {
			getGameObject().setMute();
			System.out.println(getGameObject().isMute());
		}
	}

	@Override
	public void keyReleased(int key) {
		System.out.println("off" + key);
	}

	public AISelectScreen(GameObject gameObject) {
		super(gameObject);
		setNextScreen(new AIPlayScreen(gameObject));
		fx = new SoundHandler(gameObject);
		fx.fillEffects(fxlist);

		aiTextSprite = new SystemTextCenterFloat((int) (getScreenWidth() * 0.5), (int) (getScreenHeight() * 0.5) - 50, "AI LEVEL");
		levelTextSprite = new SystemTextCenterFloat((int) (getScreenWidth() * 0.5), (int) (getScreenHeight() * 0.5), "" + level);
		diffTextSprite = new SystemTextCenterFloat((int) (getScreenWidth() * 0.5), (int) (getScreenHeight() * 0.5) + 50, "(EASY)");
		
		imageGrad = new ImageGrad();
		imageGrad.setOpacity(1);

		dotBackground = new DotSpriteBackground(10, 10, 20, 30, false, getScreenWidth(), getScreenHeight());
		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());
		
	}

	@Override
	public void update() {
		if(getGameObject().isMute()) {
			fx.stopAll();
		}
		levelTextSprite.setText("" + level);
		levelTextSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		levelTextSprite.update();
		
		aiTextSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		aiTextSprite.update();
		
		if (level < 4) {
			diffText = "EASY";
		} else if (level < 7) {
			diffText = "INTERMEDIATE";
		} else if (level < 10) {
			diffText = "HARD";
		} else {
			diffText = "INSANE";
		}
		
		diffTextSprite.setText("(" + diffText + ")");
		diffTextSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		diffTextSprite.update();
		
		imageGrad.setScreenSize(getScreenWidth(), getScreenHeight());
		imageGrad.update();

		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());
		dotBackground.update();
	}

	@Override
	public void draw(Graphics context) {
		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());

		imageGrad.draw(context);

		dotBackground.draw(context);
		
		aiTextSprite.draw(context);
		
		levelTextSprite.draw(context);
		
		diffTextSprite.draw(context);
	}
}
