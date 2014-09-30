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
		assertEquals("output should be FLOATING", TaskType.FLOATING , task.getTaskType());
		Task task2 = new Task ("\"group meeting\" from friday to saturday");
		assertEquals("output should be TIMED", TaskType.TIMED , task2.getTaskType());
		Task task3 = new Task ("\"group meeting\" by friday");
		assertEquals("output should be DEADLINE", TaskType.DEADLINE , task3.getTaskType());
		Task task4 = new Task ("\"group meeting\" every friday");
		assertEquals("output should be RECURRING", TaskType.RECURRING , task4.getTaskType());
		Task task5 = new Task ("\"group meeting every friday");
		assertEquals("output should be INVALID", TaskType.INVALID , task4.getTaskType());
	}

	@Test
	public void testParseDay() {
		Task task = new Task ("\"group meeting\" fri 2359");
		assertEquals("output should be Friday", "Friday" , task.getTaskDay());
		}
}
