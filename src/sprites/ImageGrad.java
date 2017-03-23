package sprites;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import engine.Sprite;
import utils.ImageLoader;

public class ImageGrad extends Sprite {
	private Image image;
	private boolean impress = false;
	private float size = 1;
	private float opacity = 1f;
	private boolean fadeIn = false;
	private boolean fadeOut = false;
	private float famount = 0.8f;
	
	public ImageGrad(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public ImageGrad(int x, int y, Image image) {
		super(x, y, image.getWidth(null), image.getHeight(null));
		this.image = image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	
	public ImageGrad() {
		super(0, 0, 0, 0);
		this.image = ImageLoader.loadImageFromResource("src/res/images/gradient.png");
	}

	@Override
	public void update() {
		if(impress) {
			size += Math.max(0.1, (int)(size * 0.6));;
		}
		
		if(fadeOut) {
			opacity = Math.max(0, opacity * famount);
			if(opacity == 0) {
				fadeOut = false;
			}
		}
		
		if(fadeIn) {
			opacity = Math.min(1, Math.max(0.1f, opacity) * 1.2f);
			if(opacity == 1) {
				fadeIn = false;
			}
		}
		
	}
	
	public void setOpacity(float opac) {
		this.opacity = opac;
	}
	
	public float getOpacity() {
		return opacity;
	}

	public void impress() {
		impress = true;
		fadeOut = true;
	}
	
	public void fadeIn() {
		fadeIn = true;
		fadeOut = false;
	}
	
	public void fadeOut() {
		fadeOut = true;
		fadeIn = false;
	}
	
	public void fadeOutQuick() {
		this.famount = 0f;
		fadeOut = true;
		fadeIn = false;
	}
	
	public void setSize(float size) {
		this.size = size;
	}

	@Override
	public void draw(Graphics context) {

		((Graphics2D) context).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

		context.drawImage(image, 0, (int)(getScreenHeight() * 0.7), getScreenWidth(), (int)(getScreenHeight() * 0.3), null);
		((Graphics2D) context).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}

}
