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

	// private static Storage _storage;

	public CommandEdit(int index, String toBeEdited, String editType) {
		_index = index;
		_editType = editType;
		_taskEdited = _taskExisting;
		_storage = Controller.getDBStorage();
		
	}

	private String formNewTask() throws Exception{
		//change the name
		_taskEdited = _taskExisting;
		if (_editType.equalsIgnoreCase("Task Name")||_editType.equalsIgnoreCase("Name")) {
			_taskEdited.setTaskName(_toBeEdited);
			return "name";
		}
		
		//change Start day
		 else if(_editType.equalsIgnoreCase("Start Day")||_editType.equalsIgnoreCase("Day")){
			 if(equalsWeekDay(_toBeEdited)){
				 _taskEdited.setStartDay(_toBeEdited);
				 return "Start Day";
			 }
			 else{
				 return "invalid";
			 }
		 }
		//change End day
		 else if(_editType.equalsIgnoreCase("End Day")||_editType.equalsIgnoreCase("Day")){
			 if(equalsWeekDay(_toBeEdited)){
				 _taskEdited.setEndDay(_toBeEdited);
				 return "End Day";
			 }
			 else{
				 return "invalid";
			 }
		 }
		//change Start Time
		 else if(_editType.equalsIgnoreCase("Start Time")){
			 if(_toBeEdited.length()==4){
				 _taskEdited.setStartTime(_toBeEdited);
			 	return "Start time";
			 }
			 else{
				 return "invalid";
			 }
		 }
		//change End Time
		 else if(_editType.equalsIgnoreCase("End Time")){
			 if(_toBeEdited.length()==4){
				 _taskEdited.setEndTime(_toBeEdited);
			 	return "End time";
			 }
			 else{
				 return "invalid";
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
			 return "invalid";
		 }
	}

	public String execute() {
		String feedback;
		String editedField;
		LinkedList<Task> tasks = _storage.load();
		_taskExisting = tasks.get(_index);
		try {
			editedField=formNewTask();
		} catch (Exception e1) {
			feedback = e1.getMessage();
			return feedback;
		}
		tasks.remove(_index);
		tasks.add(_index, _taskEdited);
		try {
			_storage.store(tasks);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		if(editedField.equals("invalid")){
			feedback = "Invalid input";
		}
		else{
			feedback = "Edited " + editedField + " of the task.";	
		}
		return feedback;
	}

	public String undo() {
		String feedback;
		LinkedList<Task> tasks = _storage.load();
		tasks.remove(_index);
		tasks.add(_index, _taskExisting);
		feedback = "Undone the edit command.";
		return feedback;
	}

	public boolean equalsWeekDay(String day) {
		if (day.equalsIgnoreCase("monday") || day.equalsIgnoreCase("monday")
				|| day.equalsIgnoreCase("tuesday")
				|| day.equalsIgnoreCase("wednesday")
				|| day.equalsIgnoreCase("thursday")
				|| day.equalsIgnoreCase("friday")
				|| day.equalsIgnoreCase("saturday")
				|| day.equalsIgnoreCase("today")
				|| day.equalsIgnoreCase("tomorrow")) {
			return true;
		} else {
			return false;
		}
	}

}

