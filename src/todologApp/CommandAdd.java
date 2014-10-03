package todologApp;

import java.util.LinkedList;

public class CommandAdd implements Command{
	private Task _task;
	private DBStorage _storage;

	public CommandAdd(Task task) {
		_task = task;
	}
	public void execute() {
		_storage = Controller.getDBStorage();
		LinkedList<Task> newList = _storage.load();
		newList.add(_task);
		_storage.store(newList);
	}

	public void undo() {
		
		CommandDelete undoAdd = new CommandDelete(_task);
		undoAdd.execute();

	}

}
