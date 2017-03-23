package network.Server;

import java.io.*;

import engine.GameObject;


/**
 * This class is used to handle receiving the message from the client
 * @author Weifeng
 */
public class OppoAction {
	private Player opponent;
	private Player me;
	private PrintStream toOppo;
	private GameObject gameObject;
	
	
	public OppoAction(GameObject _gameObject, Player _opponent,Player _me){
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
		opponent.setName(_name);
		gameObject.setP2Name(_name);
		toOppo.println("Your name is " + opponent.getName() + " Your opponent name is " + me.getName());
		System.out.println("Your name is " + me.getName() + " Your opponent name is " + opponent.getName());
	}
		
	/**
	 * to set both the players are ready to start the game
	 */
	public void setReady(){
		opponent.setReady(true);
		opponent.setScore(0);
		if (me.isReady() && opponent.isReady()){
			toOppo.println("All players are ready, Loding Game...");
			System.out.println("All players are ready, Loding Game...");
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
		opponent.setStarted(true);
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
		opponent.addScore(score);
		System.out.println("Your score is " +me.getScore() + " Your oppo score is " + opponent.getScore());
		toOppo.println("Your score is " +opponent.getScore() + " Your oppo score is " + me.getScore());
		gameObject.setP2Score(opponent.getScore());
	}
	
	/**
	 * to finish the game
	 */
	public void gameOver(){
		me.setStarted(false);
		me.setReady(false);
		opponent.setReady(false);
		opponent.setStarted(false);
		toOppo.println("Game Over, your opponent win");
		System.out.println("Your opponent game over, you win");
		toOppo.println("OVER:");//send key word to client to end game
	}
	
	/**
	 * handle the received key
	 * @param _key the pressed key that received
	 */
	public void receivedPressedKey(String _key){
		int key = Integer.parseInt(_key);
		gameObject.receivedKeyPressed(key);
	}
	
	/**
	 * handle the released key
	 * @param _key the released key that received
	 */
	public void receivedReleasedKey(String _key){
		int key = Integer.parseInt(_key);
		gameObject.receivedKeyReleased(key);
	}
	
	/**
	 * handle the received power
	 * @param _power the power that received
	 */
	public void receivedPower(String _power) {
		int power = Integer.parseInt(_power);
		gameObject.setP2Power(power);
	}

	/**
	 * handle the received combo
	 * @param _combo the combo that received
	 */
	public void receivedCombo(String _combo) {
		int combo = Integer.parseInt(_combo);
		gameObject.setP2Combo(combo);
	}

	/**
	 * handle the received playing text
	 * @param _text the playing text that received
	 */
	public void receivedText(String _text) {
		gameObject.setP2Text(_text);
	}
	
	/**
	 * handle the invalid message command
	 */
	public void invalidMsg(){
		String msg = "Invalid Message";
		toOppo.println(msg);
	}


	
	
}
