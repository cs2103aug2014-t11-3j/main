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
	
	private static final int INVALID_INDEX = -1;
	
	private static final String KEYWORD_TASK_NAME = "task name";
	private static final String KEYWORD_NAME = "name";
	private static final String KEYWORD_END_DATE = "end date";
	private static final String KEYWORD_DATE = "date";
	private static final String KEYWORD_START_DATE = "start date";
	private static final String KEYWORD_START_TIME = "start time";
	private static final String KEYWORD_END_TIME = "end time";
	private static final String KEYWORD_TIME = "time";
	private static final String KEYWORD_VENUE = "venue";
	private static final String KEYWORD_PLACE = "place";
	private static final String KEYWORD_PERSON = "person";
	private static final String KEYWORD_INVALID = "invalid";
	
	private static final String FEEDBACK_INVALID_TASK = "Please specify task to be edited and the details.";
	private static final String FEEDBACK_INVALID_INDEX = "Item number %1$s does not exist";
	private static final String FEEDBACK_INVALID_DETAILS = "Please specify edit type and the details.";
	private static final String FEEDBACK_INVALID_STORAGE = "Cannot store the list to ToDoLog";
	private static final String FEEDBACK_INVALID_INPUT = "Invalid input";
	private static final String FEEDBACK_VALID_EDIT = "Edited %1$s of the task.";
	private static final String FEEDBACK_INVALID_REQUEST = "Incorrect input for edit";
	private static final String FEEDBACK_VALID_UNDO = "Undone edit the %1$s.";
	 
	
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
		int indexInStorage;
		LinkedList <Task> storageList;
		String editedField;
		storageList = _storage.load();
		_displayList = Controller.getDisplayList();
		
		if (_index == INVALID_INDEX) {
			_validity = false;
			return FEEDBACK_INVALID_TASK;
		} else {
			try {
				_taskExisting = _displayList.get(_index);
				// set focus task to change UI's page
				Controller.setFocusTask(_taskExisting); 
			} catch (IndexOutOfBoundsException ioobe) {
				_validity = false;
				Controller.setFocusTask(_displayList.getLast());
				return String.format(FEEDBACK_INVALID_INDEX, _index+1);
			}	
		}
		
		if (_editType == null) {
			_validity = false;
			return FEEDBACK_INVALID_DETAILS;
		}
		try {
			editedField = formNewTask();
		} catch (Exception e1) {
			_validity = false;
			feedback = e1.getMessage();
			return feedback;
		}
		indexInStorage = storageList.indexOf(_taskExisting);
		_displayList.remove(_index);
		storageList.remove(_taskExisting);
		storageList.add(indexInStorage, _taskEdited);

		try {
			_storage.store(storageList);
		} catch (IOException e) {
			feedback = FEEDBACK_INVALID_STORAGE;
			_validity = false;
			return feedback;
		}
		if (editedField.equals("invalid")){
			feedback = FEEDBACK_INVALID_INPUT;
			_validity = false;
		}
		else {
			feedback = String.format(FEEDBACK_VALID_EDIT, editedField);
			_validity = true;
		}
		return feedback;
	}
	
	public String fakeExecute() {
		String feedback;
		String editedField;
		
		if (_index == -1) {
			return FEEDBACK_INVALID_TASK;
		} else {
			try {
				_taskExisting = _displayList.get(_index);
				// set focus task to change UI's page
				Controller.setFocusTask(_taskExisting); 
			} catch (IndexOutOfBoundsException ioobe) {
				Controller.setFocusTask(_displayList.getLast());
				return String.format(FEEDBACK_INVALID_INDEX, _index+1);
			}	
		}
		if (_editType == null) {
			return FEEDBACK_INVALID_DETAILS;
		}
		
		try {
			editedField = formNewTask();
		} catch ( Exception e1) {
			_validity = false;
			feedback = e1.getMessage();
			return feedback;
		}
		
		if (editedField.equals(KEYWORD_INVALID)){
			feedback = FEEDBACK_INVALID_INPUT;
			_validity = false;
		} else {
			feedback = String.format(FEEDBACK_VALID_EDIT, editedField);
			_validity = true;
		}
		return feedback;
	}

	private String formNewTask() throws Exception{
		_taskEdited = _taskExisting.copy();
		
		if (_editType.equalsIgnoreCase(KEYWORD_TASK_NAME) || _editType.equalsIgnoreCase(KEYWORD_NAME)) {
			_taskEdited.setTaskName(_toBeEdited);
			return KEYWORD_NAME;
		} else if (_editType.equalsIgnoreCase(KEYWORD_END_DATE) || _editType.equalsIgnoreCase(KEYWORD_DATE)){
			 if (_taskEdited.getTaskType() != TaskType.FLOATING) {
				 _taskEdited.setEndDate(_toBeEdited);
				 return KEYWORD_END_DATE;
			 } else {
				 throw new Exception(FEEDBACK_INVALID_REQUEST);
			 }
		} else if (_editType.equalsIgnoreCase(KEYWORD_START_DATE)){	
			 if (_taskEdited.getTaskType() != TaskType.FLOATING
				&& _taskEdited.getTaskType() != TaskType.DEADLINE){
			 _taskEdited.setStartDate(_toBeEdited);
			 return KEYWORD_START_DATE;	 
			 } else {
				 throw new Exception(FEEDBACK_INVALID_REQUEST);
			 }
		} else if (_editType.equalsIgnoreCase(KEYWORD_END_TIME) || _editType.equalsIgnoreCase(KEYWORD_TIME)){
			 if (_toBeEdited.length() == 4 
				&& _taskEdited.getTaskType() != TaskType.FLOATING){
				 _taskEdited.setEndTime(_toBeEdited);
			 	return KEYWORD_END_TIME;
			 } else {
				 throw new Exception(FEEDBACK_INVALID_REQUEST);
			 }
		 } else if (_editType.equalsIgnoreCase(KEYWORD_START_TIME)){
			 if (_toBeEdited.length() == 4 
			    && _taskEdited.getTaskType() != TaskType.FLOATING 
			    && _taskEdited.getTaskType() != TaskType.DEADLINE){
				 _taskEdited.setStartTime(_toBeEdited);
			 	return KEYWORD_START_TIME;
			 } else {
				 throw new Exception(FEEDBACK_INVALID_REQUEST);
			 }
		 } else if (_editType.equalsIgnoreCase(KEYWORD_VENUE) || _editType.equalsIgnoreCase(KEYWORD_PLACE)){
			 _taskEdited.setVenue(_toBeEdited);
			 return KEYWORD_VENUE;
		 } else if (_editType.equalsIgnoreCase(KEYWORD_PERSON)){
			 _taskEdited.setPerson(_toBeEdited);
			 return KEYWORD_PERSON;
		 } else {
			 throw new Exception (FEEDBACK_INVALID_REQUEST);
		 }
	}
	 
	public String undo() {
		String feedback;
		LinkedList <Task> storageList;
		
		storageList = _storage.load();
		_displayList = Controller.getDisplayList();
		
		int indexInStorage = storageList.indexOf(_displayList.get(_index));
		storageList.remove(indexInStorage);
		
		_displayList.remove(_index);
		_displayList.add(_index, _taskExisting);
		storageList.add(indexInStorage, _taskExisting);
		// set focus task to change UI's page
		Controller.setFocusTask(_taskExisting); 
		try {
			_storage.store(storageList);
		} catch (IOException e) {
			feedback = FEEDBACK_INVALID_STORAGE;;
			return feedback;
		}
		feedback = String.format(FEEDBACK_VALID_UNDO, _editType);
		return feedback;
	}
	
	public boolean isUndoable(){
		return _validity;
	}
}

