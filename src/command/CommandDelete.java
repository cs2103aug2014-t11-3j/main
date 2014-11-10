package command;

import java.io.IOException;
import java.util.LinkedList;

import common.Task;

import controller.Controller;
import storage.DBStorage;

public class CommandDelete implements Command {
	private Task _task;
	private LinkedList<Task> _displayList;
	private DBStorage _storage;
	private int _index;
	private boolean _validity;

	private static final String FEEDBACK_INVALID_FORMAT = "Please specify the task to be deleted.";
	private static final String FEEDBACK_INVALID_INDEX = "Item number %1$s does not exist";
	private static final String FEEDBACK_VALID_DELETE = "Deleted %1$s from toDoLog";
	private static final String FEEDBACK_INVALID_STORAGE = "Cannot store the list to ToDoLog";
	private static final String FEEDBACK_VALID_UNDO = "Undone the delete command";
	
	
	public CommandDelete(int index) {
		_index = index - 1;
	}
	
	public CommandDelete() {
		_index = -1;
	}
	
	public Task getDeletedTask() {
		return _task;
	}
	
	@Override
	public String execute() {
		String feedback;
		LinkedList<Task> storageList;
		
		_displayList= Controller.getScheduleList();
		_storage=Controller.getDBStorage();
		storageList=_storage.load();
		
		if (_index == -1) {
			_validity = false;
			return FEEDBACK_INVALID_FORMAT; 
		} else {
			try {
				_task = _displayList.get(_index);
				// set focus task to change UI's page
				Controller.setFocusTask(_task);
			} catch (IndexOutOfBoundsException ioobe ) {
				_validity = false;
				Controller.setFocusTask(null);
				return String.format(FEEDBACK_INVALID_INDEX, _index+1);
			}
		}
		
		_displayList.remove(_index);
		storageList.remove(_task);
		feedback = String.format(FEEDBACK_VALID_DELETE, _task.getTaskName());
		_validity=true;
		try {
			_storage.store(storageList);
		} catch (IOException e) {
			feedback = FEEDBACK_INVALID_STORAGE;
			_validity=false;
			return feedback;
		}
		return feedback;
	}
	
	public String tryExecute() { 
		LinkedList <Task> storageList;
		_displayList= Controller.getScheduleList();
		_storage=Controller.getDBStorage();
		storageList=_storage.load();
		
		if (_index == -1) {
			_validity = false;
			return FEEDBACK_INVALID_FORMAT; 
		} else {
			try {
				_task = _displayList.get(_index);
				storageList.get(storageList.indexOf(_task));
				Controller.setFocusTask(_task);
			} catch (IndexOutOfBoundsException ioobe ) {
				_validity = false;
				Controller.setFocusTask(_displayList.getLast());
				return String.format(FEEDBACK_INVALID_INDEX, _index+1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		_validity = true;
		return String.format(FEEDBACK_VALID_DELETE, _task.getTaskName());
	}
	
	@Override
	public String undo() {
		String feedback;
		CommandAdd undoDelete = new CommandAdd(_task);
		// set focus task to change UI's page
		Controller.setFocusTask(_task);
		undoDelete.execute();
		feedback = FEEDBACK_VALID_UNDO;
		return feedback;
	}
	
	@Override
	public boolean isUndoable(){
		return _validity;
	}
}
