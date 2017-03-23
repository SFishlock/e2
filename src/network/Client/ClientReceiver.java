package network.Client;

import java.io.*;

import network.Message;
import network.MessageQueue;

/**
 * This class is to get messages from server
 * @author Weifeng
 */
public class ClientReceiver extends Thread {
	private BufferedReader fromServer;
	private MessageQueue receiveQueue;
	
	public ClientReceiver(BufferedReader _server, MessageQueue _receiveQueue){
		super();
		this.fromServer = _server;
		this.receiveQueue = _receiveQueue;
	}
	
	public void run(){
		try{
			while(true){
				String readline = fromServer.readLine();
				if(readline != null){

					// if get messages, offer it to the messages queue
					Message msg = new Message(readline);
					receiveQueue.offer(msg);
				}
			}		
		}catch(IOException e){
			e.getMessage();
		}
	}
}
