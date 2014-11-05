package todologApp;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class UIFeedbackHelper {
	public static String createCmdAddHelpText(LinkedList<String> entryHelper) {
		if (entryHelper.isEmpty()) {
			return createCmdAddHelpTextEmptyDetails();
		}
		switch (entryHelper.poll()) {
			case "TIMED":
				return createCmdAddTimedTaskHelperText(entryHelper);
			case "DEADLINE":
				return createCmdAddDeadlineTaskHelperText(entryHelper);
			default:
				return createCmdAddFloatingTaskHelperText(entryHelper);
		}
	}

	private static String createCmdAddHelpTextEmptyDetails() {
		String helperText = "* ADD *\n";
		helperText += " To add, enter:  add [task name].\n"
				+ " - Use keywords: from [date], to [date], by [date], on [date], at [time], "
				+ "with [person], at [venue]\n"
				+ "   to add more details."
				+ " - Use date format: DDMMYY\n"
				+ " - Use time format: HHmm\n"
				+ " - Use \"today\", \"tomorrow\", \"tmr\", or "
				+ "days of week and short forms: monday, mon, tuesday, tue,...";
		return helperText;
	}
	
	private static String createCmdAddFloatingTaskHelperText(LinkedList<String> entryHelper) {
		String helperText = "* TASK *\n";
		helperText += "Name: " + entryHelper.poll() + "\n" +
					"Person: " + entryHelper.poll() + "\n" +
					"Venue: " + entryHelper.poll() + "\n";
		return helperText;
	}

	private static String createCmdAddDeadlineTaskHelperText(LinkedList<String> entryHelper) {
		String helperText = "* DEADLINE TASK *\n";
		helperText += "Name: " + entryHelper.poll() + "\n" +
					"Date: "+ entryHelper.poll() + "\n" +
					"Time: "+ entryHelper.poll() + "\n" +
					"Person: " + entryHelper.poll() + "\n" +
					"Venue: " + entryHelper.poll() + "\n";
		return helperText;
	}

	private static String createCmdAddTimedTaskHelperText(LinkedList<String> entryHelper) {
		String helperText = "* TASK *\n";
		helperText += "Name: " + entryHelper.poll() + "\n" +
					"Start date: "+ entryHelper.poll() + "\n" +
					"Start time: "+ entryHelper.poll() + "\n" +
					"End date: "+ entryHelper.poll() + "\n" +
					"End time: "+ entryHelper.poll() + "\n" +
					"Person: " + entryHelper.poll() + "\n" +
					"Venue: " + entryHelper.poll() + "\n";
		return helperText;
	}
	
	public static String createCmdEditHelpText(LinkedList<String> entryHelper) {
		if (entryHelper.size() == 1) {
			return createCmdEditHelpTextEmptyDetails();
		} 
		switch (entryHelper.poll()) {
			case "TIMED":
				return createCmdEditTimedTaskHelperText(entryHelper);
			case "DEADLINE":
				return createCmdEditDeadlineTaskHelperText(entryHelper);
			default:
				return createCmdEditFloatingTaskHelperText(entryHelper);
		}
	}
	
	private static String createCmdEditFloatingTaskHelperText(
			LinkedList<String> entryHelper) {
		String helperText = "* EDIT TASK *\n";
		String oldName = entryHelper.poll();
		String oldPerson = entryHelper.poll();
		String oldVenue = entryHelper.poll();
		try {
			entryHelper.removeFirst(); entryHelper.removeFirst();
			String newName = entryHelper.poll();
			String newPerson = entryHelper.poll();
			String newVenue = entryHelper.poll();
			if (oldName.equals(newName)) {
				helperText += "Name: " + oldName + "\n";
			} else {
				helperText += "Name: " + oldName + " --> " + newName + "\n";
			}
			if (oldPerson.equals(newPerson)) {
				helperText += "Person: " + oldPerson + "\n";
			} else {
				helperText += "Person: " + oldPerson + " --> " + newPerson + "\n";
			}
			if (oldVenue.equals(newVenue)) {
				helperText += "Venue: " + oldVenue + "\n";
			} else {
				helperText += "Venue: " + oldVenue + " --> " + newVenue + "\n";
			}
			return helperText;
		} catch (NoSuchElementException nsee) {
			helperText += "Name: " + oldName + "\n" +
						"Person: " + oldPerson + "\n" +
						"Venue: " + oldVenue + "\n";
			return helperText;
		}
	}
	
	private static String createCmdEditHelpTextEmptyDetails() {
		String helperText = "* EDIT *\n";
		helperText += " To edit, enter:  edit [index] [edit type] [details].\n"
				+ " - Edit types are name, start day, start time, end day, end time, \n"
				+ "   person, venue.\n"
				+ " - Cannot add time to an untimed task.\n"
				+ " - Cannot edit a task to become a deadline or vice versa.";
		return helperText;
	}
	
	private static String createCmdEditDeadlineTaskHelperText(
			LinkedList<String> entryHelper) {
		String helperText = "* EDIT DEADLINE TASK *\n";
		String oldName = entryHelper.poll();
		String oldDate = entryHelper.poll();
		String oldTime = entryHelper.poll();
		String oldPerson = entryHelper.poll();
		String oldVenue = entryHelper.poll();
		try {
			entryHelper.removeFirst(); entryHelper.removeFirst();
		
			String newName = entryHelper.poll();
			String newDate = entryHelper.poll();
			String newTime = entryHelper.poll();
			String newPerson = entryHelper.poll();
			String newVenue = entryHelper.poll();
			if (oldName.equals(newName)) {
				helperText += "Name: " + oldName + "\n";
			} else {
				helperText += "Name: " + oldName + " --> " + newName + "\n";
			}
			if (oldDate.equals(newDate)) {
				helperText += "Date: " + oldDate + "\n";
			} else {
				helperText += "Date: " + oldDate + " --> " + newDate + "\n";
			}
			if (oldTime.equals(newTime)) {
				helperText += "Time: " + oldTime + "\n";
			} else {
				helperText += "Time: " + oldTime + " --> " + newTime + "\n";
			}
			if (oldPerson.equals(newPerson)) {
				helperText += "Person: " + oldPerson + "\n";
			} else {
				helperText += "Person: " + oldPerson + " --> " + newPerson + "\n";
			}
			if (oldVenue.equals(newVenue)) {
				helperText += "Venue: " + oldVenue + "\n";
			} else {
				helperText += "Venue: " + oldVenue + " --> " + newVenue + "\n";
			}
			return helperText;
		} catch (NoSuchElementException nsee) {
			helperText += "Name: " + oldName + "\n" +
				 	"Date: " + oldDate + "\n" +
				 	"Time: " + oldTime + "\n" +
					"Person: " + oldPerson + "\n" +
					"Venue: " + oldVenue + "\n";
		return helperText;
	}
	}
	
	private static String createCmdEditTimedTaskHelperText(
						LinkedList<String> entryHelper) {
		String helperText = "* EDIT DEADLINE TASK *\n";
		String oldName = entryHelper.poll();
		String oldStartDate = entryHelper.poll();
		String oldStartTime = entryHelper.poll();
		String oldEndDate = entryHelper.poll();
		String oldEndTime = entryHelper.poll();
		String oldPerson = entryHelper.poll();
		String oldVenue = entryHelper.poll();
		try {
			entryHelper.removeFirst(); entryHelper.removeFirst();
			String newName = entryHelper.poll();
			String newStartDate = entryHelper.poll();
			String newStartTime = entryHelper.poll();
			String newEndDate = entryHelper.poll();
			String newEndTime = entryHelper.poll();
			String newPerson = entryHelper.poll();
			String newVenue = entryHelper.poll();
			if (oldName.equals(newName)) {
				helperText += "Name: " + oldName + "\n";
			} else {
				helperText += "Name: " + oldName + " --> " + newName + "\n";
			}
			if (oldStartDate.equals(newStartDate)) {
				helperText += "Start date: " + oldStartDate + "\n";
			} else {
				helperText += "Start date: " + oldStartDate + " --> " + newStartDate + "\n";
			}
			if (oldStartTime.equals(newStartTime)) {
				helperText += "Start time: " + oldStartTime + "\n";
			} else {
				helperText += "Start time: " + oldStartTime + " --> " + newStartTime + "\n";
			}
			if (oldEndDate.equals(newEndDate)) {
				helperText += "End date: " + oldEndDate + "\n";
			} else {
				helperText += "End date: " + oldEndDate + " --> " + newEndDate + "\n";
			}
			if (oldEndTime.equals(newEndTime)) {
				helperText += "End time: " + oldEndTime + "\n";
			} else {
				helperText += "End time: " + oldEndTime + " --> " + newEndTime + "\n";
			}
			if (oldPerson.equals(newPerson)) {
				helperText += "Person: " + oldPerson + "\n";
			} else {
				helperText += "Person: " + oldPerson + " --> " + newPerson + "\n";
			}
			if (oldVenue.equals(newVenue)) {
				helperText += "Venue: " + oldVenue + "\n";
			} else {
				helperText += "Venue: " + oldVenue + " --> " + newVenue + "\n";
			}
			return helperText;
		} catch (NoSuchElementException nsee) {
			helperText += "Name: " + oldName + "\n" +
					 	"Start date: " + oldStartDate + "\n" +
					 	"Start time: " + oldStartTime + "\n" +
					 	"End date: " + oldEndDate + "\n" +
					 	"End time: " + oldEndTime + "\n" +
						"Person: " + oldPerson + "\n" +
						"Venue: " + oldVenue + "\n";
			return helperText;
		}
		
	}

	public static String createCmdDeleteHelpText(LinkedList<String> entryHelper) {
		if (entryHelper.isEmpty()) {
			return createCmdDeleteHelpTextEmptyDetails();
		}
		switch (entryHelper.poll()) {
			case "TIMED":
				return createCmdDeleteTimedTaskHelperText(entryHelper);
			case "DEADLINE":
				return createCmdDeleteDeadlineTaskHelperText(entryHelper);
			default:
				return createCmdDeleteFloatingTaskHelperText(entryHelper);
		}
	}

	private static String createCmdDeleteHelpTextEmptyDetails() {
		String helperText = "* DELETE *\n";
		helperText += " To DELETE, enter:  delete [index].\n";
		return helperText;
	}

	private static String createCmdDeleteFloatingTaskHelperText(LinkedList<String> entryHelper) {
		String helperText = "* DELETE TASK *\n";
		helperText += "Name: " + entryHelper.poll() + "\n" +
					"Person: " + entryHelper.poll() + "\n" +
					"Venue: " + entryHelper.poll() + "\n";
		return helperText;
	}

	private static String createCmdDeleteDeadlineTaskHelperText(LinkedList<String> entryHelper) {
		String helperText = "* DELETE DEADLINE *\n";
		helperText += "Name: " + entryHelper.poll() + "\n" +
					"Date: "+ entryHelper.poll() + "\n" +
					"Time: "+ entryHelper.poll() + "\n" +
					"Person: " + entryHelper.poll() + "\n" +
					"Venue: " + entryHelper.poll() + "\n";
		return helperText;
	}

	private static String createCmdDeleteTimedTaskHelperText(LinkedList<String> entryHelper) {
		String helperText = "* DELETE TASK *\n";
		helperText += "Name: " + entryHelper.poll() + "\n" +
					"Start date: "+ entryHelper.poll() + "\n" +
					"Start time: "+ entryHelper.poll() + "\n" +
					"End date: "+ entryHelper.poll() + "\n" +
					"End time: "+ entryHelper.poll() + "\n" +
					"Person: " + entryHelper.poll() + "\n" +
					"Venue: " + entryHelper.poll() + "\n";
		return helperText;
	}
}
