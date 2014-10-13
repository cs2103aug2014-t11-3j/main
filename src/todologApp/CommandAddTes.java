package todologApp;

import static org.junit.Assert.*;
import org.junit.Test;

public class CommandAddTes {
	@Test
	public void testExecute() throws Exception {
		Task task = new Task("\"group meeting\"");
		CommandAdd command = new CommandAdd(task);
		assertEquals("Description", "Added group meeting to ToDoLog",
				command.execute());
	}
}
