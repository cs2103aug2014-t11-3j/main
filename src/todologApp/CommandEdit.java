package todologApp; 

import java.io.IOException;
import java.util.LinkedList;

public class CommandEdit implements Command{
	private Task _taskExisting;
	private String _toBeEdited;
	private Task _taskEdited;
	private int _index;
	private String editType;
	private DBStorage _storage;
	//private static Storage _storage;
	
	
	public CommandEdit(int index, String toBeEdited ) {
		_index = index-1;
		_toBeEdited= toBeEdited;
		_storage = Controller.getDBStorage();
	}
	private String formNewTask(){
		if(_toBeEdited.startsWith("\"")&& _toBeEdited.endsWith("\"")){
			_taskEdited.setTaskName(_toBeEdited.substring(1, _toBeEdited.length()-1));
			return "name";
		}
		return "nothing";
//		else if(equalsWeekDay(_toBeEdited)){
//			_taskEdited.setDay(_toBeEdited);
//		}
//		else if(_toBeEdited.length()==4){
//			_taskEdited.setTime(_toBeEdited);
//		}
	}
	
	public String execute() {
		String feedback;
		LinkedList<Task> tasks = _storage.load();
		
		try {
			_taskExisting = _storage.load().get(_index);
			_taskEdited = _taskExisting;
			editType = formNewTask();
			tasks.remove(_index);
			tasks.add(_index, _taskEdited);
			feedback="Edited "+editType+" of the task.";
			try {
				_storage.store(tasks);
			} catch (IOException e) {
				feedback = "Cannot store the list to ToDoLog";
				return feedback;
			}
		} catch (IndexOutOfBoundsException ioobe) {
			feedback="Invalid task number. Cannot edit";
		}
		
		
		
		return feedback;
		}
	
	public String undo() {
		String feedback;
		LinkedList<Task> tasks = _storage.load();
		tasks.remove(_index);
		tasks.add(_index,_taskExisting);
		feedback="Undone the edit command.";
		return feedback;
		}
	
	public boolean equalsWeekDay(String day){
		if(day.equalsIgnoreCase("monday")||day.equalsIgnoreCase("monday")||day.equalsIgnoreCase("tuesday")||day.equalsIgnoreCase("wednesday")||day.equalsIgnoreCase("thursday")||day.equalsIgnoreCase("friday")||day.equalsIgnoreCase("saturday")){
			return true;
		}
		else{
			return false;
		}
	}

}

