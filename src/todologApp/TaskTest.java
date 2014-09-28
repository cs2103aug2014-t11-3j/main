package todologApp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTest {

	@Test
	public void testParseName() {
		assertEquals("first line should be group meeting", "group meeting", 
				Task.parseName("\"group meeting\""));
	}

}
