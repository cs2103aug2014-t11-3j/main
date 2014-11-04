package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandMarkAsDone implements Command {
	private Task _task;
	private DBStorage _storage;
	private int _index;
	private LinkedList<Task> _displayList;
	private boolean validity;

	public CommandMarkAsDone(int index) {
		_index = index - 1;
	}

	public String execute() {
		String feedback;
		_storage = Controller.getDBStorage();
		LinkedList<Task> taskList = _storage.load();
		_displayList=Controller.getDisplayList();
		try {
			_task = _displayList.get(_index);
			_displayList.get(_index).toggleTaskStatus();
			taskList.get(taskList.indexOf(_task)).toggleTaskStatus();
			Controller.setFocusTask(_task); // set focus task to change UI's page
			if (_task.getTaskStatus()) {
				feedback = _task.getTaskName() + " is mark as completed";
				validity=true;
			} else {
				feedback = _task.getTaskName() + " is mark as not completed";
				validity=true;;
			}
		} catch (IndexOutOfBoundsException ioobe) {
			feedback = "Invalid task number. Cannot mark.";
			validity=false;
		}

		try {
			_storage.store(taskList);
		} catch (IOException e) {
			feedback="Cannot store the list to ToDoLog";
			validity=false;
			
		}
		return feedback;
	}

	public String undo() {
		return execute();
	}
	public boolean isUndoable(){
		return validity;
	}

}
