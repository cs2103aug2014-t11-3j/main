package command;

import java.util.LinkedList;

import common.Task;

import controller.Controller;

public class CommandNumber implements Command {
	private int _index;
	private Task _task;
	private LinkedList<Task> _displayList;
	
	private static final int CORRECTION_INDEX = 1;
	
	private static final String FEEDBACK_VALID_TASK = "This is a valid task";
	private static final String FEEDBACK_INVALID_TASK = "This is not a valid index";
	
	public CommandNumber(int index) {
		_index = index - CORRECTION_INDEX;
	}

	@Override
	public String execute() {
		_displayList = Controller.getScheduleList();
		try {
			_task = _displayList.get(_index);
			Controller.setFocusTask(_task);
			return FEEDBACK_VALID_TASK;
		} catch (IndexOutOfBoundsException ioobe) {
			_task = null ;
			return FEEDBACK_INVALID_TASK;
		}
	}

	@Override
	public String undo() {
		return "Cannot be undone!";
	}

	@Override
	public boolean isUndoable() {
		return false ;
	}

	public String tryExecute() {
		_displayList = Controller.getScheduleList();
		try {
			_task = _displayList.get(_index);
			Controller.setFocusTask(_task);
			return FEEDBACK_VALID_TASK;
		} catch (IndexOutOfBoundsException ioobe) {
			_task = null ;
			return FEEDBACK_INVALID_TASK;
		}
	}

	public Task getTask() {
		return _task;
	}

}
