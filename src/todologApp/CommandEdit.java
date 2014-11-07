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
	private LinkedList <Task> _displayList;
	private boolean _validity;
	
	private static final String KEYWORD_TASK_NAME = "task name";
	private static final String KEYWORD_NAME = "name";
	private static final String KEYWORD_END_DATE = "end date";
	private static final String KEYWORD_DATE = "date";
	private static final String KEYWORD_START_DATE = "start date";
	private static final String KEYWORD_START_TIME = "start time";
	private static final String KEYWORD_END_TIME = "end time";
	private static final String KEYWORD_VENUE = "venue";
	private static final String KEYWORD_PLACE = "place";
	private static final String KEYWORD_PERSON = "person";
	
	private static final String FEEDBACK_INVALID_DETAILS = "Please specify task to be edited and the details.";
	public CommandEdit() {
		_index = -1;	
	}
	
	public CommandEdit(int index, String toBeEdited, String editType) {
		_index = index-1;
		_editType = editType;
		_toBeEdited = toBeEdited;
		_storage = Controller.getDBStorage();	
	}

	public CommandEdit(int index) {
		_index = index -1;
		_storage = Controller.getDBStorage();
	}
	
	public Task getCurrentTask() {
		return _taskExisting;
	}
	
	public Task getEditedTask() {
		return _taskEdited;
	}
	
	public int getIndex() {
		return _index;
	}
	
	public String execute() {
		String feedback;
		LinkedList <Task> storageList;
		String editedField;
		storageList = _storage.load();
		_displayList = Controller.getDisplayList();
		
		if (_index == -1) {
			_validity = false;
			return FEEDBACK_INVALID_DETAILS;
		} else {
			try {
				_taskExisting = _displayList.get(_index);
				Controller.setFocusTask(_taskExisting); // set focus task to change UI's page
			} catch (IndexOutOfBoundsException ioobe) {
				_validity = false;
				Controller.setFocusTask(_displayList.getLast());
				return "Item number "+ (_index+1) +" does not exist";
			}	
		}
		if (_editType == null) {
			_validity = false;
			return "Please specify edit type and the details.";
		}
		try {
			editedField = formNewTask();
		} catch (Exception e1) {
			_validity=false;
			feedback = e1.getMessage();
			return feedback;
		}
		int indexInStorage=storageList.indexOf(_taskExisting);
		_displayList.remove(_index);
		storageList.remove(_taskExisting);
		storageList.add(indexInStorage, _taskEdited);

		try {
			_storage.store(storageList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			_validity=false;
			return feedback;
		}
		if(editedField.equals("invalid")){
			feedback = "Invalid input";
			_validity=false;
		}
		else{
			feedback = "Edited " + editedField + " of the task.";
			_validity=true;
		}
		return feedback;
	}
	public String fakeExecute() {
		String feedback;
		String editedField;
		
		if (_index == -1) {
			return "Please specify task to be edited and the details.";
		} else {
			try {
				_taskExisting = _displayList.get(_index);

				Controller.setFocusTask(_taskExisting); // set focus task to change UI's page
			} catch (IndexOutOfBoundsException ioobe) {
				Controller.setFocusTask(_displayList.getLast());
				return "Item number "+ _index +" does not exist";
			}	
		}
		if (_editType == null) {
			return "Please specify edit type and the details.";
		}
		try {
			editedField = formNewTask();
		} catch (Exception e1) {
			_validity=false;
			feedback = e1.getMessage();
			return feedback;
		}
		if(editedField.equals("invalid")){
			feedback = "Invalid input";
			_validity=false;
		}
		else{
			feedback = "Edited " + editedField + " of the task.";
			_validity=true;
		}
		return feedback;
	}

	private String formNewTask() throws Exception{
		_taskEdited = _taskExisting.copy();
		//editing the name
		if (_editType.equalsIgnoreCase(KEYWORD_TASK_NAME)||_editType.equalsIgnoreCase(KEYWORD_NAME)) {
			_taskEdited.setTaskName(_toBeEdited);
			return KEYWORD_NAME;
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
	 
	public String undo() {
		String feedback;
		LinkedList<Task> storageList = _storage.load();
		_displayList = Controller.getDisplayList();
		
		int indexInStorage=storageList.indexOf(_displayList.get(_index));
		storageList.remove(indexInStorage);
		_displayList.remove(_index);

		_displayList.add(_index, _taskExisting);
		storageList.add(indexInStorage,_taskExisting);

		Controller.setFocusTask(_taskExisting); // set focus task to change UI's page
		try {
			_storage.store(storageList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		feedback = "Undone edit the "+_editType+".";
		return feedback;
	}
	
	public boolean isUndoable(){
		return _validity;
	}

	

}

