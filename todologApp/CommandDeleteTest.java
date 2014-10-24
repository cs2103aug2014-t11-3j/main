package todologApp;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class CommandDeleteTest {
	@Test
	public void testExecute() throws Exception {
		Controller.init();
		CommandDelete command = new CommandDelete(3);
		DBStorage storage=new DBStorage();
		storage = Controller.getDBStorage();
		LinkedList<Task> taskList = storage.load();
		if(taskList.size()<3){
		assertEquals("Description", "Invalid task number. Cannot delete.",command.execute());
		}
		else{
		assertEquals("Description", "Deleted " +taskList.get(2).getTaskName()+ " from toDoLog",command.execute());
		}
	}
}
