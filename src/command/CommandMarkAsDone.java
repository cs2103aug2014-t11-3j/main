package command;

import java.io.IOException;
import java.util.LinkedList;

import common.Task;
import common.TaskType;

import controller.Controller;
import storage.DBStorage;

public class CommandMarkAsDone implements Command {
	private Task _task;
	private DBStorage _storage;
	private int _index;
	private LinkedList<Task> _displayList;
	private LinkedList<Task> _taskList;
	private boolean _validity;
	
	private static final int INVALID_INDEX = -1;
	private static final int CORRECTION_INDEX = 1;
	
	private static final String FEEDBACK_VALID_MARK_AS_DONE = "%1$s is mark as completed";
	private static final String FEEDBACK_VALID_MARK_AS_NOT_DONE = "%1$s is mark as not completed";
	private static final String FEEDBACK_INVALID_STORAGE = "Cannot store the list to ToDoLog";
	private static final String FEEDBACK_INVALID_TASK = "Invalid task number. Cannot mark.";
	private static final String FEEDBACK_INVALID_DETAILS = "Please specify the task to be marked.";
	
	
	
	public CommandMarkAsDone() {
		_index = INVALID_INDEX;
		_storage = Controller.getDBStorage();
	}
	
	public CommandMarkAsDone(int index) {
		_index = index - CORRECTION_INDEX;
		_storage = Controller.getDBStorage();
	}

	public Task getMarkedTask() {
		return _task;
	}
	
	@Override 
	public String execute() {
		String feedback;
		_taskList = _storage.load();
		_displayList = Controller.getScheduleList();
		
		try {
			_task = _displayList.get(_index);
			_displayList.get(_index).toggleTaskStatus();
			// set focus task to change UI's page
			Controller.setFocusTask(_task); 
			if (_task.getTaskStatus()) {
				feedback = String.format(FEEDBACK_VALID_MARK_AS_DONE , _task.getTaskName());
				_validity = true;
			} else {
				assert (_task.getTaskStatus() == false);
				feedback = String.format(FEEDBACK_VALID_MARK_AS_NOT_DONE , _task.getTaskName());
				_validity = true;
			}
		} catch ( IndexOutOfBoundsException ioobe) {
			_validity = false;
			return FEEDBACK_INVALID_TASK;	
		}
		sortDisplay(_task);
		try {
			_storage.store(_taskList);
		} catch ( IOException e) {
			_validity = false;	
			return FEEDBACK_INVALID_STORAGE;
			
		}
		return feedback;
	}
	
	public String tryExecute() {
		String feedback = "";
		_taskList = _storage.load();
		_displayList = Controller.getScheduleList();
		if (_index == INVALID_INDEX) {
			_validity = false;
			return FEEDBACK_INVALID_DETAILS;
		} else {
			assert (_index >= 0);
			try {
				_task = _displayList.get(_index);
				// set focus task to change UI's page
				Controller.setFocusTask(_task); 
				if (_task.getTaskStatus()) {
					feedback = String.format(FEEDBACK_VALID_MARK_AS_DONE, _task.getTaskName());
					_validity = true;
				} else {
					assert (_task.getTaskStatus() == false);
					feedback = String.format(FEEDBACK_VALID_MARK_AS_NOT_DONE, _task.getTaskName());
					_validity = true;
				}
			} catch (IndexOutOfBoundsException ioobe ) {
				_validity = false;
				Controller.setFocusTask(_displayList.getLast());
				return FEEDBACK_INVALID_TASK;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return feedback;
	}
	
	public void sortDisplay(Task task) {
		if (_task.getTaskStatus() == true ) {
			_taskList.remove(task);
			_taskList.addLast(task);
		} else {
			assert (_task.getTaskStatus() == false);
			 if (_task.getTaskType() == TaskType.FLOATING) {
				 	_taskList.remove(_task);
			    	_taskList.add(_task);
			    	Controller.setFocusTask(_task); // set focus task to change UI's page
			    } else {
			    	assert (_task.getTaskType() == TaskType.DEADLINE || _task.getTaskType() == TaskType.TIMED);
		    		sortList(_taskList);	
			    }
		}
	}
	
	public void sortList(LinkedList <Task> newList) {
		boolean isAdded = false;
		for ( int i=0; i < newList.size(); i++ ) {
			Task current = newList.get(i);
			if (current.getTaskType() == TaskType.FLOATING) {
				newList.remove(_task);
				newList.add(i, _task);
				Controller.setFocusTask(_task); // set focus task to change UI's page
				isAdded = true;
				break;
			} else {
				assert (current.getTaskType() == TaskType.DEADLINE || current.getTaskType() == TaskType.TIMED);
    			if (current.getEndDateTime().compareTo(_task.getEndDateTime()) > 0) {
    				newList.remove(_task);
    				newList.add(i,_task);
    				// set focus task to change UI's page
    				Controller.setFocusTask(_task); 
    				isAdded = true;
    				break;
    			}
			}	
		}
		if ( !isAdded) {
			newList.remove(_task);
			newList.add(_task);
		}
	}
	
	@Override
	public String undo() {
		_displayList=Controller.getScheduleList();
		CommandMarkAsDone undoMarkAsDone = new CommandMarkAsDone(_displayList.indexOf(_task) + CORRECTION_INDEX);
		return undoMarkAsDone.execute();
	}
	
	@Override
	public boolean isUndoable() {
		assert isUndoable();
		return _validity;
	}
}
