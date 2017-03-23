package network.Server;

import java.net.Socket;
/**
 * This class is used to save the player data in the server
 * @author Weifeng
 */
public class Player {
	private int score = 0;
	private int select;
	
	private String name;
	
	private boolean isReady;
	private boolean isStarted;
	
	private Socket socket;
	
	
	//this is for client player
	//save client socket
	public Player(Socket _socket) {
		this.socket = _socket;
	}
	
	//this is for server player
	//just save the name 
	public Player(String _name) {
		this.name = _name;
	}

	//save the score of player
	public void setScore(int _score){
		this.score = _score;
	}
	
	public void addScore(int _score){
		this.score = this.score + _score;
	}
	
	//save the name of player
	public void setName(String _name){
		this.name = _name;
	}
		
	//save the ready state of player
	public void setReady(boolean _isReady){
		this.isReady = _isReady;
	}
	
	//save the state of the player is or not playing the game
	public void setStarted(boolean _isStarted){
		this.isStarted = _isStarted;
	}
	
	//@return the Socket of player
	public Socket getSocket(){
		return this.socket;
	}
	
	//@return the score of player
	public int getScore(){
		return this.score;
	}
	
	//@return the name 
	public String getName(){
		return this.name;
	}
	
	//@return the ready state of player
	public boolean isReady(){
		return this.isReady;
	}
	
	//@return the game state of player
	public boolean isStarted(){
		return this.isStarted;
	}
	
	public void setSelect(int _select){
		this.select = _select;
	}
	
	public int getSelect(){
		return this.select;
	}
}
