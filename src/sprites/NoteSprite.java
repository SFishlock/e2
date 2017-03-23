package sprites;

import java.awt.Color;
import java.awt.Graphics;

import engine.Sprite;
import utils.ColorPack;

public class NoteSprite extends Sprite {
	int count = 0;
	
	int amount = 4;
	int gap = 20;
	int size = 60;
	int x = 0;
	int v = 0;
	int this_width = 0;
	boolean buttons[];
	int length = 10;
	boolean hit = false;
	int ai = 0;
	double position;
	private boolean isRemoved = false;
	private int opac = 255;
	
	public NoteSprite(int x, int y, int width, int height, boolean[] buttons, int length, double position, double speed) {
		super(x, y, width, height);
		this.buttons = buttons;
		//if(length != 0 && ((length * speed) > 60)) {
		//	this.length = (int) (length * speed);
		//} else {
			this.length = 60;
		//}
		this.position = position;
	}
	
	public int getLength() {
		return length;
	}

	@Override
	public void update() {
		if(count == 0) {
			x = (int)(getScreenWidth() * position);
		}
		
		this_width = (size + gap) * amount;
		
		count++;
		
		if(isRemoved && opac > 0) {
			opac-=1 + Math.abs(opac / 8);
		}
		
		setX(x);
		setWidth(size);
		setHeight(length);
	}
	
	public void hit() {
		hit = true;
	}
	
	public void setAI() {
		ai = 1;
	}
	
	public void remove() {
		isRemoved = true;
	}
	
	public boolean isRemoved() {
		return isRemoved;
	}

	@Override
	public void draw(Graphics context) {
		if(ai == 1) {
			context.setColor(new Color(255, 0, 0, 90));
		} else {
			context.setColor(new Color(ColorPack.WHITE.getRed(), ColorPack.WHITE.getGreen(), ColorPack.WHITE.getBlue(), opac));
		}
		if(isRemoved() && this.length == 60) {
			
		} else {
			for(int i = 0 ; i < buttons.length ; i++) {
				if (buttons[i]) context.fillRect(x + (gap / 2) + (i * (size + gap)) - (int)(this_width / 2), getY() - length, size, length);
			}
		}
		
	}
}
