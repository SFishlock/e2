package songmanager;

/**
 * Holds all the notes, beats and metadata for a song
 * @author Alex
 *
 */
public class SongObject {

	private String title, artist;
	private int songLength, averageTempo, startBeat;
	private Note[] notes;
	private Beat[] beats;
	
	/**
	 * Creates a new song object
	 * @param title
	 * @param artist
	 * @param songLength Length of the song (ms)
	 * @param averageTempo Beats per minute
	 * @param startBeat The first beat of the song
	 * @param notes Array of notes
	 * @param beats Array of beats
	 */
	public SongObject(String title, String artist, int songLength, int averageTempo, int startBeat, Note[] notes, Beat[] beats) {
		this.title = title;
		this.artist = artist;
		this.songLength = songLength;
		this.averageTempo = averageTempo;
		this.startBeat = startBeat;
		this.notes = notes;
		this.beats = beats;
	}
	
	/**
	 * Gets the title of the song
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Gets the artist of the song
	 */
	public String getArtist() {
		return artist;
	}
	
	/**
	 * Gets the length of the song (ms)
	 */
	public int getSongLength() {
		return songLength;
	}
	
	/**
	 * Gets the average tempo of the song
	 */
	public int getAverageTempo() {
		return averageTempo;
	}
	
	/**
	 * Gets the starting beat of the song
	 */
	public int getStartBeat() {
		return startBeat;
	}
	
	/**
	 * Gets the array of all notes in the song
	 */
	public Note[] getNotes() {
		return notes;
	}
	
	/**
	 * Gets the array of all beats in the song
	 */
	public Beat[] getBeats() {
		return beats;
	}
}