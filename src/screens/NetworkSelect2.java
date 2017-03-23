package screens;

import java.awt.Graphics;

import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import sprites.BannerSprite;
import sprites.DotSpriteBackground;
import sprites.FancyCenterTextSprite;
import sprites.ImageGrad;
import sprites.ImageSprite;
import sprites.SystemBox;
import sprites.SystemTextCenterFade;
import utils.ColorPack;
import utils.ImageLoader;


/**
 * A Screen to select as Server or Client
 *
 */
public class NetworkSelect2 extends Screen {

	private DotSpriteBackground dotBackground;
	private ImageGrad imageGrad;
	private FancyCenterTextSprite title;
	private SystemTextCenterFade centex;
	private SystemTextCenterFade centex2;
	private SystemBox box;

	int count = 0;

//	private ImageSprite networkImage;
//	private ImageSprite networkImage2;
	private BannerSprite bannerSprite;
	

	@Override
	public void keyPressed(int key) {
		System.out.println("on" + key);
		if (key == InputHandler.PLAYKEY0) {
			setNextScreen(new NetworkSelect(getGameObject()));
			moveScreen();
		}
		if(key == InputHandler.PLAYKEY2) {
			getGameObject().setServer(true);
			getGameObject().setMode(new NetworkSelect(getGameObject()));
			setNextScreen(new SelectScreen(getGameObject()));
			moveScreen();
		} else if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute pressed");
		}
	}

	@Override
	public void keyReleased(int key) {
		if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute");
		}
		System.out.println("off" + key);
	}

	public NetworkSelect2(GameObject gameObject) {
		super(gameObject);

		bannerSprite = new BannerSprite(getScreenWidth() / 2, getScreenHeight() / 2 + 80);



		centex = new SystemTextCenterFade(getScreenWidth() / 2, getScreenHeight() / 2, " ");
		centex2 = new SystemTextCenterFade(getScreenWidth() / 2, getScreenHeight() / 2 + 20, " ");



		box = new SystemBox();
		box.setScreenSize(getScreenWidth(), getScreenHeight());

		imageGrad = new ImageGrad();
		imageGrad.setOpacity(1);

		dotBackground = new DotSpriteBackground(10, 10, 20, 30, false, getScreenWidth(), getScreenHeight());
		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());

		title = new FancyCenterTextSprite((int) (getScreenWidth() * 0.94), (int) (getScreenHeight() * 0.85), "NETWORK");
		title.setScreenSize(getScreenWidth(), getScreenHeight());

	}

	@Override
	public void update() {

		title.setScreenSize(getScreenWidth(), getScreenHeight());
		if (count > 40) {
			title.update();
		}

		bannerSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		bannerSprite.update();



		imageGrad.setScreenSize(getScreenWidth(), getScreenHeight());
		imageGrad.update();

		centex.setScreenSize(getScreenWidth(), getScreenHeight());
		centex.update();
		
		centex2.setScreenSize(getScreenWidth(), getScreenHeight());
		centex2.update();

		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());
		dotBackground.update();

		box.setScreenSize(getScreenWidth(), getScreenHeight());

		if (count > 180) {
			box.update();
		}


		

			centex.setText("Press [Q] to be Client. Press [E] to be Server.");


		count++;

	}

	@Override
	public void draw(Graphics context) {

		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());

		imageGrad.draw(context);

		dotBackground.draw(context);

		title.draw(context);
		if (count > 80) {
			box.draw(context);

		}

		if (count > 200) {

			centex.draw(context);
			centex2.draw(context);
		}


	}
	

}
