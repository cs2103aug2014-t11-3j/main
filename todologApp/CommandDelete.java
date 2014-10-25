package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandDelete implements Command {
	private Task _task;
	private DBStorage _storage;
	private int _index;

	public CommandDelete(int index) {
		_index = index - 1;
	}

	public String execute() {
		String feedback;
		_storage = Controller.getDBStorage();
		LinkedList<Task> taskList = _storage.load();
		try {
			_task = taskList.get(_index);
			taskList.remove(_index);
			feedback = "Deleted " + _task.getTaskName() + " from toDoLog";
		} catch (IndexOutOfBoundsException ioobe) {
			feedback = "Invalid task number. Cannot delete.";
		}

		try {
			_storage.store(taskList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		return feedback;
	}

	public String undo() {
		String feedback;
		CommandAdd undoDelete = new CommandAdd(_task);
		undoDelete.execute();
		feedback = "Undone the delete command";
		return feedback;

	}

}
