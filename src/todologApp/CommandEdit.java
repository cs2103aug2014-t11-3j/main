 package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandEdit implements Command {
	private Task _taskExisting;
	private String _toBeEdited;
	private String _editType;
	private Task _taskEdited;
	private int _index;
	private DBStorage _storage;
	private LinkedList<Task> _displayList;
	private boolean validity;

	// private static Storage _storage;
	public CommandEdit() {
		_index = -1;
		
		
	}
	public CommandEdit(int index, String toBeEdited, String editType) {
		_index = index-1;
		_editType = editType;
		_toBeEdited = toBeEdited;
		_storage = Controller.getDBStorage();
		_displayList=Controller.getDisplayList();
		
		
	}
	public CommandEdit(int index) {
		_index = index -1;
		_storage = Controller.getDBStorage();
		_displayList=Controller.getDisplayList();
	}
	private String formNewTask() throws Exception{
		//change the name
		_taskEdited = _taskExisting.copy();
		if (_editType.equalsIgnoreCase("Task Name")||_editType.equalsIgnoreCase("Name")) {
			_taskEdited.setTaskName(_toBeEdited);
			return "name";
		}
		
		//change End day
		 else if(_editType.equalsIgnoreCase("End Date")||_editType.equalsIgnoreCase("Date")){
			 if (_taskEdited.getTaskType() != TaskType.FLOATING) {
				 _taskEdited.setEndDate(_toBeEdited);
				 return "end date";
			 } else {
				 throw new Exception("Cannot edit end day for floating tasks");
			 }
		 }
		//change Start day
		 else if(_editType.equalsIgnoreCase("Start Date")||_editType.equalsIgnoreCase("Date")){	
			 _taskEdited.setStartDate(_toBeEdited);
			 return "start date";
			 
		 }
		
		//change End Time
		 else if(_editType.equalsIgnoreCase("End Time")||_editType.equalsIgnoreCase("time")){
			 if(_toBeEdited.length()==4){
				 _taskEdited.setEndTime(_toBeEdited);
			 	return "end time";
			 } else {
				 throw new Exception("Incorrect time format input");
			 }
		 }
		//change Start Time
		 else if(_editType.equalsIgnoreCase("Start Time")){
			 if(_toBeEdited.length()==4){
				 _taskEdited.setStartTime(_toBeEdited);
			 	return "start time";
			 } else {
				 throw new Exception("Incorrect time format input");
			 }
		 }
		//changeVenue
		 else if(_editType.equalsIgnoreCase("Venue")){
			 _taskEdited.setVenue(_toBeEdited);
			 return "venue";
		 }
		//change person
		 else if(_editType.equalsIgnoreCase("person")){
			 _taskEdited.setPerson(_toBeEdited);
			 return "person";
		 }
		 else{
			 throw new Exception("Incorrect input for edit");
		 }
	}
	public Task getCurrentTask() {
		return _taskExisting;
	}
	public Task getEditedTask() {
		return _taskEdited;
	}

	public String execute() {
		if (_index == -1) {
			return "Please specify task to be edited and the details.";
		} else if (_editType == null) {
			_taskExisting = _displayList.get(_index);
			return "Please specify edit type and the details.";
		}
		String feedback;
		String editedField;
		LinkedList<Task> tasks = _storage.load();
		_taskExisting = _displayList.get(_index);
		try {
			editedField = formNewTask();
		} catch (Exception e1) {
			validity=false;
			feedback = e1.getMessage();
			return feedback;
		}
		_displayList.remove(_index);
		int indexInStorage=tasks.indexOf(_taskExisting);
		tasks.remove(_taskExisting);
		tasks.add(indexInStorage, _taskEdited);
		try {
			_storage.store(tasks);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			validity=false;
			return feedback;
		}
		if(editedField.equals("invalid")){
			feedback = "Invalid input";
			validity=false;
		}
		else{
			feedback = "Edited " + editedField + " of the task.";
			validity=true;
		}
		return feedback;
	}
	public String fakeExecute() {
		if (_index == -1) {
			return "Please specify task to be edited and the details.";
		} else if (_editType == null) {
			_taskExisting = _displayList.get(_index);
			Controller.setFocusTask(_taskExisting); // set focus task to change UI's page
			return "Please specify edit type and the details.";
		}
		String feedback;
		String editedField;
		_taskExisting = _displayList.get(_index);
		Controller.setFocusTask(_taskExisting); // set focus task to change UI's page
		try {
			editedField = formNewTask();
		} catch (Exception e1) {
			validity=false;
			feedback = e1.getMessage();
			return feedback;
		}
		if(editedField.equals("invalid")){
			feedback = "Invalid input";
			validity=false;
		}
		else{
			feedback = "Edited " + editedField + " of the task.";
			validity=true;
		}
		return feedback;
	}
	public String undo() {
		String feedback;
		LinkedList<Task> tasks = _storage.load();
		_taskExisting = _displayList.get(_index);
		_displayList.remove(_index);
		int indexInStorage=tasks.indexOf(_taskExisting);
		tasks.remove(_taskExisting);
		tasks.add(indexInStorage, _taskEdited);
		
		LinkedList<Task> tasks = _storage.load();
		tasks.remove(_index);
		tasks.add(_index, _taskExisting);
		Controller.setFocusTask(_taskExisting); // set focus task to change UI's page
		try {
			_storage.store(tasks);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		feedback = "Undone edit the "+_editType+".";
		return feedback;
	}
	
	public boolean isUndoable(){
		return validity;
	}

	

}

