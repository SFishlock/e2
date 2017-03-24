package screens;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import audio.Player;
import audio.SoundHandler;
import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import songmanager.SongFile;
import songmanager.SongFileProcessor;
import sprites.DotSpriteBackground;
import sprites.FancyCenterTextSprite;
import sprites.ImageGrad;
import sprites.ImageSprite;
import sprites.SystemTextCenterShine;
import sprites.SystemTextKern;
import sprites.SystemTextShine;
import utils.ColorPack;
import utils.ImageLoader;

/**
 * 
 * @author Bobby Dilley
 *
 */
public class SelectScreen extends Screen {

	private ImageSprite image;
	private SystemTextKern textSprite;
	private DotSpriteBackground dotBackground;
	private ImageGrad imageGrad;
	private FancyCenterTextSprite title;

	private SystemTextCenterShine typeText;
	private SystemTextCenterShine levelText;
	private SystemTextCenterShine numberText;
	private SystemTextShine nameText;
	private SystemTextShine byText;
	private SoundHandler fx;
	String[] fxlist = { "bang.wav", "move.wav" };
	private int count = 0;

	private Player audio = new Player(getGameObject());

	private SongFileProcessor reader = new SongFileProcessor();

	private BufferedImage backImage;

	private int currentSelector = 0;

	private SongFile songFile;

	public void keyPressed(int key) {
		System.out.println("on" + key);
		if (key == InputHandler.PLAYKEY0) {
			getGameObject().setSongFile(songFile);
			fx.playEffect("bang.wav");
			audio.stopPlaying();
			setNextScreen(getGameObject().getMode());
			moveScreen();
		}

		if (key == InputHandler.PLAYKEY2) {
			if (currentSelector == 0) {
				currentSelector = getGameObject().getSongFiles().length - 1;
			} else {
				currentSelector--;
			}
			pageUpdate();

		}

		if (key == InputHandler.PLAYKEY3) {
			if (currentSelector == getGameObject().getSongFiles().length - 1) {
				currentSelector = 0;
			} else {
				currentSelector++;
			}
			pageUpdate();
		} else if (key == InputHandler.MUTEKEY) {
			getGameObject().setMute();
			System.out.println(getGameObject().isMute());
		}
	}
	
	@Override
	public void keyReleased(int key) {
		if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute unpressed");
		}
	}

	public SelectScreen(GameObject gameObject) {
		super(gameObject);
		fx = new SoundHandler(getGameObject());

		fx.fillEffects(fxlist);

		setNextScreen(new PlayScreen(getGameObject()));

		nameText = new SystemTextShine(78, 430, "Tetris Theme Tune");
		nameText.setFontSize(0.032);

		byText = new SystemTextShine(78, 454, "Robert Dilley");
		byText.setFontSize(0.022);

		typeText = new SystemTextCenterShine(getScreenWidth() - 142, 280, "EXTREME");
		levelText = new SystemTextCenterShine(getScreenWidth() - 142, 120, "LEVEL");
		numberText = new SystemTextCenterShine(getScreenWidth() - 142, 190, "4");
		levelText.setFontSize(0.032);
		numberText.setFontSize(0.2);
		typeText.shine();

		imageGrad = new ImageGrad();
		imageGrad.setOpacity(1);

		dotBackground = new DotSpriteBackground(10, 10, 20, 30, false, getScreenWidth(), getScreenHeight());
		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());

		title = new FancyCenterTextSprite((int) (getScreenWidth() * 0.94), (int) (getScreenHeight() * 0.85),
				"SELECT MUSIC");
		title.setScreenSize(getScreenWidth(), getScreenHeight());

		textSprite = new SystemTextKern(20, 30, "FIRST SONG");

		backImage = gameObject.getSongFiles()[currentSelector].getCoverArt();

		image = new ImageSprite(getScreenWidth() / 2, (int) (getScreenHeight() * 0.4), backImage);
		image.setScreenSize(getScreenWidth(), getScreenHeight());
		image.setSize(0.5f);

	}

	public void pageUpdate() {
		songFile = getGameObject().getSongFiles()[currentSelector];
		getGameObject().setCurrentSelect(currentSelector);
		backImage = songFile.getCoverArt();
		image.setImage(backImage);
		System.out.println("UPDATE CALLED");

		fx.stopAll();

		audio.stopPlaying();

		nameText.setText(songFile.getSong().getTitle());
		byText.setText(songFile.getSong().getArtist());
		int difficulty = Math.round(songFile.getSong().getNotes().length / 100);
		if (difficulty == 0) {
			difficulty = 1;
		}
		numberText.setText("" + difficulty);
		String text;
		if (difficulty <= 1) {
			text = "EASY";
		} else if (difficulty == 2) {
			text = "MEDIUM";
		} else if (difficulty == 3) {
			text = "HARD";
		} else {
			text = "EXTREME";
		}
		typeText.setText(text);
		audio.playBack(songFile.getAudioInputPath());
	}

	@Override
	public void update() {
		if(getGameObject().isMute()) {
			fx.stopAll();
		}
		typeText.setScreenSize(getScreenWidth(), getScreenHeight());
		levelText.setScreenSize(getScreenWidth(), getScreenHeight());
		numberText.setScreenSize(getScreenWidth(), getScreenHeight());
		nameText.setScreenSize(getScreenWidth(), getScreenHeight());
		byText.setScreenSize(getScreenWidth(), getScreenHeight());

		title.setScreenSize(getScreenWidth(), getScreenHeight());
		title.update();

		imageGrad.setScreenSize(getScreenWidth(), getScreenHeight());
		imageGrad.update();

		textSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		textSprite.update();

		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());
		dotBackground.update();

		if (count == 50) {
			pageUpdate();

		}

		image.setScreenSize(getScreenWidth(), getScreenHeight());
		image.update();

		count++;
	}

	@Override
	public void draw(Graphics context) {
		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());
		imageGrad.draw(context);
		dotBackground.draw(context);

		context.setColor(ColorPack.WHITE);
		context.drawLine(0, 48, 199, 48);

		// Draw the box
		context.setColor(ColorPack.FADEDWHITE);
		int pwidth = 320;
		int pheight = 240;
		double scale = 1.4;
		pwidth = (int) (pwidth * scale);
		pheight = (int) (pheight * scale);
		context.fillRect(getScreenWidth() / 2 - pwidth / 2 - 100, getScreenHeight() / 2 - pheight / 2 - 50, pwidth,
				pheight);

		image.setX(getScreenWidth() / 2 - 100);
		image.setY(getScreenHeight() / 2 - 50);

		typeText.draw(context);
		levelText.draw(context);
		numberText.draw(context);
		nameText.draw(context);
		byText.draw(context);

		textSprite.draw(context);

		title.draw(context);

		image.draw(context);
	}

}
