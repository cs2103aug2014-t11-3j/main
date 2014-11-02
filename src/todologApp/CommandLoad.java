package todologApp;

import java.io.IOException;

public class CommandLoad implements Command {
	private String _newFileName;
	private String _oldFileName;
	public CommandLoad(String fileName) {
		_oldFileName = Controller.getDBStorage().getFileStorage().getFile().getName();
		_newFileName = fileName;
	}
	@Override
	public String execute() {
		Controller.init(_newFileName);
		return "Loaded/created \""+_newFileName+"\"";
		
	}

	@Override
	public String undo() {
		Controller.init(_oldFileName);
		return "Loaded \""+_oldFileName+"\"";
	}

	@Override
	public boolean isUndoable() {
		// TODO Auto-generated method stub
		return true;
	}

}
