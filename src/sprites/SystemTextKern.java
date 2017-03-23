package sprites;

import java.awt.Font;
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

public class SystemTextKern extends TextSprite {
	
	public SystemTextKern(int x, int y, String text) {
		super(x, y, text);
		setFontSize(0.027);
		setFont(FontLoader.loadFontFromResource("OpenSans-Regular.ttf"));
		
		Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
		attributes.put(TextAttribute.TRACKING, 0.5);
		setFont(getFont().deriveFont(attributes));
		
	}

	@Override
	public void update() {

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
		
			
		// Draw outline
		textGraphics.setColor(ColorPack.BLACK);
		
		
		int out = 1;
		for(int i = -out ; i <= out ; i++) {
			for(int j = -out ; j <= out ; j++) {
				textGraphics.drawString(getText(), getX() + i, getY()  + j + (int) bounds.getHeight());
			}
		}
	
		textGraphics.setColor(ColorPack.WHITE);
				
		// Draw the text out
		textGraphics.drawString(getText(), getX(), getY() + (int) bounds.getHeight());
		
		
	}

}


