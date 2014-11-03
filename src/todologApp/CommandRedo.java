package todologApp;

public class CommandRedo implements Command {

	private Command _toBeRedone;
	private History _history;
	public CommandRedo(Command toBeRedone) {
		_toBeRedone = toBeRedone;
		_history = Controller.getHistory();
	}
	public String execute() {
		try {
			_history.goForwards();
			return _toBeRedone.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		}
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
