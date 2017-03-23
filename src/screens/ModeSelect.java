package screens;

import java.awt.Graphics;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import audio.SoundHandler;
import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import sprites.DotSpriteBackground;
import sprites.FancyCenterTextSprite;
import sprites.ImageGrad;
import sprites.ModeBoxSprite;
import sprites.SystemBox;
import sprites.SystemText;
import sprites.SystemTextCenterShine;
import utils.ColorPack;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class ModeSelect extends Screen {

	private SystemText textSprite;
	private SystemTextCenterShine singleModeText;
	private DotSpriteBackground dotBackground;
	private ImageGrad imageGrad;
	private FancyCenterTextSprite title;
	private ModeBoxSprite boxSpriteSingle;
	private ModeBoxSprite boxSpriteAI;
	private ModeBoxSprite boxSpriteNetwork;
	private ModeBoxSprite currentBox = null;
	private ModeBoxSprite nextBox = null;
	private SystemBox box;

	private SoundHandler fx;
	String[] fxlist = { "move.wav", "bang.wav", "titlesongquiet.wav" };

	int count = 0;

	int bx = 0;
	int by = 0;

	int select = 0;

	@Override
	public void keyPressed(int key) {
		System.out.println("on" + key);
		if (key == InputHandler.PLAYKEY0) {
			fx.playEffect("bang.wav");
			if (select == 0) {
				getGameObject().setMode(new PlayScreen(getGameObject()));
			} else if (select == 1) {
				getGameObject().setMode(new AISelectScreen(getGameObject()));
			} else if (select == 2) {
				setNextScreen(new NetworkSelect2(getGameObject()));
				moveScreen();
				//getGameObject().setMode(new NetworkSelect2(getGameObject()));
			}
			moveScreen();
		}

		if (key == InputHandler.PLAYKEY3) {
			if (select == 2) {
				select = 0;
			} else {
				select++;
			}
			fx.playEffect("move.wav");
		}

		if (key == InputHandler.PLAYKEY2) {
			if (select == 0) {
				select = 2;
			} else {
				select--;
			}
			fx.playEffect("move.wav");
		} else if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute pressed");
		}
	}

	@Override
	public void keyReleased(int key) {
		if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute unpressed");
		}
		System.out.println("off" + key);
	}

	public ModeSelect(GameObject gameObject) {
		super(gameObject);
		fx = new SoundHandler();
		fx.fillEffects(fxlist);

		textSprite = new SystemText(10, 10, "HELLO");
		singleModeText = new SystemTextCenterShine((int) (getScreenWidth() * 0.5), getScreenHeight() / 2,
				"Single Player");

		try {
			textSprite.setText("ID:" + Inet4Address.getLocalHost().getHostAddress().replaceAll("\\.", ":"));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setNextScreen(new SelectScreen(gameObject));

		boxSpriteSingle = new ModeBoxSprite((int) (getScreenWidth() * 0.2), getScreenHeight() / 2,
				ModeBoxSprite.SINGLE);
		boxSpriteAI = new ModeBoxSprite((int) (getScreenWidth() * 0.5), getScreenHeight() / 2, ModeBoxSprite.COMPUTER);
		boxSpriteNetwork = new ModeBoxSprite((int) (getScreenWidth() * 0.8), getScreenHeight() / 2,
				ModeBoxSprite.NETWORK);

		box = new SystemBox();
		box.setScreenSize(getScreenWidth(), getScreenHeight());

		imageGrad = new ImageGrad();
		imageGrad.setOpacity(1);

		dotBackground = new DotSpriteBackground(10, 10, 20, 30, false, getScreenWidth(), getScreenHeight());
		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());

		title = new FancyCenterTextSprite((int) (getScreenWidth() * 0.94), (int) (getScreenHeight() * 0.85),
				"SELECT MODE");
		title.setScreenSize(getScreenWidth(), getScreenHeight());

		currentBox = boxSpriteSingle;
		nextBox = currentBox;
	}

	@Override
	public void update() {

		title.setScreenSize(getScreenWidth(), getScreenHeight());
		if (count > 40) {
			title.update();
		}

		if (count == 80) {
			fx.playEffect("titlesongquiet.wav", true);
		}

		imageGrad.setScreenSize(getScreenWidth(), getScreenHeight());
		imageGrad.update();

		singleModeText.setScreenSize(getScreenWidth(), getScreenHeight());
		singleModeText.update();

		boxSpriteSingle.setScreenSize(getScreenWidth(), getScreenHeight());
		boxSpriteAI.setScreenSize(getScreenWidth(), getScreenHeight());
		boxSpriteNetwork.setScreenSize(getScreenWidth(), getScreenHeight());

		textSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		textSprite.update();

		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());
		dotBackground.update();

		box.setScreenSize(getScreenWidth(), getScreenHeight());

		if (count > 180) {
			box.update();
		}

		if (count > 180) {
			boxSpriteAI.update();
			boxSpriteSingle.update();
			boxSpriteNetwork.update();
		}

		if (select == 0) {
			nextBox = boxSpriteSingle;
			boxSpriteSingle.select();
			boxSpriteAI.unselect();
			boxSpriteNetwork.unselect();
		}

		if (select == 1) {
			nextBox = boxSpriteAI;
			boxSpriteSingle.unselect();
			boxSpriteAI.select();
			boxSpriteNetwork.unselect();
		}

		if (select == 2) {
			nextBox = boxSpriteNetwork;
			boxSpriteSingle.unselect();
			boxSpriteAI.unselect();
			boxSpriteNetwork.select();

		}

		count++;

		if (bx != nextBox.getX()) {
			if (bx < nextBox.getX()) {
				bx += 1 + Math.abs(bx - nextBox.getX()) / 3;
			} else {
				bx -= 1 + Math.abs(bx - nextBox.getX()) / 3;
			}
		}

		if (by != nextBox.getY()) {
			if (by < nextBox.getY()) {
				by += 1 + Math.abs(by - nextBox.getY()) / 3;
			} else {
				by -= 1 + Math.abs(by - nextBox.getY()) / 3;
			}
		}

	}

	@Override
	public void draw(Graphics context) {

		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());

		imageGrad.draw(context);

		dotBackground.draw(context);

		textSprite.draw(context);

		boxSpriteSingle.draw(context);
		boxSpriteAI.draw(context);
		boxSpriteNetwork.draw(context);
		// singleModeText.draw(context);

		title.draw(context);
		if (count > 80) {
			// box.draw(context);
		}

		context.setColor(ColorPack.WHITE);

		if (count > 180) {
			context.drawRect(bx - (currentBox.getWidth() / 2), by - (currentBox.getHeight() / 2), currentBox.getWidth(),
					currentBox.getHeight());
		}

	}

}
