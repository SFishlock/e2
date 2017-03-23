package network.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import engine.GameObject;
import network.Message;
import network.MessageQueue;

public class ServerMain {
	
	public static void main(String[] args){
		
		MessageQueue serverInput = new MessageQueue();
		String name = "Admin";
		GameObject gameObject = new GameObject(800, 600);
		
		Server server = new Server(gameObject,serverInput,name);
		server.start();
		System.out.println("start");
		
		try{
			BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));
			String userInput;
			while ((userInput = fromUser.readLine()) != null){
				Message msg = new Message(userInput);
				serverInput.offer(msg);
			}
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}	
	}
}
