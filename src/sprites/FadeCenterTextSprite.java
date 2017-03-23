package sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import utils.ColorPack;
import utils.FontLoader;

public class FadeCenterTextSprite extends TextSprite {
	int counter = 0;
	double opac = 0;
	public FadeCenterTextSprite(int x, int y, String text) {
		super(x, y, text);
		setFontSize(0.04);
		setFont(FontLoader.loadFontFromResource("Roboto-Bold.ttf"));
	}
	
	@Override 
	public void update() {
		if(counter == 179) {
			counter = 0;
		}
		counter++;
		
		opac = Math.sin(Math.toRadians(counter));

		
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
		textGraphics.setColor(new Color(ColorPack.GREY.getRed(), ColorPack.GREY.getGreen(), ColorPack.GREY.getBlue(), (int)(opac * 255)));
		int out = 1;
		for(int i = -out ; i <= out ; i++) {
			for(int j = -out ; j <= out ; j++) {
				textGraphics.drawString(getText(), getX() + i - (getWidth() / 2), (int) (getY()  + j + bounds.getHeight()) - (getHeight() / 2));
			}
		}
			
		// Set the colour of the text
		textGraphics.setColor(new Color(ColorPack.WHITE.getRed(), ColorPack.WHITE.getGreen(), ColorPack.WHITE.getBlue(), (int)(opac * 255)));
				
		// Draw the text out
		textGraphics.drawString(getText(), getX() - (getWidth() / 2), (int) (getY() + bounds.getHeight()) - (getHeight() / 2));
		
		
	}

}
