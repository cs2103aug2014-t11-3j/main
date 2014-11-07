package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandMarkAsDone implements Command {
	private Task _task;
	private DBStorage _storage;
	private int _index;
	private LinkedList<Task> _displayList;
	private LinkedList<Task> _taskList;
	private boolean validity;
	public CommandMarkAsDone() {
		_index = -1;
	}
	public CommandMarkAsDone(int index) {
		_index = index - 1;
	}

	public String execute() {
		String feedback;
		_storage = Controller.getDBStorage();
		_taskList = _storage.load();
		_displayList=Controller.getDisplayList();
		try {
			_task = _displayList.get(_index);
			_displayList.get(_index).toggleTaskStatus();
			Controller.setFocusTask(_task); // set focus task to change UI's page
			if (_task.getTaskStatus()) {
				feedback = _task.getTaskName() + " is mark as completed";
				validity=true;
			} else {
				feedback = _task.getTaskName() + " is mark as not completed";
				validity=true;
			}
		} catch (IndexOutOfBoundsException ioobe) {
			feedback = "Invalid task number. Cannot mark.";
			validity=false;
		}

		try {
			_storage.store(_taskList);
		} catch (IOException e) {
			feedback="Cannot store the list to ToDoLog";
			validity=false;	
		}
		
		sortDisplay(_task);
		return feedback;
	}
	public String fakeExecute() {
		String feedback="";
		_storage = Controller.getDBStorage();
		_taskList = _storage.load();
		_displayList=Controller.getDisplayList();
		if (_index == -1) {
			validity = false;
			return "Please specify the task to be marked."; 
		} else {
			try {
				_task = _displayList.get(_index);
				
				Controller.setFocusTask(_task); // set focus task to change UI's page
				if (_task.getTaskStatus()) {
					feedback = _task.getTaskName() + " is mark as completed";
					validity=true;
				} else {
					feedback = _task.getTaskName() + " is mark as not completed";
					validity=true;
				}
			} catch (IndexOutOfBoundsException ioobe ) {
				validity = false;
				Controller.setFocusTask(_taskList.getLast());
				return "Item number "+ (_index+1) +" does not exist";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return feedback;
	}
	public void sortDisplay(Task task){
		if(_task.getTaskStatus()==true){
			_taskList.remove(task);
			_taskList.addLast(task);
		}
		else{
			 if(_task.getTaskType() == TaskType.FLOATING) {
				 	_taskList.remove(_task);
			    	_taskList.add(_task);
			    	Controller.setFocusTask(_task); // set focus task to change UI's page
			    } else {
		    		sortList(_taskList);
		    		
			    }
			
		}
	}
	public void sortList(LinkedList<Task> newList){
		boolean isAdded = false;
		for (int i=0 ; i<newList.size();i++) {
			Task curr = newList.get(i);
			if (curr.getTaskType() == TaskType.FLOATING) {
				newList.remove(_task);
				newList.add(i,_task);
				Controller.setFocusTask(_task); // set focus task to change UI's page
				isAdded=true;
				break;
			} else {
    			if (curr.getEndDateTime().compareTo(_task.getEndDateTime()) >0) {
    				newList.remove(_task);
    				newList.add(i,_task);
    				Controller.setFocusTask(_task); // set focus task to change UI's page
    				isAdded=true;
    				break;
    			}
			}	
		}
		if (!isAdded) {
			newList.remove(_task);
			newList.add(_task);
		}
	}
	public String undo() {
		_displayList=Controller.getDisplayList();
		
		CommandMarkAsDone one= new CommandMarkAsDone(_displayList.indexOf(_task)+1);
		
		return one.execute();
	}
	public boolean isUndoable(){
		return validity;
	}
	public Task getMarkedTask() {
		return _task;
	}

}
