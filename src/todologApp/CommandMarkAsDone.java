package todologApp;

public class CommandMarkAsDone implements Command{
	private Task _task;
	private Storage _storage;
	public CommandMarkAsDone(Task task) {
		_task = task;
	}
	public void execute() {
		// TODO Auto-generated method stub
	}

	public void undo() {
		// TODO Auto-generated method stub
	}

}

