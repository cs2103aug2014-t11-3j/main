package todologApp;

// remember to write unit test as you code
public class Controller {
	
	private static Storage _storage;
	private static History _history;
	
	public static void setStorage(Storage storage) {
		_storage = storage;
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
		String firstWord = getFirstWord(userCommand);
		String restOfTheString = getTheRestOfTheString(userCommand);
		if (firstWord.equalsIgnoreCase("add")) {
			Task task = new Task(restOfTheString);
			CommandAdd command = new CommandAdd(task);	
		} else if (firstWord == "delete")
			
		return null;
	}
	private static String getTheRestOfTheString(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}
	private static String getFirstWord(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}

}
