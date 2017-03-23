package songmanager;

import static org.junit.Assert.*;

import org.junit.Test;

public class SongFileProcessorTest {

	@Test
	public void test() {
		// Set up
		SongFileProcessor processor = new SongFileProcessor();
		
		// Test writeSongObjectToXML
		SongObject songObj = processor.readSongObjectFromXML("data/xml/laser.xml");
		processor.writeSongObjectToXML(songObj, "data/test/test_laser.xml");
		SongObject testObj = processor.readSongObjectFromXML("data/test/test_laser.xml");
		assertTrue(songObj.getBeats().length == testObj.getBeats().length);
		
		// Test createSongFile
		String imagePath = "data/image/laser.png";
		String audioPath = "data/audio/laser.wav";
		String notesFilePath = "data/xml/laser.xml";
		SongFile testFile = processor.createSongFile(notesFilePath, audioPath, imagePath);
		assertFalse(testFile == null);
		
		// Test writeSongFile and readSongFile
		processor.writeSongFile(testFile, "data/test/test_laser.song");
		SongFile readFile = processor.readSongFile("data/test/test_laser.song");
		assertFalse(readFile == null);
		
		// Test readAllSongFiles
		SongFile[] files = SongFileProcessor.readAllSongFiles();
		assertTrue(files.length == 3);
	}

}
