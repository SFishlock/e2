package screens;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import ai.AI;
import ai.SongArray;
import audio.Player;
import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import songmanager.Beat;
import songmanager.Note;
import songmanager.SongFile;
import songmanager.SongFileProcessor;
import songmanager.SongObject;
import sprites.BarSprite;
import sprites.NoteHitSprite;
import sprites.NoteSprite;
import sprites.PlaySprite;
import sprites.SystemTextCenter;
import sprites.SystemTextCenterFloat;
import sprites.SystemTextCenterShake;
import utils.ColorPack;

/**
 * 
 * @author Bobby Dilley
 *
 */
public class PlayScreen extends Screen {
	private SongFileProcessor reader;
	private SongObject song;
	private Beat[] beat;
	private Note[] notes;
	private ArrayList<Note> Pnotes;
	private Note[] note2;
	int score = 0;
	boolean[] keys = { false, false, false, false };
	final int MAX_NOTE_SCORE = 200;
	final int MAX_NOTE_RANGE = 200;
	int combo = 0;
	int power = 0;

	int lineY = (int) Math.round(getScreenHeight() * 0.8);

	private SystemTextCenter textSprite; // An example text sprite
	private SystemTextCenter textScore; // An example text sprite
	private SystemTextCenterFloat powerText;
	private SystemTextCenter cooldownText;
	private int count = 0; // A variable to count on the screen

	private Player audio = new Player();

	private PlaySprite playSprite;

	private BarSprite[] barSprite;
	private NoteSprite[] noteSprite;
	private NoteSprite[] noteSprite2;

	private int[] scoreQuality = new int[5];

	private ArrayList<NoteHitSprite> hits = new ArrayList<NoteHitSprite>();
	private double speedScale;
	private double origSpeedScale = 0.4;

	SongArray[] songArray;

	int n = 0;

	private SongFile songFile;

	private boolean complete = false;

	private ArrayList<SystemTextCenterFloat> floatTexts = new ArrayList<SystemTextCenterFloat>();
	private int cooldown = 0;

	public PlayScreen(GameObject gameObject) {
		super(gameObject);
		textSprite = new SystemTextCenter(getScreenWidth() / 2 - 200, 105, "Game AI: Easy");
		textScore = new SystemTextCenter(getScreenWidth() / 2 + 200, 105, "SinglePlayer");
		cooldownText = new SystemTextCenter(getScreenWidth() / 2, 105, " ");
		playSprite = new PlaySprite(0, 0, 0, 0, 0.5);
		powerText = new SystemTextCenterFloat(0, 0, " ");
		this.songFile = getGameObject().getSongFile();
		getGameObject().setSpeed(0.4);
		speedScale = getGameObject().getSpeed();
	}

