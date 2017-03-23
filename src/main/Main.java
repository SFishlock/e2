package main;

import engine.Engine;

/**
 * The main class of the game
 * 
 * @author bobbydilley
 *
 */
public class Main {
	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Launching Game...");

		// Control fullscreen
		boolean fullScreen = false;

		if (args.length > 0) {
			if (args[0].equals("window")) {
				fullScreen = false;
			}
		}

		try {
			Engine engine = new Engine("BeatNet", fullScreen);
			engine.start();
		} catch (Exception e) {
			System.out.println("FATAL SYSTEM ERROR: -> ");
			System.out.println(e.getMessage());
			System.exit(-1);
		}

	}
}
