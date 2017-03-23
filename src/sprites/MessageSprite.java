package sprites;

import java.awt.Color;
import java.awt.Graphics;

import engine.Sprite;

public class MessageSprite extends Sprite {
	private FadeCenterTextSprite text;
	int count = 0;
	static final int myheight = 40;
	int opac = 0;
	boolean fadein = false;
	boolean black = true;
	
	
	public MessageSprite(int x, int y, String textin) {
		super(x, y, -1, myheight);
		text = new FadeCenterTextSprite(-1, -1, textin);
		text.setY(y);
		
	}

	@Override
	public void update() {
		if(getWidth() == -1) {
			setWidth(getScreenWidth());
			text.setX(getScreenWidth() / 2);
		}
		
		if(fadein && opac < 150) {
			opac+=5;
		}
		
		text.setScreenSize(getScreenWidth(), getScreenHeight());
		
		if(opac >= 150) {
			text.update();
		}
		
		
		count++;
	}
	
	public void white() {
		black = false;
	}

	@Override
	public void draw(Graphics context) {
		if(black == true) {
			context.setColor(new Color(0, 0, 0, opac));
			
		} else {
			context.setColor(new Color(200, 200, 200, opac));
			
		}
		
		context.fillRect(0, getY() - (myheight / 2), getScreenWidth(), myheight);	
		
	
		text.draw(context);
		
		
	}
	
	public void fadeIn() {
		fadein = true;
	}

}
