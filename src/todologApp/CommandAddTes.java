package todologApp;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandAddTes {
	@Test
	public void testExecute() throws Exception {
		Controller.init();
		//Controller.setStorage(new DBStorageStub());
		Task task = new Task("\"group meeting\"");
		CommandAdd command = new CommandAdd(task);
		assertEquals("Description", "Added group meeting to ToDoLog",command.execute());
		Task task1 = new Task("\"lunch\" @monday by 2345");
		CommandAdd command1=new CommandAdd(task1);
		assertEquals("Description", "Added lunch to ToDoLog",command1.execute());
}
}