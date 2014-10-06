package todologApp;

import java.util.LinkedList;

public class CommandMarkAsDone implements Command{
	private Task _task;
	public CommandMarkAsDone(Task task) {
		_task = task;
	}
	public String execute() {
		String feedback;
		_task.setTaskStatus(true);
		feedback=_task.getTaskName()+" is Complete";
		return feedback;
		}
	
	public String undo() {
		String feedback;
		_task.setTaskStatus(false);
		feedback=_task.getTaskName()+" is still not complete";
		return feedback;
	}

}

