package screens;

import java.awt.Graphics;
import java.util.ArrayList;
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
 * @author Nicholas Orgill
 *
 */
public class NetworkPlayScreen extends Screen {
	private SongFileProcessor reader;
	private SongObject song;
	private Beat[] beat;
	private Note[] notes;
	int score = 0;
	int oppoScore = 0;
	boolean[] keys = {false, false, false, false};
	boolean[] oppoKeys = {false, false, false, false};
	final int MAX_NOTE_SCORE = 200;
	final int MAX_NOTE_RANGE = 200;
	int combo = 0;
	int power = 0;

	int lineY = (int) Math.round(getScreenHeight() * 0.8);
	
	private int count = 0; // A variable to count on the screen
	private SystemTextCenterFloat leftScore;
	private SystemTextCenterFloat rightScore;
	private SystemTextCenter player1Text;
	private SystemTextCenter player2Text;
	private SystemTextCenterFloat powerText;
	
	private Player audio = new Player(getGameObject());
	private SongFile songFile;
	private boolean complete = false;
	
	private int playerCooldown;
	private int oppoCooldown;
	private SystemTextCenter cooldownTextLeft;
	private SystemTextCenter cooldownTextRight;
	private PlaySprite playSpriteLeft;
	private PlaySprite playSpriteRight;
	private BarSprite[] barSpriteLeft;
	private BarSprite[] barSpriteRight;
	private NoteSprite[] noteSpriteLeft;
	private NoteSprite[] noteSpriteRight;

	
	private int[] scoreQuality = new int[5];

	private ArrayList<NoteHitSprite> hits = new ArrayList<NoteHitSprite>();
	private ArrayList<NoteHitSprite> oppoHits = new ArrayList<NoteHitSprite>();
	
	private double origSpeedScale = 0.4;
	private double speedScaleLeft = 0.4;
	private double speedScaleRight = 0.4;
	
	SongArray[] songArray;
	
	int n = 0;
	int oppoN = 0;
	
	private ArrayList<SystemTextCenterFloat> floatTexts = new ArrayList<SystemTextCenterFloat>();
	
	public NetworkPlayScreen(GameObject gameObject) {
		super(gameObject);
		leftScore = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 105, "0");
		rightScore = new SystemTextCenterFloat((int) (getScreenWidth() * 0.75), 105, "0");

		playSpriteLeft = new PlaySprite(0, 0, 0, 0, 0.25);
		playSpriteRight = new PlaySprite(0, 0, 0, 0, 0.75);

		player1Text = new SystemTextCenter((int) (getScreenWidth() * 0.25), 25, "Player");
		player2Text = new SystemTextCenter((int) (getScreenWidth() * 0.75), 25, "Opponent");	
		
		cooldownTextLeft = new SystemTextCenter((int) (getScreenWidth() * 0.25), 105, " ");
		cooldownTextRight  = new SystemTextCenter((int) (getScreenWidth() * 0.75), 105, " ");
		
