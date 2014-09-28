package todologApp;

public class CommandAdd implements Command{
	private static Task _task;
	private static Storage _storage;
	public CommandAdd(Task task) {
		_task = task;
	}
	public void execute() {
		// TODO Auto-generated method stub
		_storage = Controller.getStorage();
		_storage.store(_task);
	}

	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
