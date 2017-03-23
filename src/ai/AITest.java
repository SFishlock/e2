package ai;

import static org.junit.Assert.*;

import org.junit.Test;

import songmanager.SongFileProcessor;
import songmanager.SongObject;

public class AITest {

	@Test
	public void test() {
		AI ai = new AI();
		SongFileProcessor proc = new SongFileProcessor();
		SongObject song = proc.readSongObjectFromXML("src/songmanager/tempnotefile.xml");
		
		SongArray[] songArray = ai.recreateArray(song, 10);
		
		assertTrue(ai.getSongArray() == songArray);
		
		SongArray[] songArray2 = ai.recreateArray(song, 20);
		
		assertFalse(ai.getSongArray() == songArray);
		ai.setSongArray(songArray2);
		assertTrue(ai.getSongArray() == songArray2);
		
		assertTrue(songArray[1].getBeats().length == 395);
		assertTrue(songArray[1].getNotes().length == 374);
		
	}
}