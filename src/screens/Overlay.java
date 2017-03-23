package screens;

import java.awt.Graphics;

import engine.GameObject;
import engine.Screen;
import sprites.SystemTextCenterFade;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class Overlay extends Screen {
	private SystemTextCenterFade middleBottom;

	public Overlay(GameObject gameObject) {
		super(gameObject);
		middleBottom = new SystemTextCenterFade(getScreenWidth() / 2, (int) (getScreenHeight() * 0.95), "PRESS START");

	}

	@Override
	public void update() {
		middleBottom.setScreenSize(getScreenWidth(), getScreenHeight());
		middleBottom.update();

	}

	@Override
	public void draw(Graphics context) {
		middleBottom.draw(context);
	}
	
	public SystemTextCenterFade getMiddleBottom() {
		return middleBottom;
	}

}
