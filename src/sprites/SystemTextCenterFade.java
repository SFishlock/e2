package sprites;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import utils.ColorPack;
import utils.FontLoader;

public class SystemTextCenterFade extends TextSprite {
	int counter = 0;
	double opac = 0;
	String actualText = "";

	public SystemTextCenterFade(int x, int y, String text) {
		super(x, y, text);
		setFontSize(0.027);
		setFont(FontLoader.loadFontFromResource("OpenSans-Regular.ttf"));
		actualText = text;
	}

	@Override
	public void update() {
		if (counter == 180) {
			counter = 0;
			actualText = getText();
		}
		counter++;

		opac = Math.sin(Math.toRadians(counter));
		//opac = 1;
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
		TextLayout optTL = new TextLayout(actualText, finalFont, textGraphics.getFontRenderContext());
		Rectangle2D bounds = optTL.getBounds();

		setWidth((int) bounds.getWidth());
		setHeight((int) bounds.getHeight());

		// Draw outline
		textGraphics.setColor(new Color(ColorPack.BLACK.getRed(), ColorPack.BLACK.getGreen(), ColorPack.BLACK.getBlue(),
				(int) (opac * 255)));

		int out = 1;
		for (int i = -out; i <= out; i++) {
			for (int j = -out; j <= out; j++) {
				textGraphics.drawString(actualText, getX() + i - (getWidth() / 2),
						(int) (getY() + j + bounds.getHeight()) - (getHeight() / 2));
			}
		}

		textGraphics.setColor(new Color(ColorPack.WHITE.getRed(), ColorPack.WHITE.getGreen(), ColorPack.WHITE.getBlue(),
				(int) (opac * 255)));

		// Draw the text out
		textGraphics.drawString(actualText, getX() - (getWidth() / 2),
				(int) (getY() + bounds.getHeight()) - (getHeight() / 2));

	}

}