	@Override
	public void keyPressed(int key) {
		if (key == InputHandler.POWERKEY) {
			System.out.println("on p");
			displayPower();
		} else if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute pressed");
		} else {
			keys[key] = true;
			System.out.println("on" + key);
			playSprite.push(key);
		}
	}

	@Override
	public void keyReleased(int key) {
		if (key == InputHandler.POWERKEY) {
			System.out.println("off p");
		} else if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute unpressed");
		} else {
			keys[key] = false;
			System.out.println("off" + key);
			playSprite.unpush(key);
		}
	}
	
	private void displayPower() {
		if (power >= 50) {
			powerText = new SystemTextCenterFloat(getScreenWidth() / 2, 340, "POWER USED!");
			speedScale = Math.min(speedScale - 0.2, 2);
			cooldown = 600;
			cooldownText.setText("Activated Power! \n Time left: " + (int) (cooldown / 60));
			powerText.shine();
			power = 0;
		} else {
			powerText = new SystemTextCenterFloat(getScreenWidth() / 2, 280, "NOT ENOUGH POWER!");
			powerText.shine();
		}
	}

	public void scoreHelper(int difference) {
		if (difference <= getGameObject().PERFECT) {
			combo++;
			if (combo > 5) {
				power += combo;
			}
			score += 100;
			scoreQuality[0]++;
			SystemTextCenterFloat floatText = new SystemTextCenterFloat(getScreenWidth() / 2, 280, "PERFECT");
			floatText.shine();
			floatTexts.add(floatText);
		} else if (difference <= getGameObject().EXCELLENT) {
			combo++;
			if (combo > 5) {
				power += combo;
			}
			score += 75;
			scoreQuality[1]++;
			SystemTextCenterFloat floatText = new SystemTextCenterFloat(getScreenWidth() / 2, 280, "EXCELLENT");
			floatText.shine();
			floatTexts.add(floatText);
		} else if (difference <= getGameObject().GOOD) {
			combo = 0;
			power -= 10;
			score += 50;
			scoreQuality[2]++;
			SystemTextCenterFloat floatText = new SystemTextCenterFloat(getScreenWidth() / 2, 280, "GOOD");
			floatText.shine();
			floatTexts.add(floatText);
		} else if (difference <= getGameObject().OKAY) {
			power -= 10;
			combo = 0;
			score += 25;
			scoreQuality[3]++;
		} else {
			scoreQuality[4]++;
			bad();
		}
		textScore.setText("" + score);

		power = Math.min(100, Math.max(0, power));

		textSprite.setText("COMBO: " + combo + "POWER: " + power + "%");
	}

	public void bad() {
		if(power > 0) power--;
		combo = 0;
		SystemTextCenterShake floatText = new SystemTextCenterShake(getScreenWidth() / 2, 280, "BAD");
		floatText.shine();
		floatTexts.add(floatText);
		textSprite.setText("COMBO: " + combo + " POWER: " + power + "%");
	}

	@Override
	public void update() {
		if (cooldown != 0) {
			cooldown--;
			cooldownText.setText("Activated Power! \n Time left: " + (int) (cooldown / 60));
		} else {
			cooldownText.setText(" ");
			speedScale = origSpeedScale;
		}
		if (audio.getAudioPlayer().playCompleted) {
			if (!complete) {
				getGameObject().setP1Score(score);
				getGameObject().setScoreQuality(scoreQuality);
				setNextScreen(new EndScreen(getGameObject()));
				moveScreen();
				complete = true;
			}

		}

		else if (count == 0) {
			songFile = getGameObject().getSongFile();
			song = songFile.getSong();
			beat = song.getBeats();
			notes = song.getNotes();
			Pnotes = new ArrayList<Note>(Arrays.asList(notes));
			AI ai = new AI();
			songArray = ai.recreateArray(song, 10);

			audio.playBack(songFile.getAudioInputPath());

			note2 = songArray[6].getNotes();
			barSprite = new BarSprite[beat.length];
			noteSprite = new NoteSprite[notes.length];
			noteSprite2 = new NoteSprite[note2.length];

			for (int i = 0; i < beat.length; i++) {
				barSprite[i] = new BarSprite((int) (getScreenWidth() / 2), lineY - beat[i].getTime(), 0, 0);
			}

			for (int i = 0; i < notes.length; i++) {
				noteSprite[i] = new NoteSprite((int) (getScreenWidth() / 2), lineY - notes[i].getTime(), 0, 0,
						notes[i].getButtons(), notes[i].getSustain(), 0.5, speedScale);
				notes[i].addNoteSprite(noteSprite[i]);
			}
		}

		for (int i = 0; i < beat.length; i++) {
			barSprite[i].setY((int) (lineY - (beat[i].getTime() - count) * speedScale));
			barSprite[i].update();
		}

		for (int i = n; i < notes.length; i++) {
			noteSprite[i].setScreenSize(getScreenWidth(), getScreenHeight());
			noteSprite[i].update();

			noteSprite[i].setY((int) (lineY - (notes[i].getTime() - count) * speedScale)
					+ (noteSprite[i].getLength() / 3) + getGameObject().getOffset());

			// If the note is in the playing area
			if (noteSprite[i].isRemoved() == false) {
				if (noteSprite[i].getY() >= lineY && noteSprite[i].getY() <= lineY + (3 * noteSprite[i].getHeight())) {
					boolean notesHit = true;
					for (int p = 0; p < notes[p].getButtons().length; p++) {
						if (notes[i].getButtons()[p]) {
							if (keys[p]) {
								hits.add(new NoteHitSprite((playSprite.getX() - playSprite.getWidth() / 2)
										+ (playSprite.getBlockSizeAndGap() / 2) + (p * playSprite.getBlockSizeAndGap()),
										playSprite.getY() + (playSprite.getBlockSize() / 2), playSprite.getBlockSize(),
										playSprite.getBlockSize()));
								keys[p] = false;
							} else {
								notesHit = false;
							}

						}
					}
					if (notesHit) {
						int difference = Math.abs(noteSprite[i].getY() - 60 - lineY);
						scoreHelper(difference);
						noteSprite[i].remove();
					}

				}

				// When the note is finished, increment the start of the array
				if (noteSprite[i].getY() > getScreenHeight()) {
					if (noteSprite[i].isRemoved()) {
						n++;
					} else {
						n++;
						bad();
					}

				}
			}

		}

		textSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		textSprite.update();

		textScore.setScreenSize(getScreenWidth(), getScreenHeight());
		textScore.update();
		
		powerText.setScreenSize(getScreenWidth(), getScreenHeight());
		powerText.update();

		cooldownText.setScreenSize(getScreenWidth(), getScreenHeight());
		cooldownText.update();

		playSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		playSprite.update();

		for (NoteHitSprite hit : hits) {
			hit.update();
		}

		// Move the texts
		for (SystemTextCenterFloat floatText : floatTexts) {
			floatText.setScreenSize(getScreenWidth(), getScreenHeight());
			floatText.update();
		}

		for (int i = floatTexts.size() - 1; i > 0; i--) {
			if (floatTexts.get(i).shouldRemove()) {
				floatTexts.remove(i);
			}
		}

		for (int i = hits.size() - 1; i > 0; i--) {
			if (hits.get(i).shouldRemove()) {
				hits.remove(i);
			}
		}

		count = (int) (audio.getPlayingTimer().getTimeInMill());
	}

	@Override
	public void draw(Graphics context) {

		// We use this to draw a dark background
		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());

		// This is how you draw the sprites
		textSprite.draw(context);
		textScore.draw(context);
		playSprite.draw(context);
		cooldownText.draw(context);
		powerText.draw(context);

		for (int i = 0; i < beat.length; i++) {
			barSprite[i].draw(context);
		}

		for (int i = n; i < notes.length; i++) {
			noteSprite[i].draw(context);
		}

		// Initial box for things to go in
		context.setColor(ColorPack.DARK);
		context.fillRect(10, 10, getScreenWidth() / 2 - 20, 70);
		context.setColor(ColorPack.FADEDWHITE);
		context.drawRect(10, 10, getScreenWidth() / 2 - 20, 70);

		// Secondary box
		context.setColor(ColorPack.DARK);
		context.fillRect(10 + getScreenWidth() / 2 - 10, 10, getScreenWidth() / 2 - 20, 70);
		context.setColor(ColorPack.FADEDWHITE);
		context.drawRect(10 + getScreenWidth() / 2 - 10, 10, getScreenWidth() / 2 - 20, 70);

		for (NoteHitSprite hit : hits) {
			hit.draw(context);
		}

		for (SystemTextCenterFloat floatText : floatTexts) {
			floatText.draw(context);
		}

	}
}
