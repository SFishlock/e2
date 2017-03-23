package network;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageQueueTest {

	@Test
	public void test() {
		Message msg = new Message("test");
		MessageQueue msgq = new MessageQueue();
		
		msgq.offer(msg);
		assertFalse(msgq.isEmpty());
		
		assertSame(msg, msgq.take());
		
		
	}

}
