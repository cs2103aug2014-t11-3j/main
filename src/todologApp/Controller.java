package todologApp;

import java.util.LinkedList;

import javax.swing.JTextArea;

// remember to write unit test as you code
public class Controller {

	private static  DBStorage _DBStorage;

	private static History _history;
	public Controller() {
		_DBStorage = new DBStorage();
		//_history = new History();
	}
	public void setStorage(DBStorage DBstorage) {
		_DBStorage = DBstorage;
	}
	

	public static DBStorage getDBStorage() {
		return _DBStorage;

	}
	
	public void setHistoryStorage(History history) {
		_history = history;
	}
	public void acceptUserCommand(String userCommand) {
		Command command = createCommand(userCommand);
		command.execute();
	}

	public static Command createCommand(String userCommand) {
		String firstWord = getFirstWord(userCommand);
		String restOfTheString = getTheRestOfTheString(userCommand);
		if (firstWord.equalsIgnoreCase("add")) {
			Task task = new Task(restOfTheString);
			CommandAdd command = new CommandAdd(task);
			return command;
		} else if (firstWord.equalsIgnoreCase("delete")) {
			Task task = new Task(restOfTheString);
			CommandDelete command = new CommandDelete(task);
			return command;
//		} else if (firstWord.equalsIgnoreCase("edit")) {
//			Task task = new Task(restOfTheString);
//			CommandEdit command = new CommandEdit(task);
//			return command;
//		} else if (firstWord.equalsIgnoreCase("search")) {
//			Task task = new Task(restOfTheString);
//			CommandSearch command = new CommandSearch(task);
//			return command;
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
	public static String getOutput() {
		String output = "";
		LinkedList<Task> tasks = _DBStorage.load();
		for (Task task:tasks) {
			output += task.getTaskName()+'\n';
		}
		return output;
	}

}
