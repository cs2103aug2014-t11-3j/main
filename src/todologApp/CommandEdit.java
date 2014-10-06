package todologApp;

public class CommandEdit implements Command{
	private Task _taskExisting;
	private String _toBeEdited;
	private Task _taskEdited;
	//private static Storage _storage;
	
	
	public CommandEdit(Task taskExisting, String toBeEdited ) {
		_taskExisting = taskExisting;
		_toBeEdited= toBeEdited;
		_taskEdited=_taskExisting;
		formNewTask();
	}
	public Task formNewTask(){
		if(_toBeEdited.startsWith("\"")&& _toBeEdited.endsWith("\"")){
			_taskEdited.setTaskName(_toBeEdited.substring(1, _toBeEdited.length()-1));
		}
		return _taskEdited;
//		else if(equalsWeekDay(_toBeEdited)){
//			_taskEdited.setDay(_toBeEdited);
//		}
//		else if(_toBeEdited.length()==4){
//			_taskEdited.setTime(_toBeEdited);
//		}
	}
	
	public String execute() {
		String feedback;
		CommandDelete existing=new CommandDelete(_taskExisting);
		existing.execute();
		CommandAdd editing=new CommandAdd(_taskEdited);
		editing.execute();
		feedback="edited the string";
		return feedback;
		}
	
	public String undo() {
		String feedback;
		CommandDelete editing=new CommandDelete(_taskEdited);
		editing.execute();
		CommandAdd existing= new CommandAdd(_taskExisting);
		existing.execute();
		feedback="undone the edit command";
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

