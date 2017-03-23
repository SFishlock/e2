package songmanager;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BeatTest.class, EofRepackerTest.class, NoteTest.class, SongFileProcessorTest.class, SongFileTest.class,
		SongObjectTest.class })
public class SongmanagerTestSuite {

}
