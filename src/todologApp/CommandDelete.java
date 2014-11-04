package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandDelete implements Command {
	private Task _task;
	private LinkedList<Task> taskList;
	private int _index;
	private boolean validity;

	public CommandDelete(int index) {
		_index = index - 1;
	}

	public String execute() {
		String feedback;
		LinkedList<Task> taskList= Controller.getDisplayList();
		try {
			_task = taskList.get(_index);
			taskList.remove(_index);
			if (_index == taskList.size()) {
				if (_index == 0) {
					Controller.setFocusTask(null); // set focus task to change UI's page
				} else {
					Controller.setFocusTask(taskList.get(_index-1));
				}
			} else {
				if (taskList.size() == 0) {
					Controller.setFocusTask(null); // set focus task to change UI's page
				} else {
					Controller.setFocusTask(taskList.get(_index));
				}
			}
			feedback = "Deleted " + _task.getTaskName() + " from toDoLog";
			validity=true;
		} catch (IndexOutOfBoundsException ioobe) {
			feedback = "Invalid task number. Cannot delete.";
			validity=false;
		}

		try {
			_storage.store(taskList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			validity=false;
			return feedback;
		}
		return feedback;
	}

	public String undo() {
		String feedback;
		CommandAdd undoDelete = new CommandAdd(_task);
		Controller.setFocusTask(_task);
		undoDelete.execute();
		feedback = "Undone the delete command";
		return feedback;

	}
	
	public boolean isUndoable(){
		return validity;
	}
}
