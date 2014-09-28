package todologApp;

import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {

	@Test
	public void testGetFirstWord() {
		assertEquals("first line should be add", "add", 
				Controller.getFirstWord("add group meeting"));
		assertEquals("first line should be add", "add", 
				Controller.getFirstWord("add \"group meeting\""));
	}

	@Test
	public void testGetTheRestOfTheString() {
		assertEquals("first line should be group meeting", "group meeting", 
				Controller.getTheRestOfTheString("add group meeting"));
		assertEquals("first line should be group meeting", "\"group meeting\"", 
				Controller.getTheRestOfTheString("add \"group meeting\""));
	}
}
