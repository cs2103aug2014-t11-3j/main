package todologApp;

public class CommandUndo implements Command {

	private Command _toBeUndone;
	private History _history;
	public CommandUndo(Command toBeUndone) {
		_toBeUndone = toBeUndone;
		_history = Controller.getHistory();
	}
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
		// TODO Auto-generated method stub
		return "Unexpected Error!";
	}
	
	public boolean isUndoable(){
		return false;
	}

}
