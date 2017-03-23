package utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

/**
 * Class for loading fonts
 * 
 * @author
 *
 */
public class FontLoader {
	/**
	 * Loads a font from a given path
	 * 
	 * @param path
	 *            The path of the font
	 * @return The font
	 */
	public static Font loadFontFromResource(String path) {
		// Load the font
		ClassLoader cl = FontLoader.class.getClassLoader();
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, cl.getResourceAsStream("res/" + path));
		} catch (FontFormatException e) {
			System.err.println("Font format was incorrect when loading font: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Font could not be found when loading font: " + e.getMessage());
		}
		return font;
	}
}
