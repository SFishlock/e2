package songmanager;

import static org.junit.Assert.*;

import org.junit.Test;

public class SongObjectTest {

	@Test
	public void test() {
		// Set up
		SongObject testSongObj = (new SongFileProcessor()).readSongObjectFromXML("data/xml/laser.xml");
		
		// Test getters
		assertTrue(testSongObj.getArtist().compareTo("Kevin MacLeod") == 0);
		assertTrue(testSongObj.getTitle().compareTo("Laser Groove") == 0);
		assertTrue(testSongObj.getSongLength() == 171058);
		assertTrue(testSongObj.getAverageTempo() == 140355);
		assertTrue(testSongObj.getStartBeat() == 3000);
		assertTrue(testSongObj.getNotes().length == 374);
		assertTrue(testSongObj.getBeats().length == 395);
	}

}
