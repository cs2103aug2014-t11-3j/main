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
		assertEquals("output should be INVALID", TaskType.INVALID , task5.getTaskType());
	}

	@Test
	public void testParseDay() {
		Task task = new Task ("\"group meeting\" from fri 2359 to sat");
		assertEquals("output should be Friday", "Friday" , task.getTaskDay());
		assertEquals("output should be Saturday", "Saturday" , task.getEndDay());
		
		Task task2 = new Task ("\"group meeting\" from thur");
		assertEquals("output should be Thursday", "Thursday" , task2.getTaskDay());
		assertEquals("output should be Thursday", "Thursday" , task2.getEndDay());
		
		Task task3 = new Task ("\"group meeting\" to sunday");
		assertEquals("output should be Today", "Today" , task3.getTaskDay());
		assertEquals("output should be Sunday", "Sunday" , task3.getEndDay());

		Task task4 = new Task ("\"group meeting\" from tue");
		assertEquals("output should be Tuesday", "Tuesday" , task4.getTaskDay());
		assertEquals("output should be Tuesday", "Tuesday" , task4.getEndDay());

		Task task5 = new Task ("\"group meeting\" by weDnesDAY");
		assertEquals("output should be Today", "Today" , task5.getTaskDay());
		assertEquals("output should be Wednesday", "Wednesday" , task5.getEndDay());
	}
	
	@Test
	public void testTime() {
		Task task = new Task ("\"group meeting\" from fri @ 2359 to sat @ 1345");
		assertEquals("output should be 2359", 2359 , task.getStartTime());
		assertEquals("output should be 1345", 1345 , task.getEndTime());
		
		Task task2 = new Task ("\"group meeting\" from fri @ 2459 to sat @ 0000");
		assertEquals("output should be 0000", 0000 , task2.getStartTime());
		assertEquals("output should be 0000", 0000 , task2.getEndTime());
		
		Task task3 = new Task ("\"group meeting\" from fri @ 1100 to sat @ 1650");
		assertEquals("output should be 1100", 1100 , task3.getStartTime());
		assertEquals("output should be 1650", 1650 , task3.getEndTime());
	}
}
