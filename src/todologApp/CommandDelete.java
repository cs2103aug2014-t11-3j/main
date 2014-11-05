package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandDelete implements Command {
	private Task _task;
	private LinkedList<Task> taskList;
	private DBStorage _storage;
	private int _index;
	private boolean validity;

	public CommandDelete(int index) {
		_index = index - 1;
	}
	public CommandDelete() {
		_index = -1;
	}
	public Task getDeletedTask() {
		return _task;
	}
	public String execute() {
		String feedback;
		taskList= Controller.getDisplayList();
		_storage=Controller.getDBStorage();
		LinkedList<Task> storageList=_storage.load();
		if (_index == -1) {
			validity = false;
			return "Please specify the task to be edited."; 
		} else {
			try {
				_task = taskList.get(_index);
				Controller.setFocusTask(_task);
			} catch (IndexOutOfBoundsException ioobe ) {
				validity = false;
				Controller.setFocusTask(null);
				return "Item number "+ (_index+1) +" does not exist";
			}
		}
		
		
		taskList.remove(_index);
		storageList.remove(_task);
		feedback = "Deleted " + _task.getTaskName() + " from toDoLog";
		validity=true;
		try {
			_storage.store(storageList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			validity=false;
			return feedback;
		}
		return feedback;
	}
	public String fakeExecute() { 
		taskList= Controller.getDisplayList();
		_storage=Controller.getDBStorage();
		LinkedList<Task> storageList=_storage.load();
		if (_index == -1) {
			validity = false;
			return "Please specify the task to be edited."; 
		} else {
			try {
				_task = taskList.get(_index);
				storageList.get(storageList.indexOf(_task));
				Controller.setFocusTask(_task);
			} catch (IndexOutOfBoundsException ioobe ) {
				validity = false;
				Controller.setFocusTask(taskList.getLast());
				return "Item number "+ (_index+1) +" does not exist";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		validity = true;
		return "Deleted " + _task.getTaskName() + " from toDoLog";
	}
	public String undo() {
		String feedback;
		CommandAdd undoDelete = new CommandAdd(_task);
		Controller.setFocusTask(_task);
		undoDelete.execute();
		feedback = "Undone the delete command";
		return feedback;

	}
	
	public boolean isUndoable(){
		return validity;
	}
}
