package screens;

import java.awt.Color;
import java.awt.Graphics;

import audio.SoundHandler;
import engine.GameObject;
import engine.Screen;
import input.InputHandler;
import sprites.DotSpriteBackground;
import sprites.FancyCenterTextSprite;
import sprites.ImageGrad;
import sprites.SystemText;
import sprites.SystemTextCenterShine;
import sprites.SystemTextKern;
import sprites.SystemTextScore;
import utils.ColorPack;
/**
 * 
 * @author Bobby Dilley
 *
 */
public class EndScreen extends Screen {

	private SystemTextKern textSprite;
	private SystemTextKern textSpritePoor;
	private SystemTextKern textSpriteExcelent;

	private DotSpriteBackground dotBackground;
	private ImageGrad imageGrad;
	private FancyCenterTextSprite title;

	private SystemTextScore player1Text;
	private SystemTextScore player2Text;
	private SystemText playerText;
	private SystemText playerText2;
	private int player1Score;
	private int player2Score;
	private int[] howWell;
	private int[] howWellBefore;
	private int scoreOut1 = 0;
	private int scoreOut2 = 0;
	private boolean played = false;
	int count = 0;
	private boolean won;

	private SystemTextCenterShine resultText;

	private SoundHandler fx;
	String[] fxlist = { "move.wav", "titlesongquiet.wav" };

	public EndScreen(GameObject gameObject) {
		super(gameObject);
		fx = new SoundHandler();

		fx.fillEffects(fxlist);
		// get the player's scores from the game object

		player1Score = gameObject.getP1Score();
		player2Score = gameObject.getP2Score();

		playerText = new SystemText(getScreenWidth() - 214, 92, "Player 1");
		playerText2 = new SystemText(getScreenWidth() - 214, 92 + 150, "Player 2");
		player1Text = new SystemTextScore(getScreenWidth() - 200, 120, "");
		player1Text.shine();
		player2Text = new SystemTextScore(getScreenWidth() - 200, 270, "" + player2Score);

		whoWon();
		if (won) {
			resultText = new SystemTextCenterShine((getScreenWidth() / 2) - 129, 170, "YOU WIN!");
			resultText.shine();
		} else if (player2Score > player1Score) {
			resultText = new SystemTextCenterShine((getScreenWidth() / 2) - 129, 170, "YOU LOSE!");
		} else {
			resultText = new SystemTextCenterShine((getScreenWidth() / 2) - 129, 170, "TIE!");
		}

		resultText.setFontSize(0.08);

		imageGrad = new ImageGrad();
		imageGrad.setOpacity(1);

		dotBackground = new DotSpriteBackground(10, 10, 20, 30, false, getScreenWidth(), getScreenHeight());
		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());

		title = new FancyCenterTextSprite((int) (getScreenWidth() * 0.94), (int) (getScreenHeight() * 0.85), "RESULTS");
		title.setScreenSize(getScreenWidth(), getScreenHeight());

		textSprite = new SystemTextKern(20, 30, "ACHIEVEMENT");

		textSpritePoor = new SystemTextKern(99, 410, "POOR");
		textSpriteExcelent = new SystemTextKern(307, 410, "EXCELLENT");

		howWell = new int[7];
		howWellBefore = new int[7];
		for (int i = 0; i < howWellBefore.length; i++)
			howWellBefore[i] = 0;

