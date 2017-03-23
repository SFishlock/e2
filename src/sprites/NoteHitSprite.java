package sprites;

import java.awt.Color;
import java.awt.Graphics;

import engine.Sprite;

public class NoteHitSprite extends Sprite {
	int opac = 255;
	int opacTarget = 0;
	boolean rem = false;
	int maxWidth = (int) (getWidth() * 1.75);
	int maxHeight = (int) (getHeight() * 1.75);

	public NoteHitSprite(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void update() {
		if(opac > 5) {
			setWidth(getWidth() + 1 + Math.abs(getWidth() - maxWidth) / 3);
			setHeight(getHeight() + 1 + Math.abs(getHeight() - maxHeight) / 3);
			opac -= 1 + Math.abs(opac - opacTarget) / 3;
			opac = Math.max(0, opac); // making sure opacity is not negative
		} else {
			rem = true;
		}
		
	}
	
	public boolean shouldRemove() {
		return rem;
	}

	@Override
	public void draw(Graphics context) {
		if(opac > 5) {
			context.setColor(new Color(255, 255, 255, opac));
			context.fillRect(getX() - (getWidth()/2), getY() - (getHeight()/2), getWidth(), getHeight());
		}
	
	}
}
