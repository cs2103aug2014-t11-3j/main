package todologApp;

public class CommandUndo implements Command {

	private Command _toBeUndone;
	private History _history;
	
	private static final String FEEDBACK_IVALID_UNDO = "Cannot undo the undo";
	
	public CommandUndo(Command toBeUndone) {
		_toBeUndone = toBeUndone;
		_history = Controller.getHistory();
	}
	
	@Override
	public String execute() {
		try {
			_history.goBackwards();
		} catch (Exception e) {
			return e.getMessage();
		}
		return _toBeUndone.undo();
	}

	@Override
	public String undo() {
		return FEEDBACK_IVALID_UNDO;
	}
	
	@Override
	public boolean isUndoable() {
		return false;
	}

}
