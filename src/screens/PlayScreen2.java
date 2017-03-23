package screens;

import java.awt.Color;
import java.awt.Graphics;

import engine.GameObject;
import engine.Screen;
import sprites.ImageSprite;
import sprites.ImageSprite2;
import sprites.SystemText;
import utils.ImageLoader;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class PlayScreen2 extends Screen {
	SystemText text = new SystemText(40, 30, "Hello");
	ImageSprite2 outline = new ImageSprite2(getScreenWidth() / 2, 40, ImageLoader.loadImageFromResource("src/res/images/outline.png"));
	ImageSprite2 outlineInner = new ImageSprite2(getScreenWidth() / 2, 40, ImageLoader.loadImageFromResource("src/res/images/outline_inner.png"));
	ImageSprite2 outlineProgress = new ImageSprite2(getScreenWidth() / 2, 40, ImageLoader.loadImageFromResource("src/res/images/outline_progress.png"));
	
	ImageSprite2 keys[] = new ImageSprite2[4];
	ImageSprite2 keysOver[] = new ImageSprite2[4];
	
	int count = 0;
	
	@Override
	public void keyPressed(int key) {
		

	}
	
	@Override
	public void keyReleased(int key) {
		
	}
	
	public PlayScreen2(GameObject gameObject) {
		super(gameObject);
		keys[0] = new ImageSprite2((int)(getScreenWidth() * 0.2), getScreenHeight() - 120, ImageLoader.loadImageFromResource("src/res/images/q.png"));
		keys[1] = new ImageSprite2((int)(getScreenWidth() * 0.4), getScreenHeight() - 120, ImageLoader.loadImageFromResource("src/res/images/w.png"));
		keys[2] = new ImageSprite2((int)(getScreenWidth() * 0.6), getScreenHeight() - 120, ImageLoader.loadImageFromResource("src/res/images/e.png"));
		keys[3] = new ImageSprite2((int)(getScreenWidth() * 0.8), getScreenHeight() - 120, ImageLoader.loadImageFromResource("src/res/images/r.png"));
	
		for(int i = 0 ; i < keysOver.length ; i++) {
			keysOver[i] = new ImageSprite2((int)(getScreenWidth() * (0.2 * (i + 1))), getScreenHeight() - 120, ImageLoader.loadImageFromResource("src/res/images/beatOutline.png"));
			
		}
	}
	
	@Override
	public void update() {
			count++;
			
			text.setScreenSize(getScreenWidth(), getScreenHeight());
			text.update();
			
			outline.setScreenSize(getScreenWidth(), getScreenHeight());
			outline.update();
			
			for(int i = 0 ; i < keys.length ; i++) {
				keys[i].setScreenSize(getScreenWidth(), getScreenHeight());
				keys[i].update();
				
				keysOver[i].setScreenSize(getScreenWidth(), getScreenHeight());
				keysOver[i].update();
			}
			
			if(count == 159) {
				keysOver[0].impress();
			}
			
			if(count == 169) {
				keysOver[1].impress();
			}
			
			if(count == 179) {
				keysOver[2].impress();
			}
			
			if(count == 189) {
				keysOver[3].impress();
			}
	}
	
	@Override
	public void draw(Graphics context) {
		context.setColor(new Color(56, 60, 63));
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());
		
		// Draw top bar
		context.setColor(new Color(0, 0, 0, 128));
		context.fillRect(0, 0, getScreenWidth(), 80);
		
		// Draw bottom bar
		context.setColor(new Color(0, 0, 0, 128));
		context.fillRect(0, getScreenHeight() - 80, getScreenWidth(), 80);
		
		// Draw the text
		text.draw(context);
		
		// Draw the outline
		outlineInner.draw(context);
		outlineProgress.draw(context);
		outline.draw(context);
		
		// Draw Keys
		for(int i = 0 ; i < keys.length ; i++) {
			keys[i].draw(context);
			keysOver[i].draw(context);
		}
	}
}
