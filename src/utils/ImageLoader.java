package utils;

import java.awt.Image;
import java.awt.Toolkit;

/**
 * Class for loading images
 * 
 * @author
 *
 */
public class ImageLoader {
	/**
	 * Loads an image from a given path
	 * 
	 * @param path
	 *            The path of the image
	 * @return The image
	 */
	public static Image loadImageFromResource(String path) {
		Image image = Toolkit.getDefaultToolkit().getImage(path);
		return image;
	}
}
