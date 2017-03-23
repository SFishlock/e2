package sprites;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import engine.Sprite;
import utils.ColorPack;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class BannerSprite extends Sprite {
	private GradientPaint first;
	private GradientPaint second;
	private SystemTextCenterShine resultText;

	public BannerSprite(int x, int y) {
		super(x, y, 600, 80);

		second = new GradientPaint(getX(), getY(), ColorPack.DARK, getX() + getWidth() / 2, getY(),
				ColorPack.WHITETRANS);

		first = new GradientPaint(getX() - getWidth() / 2, getY(), ColorPack.WHITETRANS, getX(), getY(),
				ColorPack.DARK);

		resultText = new SystemTextCenterShine(x, y + getHeight() / 2, "NETWORK CONNECTED!");
		resultText.setFontSize(0.06);
		resultText.shine();
		// resultText.removeBorder();

	}

	@Override
	public void update() {
		resultText.setScreenSize(getScreenWidth(), getScreenHeight());
	}

	@Override
	public void draw(Graphics context) {
		Graphics2D graphics = (Graphics2D) context;

		// First side of it
		graphics.setColor(ColorPack.PRIMARY3);
		graphics.setPaint(first);
		graphics.fillRect(getX() - (getWidth() / 2), getY(), getWidth() / 2, getHeight());

		// Second side of it
		graphics.setColor(ColorPack.PRIMARY);
		graphics.setPaint(second);
		graphics.fillRect(getX(), getY(), getWidth() / 2, getHeight());

		resultText.draw(context);

	}

}
