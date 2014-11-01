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
	private boolean validity;

	// private static Storage _storage;

	public CommandEdit(int index, String toBeEdited, String editType) {
		_index = index-1;
		_editType = editType;
		_toBeEdited = toBeEdited;
		_storage = Controller.getDBStorage();
		
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
		String feedback;
		String editedField;
		LinkedList<Task> tasks = _storage.load();
		_taskExisting = tasks.get(_index);
		try {
			editedField = formNewTask();
		} catch (Exception e1) {
			validity=false;
			feedback = e1.getMessage();
			return feedback;
		}
		tasks.remove(_index);
		tasks.add(_index, _taskEdited);
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
		String feedback;
		String editedField;
		LinkedList<Task> tasks = _storage.load();
		_taskExisting = tasks.get(_index);
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
		tasks.remove(_index);
		tasks.add(_index, _taskExisting);
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

