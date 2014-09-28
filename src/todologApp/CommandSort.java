package todologApp;

public class CommandSort implements Command{
	private static Task _task;
	private static Storage _storage;
	public CommandSort(Task task) {
		_task = task;
	}
	public void execute() {
	
	}

	public void undo() {
	
	}

}

