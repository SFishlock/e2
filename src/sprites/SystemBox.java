package sprites;

import java.awt.Color;
import java.awt.Graphics;

import engine.Sprite;

public class SystemBox extends Sprite {
	int count = 170;
	boolean run = true;
	
	
	
	public SystemBox() {
		super(0, 0, 0, 0);
		
	}


	@Override
	public void update() {
		if(run) {
			count-=(count/10);
			if(count == 0) {
				run = false;
			}
		}
		
	}



	@Override
	public void draw(Graphics context) {
		context.setColor(new Color(255, 255, 255, 170 - count));
		context.fillRect((int)(getScreenWidth() * 0.02), (int)(getScreenHeight() * 0.04), (int)(getScreenWidth() * 0.96), (int)(getScreenHeight() * 0.92));
	}

}
