package network;

import static org.junit.Assert.*;

import org.junit.Test;

public class MessageTest {

	@Test
	public void test() {
		String test = "test";
		Message msg = new Message(test);
		assertSame(test, msg.getMessage());
		
	}

}
