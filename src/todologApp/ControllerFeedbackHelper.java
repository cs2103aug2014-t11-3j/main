package todologApp;

import java.util.LinkedList;

import org.joda.time.format.DateTimeFormat;

public class ControllerFeedbackHelper {
	public static LinkedList<String> createHelperTexts(String commandType, Task task) {
		LinkedList<String> helperTexts = new LinkedList<String>();
		helperTexts.add(commandType);
		if (task == null) { 
			return helperTexts; 
		}
		switch (task.getTaskType()) {
			case TIMED:
				helperTexts.addAll(createTimedTaskHelper(task));
				break;
			case DEADLINE:
				helperTexts.addAll(createDeadlineHelper(task));
				break;
			default:
				helperTexts.addAll(createFloatingTaskHelper(task));
				break;
		}
		return helperTexts;
	}

	private static LinkedList<String> createFloatingTaskHelper(Task task) {
		LinkedList<String> helperTexts = new LinkedList<String>();
		helperTexts.add(task.getTaskType().toString());
		helperTexts.add(task.getTaskName().toString());
		helperTexts.add(task.getTaskPerson().toString());
		helperTexts.add(task.getTaskVenue().toString());
		return helperTexts;
	}

	private static LinkedList<String> createDeadlineHelper(Task task) {
		LinkedList<String> helperTexts = new LinkedList<String>();
		helperTexts.add(task.getTaskType().toString());
		helperTexts.add(task.getTaskName().toString());
		helperTexts.add((DateTimeFormat.forPattern("dd/MM/yyyy")).print(task.getEnd()));
		helperTexts.add((DateTimeFormat.forPattern("HH:mm")).print(task.getEnd()));
		helperTexts.add(task.getTaskPerson().toString());
		helperTexts.add(task.getTaskVenue().toString());
		return helperTexts;
	}

	private static LinkedList<String> createTimedTaskHelper(Task task) {
		LinkedList<String> helperTexts = new LinkedList<String>();
		helperTexts.add(task.getTaskType().toString());
		helperTexts.add(task.getTaskName().toString());
		helperTexts.add((DateTimeFormat.forPattern("dd/MM/yyyy")).print(task.getStart()));
		helperTexts.add((DateTimeFormat.forPattern("HH:mm")).print(task.getStart()));
		helperTexts.add((DateTimeFormat.forPattern("dd/MM/yyyy")).print(task.getEnd()));
		helperTexts.add((DateTimeFormat.forPattern("HH:mm")).print(task.getEnd()));
		helperTexts.add(task.getTaskPerson().toString());
		helperTexts.add(task.getTaskVenue().toString());
		return helperTexts;
	}

}
