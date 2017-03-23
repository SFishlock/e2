package songmanager;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BeatTest {

	@Test
	public void test() {
		// Set up
		Beat beat = new Beat(100, 101);
		
		// Test getters
		assertTrue(beat.getTime() == 100);
		assertTrue(beat.getMeasure() == 101);
	}

}
