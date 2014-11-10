package command;

import controller.Controller;
import storage.History;

public class CommandRedo implements Command {

	private Command _toBeRedone;
	private History _history;
	
	private static final String FEEDBACK_CANNOT_REDO = "No more commands to Redo";
	private static final String FEEDBACK_CANNOT_REDO_UNDO = "Redo cannot be undone";
	public CommandRedo(Command toBeRedone) {
		_toBeRedone = toBeRedone;
		_history = Controller.getHistory();
	}
	
	@Override
	public String execute() {
		try {
			_history.goForwards();
			return _toBeRedone.execute();
		} catch (Exception e) {
			return FEEDBACK_CANNOT_REDO;
		}
	}

	@Override
	public String undo() {
		return FEEDBACK_CANNOT_REDO_UNDO;
	}
	
	@Override
	public boolean isUndoable() {
		return false;
		
	}

}