		howWell[0] = getGameObject().getScoreQuality()[4];
		howWell[1] = getGameObject().getScoreQuality()[3];
		howWell[2] = getGameObject().getScoreQuality()[2];
		howWell[3] = (getGameObject().getScoreQuality()[2] + getGameObject().getScoreQuality()[1]) / 2;
		howWell[4] = getGameObject().getScoreQuality()[1];
		howWell[5] = (getGameObject().getScoreQuality()[1] + getGameObject().getScoreQuality()[0]) / 2;
		howWell[6] = getGameObject().getScoreQuality()[0];
		/*
		System.out.println(getGameObject().getScoreQuality()[4]);
		System.out.println(getGameObject().getScoreQuality()[3]);
		System.out.println(getGameObject().getScoreQuality()[2]);
		System.out.println(getGameObject().getScoreQuality()[1]);
		System.out.println(getGameObject().getScoreQuality()[0]);
		*/
	}
	
	@Override
	public void keyPressed(int key) {
		if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute pressed");
		}
	}
	
	@Override
	public void keyReleased(int key) {
		if (key == InputHandler.MUTEKEY) {
			System.out.println("Mute unpressed");
		}
	}

	public void whoWon(){
		if (player1Score < player2Score)
			this.won = false;
		else 
			this.won = true;
	}
	
	@Override
	public void update() {

		if (count >= 100) {
			if (scoreOut1 < player1Score) {
				scoreOut1 += 1 + (int) (player1Score / (int) (player1Score * 0.01));
			} else {
				if (played == false) {
					played = true;
					// fx.playEffect("titlesongquiet.wav");
				}
			}

			if (scoreOut2 < player2Score) {
				scoreOut2 += 1 + (int) (player2Score / (int) (player2Score * 0.01));
			}
		}

		player1Text.setText("" + player1Score);
		player2Text.setText("" + player2Score);

		playerText.setScreenSize(getScreenWidth(), getScreenHeight());
		playerText2.setScreenSize(getScreenWidth(), getScreenHeight());
		player1Text.setScreenSize(getScreenWidth(), getScreenHeight());
		player2Text.setScreenSize(getScreenWidth(), getScreenHeight());
		resultText.setScreenSize(getScreenWidth(), getScreenHeight());
		textSpritePoor.setScreenSize(getScreenWidth(), getScreenHeight());
		textSpriteExcelent.setScreenSize(getScreenWidth(), getScreenHeight());

		title.setScreenSize(getScreenWidth(), getScreenHeight());
		title.update();

		imageGrad.setScreenSize(getScreenWidth(), getScreenHeight());
		imageGrad.update();

		textSprite.setScreenSize(getScreenWidth(), getScreenHeight());
		textSprite.update();

		dotBackground.setScreenSize(getScreenWidth(), getScreenHeight());
		dotBackground.update();

		for (int i = 0; i < howWell.length; i++) {

			if (howWell[i] > howWellBefore[i]) {
				howWellBefore[i] += 1;
			}
		}
		count++;
	}

	@Override
	public void draw(Graphics context) {
		context.setColor(ColorPack.DARK);
		context.fillRect(0, 0, getScreenWidth(), getScreenHeight());

		context.setColor(ColorPack.WHITE);
		context.drawLine(0, 48, 199, 48);

		// Draw the player1 score line
		context.setColor(ColorPack.WHITE);
		context.drawLine(800 - 220, 148, 800, 148);
		context.fillOval(800 - 220 - 4, 148 - 4, 8, 8);

		// Draw the player2 score line
		context.setColor(ColorPack.WHITE);
		context.drawLine(800 - 220, 298, 800, 298);
		context.fillOval(800 - 220 - 4, 298 - 4, 8, 8);

		// Draw the graph

		context.setColor(ColorPack.WHITE);
		context.drawLine(100, 400, 100 + 340, 400);

		for (int i = 100; i < 100 + 340 + 10; i += 50) {

			int height = howWellBefore[(i - 100) / 50];
			context.setColor(new Color(ColorPack.PRIMARY.getRed(), ColorPack.PRIMARY.getGreen(),
					ColorPack.PRIMARY.getBlue(), height));
			context.fillRect(i, 400 - 10 - height, 40, height);
		}

		playerText.draw(context);
		playerText2.draw(context);
		player1Text.draw(context);
		player2Text.draw(context);

		imageGrad.draw(context);

		dotBackground.draw(context);

		textSprite.draw(context);

		title.draw(context);

		resultText.draw(context);

		textSpritePoor.draw(context);
		textSpriteExcelent.draw(context);
	}

}
