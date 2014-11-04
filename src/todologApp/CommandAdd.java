package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandAdd implements Command {
	private Task _task;
	private DBStorage _storage;
	private boolean validity;
	public CommandAdd(Task task) {
		_task = task;
	}

	public String execute(){
		if (_task == null) {
			validity = false;
			return "Please enter task details";
		}
		String feedback;
		_storage= Controller.getDBStorage();
		LinkedList<Task> newList = new LinkedList<Task>();
		newList=_storage.load();
		sortByDate(newList);
		try {
			_storage.store(newList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			validity=false;
			return feedback;
		}
		feedback = "Added " + _task.getTaskName() + " to ToDoLog";
		Controller.setFocusTask(_task);
		validity=true;
		return feedback;
	}
	
	public void sortByDate(LinkedList<Task> newList){
	    if(_task.getTaskType() == TaskType.FLOATING) {
	    	newList.add(_task);
	    	Controller.setFocusTask(_task); // set focus task to change UI's page
	    } else {
	    	boolean isAdded = false;
    		for (int i=0 ; i<newList.size();i++) {
    			Task curr = newList.get(i);
    			if (curr.getTaskType() == TaskType.FLOATING) {
    				newList.add(i,_task);
    				Controller.setFocusTask(_task); // set focus task to change UI's page
    				isAdded=true;
    				break;
    			} else {
	    			if (curr.getEndDateTime().compareTo(_task.getEndDateTime()) >0) {
	    				newList.add(i,_task);
	    				Controller.setFocusTask(_task); // set focus task to change UI's page
	    				isAdded=true;
	    				break;
	    			}
    			}	
    		}
    		if (!isAdded) {
    			newList.add(_task);
    		}
	    	
	    }
	}
	
	public String undo() {
		String feedback;
		_storage = Controller.getDBStorage();
		LinkedList<Task> taskList = _storage.load();
		int index = taskList.indexOf(_task);
		Task removedTask = taskList.remove(index);
		if (index == taskList.size()) {
			if (index == 0) {
				Controller.setFocusTask(null); // set focus task to change UI's page
			} else {
				Controller.setFocusTask(taskList.get(index-1));
			}
		} else {
			if (taskList.size() == 0) {
				Controller.setFocusTask(null); // set focus task to change UI's page
			} else {
				Controller.setFocusTask(taskList.get(index));
			}
		}
		
		try {
			_storage.store(taskList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		if (removedTask != null) {
			feedback = "Undone adding "+ _task.getTaskName();
		} 
		else {
			feedback = "Cannot undo adding "+ _task.getTaskName(); 
		}
		
		return feedback;
	}
	public boolean isUndoable(){
		return validity;
	}

	public String fakeExecute() {
		if (_task == null) {
			validity = false;
			return "Please enter task details";
		}
		String feedback;
		_storage= Controller.getDBStorage();
		LinkedList<Task> newList = new LinkedList<Task>(_storage.load());
		sortByDate(newList);
		feedback = "Added " + _task.getTaskName() + " to ToDoLog";
		validity=true;
		return feedback;
		
	}

	public Task getAddedTask() {
		// TODO Auto-generated method stub
		return _task;
	}

}
