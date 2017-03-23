package network.Server;

import java.io.*;

import engine.GameObject;
import network.MessageQueue;

/**
 * This class is to get messages from user
 * @author Administrator
 */
public class ServerThread extends Thread{
	private Player opponent;
	private Player me;
	private MessageQueue serverInput;
	private GameObject gameObject;
	
	public ServerThread(GameObject _gameObject,MessageQueue _serverInput,Player _opponent,Player _me){
		super("ServerThread");
		this.gameObject = _gameObject;
		this.serverInput = _serverInput;
		this.opponent = _opponent;
		this.me = _me;
	}
	
	public void run(){
		
		PrintStream toClient;
		BufferedReader fromClient;
	
		try {
			toClient = new PrintStream(opponent.getSocket().getOutputStream());
			fromClient = new BufferedReader(new InputStreamReader(opponent.getSocket().getInputStream()));
			
			String readLine;

			OppoResolve solve = new OppoResolve(gameObject,opponent,me);
			new SelfResolve(gameObject,serverInput,opponent,me).start();
			
			
			// for now just return any messages get from player
			// will use ServerResolve to resolve messages later
			while ( (readLine = fromClient.readLine()) !=null ){
				System.out.println("got message "+ readLine +" from " + opponent.getName());
				toClient.println("I got you message : "+ readLine);
				solve.resolve(readLine);
			}
			
		} catch (IOException e) {
			gameObject.setNetworkError("Player " + opponent.getName() + " disconnected");
			System.out.println("Player " + opponent.getName() + " disconnected");
		}
	}
	
}
