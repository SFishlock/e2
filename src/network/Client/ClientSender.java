package network.Client;

import java.io.*;

import network.Message;
import network.MessageQueue;

/**
 * This class is to send messages to server
 * @author Weifeng
 */
public class ClientSender extends Thread {
	private PrintStream toServer;
	private MessageQueue sendQueue;
	
	public ClientSender(PrintStream _server, MessageQueue _sendQueue){
		this.toServer = _server;
		this.sendQueue = _sendQueue;
	}
	
	
	public void run(){
		while (true){
			Message sendMsg = sendQueue.take();
			
			//if there are messages in the queue
			//send it to server
			if (sendMsg != null){

				toServer.println(sendMsg.getMessage());
			}
		}
	}
}
