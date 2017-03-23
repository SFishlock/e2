package sprites;

import java.awt.Graphics;

import engine.Sprite;
import utils.ColorPack;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class BarSprite extends Sprite {
	int count = 0;

	int size = 4 * (20 + 60) - 20;
	
	
	public BarSprite(int x, int y, int width, int height) {
		super(x, y, width, height);

	}

	@Override
	public void update() {
		count++;
	}

	@Override
	public void draw(Graphics context) {
		context.setColor(ColorPack.FADEDWHITE);
		context.drawRect(getX() - (int)(size / 2), getY(), size, 1);	
	}

}
