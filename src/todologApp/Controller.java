package todologApp;

import java.util.LinkedList;

// remember to write unit test as you code

public class Controller {

	private static DBStorage _dbStorage;

	private static History _history;
	private static String _textDisplay;

	private static String _feedback;
	private static final String FEEDBACK_START = "To start, enter a command: add, delete, edit, done.";
	private static final String FEEDBACK_TYPE = "Type in a command: add, delete, edit, done.";

	private static final String HELP_TEXT_ADD = "To add, enter:\n - add \"[task name]\" (from [date] @ [time] to "
			+ "[date] @ [time]).\n - add \"[task name]\" by [date] @ [time] if you want to create a\ndeadline.";

	private static final String HELP_TEXT_DELETE = "To delete, enter:\n - delete [task number].";

	private static final String HELP_TEXT_DONE = "To mark/unmark a task as done, enter:\n - done [task number].";

	private static final String HELP_TEXT_EDIT = "To edit task name, enter:\n - edit [task number] \"[new name]\"";

	public void setStorage(DBStorage DBstorage) {
		_dbStorage = DBstorage;
	}

	public static DBStorage getDBStorage() {
		return _dbStorage;

	}

	public static void setHistoryStorage(History history) {
		_history = history;
	}

	public static String createNewDisplay() {
		String display = "";
		LinkedList<Task> tasks = _dbStorage.load();
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			switch (task.getTaskType()) {
			case FLOATING:
				display += String.valueOf(i + 1) + ". " + task.getTaskName()
						+ " " + String.valueOf(task.getTaskStatus()) + '\n';
				break;
			case TIMED:
				display += String.valueOf(i + 1) + ". " + task.getTaskName()
						+ " " + task.getStartDay() + " " + task.getStartTime()
						+ " " + task.getEndDay() + " " + task.getEndTime()
						+ " " + String.valueOf(task.getTaskStatus()) + '\n';
				break;
			case DEADLINE:
				display += String.valueOf(i + 1) + ". " + task.getTaskName()
						+ " " + task.getEndDay() + " " + task.getEndTime()
						+ " " + String.valueOf(task.getTaskStatus()) + '\n';
				break;
			default:
				display += "invalid" + '\n';
				break;
			}

		}
		return display;
	}

	public static void acceptUserCommand(String userCommand) {
		Command command;
		try {
			command = createCommand(userCommand);
			_feedback = command.execute();
		} catch (Exception e) {
			_feedback = e.getMessage();
		}
		_textDisplay = createNewDisplay();
	}

	public static Command createCommand(String userCommand) throws Exception {
		userCommand = userCommand.trim();
		String firstWord = getFirstWord(userCommand);
		if (firstWord.equalsIgnoreCase("add")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			if (restOfTheString == null) {
				throw new Exception(HELP_TEXT_ADD);
			}
			Task task = new Task(restOfTheString);
			CommandAdd command = new CommandAdd(task);
			return command;
		} else if (firstWord.equalsIgnoreCase("delete")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			if (restOfTheString == null) {
				throw new Exception(HELP_TEXT_DELETE);
			}
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(restOfTheString);
			Task task = _dbStorage.load().get(index - 1);
			CommandDelete command = new CommandDelete(index);
			return command;
		} else if (firstWord.equalsIgnoreCase("done")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			if (restOfTheString == null) {
				throw new Exception(HELP_TEXT_DONE);
			}
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(restOfTheString);
			Task task = _dbStorage.load().get(index - 1);
			CommandMarkAsDone command = new CommandMarkAsDone(index);
			return command;
			// } else if (firstWord.equalsIgnoreCase("display")) {
			// Task task = _dbStorage.load().get(index-1);
			// CommandDisplay command = new CommandDisplay(task);
			// return command;
		} else if (firstWord.equalsIgnoreCase("edit")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			if (restOfTheString == null) {
				throw new Exception(HELP_TEXT_EDIT);
			}
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(getFirstWord(restOfTheString));
			Task task = _dbStorage.load().get(index - 1);
			restOfTheString = getTheRestOfTheString(restOfTheString);
			CommandEdit command = new CommandEdit(task, restOfTheString);
			return command;
			// } else if (firstWord.equalsIgnoreCase("search")) {
			// CommandSearch command = new CommandSearch(restOfTheString);
			// return command;
		} else {
			throw new Exception("Invalid command.\n" + FEEDBACK_TYPE);
		}
	}

	public static String getTheRestOfTheString(String userCommand)
			throws Exception {
		try {
			String[] result = userCommand.split(" ", 2);
			String restOfTheWord = result[1];
			return restOfTheWord;
		} catch (ArrayIndexOutOfBoundsException aioobe) {
			return null;
		}
	}

	public static String getFirstWord(String userCommand) {
		String[] result = userCommand.split(" ", 2);
		String firstWord = result[0];
		return firstWord;
	}

	public static String getPlainTextOutput() {
		return _textDisplay;
	}

	public static void init() {
		_dbStorage = new DBStorage();
		_textDisplay = createNewDisplay();
		_feedback = FEEDBACK_START;
	}

	public static void init(String fileName) {
		_dbStorage = new DBStorage(fileName);
	}

	public static String getFeedback() {
		return _feedback;
	}

}
