package todologApp;

import java.util.LinkedList;

// remember to write unit test as you code

public class Controller {

	private static DBStorage _dbStorage;

	private static History _history;
	private static String _textDisplay;

	private static String _feedback;
	private static final String FEEDBACK_START = "To start, enter a command: add, delete, edit, done.";
	
	public static void setStorage(DBStorage DBstorage) {
		_dbStorage = DBstorage;
	}
	

	public static DBStorage getDBStorage() {
		return _dbStorage;

	}
	
	public static void setHistory(History history) {
		_history = history;
	}
	public static History getHistory() {
		return _history;

	}
	 
	// unused
	// code for TextGUI display
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
						" "+task.getStartDay()+" "+task.getStartTime()+" "+
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
		Command command;
		try {
			command = Parser.createCommand(userCommand);
			if (!(command instanceof CommandUndo)){
				_history.addCommand(command);
			}
			_feedback = command.execute();
		} catch (Exception e) {
			_feedback = e.getMessage();
		}
		_textDisplay = createNewDisplay();
	}

	public static String getPlainTextOutput() {
		return _textDisplay;
	}

	public static void init() {
		_dbStorage = new DBStorage();
		_textDisplay = createNewDisplay();
		_history = new History();
		_feedback = FEEDBACK_START;
	}
	public static void init(String fileName) {
		_dbStorage = new DBStorage(fileName);
	}


	public static String getFeedback() {
		return _feedback;
	}

}
