package todologApp;

public class CommandSort implements Command{
	private Task _task;
	private Storage _storage;
	public CommandSort(Task task) {
		_task = task;
	}
	public void execute() {
	
	}

	public void undo() {
	
	}

}

