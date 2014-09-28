package todologApp;

public class CommandDelete implements Command{
	private static Task _task;
	private static Storage _storage;
	public CommandDelete(Task task) {
		_task = task;
	}
	public void execute() {
		_storage=Controller.getStorage();
		_storage.delete(_task);
	}

	public void undo() {
		CommandAdd undoDelete=new CommandAdd(_task);
		undoDelete.execute();
		
	}

}
