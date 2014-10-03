package todologApp;

import java.io.IOException;
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
		try {
			_storage.store(newList);
		} catch (IOException e) {
			//TODO return feedback
		}
		//return feedback
	}

	public void undo() {
		
		CommandDelete undoAdd = new CommandDelete(_task);
		undoAdd.execute();

	}

}
