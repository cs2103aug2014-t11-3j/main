package todologApp;

public class CommandEdit implements Command{
	private static Task _taskExisting;
	private static Task _taskEdited;
	//private static Storage _storage;
	public CommandEdit(Task taskExisting, Task taskEdited) {
		_taskExisting = taskExisting;
		_taskEdited=taskEdited;
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

