package sprites;

import java.awt.Graphics;
import java.util.Random;

import engine.Sprite;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class DotSpriteBackground extends Sprite {
	private DotSprite[] dotSprite;
	int amount = 100;
	private Random random;
	
	public DotSpriteBackground(int x, int y, int width, int height, boolean bottom, int scrwidth, int scrheight) {
		super(x, y, width, height);
		dotSprite = new DotSprite[amount];
		random = new Random();
		
		for (int i = 0; i < amount; i++) {
			
			dotSprite[i] = new DotSprite(10, 20, 10, 10, bottom, scrwidth, scrheight);
			dotSprite[i].setScreenSize(getScreenWidth(), getScreenHeight());
		}
	}

	@Override
	public void update() {
		for (int i = 0; i < amount; i++) {
			dotSprite[i].setScreenSize(getScreenWidth(), getScreenHeight());
			dotSprite[i].update();
		}
	}

	@Override
	public void draw(Graphics context) {
		for (int i = 0; i < amount; i++) {
			dotSprite[i].draw(context);
		}
		
		
	}

}
