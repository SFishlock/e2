package network.Server;

import java.io.IOException;
import java.io.PrintStream;

import engine.GameObject;


/**
 * This class is used to handle sending the message to the client
 * @author Weifeng
 */
public class SelfAction {
	private Player me;
	private Player opponent;
	private PrintStream toOppo;
	private GameObject gameObject;
	
	public SelfAction(GameObject _gameObject,Player _opponent, Player _me){
		this.gameObject = _gameObject;
		this.opponent = _opponent;//opponent is client player
		this.me = _me;//me is server player
		
		try {
			toOppo = new PrintStream(_opponent.getSocket().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * set the name of the player
	 * @param _name name of the opponent
	 */
	public void setName(String _name){
		me.setName(_name);
		gameObject.setP1Name(_name);
		System.out.println("your name is " + me.getName() + " Your opponent name is " + opponent.getName());
		toOppo.println("Your name is " + opponent.getName() + " Your opponent name is " + me.getName());
		toOppo.println("NAME:"+_name);
	}
	
	/**
	 * to set both the players are ready to start the game
	 */
	public void setReady(){
		me.setReady(true);
		me.setScore(0);
		if (me.isReady() && opponent.isReady()){
			System.out.println("All players are ready, Loding Game...");
			toOppo.println("All players are ready, Loding Game...");
			setStart();
		}else if (me.isReady() && !opponent.isReady()){
			System.out.println("You are ready now, wait your oppoent...");
			toOppo.println("Your opponent is ready, wait you...");
		}else if (!me.isReady() && opponent.isReady()){
			System.out.println("Your opponent is ready, wait you...");
			toOppo.println("You are ready now, wait your oppoent...");
		}
	}
	
	/**
	 * to start the game
	 */
	public void setStart(){
		me.setStarted(true);
		System.out.println("Game Start");
		toOppo.println("Game Start");
		toOppo.println("SONG:" + me.getSelect());
		toOppo.println("LOAD:");//send key word to client to start game
		gameObject.setReady(true);

	}
	
	/**
	 * to update score of the players
	 * @param _score the score
	 */
	public void updateScore(String _score){
		int score = Integer.parseInt(_score);
		me.addScore(score);
		System.out.println("Your score is " +me.getScore() + " Your oppo score is " + opponent.getScore());
		toOppo.println("Your score is " +opponent.getScore() + " Your oppo score is " + me.getScore());
		toOppo.println("SCOR:"+me.getScore());
	}
	
	
	/**
	 * to finish the game
	 */
	public void gameOver(){
		me.setStarted(false);
		me.setReady(false);
		opponent.setReady(false);
		opponent.setStarted(false);
		System.out.println("Game Over, your opponent win");
		toOppo.println("Your opponent game over, you win");
		toOppo.println("OVER:");//send key word to client to end game
	}
	
	/**
	 * send the pressed key
	 * @param _key the key that pressed
	 */
	public void sendPressedKey(String _key){
		toOppo.println("PREE:"+_key);
	}
	
	/**
	 * send the released key
	 * @param _key the key that released
	 */
	public void sendReleasedKey(String _key){
		toOppo.println("RELE:"+_key);
	}
	
	/**
	 * send the power
	 * @param _power the power to be sent
	 */
	public void sendPower(String _power) {
		toOppo.println("POWE:"+_power);
	}

	/**
	 * send the combo
	 * @param _combo the combo to be sent
	 */
	public void sendCombo(String _combo) {
		toOppo.println("COMB:"+_combo);
	}

	/**
	 * send the playing text
	 * @param _text the playing text to be sent
	 */
	public void sendText(String _text) {
		toOppo.println("TEXT:"+_text);
	}
	
	public void invalidMsg(){
		String msg = "Invalid Message";
		System.out.println(msg);
	}

	public void sendSelect(String _select){
		me.setSelect(Integer.parseInt(_select));
	}
	
	
}
