package network.Server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;

public class PlayerTest {
	Socket socket;

	@Test
	public void test() {
		Player p = new Player("localhost");
		Player p2 = new Player(socket);
		
		
		assertEquals(0, p.getScore());
		p.addScore(100);
		assertEquals(100, p.getScore());
		p.setScore(110);
		assertEquals(110, p.getScore());
		p.setName("P1");
		assertEquals("P1", p.getName());
		p.setReady(false);
		assertFalse(p.isReady());
		p.setStarted(false);
		assertFalse(p.isStarted());
		p.setSelect(1);
		assertEquals(1, p.getSelect());
		assertNull(p2.getSocket());
	}

}
