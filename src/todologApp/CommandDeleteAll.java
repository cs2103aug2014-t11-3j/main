package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandDeleteAll implements Command {
	private LinkedList<Task> _tasks;
	private DBStorage _storage;
	private boolean validity;

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
			validity=false;
			return feedback;
		}
		Controller.setFocusTask(null);
		feedback = "Deleted all tasks";
		validity=true;
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
	
	public boolean isUndoable(){
		return validity;
	}

}
