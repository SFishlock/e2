package songmanager;

/**
 * Represents a note, beat marker or chord.
 * @author Alex
 *
 */
public class Beat {

	private int time, measure;
	
	/**
	 * Creates a measure (beat marker, with no actual note or chord)
	 * @param time Time marker (ms)
	 * @param measure Bar number
	 */
	public Beat(int time, int measure) {
		this.time = time;
		this.measure = measure;
	}
	
	/**
	 * Gets the time a note is place
	 * @return Time marker (ms)
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Returns the bar number of a beat
	 * -1 means it is a beat which is not a new bar
	 * @return Beat marker
	 */
	public int getMeasure() {
		return measure;
	}
}