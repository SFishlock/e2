package audio;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

/**
 * Non-blocking class for playing small audio effects
 * 
 * @author Nicholas Orgill
 *
 */
public class SoundHandler {

	HashMap<String, AudioInputStream> effects;
	HashMap<String, AudioFormat> formats;

	static ArrayList<Clip> clips = new ArrayList<Clip>();

	/**
	 * Constructor for new sound handler
	 */
	public SoundHandler() {
		effects = new HashMap<String, AudioInputStream>();
		formats = new HashMap<String, AudioFormat>();
		// clips = new ArrayList<Clip>();

	}

	/**
	 * Fills the hash map of sound handler with effects
	 * 
	 * @param list
	 *            The list of sound effects to be used in this instance
	 */
	public void fillEffects(String[] list) {
		try {
			// System.out.println("HI");
			for (String elem : list) {
				AudioInputStream ais = AudioSystem.getAudioInputStream(new File("src/res/audio/" + elem));
				AudioFormat format = ais.getFormat();
				effects.put(elem, ais);
				formats.put(elem, format);
			}
		} catch (Exception e) {
			System.err.println("Terminal Exception");
			e.printStackTrace();
		}

	}

	/**
	 * Plays effect once
	 * 
	 * @param effect
	 *            The string of the effect to be played
	 */
	public void playEffect(String effect) {
		playEffect(effect, false);
	}

	/**
	 * Plays effect
	 * 
	 * @param effect
	 *            The string of the effect to be played
	 * @param loop
	 *            Set to true if effect should be looped
	 */
	public void playEffect(String effect, boolean loop) {
		try {
			DataLine.Info info = new DataLine.Info(Clip.class, formats.get(effect));
			Clip clip = (Clip) AudioSystem.getLine(info);
			// Clip clip = AudioSystem.getClip();
			clip.open(effects.get(effect));
			if (loop)
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			else
				clip.start();
			clips.add(clip);
			effects.put(effect, AudioSystem.getAudioInputStream(new File("src/res/audio/" + effect)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stops all clips from playing
	 */
	public void stopAll() {
		for (Clip clip : clips) {
			clip.stop();
		}
	}
}