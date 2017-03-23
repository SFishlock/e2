package sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import engine.Sprite;
import utils.FontLoader;

public class TextSprite extends Sprite {
	private Font font;
	private String text = "";
	private double fontSize = 0.05;
	private Color color;
	
	public TextSprite(int x, int y, String text, double fontSize, Color color) {
		super(x, y);
		this.text = text;
		this.font = FontLoader.loadFontFromResource("Konsystem.ttf");
		this.fontSize = fontSize;
		this.color = color;
	}
	
	public TextSprite(int x, int y, String text, double fontSize) {
		this(x, y, text, fontSize, new Color(255, 255, 255, 255));
	}
	
	public TextSprite(int x, int y, String text, Color color) {
		this(x, y, text, 0.05, color);
	}

	public TextSprite(int x, int y, String text) {
		this(x, y, text, 0.05, new Color(255, 255, 255, 255));
	}
	
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getFontSize() {
		return fontSize;
	}

	public void setFontSize(double fontSize) {
		this.fontSize = fontSize;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	

	@Override
	public void update() {
		
	}

	@Override
	public void draw(Graphics context) {
		// Create the fontSize from the size of the screen
		float dynamicFontSize = (float) (getScreenHeight() * fontSize);

		// Create a Graphics2D Object which allows us to set anti aliasing
		Graphics2D textGraphics = (Graphics2D) context.create();

		// Set the anti aliasing
		textGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		

		// Make the final font object with the correct font size
		Font finalFont = font.deriveFont(dynamicFontSize);

		// Set the font
		textGraphics.setFont(finalFont);

		// Work out the bounds of the text
		TextLayout optTL = new TextLayout(text, finalFont, textGraphics.getFontRenderContext());
		Rectangle2D bounds = optTL.getBounds();
		
		setWidth((int) bounds.getWidth());
		setHeight((int) bounds.getHeight());
	
		// Set the colour of the text
		textGraphics.setColor(color);
				
		// Draw the text out
		textGraphics.drawString(text, getX(), (int) (getY() + bounds.getHeight()));
		
	}

}
