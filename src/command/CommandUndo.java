package command;

import controller.Controller;
import storage.History;

public class CommandUndo implements Command {

	private Command _toBeUndone;
	private History _history;
	
	private static final String FEEDBACK_INVALID_UNDO = "Cannot undo the undo";
	
	//@Author A0118899E
	public CommandUndo(Command toBeUndone) {
		_toBeUndone = toBeUndone;
		_history = Controller.getHistory();
	}
	
	//@Author A0118899E
	@Override
	public String execute() {
		try {
			_history.goBackwards();
			return _toBeUndone.undo();
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	//@Author A0118899E
	@Override
	public String undo() {
		return FEEDBACK_INVALID_UNDO;
	}
	
	//@Author A0118899E
	@Override
	public boolean isUndoable() {
		return false;
	}
}
