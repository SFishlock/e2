package songmanager;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class SongFileTest {

	@Test
	public void test() {
		// Set up
		BufferedImage testImage = null;
		try {
			testImage = ImageIO.read(new File("data/image/laser.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String testAudioPath = "data/audio/laser.wav";
		SongObject testSongObj = (new SongFileProcessor()).readSongObjectFromXML("data/xml/laser.xml");
		SongFile file = new SongFile(testImage, testAudioPath, testSongObj);
		
		// Test getters
		assertFalse(file.getCoverArt() == null);
		assertTrue(file.getAudioInputPath().compareTo(testAudioPath) == 0);
		assertTrue(file.getSong() == testSongObj);
	}

}
