package todologApp;

import java.util.LinkedList;

// remember to write unit test as you code

public class Controller {

	private static DBStorage _dbStorage;

	private static History _history;
	private static InputHistory _input;
	private static String _textDisplay;
	private static LinkedList<Task> _displayList;
	private static Task _focusTask;
	private static String _feedback;
	private static String _viewOrSearchType;
	private static CommandView _currentViewMode;
	private static CommandSearch _currentSearchCriterion;
	private static final String FEEDBACK_START = "To start, type a command HELP \n"
			+ "Or enter a command: add, delete, edit, done, view, search.\n";
	
	public static void setStorage(DBStorage DBstorage) {
		_dbStorage = DBstorage;
	}
	
	public static DBStorage getDBStorage() {
		return _dbStorage;
	}
	public static LinkedList<Task> getCurrentView() {
		LinkedList<Task> displayTasks = new LinkedList<Task>();
		for (Task task : _displayList) {
			if (task.getTaskType() != TaskType.FLOATING) {
				displayTasks.add(task);
			}
		}
		return displayTasks;
	}
	public static LinkedList<Task> getDisplayList() {
		return _displayList;
	}
	public static LinkedList<Task> getFloatingTasksList() {
		LinkedList<Task> floatingTasks = new LinkedList<Task>();
		for (Task task : _dbStorage.load()) {
			if (task.getTaskType() == TaskType.FLOATING) {
				floatingTasks.add(task);
			}
		}
		return floatingTasks;
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
	
	public static void setInputHistory (InputHistory input) {
		_input = input;
	}
	public static InputHistory getInput() {
		return _input;
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
			_input.addInput(userCommand);
			Command command = Parser.createCommand(userCommand);
			_feedback = command.execute();
			if (command instanceof CommandSearch) {
				_displayList = ((CommandSearch) command).getReturnList();
				_currentSearchCriterion = (CommandSearch) command;
				_displayList = _currentSearchCriterion.getReturnList();
				setViewOrSearchType("Search results for \""+((CommandSearch) command).getSearchKey()+"\"");
			} else if (command instanceof CommandView) {
				setFocusTask(null);
				if (((CommandView)command).getViewType() != null) {
					_currentViewMode = (CommandView) command;
					_displayList =  _currentViewMode.getReturnList();
					setViewOrSearchType(((CommandView) command).getViewType()+" events and deadlines:");
				}
			} else {
				_currentViewMode.execute();
				_displayList =  _currentViewMode.getReturnList();
				setViewOrSearchType(_currentViewMode.getViewType()+" events and deadlines:");
			}
			if (!(command instanceof CommandUndo) && !(command instanceof CommandRedo) 
					&& !(command instanceof CommandSearch) && !(command instanceof CommandView) 
					&& !(command instanceof CommandHelp)){
				_history.addCommand(command);
			}
		} catch (Exception e) {
			_feedback = e.getMessage();
			
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

			} else if (command instanceof CommandDelete) {
				((CommandDelete) command).fakeExecute();
				Task task = ((CommandDelete) command).getDeletedTask();
				LinkedList<String> details = ControllerFeedbackHelper.createHelperTexts("delete",task);
				return details;
			} else if (command instanceof CommandDeleteAll) {
				((CommandDeleteAll) command).fakeExecute();
				LinkedList<String> details = ControllerFeedbackHelper.createHelperTexts("deleteall",null);
				return details;
			} else if (command instanceof CommandMarkAsDone) {
				((CommandMarkAsDone) command).fakeExecute();
				Task task = ((CommandMarkAsDone) command).getMarkedTask();
				LinkedList<String> details = ControllerFeedbackHelper.createHelperTexts("done",task);
				return details;
			} else if (command instanceof CommandNumber) {
				((CommandNumber) command).fakeExecute();
				Task task = ((CommandNumber) command).getTask();
				LinkedList<String> details = ControllerFeedbackHelper.createHelperTexts("number",task);
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
		_input = new InputHistory();
		_feedback = FEEDBACK_START;
		_currentViewMode = new CommandView("this week");
		_currentViewMode.execute();
		_displayList = _currentViewMode.getReturnList();
		setViewOrSearchType(_currentViewMode.getViewType()+" events and deadlines:");
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
	
	public static int getNumberOfScheduledTasks() {
		int numberOfScheduledTasks = 0;
		for (Task task : _displayList) {
			if (task.getTaskType() != TaskType.FLOATING) {
				numberOfScheduledTasks++;
			}
		}
		return numberOfScheduledTasks;
	}

	public static String getViewOrSearchType() {
		return _viewOrSearchType;
	}
	private static void setViewOrSearchType(String text) {
		_viewOrSearchType = text;
	}
}
