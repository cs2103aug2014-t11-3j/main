package todologApp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTest {

	@Test
	public void testParseName() {
		Task task = new Task ("\"group meeting\"");
		assertEquals("output should be group meeting", "group meeting" , task.getTaskName());
	}
	
	@Test
	public void testParseType() {
		Task task = new Task ("\"group meeting\"");
		assertEquals("output should be floating", TaskType.FLOATING , task.getTaskType());
	}

}
