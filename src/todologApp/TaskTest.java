package todologApp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTest {

	@Test
	public void testParseName() {
		Task task = new Task ("\"group meeting\"");
		assertEquals("output should be group meeting", "group meeting" , task.getTaskName());
		Task task2 = new Task ("\"group meeting");
		assertEquals("output should be invalid input", "Invalid Input!" , task2.getTaskName());
		Task task3 = new Task ("group meeting");
		assertEquals("output should be invalid input", "Invalid Input!" , task3.getTaskName());
	}
	
	@Test
	public void testParseType() {
		Task task = new Task ("\"group meeting\"");
		assertEquals("output should be floating", TaskType.FLOATING , task.getTaskType());
	}

	@Test
	public void testParseDay() {
		Task task = new Task ("\"group meeting\" fri 2359");
		assertEquals("output should be Friday", "Friday" , task.getTaskDay());
	}
}
