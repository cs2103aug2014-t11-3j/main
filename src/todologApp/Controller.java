package todologApp;

// remember to write unit test as you code
public class Controller {
	
	private static Storage _DBStorage;
	private static History _history;
	
	public static void setStorage(Storage storage) {
		_DBStorage = storage;
	}
	
	public static Storage getDBStorage() {
		return _DBStorage;
	}
	
	public static void setHistoryStorage(History history) {
		_history = history;
	}
	public static void acceptUserCommand(String userCommand) {
		Command command = createCommand(userCommand);
		command.execute();
		//...
		command.undo();
	}
	private static Command createCommand(String userCommand) {
		//TODO parse command
		return null;
	}

}
