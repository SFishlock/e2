package songmanager;

import static org.junit.Assert.*;

import org.junit.Test;

import sprites.NoteSprite;

public class NoteTest {

	@Test
	public void test() {
		// Set up
		boolean[] buttons = {true, false, false, true};
		Note note = new Note(100, 101, buttons);
		
		// Test getters
		assertTrue(note.getButtons() == buttons);
		assertTrue(note.getSustain() == 101);
		assertTrue(note.getTime() == 100);
		assertFalse(note.isHeld());
		
		// Test setters
		note.setHeld(true);
		assertTrue(note.isHeld());
		
		// Test other methods
		assertFalse(note.getGraphicalNotes() == null);
		assertTrue(note.toString().compareTo("Note at: 100") == 0);
		note.addNoteSprite(null);
		assertTrue(note.getGraphicalNotes().get(0) == null);
	}

}
