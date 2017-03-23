package screens;

import java.awt.Color;
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
import sprites.TextSprite;
import utils.ColorPack;

public class AIPlayScreen extends Screen {
	private SongFileProcessor reader;
	private SongObject song;
	private Beat[] beat;
	private Note[] notes;
	private Note[] AINotes;
	int score = 0;
	int aiScore = 0;
	boolean[] keys = { false, false, false, false };
	final int MAX_NOTE_SCORE = 200;
	final int MAX_NOTE_RANGE = 100;

	int lineY = (int) Math.round(getScreenHeight() * 0.8);

	private SystemTextCenter textAILevel;
	private int count = 0;
	private SystemTextCenterFloat leftScore;
	private SystemTextCenterFloat rightScore;
	private SystemTextCenter player1Text;
	private SystemTextCenter player2Text;

	private Player audio = new Player();

	private PlaySprite playSpriteLeft;
	private PlaySprite playSpriteRight;

	private BarSprite[] barSpriteLeft;
	private BarSprite[] barSpriteRight;
	private NoteSprite[] noteSpriteLeft;
	private NoteSprite[] noteSpriteRight;
	private NoteSprite[] noteSpriteAI;

	private int aiLevel;
	private int origAiLevel;

	private int[] scoreQuality = new int[5];

	private ArrayList<NoteHitSprite> hits = new ArrayList<NoteHitSprite>();
	private ArrayList<NoteHitSprite> aiHits = new ArrayList<NoteHitSprite>();

	private double origSpeedScale = 0.4;
	private double speedScaleLeft = 0.4;
	private double speedScaleRight = 0.4;

	SongArray[] songArray;

	int n = 0;
	private int power;
	private int combo;
	private ArrayList<SystemTextCenterFloat> floatTexts = new ArrayList<SystemTextCenterFloat>();
	private int aiCombo;
	private int aiPower;
	private SystemTextCenterFloat powerText;
	private SongFile songFile;
	private int playerCooldown;
	private int aiCooldown;
	private SystemTextCenter cooldownTextLeft;
	private SystemTextCenter cooldownTextRight;

