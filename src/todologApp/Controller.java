package todologApp;

import java.util.LinkedList;

import javax.swing.JTextArea;

// remember to write unit test as you code
public class Controller {

	private static DBStorage _dbStorage;

	private static History _history;
	
	public void setStorage(DBStorage DBstorage) {
		_dbStorage = DBstorage;
	}
	

	public static DBStorage getDBStorage() {
		return _dbStorage;

	}
	
	public static void setHistoryStorage(History history) {
		_history = history;
	}
	public static void acceptUserCommand(String userCommand) {
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
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(restOfTheString);
			Task task = _dbStorage.load().get(index-1);
			CommandDelete command = new CommandDelete(task);
			return command;
		} else if (firstWord.equalsIgnoreCase("done")) {
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(restOfTheString);
			Task task = _dbStorage.load().get(index-1);
			CommandDone command = new CommandDone(task);
			return command;
		} else if (firstWord.equalsIgnoreCase("display")) {
			Task task = _dbStorage.load().get(index-1);
			CommandDisplay command = new CommandDisplay(task);
			return command;
//			else if (firstWord.equalsIgnoreCase("edit")) {
//			CommandEdit command = new CommandEdit(restOfTheString);
//			return command;
//		} else if (firstWord.equalsIgnoreCase("search")) {
//			CommandSearch command = new CommandSearch(restOfTheString);
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
		LinkedList<Task> tasks = _dbStorage.load();
		for (int i = 0; i< tasks.size(); i++) {
			Task task = tasks.get(i);
			output += String.valueOf(i+1)+". "+ task.getTaskName()+'\n';
		}
		return output;
	}

	public static void init() {
		_dbStorage = new DBStorage();
	}
	public static void init(String fileName) {
		_dbStorage = new DBStorage(fileName);
	}

}
