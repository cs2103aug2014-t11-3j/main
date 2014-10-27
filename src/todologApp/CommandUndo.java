package todologApp;

public class CommandUndo implements Command {

	private Command _toBeUndone;
	public CommandUndo(Command toBeUndone) {
		_toBeUndone = toBeUndone;
	}
	public String execute() {
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
