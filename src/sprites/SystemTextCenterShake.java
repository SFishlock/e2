package sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import utils.ColorPack;
import utils.FontLoader;

public class SystemTextCenterShake extends SystemTextCenterFloat {
	boolean shine = false;
	boolean removeBorder = false;
	int opac = 255;
	boolean rem = false;
	int run = 0;
	
	public SystemTextCenterShake(int x, int y, String text) {
		super(x, y, text);
		setFontSize(0.047);
		setFont(FontLoader.loadFontFromResource("OpenSans-Regular.ttf"));
		
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(TextAttribute.TRACKING, 0.2);
		setFont(getFont().deriveFont(attributes));
	}

	@Override
	public void update() {
		if(opac != 0) {
			if(run == 0) {
				setY(getY() - 1);
			} if(run == 1) {
				setY(getY() + 1);
			} if(run == 3) {
				setY(getY() + 1);
			} if(run == 4) {
				setY(getY() - 1);
				run = 0;
			}
			
			opac-= 5;
		} else {
			rem = true;
		}
	}
	
	public boolean shouldRemove() {
		return rem;
	}

	public void shine() {
		shine = true;
	}
	
	public void removeBorder() {
		removeBorder = true;
	}
	
	


	@Override
	public void draw(Graphics context) {
		// Create the fontSize from the size of the screen
		float dynamicFontSize = (float) (getScreenHeight() * getFontSize());

		// Create a Graphics2D Object which allows us to set anti aliasing
		Graphics2D textGraphics = (Graphics2D) context.create();

		// Set the anti aliasing
		textGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		

		// Make the final font object with the correct font size
		Font finalFont = getFont().deriveFont(dynamicFontSize);

		// Set the font
		textGraphics.setFont(finalFont);

		// Work out the bounds of the text
		TextLayout optTL = new TextLayout(getText(), finalFont, textGraphics.getFontRenderContext());
		Rectangle2D bounds = optTL.getBounds();
	
		setWidth((int) bounds.getWidth());
		setHeight((int) bounds.getHeight());
		
		if(!removeBorder) {
			// Draw outline
			textGraphics.setColor(new Color(ColorPack.BLACK.getRed(), ColorPack.BLACK.getGreen(), ColorPack.BLACK.getBlue(), opac));
			
			
			int out = 1;
			for(int i = -out ; i <= out ; i++) {
				for(int j = -out ; j <= out ; j++) {
					textGraphics.drawString(getText(), getX() + i - (getWidth() / 2), (int) (getY()  + j + bounds.getHeight()) - (getHeight() / 2));
				}
			}
		}
		
	
		textGraphics.setColor(ColorPack.WHITE);
		
		if (shine) {
			GradientPaint gp = new GradientPaint(getX(), getY(), new Color(ColorPack.BROWN.getRed(), ColorPack.BROWN.getGreen(), ColorPack.BROWN.getBlue(), opac), getX() + getWidth(),
					getY() + getHeight(), new Color(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue(), opac));
			textGraphics.setPaint(gp);
		}
				
		// Draw the text out
		textGraphics.drawString(getText(), getX() - (getWidth() / 2), (int) (getY() + bounds.getHeight()) - (getHeight() / 2));
		
		
	}

}
