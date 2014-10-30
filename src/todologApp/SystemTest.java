package todologApp;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

public class SystemTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@Test
	public void testPerformance() {
		fail("Not yet implemented");
	}
	@Test
	public void testScalability() {
		Controller.init("test.xml");
		//DateTime now = new DateTime();
		for (int i = 0; i<300; i++) {
			Controller.acceptUserCommand(createRandomFloatingTaskString());
			Controller.acceptUserCommand(createRandomTimedTaskString());
			Controller.acceptUserCommand(createRandomDeadlineTaskString());
		}
//		DateTime now2 = new DateTime();
//		System.out.println(now2.getSecondOfDay()-now.getSecondOfDay());
	}
	
		
	private String createRandomFloatingTaskString() {
		String name = createRandomString();
		return "add "+name;
	}

	
	private String createRandomTimedTaskString() {
		//TODO
		String name = createRandomString();
		return "add "+name + "by tomorrow";
	}
	private String createRandomDeadlineTaskString() {
		String name = createRandomString();
		return "add "+name + "on sunday";
	}
	private String createRandomString() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz01123456789".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String name = sb.toString();
		return name;
	}
}
