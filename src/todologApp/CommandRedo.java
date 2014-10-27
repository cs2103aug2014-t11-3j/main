package todologApp;

public class CommandRedo implements Command {

	private Command _toBeRedone;
	public CommandRedo(Command toBeRedone) {
		_toBeRedone = toBeRedone;
	}
	public String execute() {
		try {
			return _toBeRedone.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String undo() {
		return "Unexpected Error!";
	}
	@Override
	public boolean isUndoable() {
		return false;
	}

}
