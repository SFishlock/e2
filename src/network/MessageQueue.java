package network;


import java.util.concurrent.*;

/**
 * This class is to save messages in a blocking queue
 * @author Weifeng
 */
public class MessageQueue {
	private BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
	
	/**
	 * Insert the message in the queue
	 */
	public void offer(Message m){
		queue.offer(m);
	}
	
	/**
	 * Retrieves and removes the message from the head of this queue
	 */
	public Message take(){
		while (true){
			try{
				return (queue.take());
			}catch(InterruptedException e){
				
			}
		}
	}
	
	/**
	 * @return whether the queue is empty
	 */
	public boolean isEmpty(){
		return queue.isEmpty();
	}
}
