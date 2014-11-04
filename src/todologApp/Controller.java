package todologApp;

import java.util.LinkedList;

// remember to write unit test as you code

public class Controller {

	private static DBStorage _dbStorage;

	private static History _history;
	private static String _textDisplay;
	private static LinkedList<Task> _displayList;
	private static Task _focusTask;
	private static String _feedback;
	private static final String FEEDBACK_START = "To start, enter a command: add, delete, edit, done.\n";
	
	public static void setStorage(DBStorage DBstorage) {
		_dbStorage = DBstorage;
	}
	
	public static DBStorage getDBStorage() {
		return _dbStorage;
	}
	
	public static LinkedList<Task> getDisplayList() {
		return _displayList;
	}
	public static Task getFocusTask() {
		return _focusTask;
	}
	public static void setFocusTask(Task focusTask) {
		_focusTask = focusTask;
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
		try {
			Command command = Parser.createCommand(userCommand);
			_feedback = command.execute();
			if (command instanceof CommandSearch) {
				_displayList = ((CommandSearch) command).getReturnList();
			} else if (command instanceof CommandView) {
				_displayList = ((CommandView) command).getReturnList();
			} else {
				_displayList = _dbStorage.load();
			}
			if (!(command instanceof CommandUndo) && !(command instanceof CommandRedo) 
					&& !(command instanceof CommandSearch) && !(command instanceof CommandView)){
				_history.addCommand(command);
			}
		} catch (Exception e) {
			_feedback = e.getMessage();
			_displayList = _dbStorage.load();
		}
		//_textDisplay = createNewDisplay();
	}
	
	public static LinkedList<String> getCommandEntryHelperDetailsFromInput(String userCurrentInput) {
		try {			
			Command command = Parser.createCommand(userCurrentInput);
			if (command instanceof CommandAdd) {
				/*Task task = Parser.createTask(userCurrentInput);
				LinkedList<String> details = ControllerFeedbackHelper.createHelperTexts("add",task);
				return details;*/
				Task task = ((CommandAdd) command).getAddedTask();
				LinkedList<String> details = ControllerFeedbackHelper.createHelperTexts("add",task);
				return details;
			} else if (command instanceof CommandEdit) {
				((CommandEdit) command).fakeExecute();
				Task task = ((CommandEdit) command).getCurrentTask();
				LinkedList<String> currentTaskDetails = ControllerFeedbackHelper.createHelperTexts("edit",task);
				task = ((CommandEdit) command).getEditedTask();
				LinkedList<String> newTaskDetails = ControllerFeedbackHelper.createHelperTexts("edit",task);
				LinkedList<String> details = new LinkedList<String>(currentTaskDetails);
				details.addAll(newTaskDetails);
				return details;
			} 
			 else {
				return new LinkedList<String>();
			}
		} catch (Exception e) {
			return new LinkedList<String>();
		}
	}

	// unused
	// code for TextGUI display
	public static String getPlainTextOutput() {
		return _textDisplay;
	}

	public static void init() {
		_dbStorage = new DBStorage();
		//_textDisplay = createNewDisplay();
		_history = new History();
		_feedback = FEEDBACK_START;
	}
	public static void init(String fileName) {
		_dbStorage = new DBStorage(fileName);
	}


	public static String getFeedback() {
		return _feedback;
	}

	public static void resetDisplayListToAll() {
		_displayList = _dbStorage.load();
		
	}

}
