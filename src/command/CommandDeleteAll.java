package command;

import java.io.IOException;
import java.util.LinkedList;

import common.Task;

import controller.Controller;
import storage.DBStorage;

public class CommandDeleteAll implements Command {
	private LinkedList<Task> _storageList;
	private DBStorage _storage;
	private boolean _validity;
	
	private static final String FEEDBACK_INVALID_STORAGE = "Cannot store the list to ToDoLog";
	private static final String FEEDBACK_VALID_DELETE_ALL = "Deleted all tasks";
	private static final String FEEDBACK_VALID_UNDO = "Undone the delete command";

	@Override
	public String execute() {
		String feedback;
		_storage = Controller.getDBStorage();
		_storageList = _storage.load();
		try {
			_storage.store(new LinkedList<Task>());
		} catch (IOException e) {
			feedback = FEEDBACK_INVALID_STORAGE;
			_validity=false;
			return feedback;
		}
		// set focus task to change UI's page
		Controller.setFocusTask(null);
		feedback = FEEDBACK_VALID_DELETE_ALL;
		_validity=true;
		return feedback;
	}
	
	public String tryExecute() {
		String feedback;
		_storage = Controller.getDBStorage();
		_storageList = _storage.load();
		// set focus task to change UI's page
		Controller.setFocusTask( null );
		feedback = FEEDBACK_VALID_DELETE_ALL;
		_validity = true;
		return feedback;
	}

	@Override
	public String undo() {
		String feedback;
		try {
			_storage.store(_storageList);
		} catch (IOException e) {
			feedback = FEEDBACK_INVALID_STORAGE;
			return feedback;
		}
		feedback = FEEDBACK_VALID_UNDO;
		return feedback;
	}
	
	@Override
	public boolean isUndoable() {
		return _validity;
	}

}
