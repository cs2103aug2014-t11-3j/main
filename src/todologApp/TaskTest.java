package todologApp;

import static org.junit.Assert.*;

import org.junit.Test;

public class TaskTest {

	@Test
	public void testParseName() throws Exception {
		Task task = new Task ("\"group meeting\"");
		assertEquals("output should be group meeting", "group meeting" , task.getTaskName());
		try {
			new Task ("\"group meeting");
		} catch (Exception e) {
			assertEquals("output should be invalid input", "Invalid command. Missing task name.\nTask name must be inside quotation marks." ,
					e.getMessage());
		}
		try {
			new Task ("group meeting");
		} catch (Exception e) {
		assertEquals("output should be invalid input", "Invalid command. Missing task name.\nTask name must be inside quotation marks." , 
				e.getMessage());
		}
	}
	
	@Test
	public void testParseType() throws Exception {
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
	public void testParseDay() throws Exception {
		Task task = new Task ("\"group meeting\" from fri 2359 to sat");
		assertEquals("output should be Friday", "Friday" , task.getStartDay());
		assertEquals("output should be Saturday", "Saturday" , task.getEndDay());
		
		Task task2 = new Task ("\"group meeting\" from thur");
		assertEquals("output should be Thursday", "Thursday" , task2.getStartDay());
		assertEquals("output should be Thursday", "Thursday" , task2.getEndDay());
		
		Task task3 = new Task ("\"group meeting\" to sunday");
		assertEquals("output should be Today", "Today" , task3.getStartDay());
		assertEquals("output should be Sunday", "Sunday" , task3.getEndDay());

		Task task4 = new Task ("\"group meeting\" from tue");
		assertEquals("output should be Tuesday", "Tuesday" , task4.getStartDay());
		assertEquals("output should be Tuesday", "Tuesday" , task4.getEndDay());

		Task task5 = new Task ("\"group meeting\" by weDnesDAY");
		assertEquals("output should be Today", "Today" , task5.getStartDay());
		assertEquals("output should be Wednesday", "Wednesday" , task5.getEndDay());
	}
	
	@Test
	public void testTime() throws Exception {
		Task task = new Task ("\"group meeting\" from fri @ 2359 to sat @ 1345");
		assertEquals("output should be 2359", 2359 , task.getStartTime());
		assertEquals("output should be 1345", 1345 , task.getEndTime());
		
		Task task2 = new Task ("\"group meeting\" from fri @ 2459 to sat @ 0000");
		assertEquals("output should be 0000", 0000 , task2.getStartTime());
		assertEquals("output should be 0000", 0000 , task2.getEndTime());
		
		Task task3 = new Task ("\"group meeting\" from fri @ 1100 to sat @ 1650");
		assertEquals("output should be 1100", 1100 , task3.getStartTime());
		assertEquals("output should be 1650", 1650 , task3.getEndTime());
		
		try {
			Task task4 = new Task ("\"group meeting\" from fri @ 11h00 to sat @ 16h50");
		} catch (Exception e) {
			//pass
		}
		
		Task task5 = new Task ("\"group meeting\" to sat @ 1650");
		assertEquals("output should be 0000", 0000 , task5.getStartTime());
		assertEquals("output should be 1650", 1650 , task5.getEndTime());
	}
	
	@Test
	public void testDateMonthYear() throws Exception {
		Task task = new Task ("\"group meeting\" from 090314 @ 1500 to sat @ 1650");
		assertEquals("output should be 9", 9, task.getStartDate());
		assertEquals("output should be 03", 3, task.getStartMonth());
		assertEquals("output should be 14", 14, task.getStartYear());
		assertEquals("output should be 1", 1, task.getEndDate());
		assertEquals("output should be 1", 1, task.getEndMonth());
		assertEquals("output should be 14", 14, task.getEndYear());
		assertEquals("output should be Today", "Today", task.getStartDay());
		assertEquals("output should be Saturday", "Saturday", task.getEndDay());
		
		Task task2 = new Task ("\"group meeting\" from 090314 @ 1500 to 100514 @ 1650");
		assertEquals("output should be 9", 9, task2.getStartDate());
		assertEquals("output should be 03", 3, task2.getStartMonth());
		assertEquals("output should be 14", 14, task2.getStartYear());
		assertEquals("output should be 10", 10, task2.getEndDate());
		assertEquals("output should be 5", 5, task2.getEndMonth());
		assertEquals("output should be 14", 14, task2.getEndYear());
		assertEquals("output should be Today", "Today", task2.getStartDay());
		assertEquals("output should be Saturday", "Today", task2.getEndDay());
	}
	
	@Test
	public void testVenueAndPerson() throws Exception {
		Task task = new Task ("\"group meeting\" with Linh at school cafe @ 1500" );
		assertEquals ("output should be Linh", "Linh" , task.getTaskPerson());
		assertEquals ("output should be school cafe", "school cafe" , task.getTaskVenue());
		
		Task task2 = new Task ("\"camping\" with schoolmates in school from saturday @ 1500 to sunday @ 1600" );
		assertEquals ("output should be schoolmates", "schoolmates" , task2.getTaskPerson());
		assertEquals ("output should be school", "school" , task2.getTaskVenue());
		assertEquals ("output should be saturday", "Saturday" , task2.getStartDay());
		assertEquals ("output should be sunday", "Sunday" , task2.getEndDay());
		assertEquals ("output should be 1500", 1500 , task2.getStartTime());
		assertEquals ("output should be 1500", 1600 , task2.getEndTime());
	}
}