	@Override
	public void keyPressed(int key) {
		if (key == InputHandler.POWERKEY) {
			System.out.println("on p");
			displayPowerPlayer();
		} else if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute");
		} else {
			keys[key] = true;
			System.out.println("on" + key);
			playSpriteLeft.push(key);
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
			playSpriteLeft.unpush(key);
		}
	}

	private void displayPowerPlayer() {
		if (power >= 50) {
			powerText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 340, "POWER USED!");
			speedScaleRight = Math.min(speedScaleRight + 0.2, 2);
			aiLevel = (int) speedScaleRight * 10;
			playerCooldown = 600;
			cooldownTextRight.setText("Opponent Activated Power! \n Time left: " + (int) (playerCooldown / 60));
			power = 0;
		} else {
			powerText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 280, "NOT ENOUGH POWER!");
		}
	}

	private void displayPowerAI() {
		powerText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.75), 340, "POWER USED!");
		aiCooldown = 600;
		cooldownTextLeft.setText("Opponent Activated Power! \n Time left: " + (int) (aiCooldown / 60));
		speedScaleLeft = Math.min(speedScaleLeft + 0.2, 2);
		aiPower = 0;
	}

	public void scoreHelper(int difference, boolean ai) {
		SystemTextCenterFloat text;
		SystemTextCenterFloat scoreText;
		int playerScore;
		int playerCombo;
		int playerPower;
		boolean badBool = false;
		if (ai) {
			text = new SystemTextCenterFloat((int) (getScreenWidth() * 0.75), 280, "");
			scoreText = rightScore;
			playerScore = aiScore;
			playerCombo = aiCombo;
			playerPower = aiPower;
		} else {
			text = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 280, "");
			scoreText = leftScore;
			playerScore = score;
			playerCombo = combo;
			playerPower = power;
		}
		if (difference <= getGameObject().PERFECT) {
			playerCombo++;
			if (playerCombo > 5) {
				playerPower += playerCombo;
			}
			text.setText("PERFECT!");
			text.shine();
			floatTexts.add(text);
			playerScore += 100;
			if (!ai)
				scoreQuality[0]++;
		} else if (difference <= getGameObject().EXCELLENT) {
			playerCombo++;
			if (playerCombo > 5) {
				playerPower += playerCombo;
			}
			text.setText("EXCELLENT!");
			text.shine();
			floatTexts.add(text);
			playerScore += 75;
			if (!ai)
				scoreQuality[1]++;
		} else if (difference <= getGameObject().GOOD) {
			playerCombo = 0;
			playerPower -= 10;
			text.setText("GOOD!");
			text.shine();
			floatTexts.add(text);
			playerScore += 50;
			if (!ai)
				scoreQuality[2]++;
		} else if (difference <= getGameObject().OKAY) {
			playerCombo = 0;
			playerPower -= 10;
			text.setText("OKAY!");
			text.shine();
			floatTexts.add(text);
			playerScore += 25;
			if (!ai)
				scoreQuality[3]++;
		} else {
			bad(ai);
			badBool = true;
		}
		if (ai) {
			if (!badBool) {
				aiScore = playerScore;
				aiCombo = playerCombo;
				aiPower = playerPower;
				aiPower = Math.min(100, Math.max(0, aiPower));
			}
			player2Text.setText("AI COMBO: " + aiCombo + " POWER: " + aiPower + "%");
			if (aiPower >= 50) {
				displayPowerAI();
			}
		} else {
			if (!badBool) {
				score = playerScore;
				combo = playerCombo;
				power = playerPower;
				power = Math.min(100, Math.max(0, power));
			}
			player1Text.setText("Player COMBO: " + combo + " POWER: " + power + "%");
		}
		scoreText.setText("" + playerScore);
	}

	public void bad(boolean ai) {
		SystemTextCenterShake shakeText;
		if (ai) {
			shakeText = new SystemTextCenterShake((int) (getScreenWidth() * 0.75), 280, "BAD");
			aiPower = 0;
			aiCombo = 0;
			player2Text.setText("AI COMBO: " + aiCombo + " POWER: " + aiPower + "%");
		} else {
			scoreQuality[4]++;
			shakeText = new SystemTextCenterShake((int) (getScreenWidth() * 0.25), 280, "BAD");
			power = 0;
			combo = 0;
			player1Text.setText("Player COMBO: " + combo + " POWER: " + power + "%");
		}
		shakeText.shine();
		floatTexts.add(shakeText);
	}

	public AIPlayScreen(GameObject gameObject) {
		super(gameObject);
		textAILevel = new SystemTextCenter(getScreenWidth() / 2, getScreenHeight() - 30,
				"Difficulty: " + getGameObject().getAiLevelText());

		leftScore = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 105, "0");
		rightScore = new SystemTextCenterFloat((int) (getScreenWidth() * 0.75), 105, "0");

		cooldownTextLeft = new SystemTextCenter((int) (getScreenWidth() * 0.25), 105, " ");
		cooldownTextRight = new SystemTextCenter((int) (getScreenWidth() * 0.75), 105, " ");

		playSpriteLeft = new PlaySprite(0, 0, 0, 0, 0.25);
		playSpriteRight = new PlaySprite(0, 0, 0, 0, 0.75);

		player1Text = new SystemTextCenter((int) (getScreenWidth() * 0.25), 25, "Player");
		player2Text = new SystemTextCenter((int) (getScreenWidth() * 0.75), 25, "AI");

		powerText = new SystemTextCenterFloat(0, 0, " ");
	}

	@Override
	public void update() {

		int lineY = (int) Math.round(getScreenHeight() * 0.8);

		if (audio.getAudioPlayer().playCompleted) {
			getGameObject().setP1Score(score);
			getGameObject().setP2Score(aiScore);
			getGameObject().setScoreQuality(scoreQuality);
			setNextScreen(new EndScreen(getGameObject()));
			moveScreen();
		}

		else if (count == 0) {
			songFile = getGameObject().getSongFile();
			song = songFile.getSong();
			beat = song.getBeats();
			notes = song.getNotes();
			AI ai = new AI();
			songArray = ai.recreateArray(song, 20);

			audio.playBack(songFile.getAudioInputPath());

			origAiLevel = getGameObject().getAiLevel();
			textAILevel.setText("Difficulty: " + getGameObject().getAiLevelText());
			AINotes = songArray[origAiLevel].getNotes();
			barSpriteLeft = new BarSprite[beat.length];
			barSpriteRight = new BarSprite[beat.length];
			noteSpriteLeft = new NoteSprite[notes.length];
			noteSpriteRight = new NoteSprite[notes.length];
			noteSpriteAI = new NoteSprite[AINotes.length];

			for (int i = 0; i < beat.length; i++) {
				barSpriteLeft[i] = new BarSprite((int) (getScreenWidth() * 0.25), lineY - beat[i].getTime(), 0, 0);
				barSpriteRight[i] = new BarSprite((int) (getScreenWidth() * 0.75), lineY - beat[i].getTime(), 0, 0);
			}

			for (int i = 0; i < notes.length; i++) {
				noteSpriteLeft[i] = new NoteSprite((int) (getScreenWidth() * 0.25), lineY - notes[i].getTime(), 0, 0,
						notes[i].getButtons(), notes[i].getSustain(), 0.25, getGameObject().getSpeed());
				noteSpriteRight[i] = new NoteSprite((int) (getScreenWidth() * 0.75), lineY - notes[i].getTime(), 0, 0,
						notes[i].getButtons(), notes[i].getSustain(), 0.75, getGameObject().getSpeed());
			}

			for (int i = 0; i < AINotes.length; i++) {
				noteSpriteAI[i] = new NoteSprite((int) (getScreenWidth() * 0.75), lineY - AINotes[i].getTime(), 0, 0,
						AINotes[i].getButtons(), AINotes[i].getSustain(), 0.75, getGameObject().getSpeed());
			}
		}

		for (int i = 0; i < beat.length; i++) {
			barSpriteLeft[i].setY((int) (lineY - (beat[i].getTime() - count) * speedScaleLeft));
			barSpriteLeft[i].update();
			barSpriteRight[i].setY((int) (lineY - (beat[i].getTime() - count) * speedScaleRight));
			barSpriteRight[i].update();
		}

		for (int i = 0; i < notes.length; i++) {
			noteSpriteLeft[i].setScreenSize(getScreenWidth(), getScreenHeight());
			noteSpriteLeft[i].update();
			noteSpriteLeft[i].setY((int) (lineY - (notes[i].getTime() - count) * speedScaleLeft)
					+ (noteSpriteLeft[i].getLength() / 3));

			noteSpriteRight[i].setScreenSize(getScreenWidth(), getScreenHeight());
			noteSpriteRight[i].update();
			noteSpriteRight[i].setY((int) (lineY - (notes[i].getTime() - count) * speedScaleRight)
					+ (noteSpriteRight[i].getLength() / 3));
			noteSpriteAI[i].setScreenSize(getScreenWidth(), getScreenHeight());
			noteSpriteAI[i].update();
			noteSpriteAI[i].setY((int) (lineY - (AINotes[i].getTime() - count) * speedScaleRight)
					+ (noteSpriteAI[i].getLength() / 3));

			// Checks if the notes are in the playing area whether they should
			// be removed
			removeSprites(i, noteSpriteLeft, playSpriteLeft, hits, false);
			removeSprites(i, noteSpriteRight, playSpriteRight, aiHits, true);
		}

		textAILevel.setScreenSize(getScreenWidth(), getScreenHeight());
		textAILevel.update();

		leftScore.setScreenSize(getScreenWidth(), getScreenHeight());
		leftScore.update();

		rightScore.setScreenSize(getScreenWidth(), getScreenHeight());
		rightScore.update();

		playSpriteLeft.setScreenSize(getScreenWidth(), getScreenHeight());
		playSpriteLeft.update();

		playSpriteRight.setScreenSize(getScreenWidth(), getScreenHeight());
		playSpriteRight.update();

		player1Text.setScreenSize(getScreenWidth(), getScreenHeight());
		player1Text.update();

		player2Text.setScreenSize(getScreenWidth(), getScreenHeight());
		player2Text.update();

		powerText.setScreenSize(getScreenWidth(), getScreenHeight());
		powerText.update();

		cooldownTextLeft.setScreenSize(getScreenWidth(), getScreenHeight());
		cooldownTextLeft.update();

		cooldownTextRight.setScreenSize(getScreenWidth(), getScreenHeight());
		cooldownTextRight.update();

		if (playerCooldown != 0) {
			playerCooldown--;
			cooldownTextRight.setText("Opponent Activated Power! \n Time left: " + (int) (playerCooldown / 60));
		} else {
			cooldownTextRight.setText(" ");
			speedScaleRight = origSpeedScale;
			AINotes = songArray[origAiLevel].getNotes();
		}

		if (aiCooldown != 0) {
			aiCooldown--;
			cooldownTextLeft.setText("Opponent Activated Power! \n Time left: " + (int) (aiCooldown / 60));
		} else {
			cooldownTextLeft.setText(" ");
			speedScaleLeft = origSpeedScale;
		}

		if (powerText.shouldRemove()) {
			powerText.setText(" ");
		}

		for (NoteHitSprite hit : hits) {
			hit.update();
		}

		for (NoteHitSprite hit : aiHits) {
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

		count = (int) (audio.getPlayingTimer().getTimeInMill());

	}

	private void removeSprites(int i, NoteSprite[] noteSprite, PlaySprite playSprite, ArrayList<NoteHitSprite> hits,
			boolean ai) {
		if (noteSprite[i].isRemoved() == false) {
			if (noteSprite[i].getY() >= lineY && noteSprite[i].getY() <= lineY + (3 * noteSprite[i].getHeight())) {
				boolean notesHit = true;
				for (int p = 0; p < notes[p].getButtons().length; p++) {
					if (notes[i].getButtons()[p]) {
						if (keys[p] || ai) {
							hits.add(new NoteHitSprite((playSprite.getX() - playSprite.getWidth() / 2)
									+ (playSprite.getBlockSizeAndGap() / 2) + (p * playSprite.getBlockSizeAndGap()),
									playSprite.getY() + (playSprite.getBlockSize() / 2), playSprite.getBlockSize(),
									playSprite.getBlockSize()));
							if (!ai)
								keys[p] = false;
						} else {
							notesHit = false;
						}

					}
				}
				if (notesHit) {
					int difference;
					if (ai) {
						difference = Math.abs(notes[i].getTime() - AINotes[i].getTime());
					} else {
						difference = Math.abs(noteSprite[i].getY() - 60 - lineY);
					}
					scoreHelper(difference, ai);
					noteSprite[i].remove();
					if (ai) {
						noteSpriteAI[i].remove();
					}
				}

			}

			if (noteSprite[i].getY() > getScreenHeight()) {
				if (noteSprite[i].isRemoved()) {
					n++;
				} else {
					n++;
					bad(ai);
					noteSprite[i].remove();
				}

			}
		}
	}

	@Override
	public void draw(Graphics context) {

		// We use this to draw a dark background
		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());

		playSpriteLeft.draw(context);
		playSpriteRight.draw(context);

		for (int i = 0; i < beat.length; i++) {
			barSpriteLeft[i].draw(context);
			barSpriteRight[i].draw(context);
		}

		for (int i = 0; i < notes.length; i++) {
			if (!noteSpriteLeft[i].isRemoved())
				noteSpriteLeft[i].draw(context);
			if (!noteSpriteRight[i].isRemoved())
				noteSpriteRight[i].draw(context);
			if (!noteSpriteAI[i].isRemoved()) {
				noteSpriteAI[i].setAI();
				noteSpriteAI[i].draw(context);
			}
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

		player1Text.draw(context);
		player2Text.draw(context);

		leftScore.draw(context);
		rightScore.draw(context);

		cooldownTextLeft.draw(context);
		cooldownTextRight.draw(context);

		textAILevel.draw(context);

		powerText.draw(context);

		for (NoteHitSprite hit : hits) {
			hit.draw(context);
		}

		for (NoteHitSprite hit : aiHits) {
			hit.draw(context);
		}

		for (SystemTextCenterFloat floatText : floatTexts) {
			floatText.draw(context);
		}
	}
}
