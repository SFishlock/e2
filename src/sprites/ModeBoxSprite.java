package sprites;

import java.awt.Color;
import java.awt.Graphics;

import engine.Sprite;
import utils.ColorPack;

public class ModeBoxSprite extends Sprite {
	static int sizex = 230;
	static int sizey = 180;
	private double scale = 1;
	private double targetScale = scale;
	static int count = 0;
	static int max = 90;
	static int trans = 0;
	static int version = 0;
	public static final int SINGLE = 0;
	public static final int COMPUTER = 1;
	public static final int NETWORK = 2;
	private boolean selected = false;
	private SystemTextShine title;
	
	public ModeBoxSprite(int x, int y, int version) {
		super(x, y, sizex, sizey);
		this.version = version;
		
		System.out.println(version);
		
		
		if(version == 0) {
			title = new SystemTextShine((int)(getX() - sizex/2 + 105 - 20), (int)(getY() + sizey/2 - 30 - 30), "SINGLE");
			title.setFontSize(0.03);
			setY(getY() - 30);
			setX(getX() - 20);
		}
		
		if(version == 1) {
			title = new SystemTextShine((int)(getX() - sizex/2 + 53), (int)(getY() + sizey/2 - 30), "COMPUTER");
			title.setFontSize(0.03);
		}
		
		if(version == 2) {
			title = new SystemTextShine((int)(getX() - sizex/2 + 73 + 20), (int)(getY() + sizey/2 - 30 + 30), "NETWORK");
			title.setFontSize(0.03);
			setY(getY() + 30);
			setX(getX() + 20);
		}
		
		title.setScreenSize(getScreenWidth(), getScreenHeight());
	}

	public void select() {
		selected = true;
		targetScale = 1.2;
	}
	
	public void unselect() {
		selected = false;
		targetScale = 0.8;
	}

	
	
	@Override
	public void update() {
		//title.setX((int)(getScreenWidth() / 2));
		//title.setY((int)(getScreenHeight() / 2));
		title.setScreenSize(getScreenWidth(), getScreenHeight());
		title.update();
		
		if(count != max) {
			count++;
		}
		trans = (int) (255 * Math.sin(Math.toRadians(count)));
		
		if(scale != targetScale) {
			if(scale < targetScale) {
				scale += Math.abs(scale - targetScale) / 4;
			} else {
				scale -= Math.abs(scale - targetScale) / 4;
			}
		}
		
	}

	
	@Override
	public void draw(Graphics context) {
		context.setColor(new Color(ColorPack.FADEDWHITE.getRed(), ColorPack.FADEDWHITE.getRed(), ColorPack.FADEDWHITE.getRed(), Math.max(0, (int)(trans*scale) - 160)));
		
		context.fillRect(getX() - (int)(sizex*scale / 2), getY() - (int)(sizey*scale / 2), (int)(sizex*scale), (int)(sizey*scale));
		if(count == max) {
			title.draw(context);
		}
		
	}

}
