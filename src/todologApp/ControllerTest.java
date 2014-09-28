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
	
	@Test
	public void testCreateCommand() {
		Command toBeTested = Controller.createCommand("add \"group meeting\"");
		CommandAdd same = new CommandAdd(new Task("\"group meeting\""));
		assertEquals("...",same.getTask().getTaskName(), ((CommandAdd) toBeTested).getTask().getTaskName());	
	}
}
