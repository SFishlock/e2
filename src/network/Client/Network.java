package network.Client;

import engine.GameObject;
import network.Message;
import network.MessageQueue;

/**
 * This class is for user connecting, sending and receiving
 * @author Weifeng
 */
public class Network{

	private MessageQueue sendQueue = new MessageQueue();;
	private MessageQueue receiveQueue = new MessageQueue();;
	private boolean isConnected = false;
	
	/**
	 * start connecting
	 * @param hostname of server
	 * @param name of client
	 */
	public Network(GameObject gameObject, String hostname, String name){
		Client c = new Client(gameObject, hostname, sendQueue, receiveQueue);
		c.start();
		(new ClientResolve(gameObject,this)).start();
		Message msg = new Message("NAME:" + name);
		this.sendQueue.offer(msg);
		isConnected = true;
	}
	
	//send message
	public void send(String _readline){
		Message msg = new Message(_readline);
		this.sendQueue.offer(msg);
	}
	
	//send a ready message
	public void sendReadyMsg(){
		Message msg = new Message("READ:");
		this.sendQueue.offer(msg);
	}
	
	//send a gameover message
	public void sendGameOverMsg(){
		Message msg = new Message("OVER:");
		this.sendQueue.offer(msg);
	}
	
	
	//check the receive queue is or not empty
	public boolean receiveMessage(){
		return this.receiveQueue.isEmpty();
	}
	
	
	//@return the messages from server
	public String receive(){
		return this.receiveQueue.take().getMessage();
	}
	
	
}
