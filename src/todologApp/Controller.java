package todologApp;

import java.util.LinkedList;

import javax.swing.JTextArea;

// remember to write unit test as you code
public class Controller {

	private static DBStorage _dbStorage;

	private static History _history;
	private static String _display;
	public void setStorage(DBStorage DBstorage) {
		_dbStorage = DBstorage;
	}
	

	public static DBStorage getDBStorage() {
		return _dbStorage;

	}
	
	public static void setHistoryStorage(History history) {
		_history = history;
	}
	public static void setDisplay(String display) {
		_display = display;
	}
	
	public static String createNewDisplay() {
		String display = "";
		LinkedList<Task> tasks = _dbStorage.load();
		for (int i = 0; i< tasks.size(); i++) {
			Task task = tasks.get(i);
			switch (task.getTaskType()) {
				case FLOATING:
					display += String.valueOf(i+1)+". "+ task.getTaskName()+" "
							+ String.valueOf(task.getTaskStatus()) +'\n';
					break;
				case TIMED:
					display += String.valueOf(i+1)+". "+ task.getTaskName()+
						" "+task.getTaskDay()+" "+task.getStartTime()+" "+
						task.getEndDay()+" "+task.getEndTime()+" "
						+ String.valueOf(task.getTaskStatus())+'\n';
					break;
				case DEADLINE:
					display += String.valueOf(i+1)+". "+ task.getTaskName()+
						" "+task.getEndDay()+" "+task.getEndTime()+" "
						+ String.valueOf(task.getTaskStatus())+'\n';
					break;
				default:
					display += "invalid"+'\n';
					break;
			}
			
		}
		return display;
	}
	
	public static void acceptUserCommand(String userCommand) {
		Command command = createCommand(userCommand); //exception here
		command.execute();
		setDisplay(createNewDisplay());
		
	}

	public static Command createCommand(String userCommand) {
		String firstWord = getFirstWord(userCommand);
		String restOfTheString = getTheRestOfTheString(userCommand);
		if (firstWord.equalsIgnoreCase("add")) {
			Task task = new Task(restOfTheString); // exception here
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
			CommandMarkAsDone command = new CommandMarkAsDone(task);
			return command;
//		} else if (firstWord.equalsIgnoreCase("display")) {
//			Task task = _dbStorage.load().get(index-1);
//			CommandDisplay command = new CommandDisplay(task);
//			return command;
		}else if (firstWord.equalsIgnoreCase("edit")) {
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(getFirstWord(restOfTheString));
			restOfTheString = getTheRestOfTheString(restOfTheString);
			Task task = _dbStorage.load().get(index-1);
			CommandEdit command = new CommandEdit(task, restOfTheString);
			return command;
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
		return _display;
	}

	public static void init() {
		_dbStorage = new DBStorage();
	}
	public static void init(String fileName) {
		_dbStorage = new DBStorage(fileName);
	}

}
