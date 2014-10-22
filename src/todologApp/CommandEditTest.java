package todologApp;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class CommandEditTest {
	@Test
	public void testExecute() throws Exception {
		Controller.init();
		CommandEdit command = new CommandEdit(1, "123", "name");
		String testFileName = "test.xml";
		DBStorage testStorage = new DBStorage(testFileName);
		Controller.setStorage(testStorage);
		LinkedList<Task> taskList = testStorage.load();
		if(taskList.size()<3)	{
			assertEquals("Description", "Invalid task number. Cannot delete.",command.execute());
		} else {
			assertEquals("Description", "Deleted " +taskList.get(3).getTaskName()+ " from toDoLog",command.execute());
		}
	}
}
