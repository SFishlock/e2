package network.Client;

import java.io.*;

import engine.GameObject;
/**
 * This class is just for test 
 * @author Administrator
 */
public class Test {
	public static void main(String[] args){
		
		String hostname = "localhost";
		String name = "Client";
		
		GameObject gameObject = new GameObject(800, 600);
		Network n = new Network(gameObject,hostname,name);
		
		//start receive test
		(new ClientResolve(gameObject,n)).start();
				
		//send any input from user to server
		try{
			BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
			String userInput;
			while ((userInput = fromUser.readLine()) != null){
				n.send(userInput);
			}
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}	
	}
}
