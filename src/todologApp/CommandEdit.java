package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandEdit implements Command{
	private Task _taskExisting;
	private String _toBeEdited;
	private Task _taskEdited;
	private int index;
	private String editType;
	private DBStorage _storage;
	//private static Storage _storage;
	
	
	public CommandEdit(Task taskExisting, String toBeEdited ) {
		_taskExisting = taskExisting;
		_toBeEdited= toBeEdited;
		_taskEdited=_taskExisting;
		_storage = Controller.getDBStorage();
		editType = formNewTask();
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
		index = tasks.indexOf(_taskExisting);
		tasks.remove(index);
		tasks.add(index, _taskEdited);
		try {
			_storage.store(tasks);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		feedback="Edited "+editType+" of the task.";
		return feedback;
		}
	
	public String undo() {
		String feedback;
		LinkedList<Task> tasks = _storage.load();
		tasks.remove(index);
		tasks.add(index,_taskExisting);
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

