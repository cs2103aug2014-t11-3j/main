package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandDeleteAll implements Command {
	private LinkedList<Task> _tasks;
	private DBStorage _storage;
	private int _index;

	public CommandDeleteAll() {
	}

	public String execute() {
		String feedback;
		_storage = Controller.getDBStorage();
		_tasks = _storage.load();
		try {
			_storage.store(new LinkedList<Task>());
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		feedback = "Deleted all tasks";
		return feedback;
	}

	public String undo() {
		String feedback;
		try {
			_storage.store(_tasks);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		feedback = "Undone the delete command";
		return feedback;

	}

}
