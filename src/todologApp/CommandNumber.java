package todologApp;
import java.util.LinkedList;
public class CommandNumber implements Command {
	private int _index;
	private Task _task;
	private LinkedList<Task> _displayList;
	public CommandNumber(int index) {
		_index = index-1;
	}

	@Override
	public String execute() {
		_displayList = Controller.getDisplayList();
		try {
			_task = _displayList.get(_index);
			Controller.setFocusTask(_task);
			return "This is a valid task";
		} catch (IndexOutOfBoundsException ioobe) {
			_task = null;
			return "This is not a valid index";
		}
		
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	public String fakeExecute() {
		_displayList = Controller.getDisplayList();
		try {
			_task = _displayList.get(_index);
			Controller.setFocusTask(_task);
			return "This is a valid task";
		} catch (IndexOutOfBoundsException ioobe) {
			_task = null;
			return "This is not a valid index";
		}
	}

	public Task getTask() {
		return _task;
	}

}
