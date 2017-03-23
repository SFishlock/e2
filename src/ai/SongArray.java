package ai;

import songmanager.Beat;
import songmanager.Note;

/**
 * Contains the Beats and Notes that the AI will use to play
 * 
 * @author Sam
 *
 */
public class SongArray {

	private Note[] notes;
	private Beat[] beats;

	public SongArray(Note[] notes, Beat[] beats) {
		this.notes = notes;
		this.beats = beats;
	}

	public Note[] getNotes() {
		return notes;
	}

	public Beat[] getBeats() {
		return beats;
	}

}
