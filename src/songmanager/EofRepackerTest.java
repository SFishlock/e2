package songmanager;

import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;

import org.junit.Test;

public class EofRepackerTest {

	@Test
	public void test() {
		// Set up
		EofRepacker repacker = new EofRepacker();
		SongFileProcessor processor = new SongFileProcessor();
		SongObject bassSong = repacker.getSongObjectFromBassFile("data/test/PART REAL_BASS_RS2.xml");
		SongObject song = processor.readSongObjectFromXML("data/xml/laser.xml");
		
		// Test
		assertTrue(song.getArtist().compareTo(bassSong.getArtist()) == 0);
	}
}
