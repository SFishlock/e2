package audio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * This class counts playing time in the form of HH:mm:ss It also updates the
 * time slider
 * 
 * @author Nicholas Orgill
 *
 */
public class PlayingTimer extends Thread {
	private DateFormat dateFormater = new SimpleDateFormat("HH:mm:ss");
	private boolean isRunning = false;
	private boolean isPause = false;
	private boolean isReset = false;
	private long startTime;
	private long pauseTime;
	private Clip audioClip;

	/**
	 * Empty constructor
	 */
	public PlayingTimer() {
	}

	public void run() {
		isRunning = true;

		startTime = System.currentTimeMillis();

		while (isRunning) {
			try {
				Thread.sleep(100);
				if (isPause) {
					pauseTime += 100;
				}
			} catch (InterruptedException ex) {
				startTime = System.currentTimeMillis();
				isRunning = false;
				break;
			}
		}
	}

	/**
	 * Reset counting to "00:00:00"
	 */
	public void reset() {
		isReset = true;
		isRunning = false;
	}

	/**
	 * Pauses timer
	 */
	public void pauseTimer() {
		isPause = true;
	}

	/**
	 * Resumes timer
	 */
	public void resumeTimer() {
		isPause = false;
	}

	/**
	 * Generate a String for time counter in the format of "HH:mm:ss"
	 * 
	 * @return The time counter in string form
	 */
	public String toTimeString() {
		if (!isRunning) {
			return "00:00:00";
		}
		long now = System.currentTimeMillis();
		Date current = new Date(now - startTime - pauseTime);
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		String timeCounter = dateFormater.format(current);
		return timeCounter;
	}

	/**
	 * Returns length of the timer so far in milliseconds
	 * 
	 * @return Length of the timer in milliseconds
	 */
	public long getTimeInMill() {
		if (!isRunning) {
			return 0;
		}
		long now = System.currentTimeMillis();
		Date current = new Date(now - startTime - pauseTime);
		long time = current.getTime();
		return time;
	}

	/**
	 * Setter for audio clip
	 * 
	 * @param audioClip
	 *            The clip to be played
	 */
	public void setAudioClip(Clip audioClip) {
		this.audioClip = audioClip;
	}
}