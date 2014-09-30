package todologApp;

public class CommandEdit implements Command{
	private static Task _taskExisting;
	private static String _toBeEdited;
	private static Task _taskEdited;
	//private static Storage _storage;
	
	
	public CommandEdit(Task taskExisting, String toBeEdited ) {
		_taskExisting = taskExisting;
		_toBeEdited= toBeEdited;
		_taskEdited=_taskExisting;
	}
	public Task formNewTask(){
		if(_toBeEdited.startsWith("\"")&& _toBeEdited.endsWith("\"")){
			_taskEdited.setName(_taskExisting.getName());
		}
	}
	
	public void execute() {
		CommandDelete existing=new CommandDelete(_taskExisting);
		existing.execute();
		CommandAdd editing=new CommandAdd(_taskEdited);
		editing.execute();
		}
	
	public void undo() {
		CommandDelete editing=new CommandDelete(_taskEdited);
		editing.execute();
		CommandAdd existing= new CommandAdd(_taskExisting);
		existing.execute();
		}

}

