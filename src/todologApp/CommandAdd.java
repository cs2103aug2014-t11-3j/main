package todologApp;

import java.util.LinkedList;

public class CommandAdd implements Command{
	private static Task _task;
	private static DBStorage _storage;
	public CommandAdd(Task task) {
		_task = task;
	}
	public String execute() {
		String feedback;
		_storage = Controller.getStorage();
		LinkedList<Task> newList = _storage.load();
		newList.add(_task);
		_storage.store(newList);
		feedback="Added "+ _task.getTaskName()+" to ToDoLog";
		return feedback;	
	}

	public String undo() {
		String feedback;
		CommandDelete undoAdd = new CommandDelete(_task);
		undoAdd.execute();
		feedback="undone the add comand";
		return feedback;
		
	}

}
