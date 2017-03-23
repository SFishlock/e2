package sprites;

import java.awt.Graphics;

import engine.Sprite;
import utils.ColorPack;

public class PlaySprite extends Sprite {
	int count = 0;

	int amount = 4;
	int gap = 20;
	int size = 60;
	int[] push = new int[amount];
	double position;

	public PlaySprite(int x, int y, int width, int height, double d) {
		super(x, y, width, height);
		for (int i = 0; i < push.length; i++) {
			push[i] = 0;
		}
		this.position = d;
	}

	@Override
	public void update() {
		setX((int) (getScreenWidth() * position));
		setY((int) (getScreenHeight() * 0.8));
		setWidth(getBlockSizeAndGap() * amount);
		count++;
	}

	public void push(int pushed) {
		push[pushed] = 1;
	}

	public void unpush(int pushed) {
		push[pushed] = 0;
	}
	
	public int getBlockSizeAndGap() {
		return size + gap;
	}
	
	public int getBlockSize() {
		return size;
	}
	
	@Override
	public void draw(Graphics context) {
		context.setColor(ColorPack.WHITE);
		for (int i = 0; i < amount; i++) {
			if (push[i] == 1) {
				context.drawRect(getX() + (gap / 2) + (i * getBlockSizeAndGap()) - (int) (getWidth() / 2), getY() + 5, size,
						60);
			} else {
				context.drawRect(getX() + (gap / 2) + (i * getBlockSizeAndGap()) - (int) (getWidth() / 2), getY(), size, 60);
			}

			context.drawLine(getX() + (gap / 2) + (i * getBlockSizeAndGap()) + (int) (size / 2) - (int) (getWidth() / 2),
					getY(), getX() + (gap / 2) + (i * getBlockSizeAndGap()) + (int) (size / 2) - (int) (getWidth() / 2), 0);
		}
	}
}