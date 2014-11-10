package command;

import controller.Controller;
import storage.History;

public class CommandUndo implements Command {

	private Command _toBeUndone;
	private History _history;
	
	private static final String FEEDBACK_INVALID_UNDO = "Cannot undo the undo";
	
	public CommandUndo(Command toBeUndone) {
		_toBeUndone = toBeUndone;
		_history = Controller.getHistory();
	}
	
	@Override
	public String execute() {
		try {
			_history.goBackwards();
			return _toBeUndone.undo();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Override
	public String undo() {
		return FEEDBACK_INVALID_UNDO;
	}
	
	@Override
	public boolean isUndoable() {
		return false;
	}

}
