package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandMarkAsDone implements Command{
	private Task _task;
	private DBStorage _storage;
	private int _index;
	public CommandMarkAsDone(int index) {
		_index = index-1;
	}
	public String execute() {
		String feedback;
		_storage=Controller.getDBStorage();
		LinkedList<Task> taskList= _storage.load();
		try {
			_task = taskList.get(_index);
			taskList.get(_index).toggleTaskStatus();
			if (_task.getTaskStatus()) {
				feedback=_task.getTaskName()+" is mark as completed";
			} else {
				feedback=_task.getTaskName()+" is mark as not completed";
			}
		} catch (IndexOutOfBoundsException ioobe) {
			feedback="Invalid task number. Cannot mark.";
		}	
		
		try {
			_storage.store(taskList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
		}
		return feedback;
	}

	public String undo() {
		return execute();
	}

}

