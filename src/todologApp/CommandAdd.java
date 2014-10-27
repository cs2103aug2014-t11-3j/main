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
		validity=true;
		return feedback;
	}
	
	public void sortByDate(LinkedList<Task> newList){
	    if(_task.getTaskType() == TaskType.FLOATING) {
	    	newList.add(_task);
	    } else {
	    	boolean isAdded = false;
    		for (int i=0 ; i<newList.size();i++) {
    			Task curr = newList.get(i);
    			if (curr.getTaskType() == TaskType.FLOATING) {
    				newList.add(i,_task);
    				isAdded=true;
    				break;
    			} else {
	    			if (curr.getEndDateTime().compareTo(_task.getEndDateTime()) >0) {
	    				newList.add(i,_task);
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
		boolean isRemoved = taskList.remove(_task);
		try {
			_storage.store(taskList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		if (isRemoved) {
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

}
