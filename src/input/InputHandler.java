package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import engine.Screen;

/**
 * 
 * @author fannychen, bobbydilley
 *
 */
public class InputHandler implements KeyListener {

	private ArrayList<Integer> playKey; // store the keys for playing
	private ArrayList<Integer> powerKey; // store the keys for powers/abilities
	private Screen screen; // The screen to send the action to

	public static int PLAYKEY0 = 0;
	public static int PLAYKEY1 = 1;
	public static int PLAYKEY2 = 2;
	public static int PLAYKEY3 = 3;
	public static int POWERKEY = 100;

	private static int MUTEKEYCODE;
	public static int MUTEKEY = 200;

	/**
	 * Initialise InputHandler
	 */
	public InputHandler() {
		playKey = new ArrayList<Integer>();
		powerKey = new ArrayList<Integer>();
	}

	/**
	 * Sets the new screen
	 * 
	 * @param screen
	 *            The new screen
	 */
	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	/**
	 * Gets the current screen
	 * 
	 * @return The current screen
	 */
	public Screen getScreen() {
		return this.screen;
	}

	/**
	 * Handles key presses
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == playKey.get(0)) {
			screen.keyPressed(PLAYKEY0);
		}
		if (e.getKeyCode() == playKey.get(1)) {
			screen.keyPressed(PLAYKEY1);
		}
		if (e.getKeyCode() == playKey.get(2)) {
			screen.keyPressed(PLAYKEY2);
		}
		if (e.getKeyCode() == playKey.get(3)) {
			screen.keyPressed(PLAYKEY3);
		}
		if (!powerKey.isEmpty()) {
			if (e.getKeyCode() == powerKey.get(0)) {
				screen.keyPressed(POWERKEY);
			}
		}
		if (e.getKeyCode() == MUTEKEYCODE) {
			screen.keyPressed(MUTEKEY);
		}
	}

	/**
	 * Handles key releases
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == playKey.get(0)) {
			screen.keyReleased(PLAYKEY0);
		}
		if (e.getKeyCode() == playKey.get(1)) {
			screen.keyReleased(PLAYKEY1);
		}
		if (e.getKeyCode() == playKey.get(2)) {
			screen.keyReleased(PLAYKEY2);
		}
		if (e.getKeyCode() == playKey.get(3)) {
			screen.keyReleased(PLAYKEY3);
		}
		if (!powerKey.isEmpty()) {
			if (e.getKeyCode() == powerKey.get(0)) {
				screen.keyReleased(POWERKEY);
			}
		}
		if (e.getKeyCode() == MUTEKEYCODE) {
			screen.keyReleased(MUTEKEY);
		}
	}

	/**
	 * Handles key types
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * @param key
	 *            the input playing key to be stored
	 */
	public void storePlayKey(char key) {
		int i = (int) key;
		if (i >= 97 && i <= 122) {
			i = i - 32;
		}
		if (i != 58 && i != 60 && i != 62 && i != 63 && i != 64) {
			if (i >= 48 && i <= 93) {
				if (playKey.size() < 4 && !playKey.contains(i) && !powerKey.contains(i)) {
					playKey.add(i);
				} else
					System.out.println("Invalid key");
			} else
				System.out.println("Invalid key");
		} else
			System.out.println("Invalid key");

	}

	/**
	 * @param key
	 *            the input power key to be stored
	 */
	public void storePowerKey(char key) {
		int i = (int) key;
		if (i >= 97 && i <= 122) {
			i = i - 32;
		}
		if (i != 58 && i != 60 && i != 62 && i != 63 && i != 64) {
			if (i >= 48 && i <= 93) {
				if (powerKey.size() < 1 && !powerKey.contains(i) && !playKey.contains(i)) {
					powerKey.add(i);
				} else
					System.out.println("Invalid key");
			} else
				System.out.println("Invalid key");
		} else
			System.out.println("Invalid key");
	}

	/**
	 * Allows the storage of a mute key
	 * 
	 * @param key
	 *            The new mute key
	 */
	public void storeMuteKey(char key) {
		int i = (int) key;
		if (i >= 97 && i <= 122) {
			i = i - 32;
		}
		if (i != 58 && i != 60 && i != 62 && i != 63 && i != 64) {
			if (i >= 48 && i <= 93) {
				MUTEKEYCODE = i;
			} else
				System.out.println("Invalid key");
		} else
			System.out.println("Invalid key");
	}

	/**
	 * @return the arraylist of playing keys
	 */
	public ArrayList<Integer> getPlayKey() {
		return playKey;
	}

	/**
	 * @return the arraylist of power keys
	 */
	public ArrayList<Integer> getPowerKey() {
		return powerKey;
	}

	/**
	 * @return is all the keys are stored and ready to start a game
	 */
	public boolean containAllKey() {
		return (playKey.size() == 4 && powerKey.size() == 1);
	}

	/**
	 * remove all the keys that have been stored
	 */
	public void removeAllKey() {
		playKey.clear();
		powerKey.clear();
	}

	/**
	 * @param index
	 *            the index of playing key to be removed
	 */
	public void removePlayKey(int index) {

		if (index < playKey.size()) {
			playKey.remove(index);
		} else
			System.out.println("Invalid index");

	}

	/**
	 * @param index
	 *            the index of power key to be removed
	 */
	public void removePowerKey(int index) {

		if (index < powerKey.size()) {
			powerKey.remove(index);
		} else
			System.out.println("Invalid index");

	}

	/**
	 * @return is the powerKey arraylist empty (all the powers/abilities are
	 *         used)
	 */
	public boolean emptyPowerKey() {
		return powerKey.isEmpty();
	}
}