		powerText = new SystemTextCenterFloat(0, 0, " ");

	}
	
	@Override
	public void keyPressed(int key) {
		if (key == InputHandler.POWERKEY) {
			System.out.println("on p");
			displayPowerOppo(key);
		} else if (key == InputHandler.MUTEKEY) {
			getGameObject().setMute();
			System.out.println(getGameObject().isMute());
		} else {
			keys[key] = true;
			System.out.println("on" + key);
			playSpriteLeft.push(key);
			sendPressedKey(key);
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
		sendReleasedKey(key);
	}
	
	@Override
	public void oppoKeyPressed(int key) {
		if (key == InputHandler.POWERKEY) {
			System.out.println("on p");
			displayPowerPlayer();
		} else {
			oppoKeys[key] = true;
			System.out.println("on" + key);
			playSpriteRight.push(key);
		}
	}
	
	public void oppoKeyReleased(int key){
		if (key == InputHandler.POWERKEY) {
			System.out.println("off p");
		} else {
			oppoKeys[key] = false;
			System.out.println("off" + key);
			playSpriteRight.unpush(key);
		}
	}
	
	private void displayPowerOppo(int key) {
		if (power >= 50){
			sendPressedKey(key);
			powerText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.5), 340, "POWER USED!");
			speedScaleRight = Math.min(speedScaleRight + 0.2, 2);
			oppoCooldown = 600;
			cooldownTextRight.setText("Opponent Activated Power! \n Time left: " + (int)(oppoCooldown/60));
			power = 0;
		} else {
			powerText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 280, "NOT ENOUGH POWER!");
		}

	}
	
	private void displayPowerPlayer() {
		powerText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.5), 340, "POWER USED!");
		playerCooldown = 600; 
		cooldownTextLeft.setText("Opponent Activated Power! \n Time left: " + (int)(playerCooldown/60));
		speedScaleLeft = Math.min(speedScaleLeft + 0.2, 2);
	}
	
	public void scoreHelper(int difference, boolean oppo) {
		if(oppo){
			if(getGameObject().getP2Text() != null){
				if(getGameObject().getP2Text().equals("BAD")){
					SystemTextCenterShake floatText = new SystemTextCenterShake((int) (getScreenWidth() * 0.75), 280, "BAD");
					floatText.shine();
					floatTexts.add(floatText);
				}else{
					SystemTextCenterFloat floatText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.75), 280, getGameObject().getP2Text());
					floatText.shine();
					floatTexts.add(floatText);
				}
			}
			
			
		}else{
			if (difference <= getGameObject().PERFECT) {
				combo++;
				if(combo > 5) {
					power+=combo;
				}
				score += 100;
				sendScore(100);
				sendCombo(combo);
				scoreQuality[0]++;
				SystemTextCenterFloat floatText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 280, "PERFECT");
				sendText("PERFECT");
				floatText.shine();
				floatTexts.add(floatText);
				
		} else if (difference <= getGameObject().EXCELLENT) {
				combo++;
				if(combo > 5) {
					power+=combo;
				}
				score += 75;
				sendScore(75);
				sendCombo(combo);
				scoreQuality[1]++;
				SystemTextCenterFloat floatText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 280, "EXCELLENT");
				sendText("EXCELLENT");
				floatText.shine();
				floatTexts.add(floatText);
		} else if (difference <= getGameObject().GOOD) {
				combo++;
				power-=10;
				score += 50;
				sendScore(50);
				sendCombo(combo);
				scoreQuality[2]++;
				SystemTextCenterFloat floatText = new SystemTextCenterFloat((int) (getScreenWidth() * 0.25), 280, "GOOD");
				sendText("GOOD");
				floatText.shine();
				floatTexts.add(floatText);
		} else if (difference <= getGameObject().OKAY) {
				power-=10;
				combo = 0;
				score += 25;
				sendScore(25);
				sendCombo(combo);
				scoreQuality[3]++;
		} else {
			scoreQuality[4]++;
			bad();
		}
		
		
		leftScore.setText("" + score);
		power = Math.min(100, Math.max(0, power));
		sendPower(power);
		player1Text.setText("COMBO: " + combo + "POWER: " + power + "%");
		}
		
		rightScore.setText("" + getGameObject().getP2Score());
		player2Text.setText("COMBO: " + getGameObject().getP2Combo() + "POWER: " + getGameObject().getP2Power() + "%");
	}
	
	
	public void bad() {
			if (power > 0) power--;
			combo = 0;
			sendCombo(combo);
			sendPower(power);
			SystemTextCenterShake floatText = new SystemTextCenterShake((int) (getScreenWidth() * 0.25), 280, "BAD");
			sendText("BAD");
			floatText.shine();
			floatTexts.add(floatText);
			player1Text.setText("COMBO: " + combo + "POWER: " + power + "%");
	}
	
	@Override
	public void update() {
		
		if(getGameObject().isMute()) {
			audio.getAudioPlayer().stop();
		}
		
		int lineY = (int) Math.round(getScreenHeight() * 0.8);
		

		if (audio.getAudioPlayer().playCompleted) {
			if (!complete){
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
			audio.playBack(songFile.getAudioInputPath());
			
			beat = song.getBeats();
			notes = song.getNotes();	
			
			barSpriteLeft = new BarSprite[beat.length];
			barSpriteRight = new BarSprite[beat.length];
			noteSpriteLeft = new NoteSprite[notes.length];
			noteSpriteRight = new NoteSprite[notes.length];

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
		}

		for (int i = 0; i < beat.length; i++) {
			barSpriteLeft[i].setY((int) (lineY - (beat[i].getTime() - count) * speedScaleLeft));
			barSpriteLeft[i].update();
			barSpriteRight[i].setY((int) (lineY - (beat[i].getTime() - count) * speedScaleRight));
			barSpriteRight[i].update();
		}

		for (int i = n; i < notes.length; i++) {
			noteSpriteLeft[i].setScreenSize(getScreenWidth(), getScreenHeight());
			noteSpriteLeft[i].update();
			noteSpriteLeft[i].setY(
					(int) (lineY - (notes[i].getTime() - count) * speedScaleLeft) + (noteSpriteLeft[i].getLength() / 3));

			noteSpriteRight[i].setScreenSize(getScreenWidth(), getScreenHeight());
			noteSpriteRight[i].update();
			noteSpriteRight[i].setY(
					(int) (lineY - (notes[i].getTime() - count) * speedScaleRight) + (noteSpriteRight[i].getLength() / 3));

			// If the note is in the playing area
			if (noteSpriteLeft[i].isRemoved() == false) {
				if (noteSpriteLeft[i].getY() >= lineY && noteSpriteLeft[i].getY() <= lineY + (3 * noteSpriteLeft[i].getHeight())) {
					boolean notesHit = true;
					for (int p = 0; p < notes[p].getButtons().length; p++) {
						if (notes[i].getButtons()[p]) {
							if (keys[p]) {
								hits.add(new NoteHitSprite((playSpriteLeft.getX() - playSpriteLeft.getWidth() / 2)
										+ (playSpriteLeft.getBlockSizeAndGap() / 2) + (p * playSpriteLeft.getBlockSizeAndGap()),
										playSpriteLeft.getY() + (playSpriteLeft.getBlockSize() / 2), playSpriteLeft.getBlockSize(),
										playSpriteLeft.getBlockSize()));
								keys[p] = false;
							} else {
								notesHit = false;
							}
						}
					}
					if (notesHit) {
						int difference = Math.abs(noteSpriteLeft[i].getY() - 60 - lineY);
						scoreHelper(difference, false);
						noteSpriteLeft[i].remove();
					}
				}
				// When the note is finished, increment the start of the array
				if (noteSpriteLeft[i].getY() > getScreenHeight()) {
					if(noteSpriteLeft[i].isRemoved()) {
						n++;
					} else {
						n++;
						bad();
					}
					
				}
			}
			
		}
		
		
		for (int i = oppoN; i < notes.length; i++) {	
			// If the note is in the playing area
			if (noteSpriteRight[i].isRemoved() == false) {
				if (noteSpriteRight[i].getY() >= lineY && noteSpriteRight[i].getY() <= lineY + (3 * noteSpriteRight[i].getHeight())) {
					boolean notesHit = true;
					for (int p = 0; p < notes[p].getButtons().length; p++) {
						if (notes[i].getButtons()[p]) {
							if (oppoKeys[p]) {
								oppoHits.add(new NoteHitSprite((playSpriteRight.getX() - playSpriteRight.getWidth() / 2)
										+ (playSpriteRight.getBlockSizeAndGap() / 2) + (p * playSpriteRight.getBlockSizeAndGap()),
										playSpriteRight.getY() + (playSpriteRight.getBlockSize() / 2), playSpriteRight.getBlockSize(),
										playSpriteRight.getBlockSize()));
								oppoKeys[p] = false;
							} else {
								notesHit = false;
							}
						}
					}
					if (notesHit) {
						int difference = Math.abs(noteSpriteRight[i].getY() - 60 - lineY);
						scoreHelper(difference, true);
						noteSpriteRight[i].remove();
					}
				}
				// When the note is finished, increment the start of the array
				if (noteSpriteRight[i].getY() > getScreenHeight()) {
					if(noteSpriteRight[i].isRemoved()) {
						oppoN++;
					} else {
						oppoN++;
					}
					
				}
			}
			
		}

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
		
		if (playerCooldown != 0){
			playerCooldown--;
			cooldownTextLeft.setText("Opponent Activated Power! \n Time left: " + (int)(playerCooldown/60));
		} else {
			cooldownTextLeft.setText(" ");
			speedScaleLeft = origSpeedScale;
		}
		
		if (oppoCooldown != 0){
			oppoCooldown--;
			cooldownTextRight.setText("Opponent Activated Power! \n Time left: " + (int)(oppoCooldown/60));
		} else {
			cooldownTextRight.setText(" ");
			speedScaleRight = origSpeedScale;
		}
		
		
		if(powerText.shouldRemove()) {
			powerText.setText(" ");
		}

		

		for (NoteHitSprite hit : hits) {
			hit.update();
		}
		
		for (NoteHitSprite hit : oppoHits) {
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
		
		for (int i = oppoHits.size() - 1; i > 0; i--) {
			if (oppoHits.get(i).shouldRemove()) {
				oppoHits.remove(i);
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
		playSpriteLeft.draw(context);
		playSpriteRight.draw(context);


		for (int i = 0; i < beat.length; i++) {
			barSpriteLeft[i].draw(context);
			barSpriteRight[i].draw(context);
		}

		for (int i = n; i < notes.length; i++) {
			if (!noteSpriteLeft[i].isRemoved())
				noteSpriteLeft[i].draw(context);
			if (!noteSpriteRight[i].isRemoved())
				noteSpriteRight[i].draw(context);
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
		
		powerText.draw(context);
		

		for (NoteHitSprite hit : hits) {
			hit.draw(context);
		}
		
		for (NoteHitSprite hit : oppoHits) {
			hit.draw(context);
		}

		for (SystemTextCenterFloat floatText : floatTexts) {
			floatText.draw(context);
		}

	}
	
	public void sendScore(int _score){
		
		if (getGameObject().isServer()){
			getGameObject().getServer().inputMessage("SCOR:"+_score);
		}else{
			getGameObject().getNetwork().send("SCOR:"+_score);
		}
	}
	
	public void sendPressedKey(int _key){
		
		if (getGameObject().isServer()){
			getGameObject().getServer().inputMessage("PREE:"+_key);
		}else{
			getGameObject().getNetwork().send("PREE:"+_key);
		}
	}
	
	public void sendReleasedKey(int _key){
		
		if (getGameObject().isServer()){
			getGameObject().getServer().inputMessage("RELE:"+_key);
		}else{
			getGameObject().getNetwork().send("RELE:"+_key);
		}
	}
	
	public void sendPower(int _power){
		
		if (getGameObject().isServer()){
			getGameObject().getServer().inputMessage("POWE:"+_power);
		}else{
			getGameObject().getNetwork().send("POWE:"+_power);
		}
	}
	
	public void sendCombo(int _combo){
		
		if (getGameObject().isServer()){
			getGameObject().getServer().inputMessage("COMB:"+_combo);
		}else{
			getGameObject().getNetwork().send("COMB:"+_combo);
		}
	}
	
	public void sendText(String _text){
		
		if (getGameObject().isServer()){
			getGameObject().getServer().inputMessage("TEXT:"+_text);
		}else{
			getGameObject().getNetwork().send("TEXT:"+_text);
		}
	}
}
