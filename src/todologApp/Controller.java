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
	public static Command createCommand(String userCommand) {
		String firstWord = getFirstWord(userCommand);
		String restOfTheString = getTheRestOfTheString(userCommand);
		if (firstWord.equalsIgnoreCase("add")) {
			Task task = new Task(restOfTheString);
			CommandAdd command = new CommandAdd(task);
			return command;
		} else if (firstWord.equalsIgnoreCase("delete")) {
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(restOfTheString);
			CommandDelete command = new CommandDelete(index);
			return command;
		} else if (firstWord.equalsIgnoreCase("edit")) {
			CommandEdit command = new CommandEdit(restOfTheString);
			return command;
		} else if (firstWord.equalsIgnoreCase("search")) {
			CommandSearch command = new CommandSearch(restOfTheString);
			return command;
		}
		return null;
	}
	
	public static String getTheRestOfTheString(String userCommand) {
		String[] result = userCommand.split(" ", 2);
		String restOfTheWord = result[1];
		return restOfTheWord;
	}

	public static String getFirstWord(String userCommand) {
		String[] result = userCommand.split(" ", 2);
		String firstWord = result[0];
		return firstWord;
	}

}
