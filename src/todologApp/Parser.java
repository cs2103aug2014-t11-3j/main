package todologApp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class Parser {

	private static String INVALID_MESSAGE = "Invalid Input!";

	//MISC
	private static String EMPTY_STRING = "";
	private static String SINGLE_SPACE = " ";
	private static String DATE_SEPARATOR = "/";
	private static String SYMBOL_DASH = "-";
	private static String SYMBOL_AT = "@";
	private static String QUOTATION_MARK = "\"";

	//KEYWORDS
	private static String KEYWORD_DAY_STARTING = "from";
	private static String KEYWORD_DAY_STARTING_2 = "on";
	private static String KEYWORD_DAY_ENDING = "to";
	private static String KEYWORD_DEADLINE = "by";
	private static String KEYWORD_RECURRING = "every";
	private static String KEYWORD_WITH = "with";
	private static String KEYWORD_AT = "at";
	private static String KEYWORD_IN = "in";

	//DAY KEYWORDS
	private static String DAY_KEYWORD_TODAY = "Today";
	private static String DAY_KEYWORD_TDY = "Tdy";
	private static String DAY_KEYWORD_TOMORROW = "Tomorrow";
	private static String DAY_KEYWORD_TMR = "tmr";
	private static String DAY_KEYWORD_MONDAY = "Monday";
	private static String DAY_KEYWORD_MON = "mon";
	private static String DAY_KEYWORD_TUESDAY = "Tuesday";
	private static String DAY_KEYWORD_TUES = "tues";
	private static String DAY_KEYWORD_TUE = "tue";
	private static String DAY_KEYWORD_WEDNESDAY = "Wednesday";
	private static String DAY_KEYWORD_WED = "wed";
	private static String DAY_KEYWORD_THURSDAY = "Thursday";
	private static String DAY_KEYWORD_THURS = "thurs";
	private static String DAY_KEYWORD_THUR = "thur";
	private static String DAY_KEYWORD_THU = "thu";
	private static String DAY_KEYWORD_FRIDAY = "Friday";
	private static String DAY_KEYWORD_FRI = "fri";
	private static String DAY_KEYWORD_SATURDAY = "Saturday";
	private static String DAY_KEYWORD_SAT = "sat";
	private static String DAY_KEYWORD_SUNDAY = "Sunday";
	private static String DAY_KEYWORD_SUN = "sun";


	private static final String FEEDBACK_TYPE = "Type in a command: add, delete, edit, done.";

	private static final String HELP_TEXT_ADD = "To add, enter:\n - add \"[task name]\" (from [date] @ [time] to "
			+ "[date] @ [time]).\n - add \"[task name]\" by [date] @ [time] if you want to create a\ndeadline.";

	private static final String HELP_TEXT_DELETE = "To delete, enter:\n - delete [task number].";

	private static final String HELP_TEXT_DONE = "To mark/unmark a task as done, enter:\n - done [task number].";

	private static final String HELP_TEXT_EDIT = "To edit task name, enter:\n - edit [task number] \"[new name]\"";

	private static final int TODAY = 0;
	private static final int TOMORROW = -1;


	public static Command createCommand(String userCommand) throws Exception{
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
			if (isInteger(restOfTheString)) {
				int index = Integer.valueOf(restOfTheString);
				CommandDelete command = new CommandDelete(index);
				return command; 
			} else {
				if (restOfTheString.equalsIgnoreCase("all")) {
					CommandDeleteAll command = new CommandDeleteAll();
					return command;
				} else {
					throw new Exception(HELP_TEXT_DELETE);
				}
			}
			
		} else if (firstWord.equalsIgnoreCase("done")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			if (restOfTheString == null) {
				throw new Exception(HELP_TEXT_DONE);
			}
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(restOfTheString);
			CommandMarkAsDone command = new CommandMarkAsDone(index);
			return command;
			//		} else if (firstWord.equalsIgnoreCase("display")) {
			//			Task task = _dbStorage.load().get(index-1);
			//			CommandDisplay command = new CommandDisplay(task);
			//			return command;
		} else if (firstWord.equalsIgnoreCase("edit")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			if (restOfTheString == null) {
				throw new Exception(HELP_TEXT_EDIT);
			}
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(getFirstWord(restOfTheString));
			restOfTheString = getTheRestOfTheString(restOfTheString);
			String editType = getFirstWord(restOfTheString);
			restOfTheString = getTheRestOfTheString(restOfTheString);
			if (editType.equalsIgnoreCase("start") || editType.equalsIgnoreCase("end")) {
				editType = editType.concat(" ").concat(getFirstWord(restOfTheString));
				restOfTheString = getTheRestOfTheString(restOfTheString);
			} else if (editType.equalsIgnoreCase("task")) {
				editType = getFirstWord(restOfTheString);
				restOfTheString = getTheRestOfTheString(restOfTheString);
			}
			CommandEdit command = new CommandEdit(index, restOfTheString, editType);
			return command;
			//		} else if (firstWord.equalsIgnoreCase("search")) {
			//			CommandSearch command = new CommandSearch(restOfTheString);
			//			return command;
		} else if (firstWord.equalsIgnoreCase("undo")) {
			History history = Controller.getHistory();
			Command toBeUndone = history.removeCommand();
			CommandUndo command = new CommandUndo(toBeUndone);
			return command;
		}
		else {
			throw new Exception("Invalid command.\n"+FEEDBACK_TYPE);
		}
	}

	public static String getTheRestOfTheString(String userCommand) throws Exception {
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
	
	private static String[] generateArray(String parameter) {
		parameter = parameter.trim();
		String[] array = parameter.split(SINGLE_SPACE);
		return array;
	}
	
	public static int parseTaskEndTime(String parameter) throws Exception {
		String [] messageArray = generateArray(parameter);

		for (int i = 0; i+3<=messageArray.length; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING) && 
					messageArray[i+2].equalsIgnoreCase(SYMBOL_AT)){
				try {
					int endTime = Integer.parseInt(messageArray[i+3]);
					if (endTime >= 0000 && endTime <= 2359) {
						return endTime;
					} else {
						return 2359;
					}
				} catch (NumberFormatException nfe) {
					throw new Exception("Invalid time format");
				}
			} else if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING) && 
					messageArray[i+1].equalsIgnoreCase(SYMBOL_AT)){
				try {
					int endTime = Integer.parseInt(messageArray[i+2]);
					if (endTime >= 0000 && endTime <= 2359) {
						return endTime;
					} else {
						return 2359;
					}
				} catch (NumberFormatException nfe) {
					throw new Exception("Invalid Time Format");
				}
			}
		}

		return 2359;
	}

	public static int parseTaskStartTime(String parameter) throws Exception  {
		String [] messageArray = generateArray(parameter);

//		for (int i = 0; i+3<=messageArray.length; i++) {
//			if ((messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING)
//					|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2))
//					&& messageArray[i+2].equalsIgnoreCase(SYMBOL_AT)) {
//				try {
//					int startTime = Integer.parseInt(messageArray[i+3]);
//					if (startTime >= 0000 && startTime <= 2359) {
//						return startTime;
//					} else {
//						return 0000;
//					}
//				} catch (NumberFormatException nfe) {
//					throw new Exception("Invalid Time Format");
//				}
//			} else if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) && 
//					messageArray[i+1].equalsIgnoreCase(SYMBOL_AT)) {
//				try {
//					int startTime = Integer.parseInt(messageArray[i+2]);
//					if (startTime >= 0000 && startTime <= 2359) {
//						return startTime;
//					} else {
//						return 0000;
//					}
//				} catch (NumberFormatException nfe) {
//					throw new Exception("Invalid Time Format");
//				}
//			} 
//		}
		
		for (int i = 0; i<=messageArray.length-1; i++) {
			if ((messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING)
					|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2))) {
				for (int j=i+1; j<=messageArray.length-1) {
					if (messageArray[j])
				}
			}
		}
				return 0000;
	}

	public static int parseYear(String dateInString) throws Exception {
		int _year = 14;
		_year = Integer.parseInt(dateInString);
		_year = _year % 100 + 2000 ;
		if (_year >= 2014) {
			return _year;
		} else {
			throw new Exception("You added a day in the past!");
		}
	}
	public static int parseDayOfMonth(String dateInString) throws Exception {
		int month = parseMonth(dateInString);
		int date = Integer.parseInt(dateInString);
		date = date/10000;
		if (month == 2 && date > 0 && date <= 28) {
			return date;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (date > 0 && date <= 30){
				return date;
			}
		} else if (date > 0 &&date <= 31){
			return date;
		} else {
			throw new Exception("Invalid Date Format");
		}
		return date;
		
	}
	
	public static int parseMonth(String dateInString) throws Exception {
		int _month = 1;
		_month = Integer.parseInt(dateInString);
		_month = _month/100;
		_month = _month % 100;
		if (_month > 0 && _month <= 12) {
			return _month;
		} else {
			throw new Exception("Invalid Date Format");
		}

	}

	public static int parseDayOfWeek(String parameter) {
		String day = parameter;

		if (day.equalsIgnoreCase(DAY_KEYWORD_TODAY) || day.equalsIgnoreCase(DAY_KEYWORD_TDY)) {
			return TODAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_TOMORROW) || day.equalsIgnoreCase(DAY_KEYWORD_TMR)) {
			return TOMORROW;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_MONDAY) || day.equalsIgnoreCase(DAY_KEYWORD_MON)) {
			return DateTimeConstants.MONDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_TUESDAY) || day.equalsIgnoreCase(DAY_KEYWORD_TUE) || 
				day.equalsIgnoreCase(DAY_KEYWORD_TUES)) {
			return DateTimeConstants.TUESDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_WEDNESDAY) || day.equalsIgnoreCase(DAY_KEYWORD_WED)) {
			return DateTimeConstants.WEDNESDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_THURSDAY) || day.equalsIgnoreCase(DAY_KEYWORD_THURS) || 
				day.equalsIgnoreCase(DAY_KEYWORD_THUR) || day.equalsIgnoreCase(DAY_KEYWORD_THU)) {
			return DateTimeConstants.THURSDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_FRIDAY) || day.equalsIgnoreCase(DAY_KEYWORD_FRI)) {
			return DateTimeConstants.FRIDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_SATURDAY) || day.equalsIgnoreCase(DAY_KEYWORD_SAT)) {
			return DateTimeConstants.SATURDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_SUNDAY) || day.equalsIgnoreCase(DAY_KEYWORD_SUN)) {
			return DateTimeConstants.SUNDAY;
		} else {
			return TODAY;
		}
	}

	public static TaskType parseTaskType (String parameter) {
		//		String taskDateTime = parameter.substring(parameter.lastIndexOf(QUOTATION_MARK)+1);
		//		taskDateTime = taskDateTime.trim();
		//		System.out.println(parameter);
		//		System.out.println(taskDateTime);
		//		String[] analyseTask = taskDateTime.split(SINGLE_SPACE);
		String [] messageArray = generateArray(parameter);
		if (messageArray.length != 0) {
			for (int i=0; i<=messageArray.length-1; i++) {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
						|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)
						|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING)) {
					return (TaskType.TIMED);
				}
			}

			for (int i=0; i<=messageArray.length-1; i++) {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)) {
					return (TaskType.DEADLINE);
				} 
			}		

			for (int i=0; i<=messageArray.length-1; i++) {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_RECURRING)) {
					return (TaskType.RECURRING);
				} 		
			}

			for (int i=0; i<=messageArray.length-1; i++) {
				if (!messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
						&& !messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)
						&& !messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING)
						&& !messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)
						&& !messageArray[i].equalsIgnoreCase(KEYWORD_RECURRING)
						&& !messageArray[i].equalsIgnoreCase(SYMBOL_AT) 
						&& !messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING)){
					return (TaskType.FLOATING);
				} 
			}
		}

		return (TaskType.INVALID);
	}

	//			if (taskDateTime.length() == 0) {
	//				return (TaskType.FLOATING);
	//			} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_DAY_STARTING) || analyseTask[0].equalsIgnoreCase(KEYWORD_DAY_ENDING)) {
	//				return (TaskType.TIMED);
	//			} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_DEADLINE)) {
	//				return (TaskType.DEADLINE);
	//			} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_RECURRING)) {
	//				return (TaskType.RECURRING);
	//			} else {
	//				return (TaskType.INVALID);
	//			}

	//	public static String parseTaskName(String parameter) throws Exception{
	//		int firstIndex = parameter.indexOf(QUOTATION_MARK);
	//		int lastIndex = parameter.lastIndexOf(QUOTATION_MARK);
	//
	//		if (lastIndex > firstIndex) {
	//			String taskName = parameter.substring(firstIndex+1, lastIndex);
	//			return taskName;
	//		} else if (lastIndex == firstIndex) {
	//			throw new Exception("Invalid command. Missing task name.\nTask name must be inside quotation marks.");
	//		} else if (lastIndex < firstIndex) {
	//			throw new Error(); //never occurs
	//		} else {
	//			return null;
	//		}	
	//	}

	public static String parseTaskName(String parameter) throws Exception {
		String [] messageArray = generateArray(parameter);
		String taskName = EMPTY_STRING;

		for (int i=0; i<=messageArray.length-1; i++) {
			if (!messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
					&& !messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)
					&& !messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING)
					&& !messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)
					&& !messageArray[i].equalsIgnoreCase(KEYWORD_RECURRING)
					&& !messageArray[i].equalsIgnoreCase(KEYWORD_WITH)
					&& !messageArray[i].equalsIgnoreCase(KEYWORD_AT)
					&& !messageArray[i].equalsIgnoreCase(KEYWORD_IN)
					&& !messageArray[i].equalsIgnoreCase(SYMBOL_AT)) {
				taskName = taskName + messageArray[i] + SINGLE_SPACE;
			} else {
				break;
			}
		}

		return taskName.trim();
	}

	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		// only got here if we didn't return false
		return true;
	}

	public static String parseTaskPerson(String parameter) {
		String [] messageArray = generateArray(parameter);
		String taskPerson = EMPTY_STRING;

		for (int i=0; i+1<=messageArray.length-1; i++) {
			for (int j=i+1; j<=messageArray.length-1; j++) {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_WITH) 
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_DAY_STARTING)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_DAY_ENDING)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_DEADLINE)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_RECURRING)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_AT)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_IN)
						&& !messageArray[j].equalsIgnoreCase(SYMBOL_AT)) {
					taskPerson = taskPerson + messageArray[j] + SINGLE_SPACE;
				} else {
					break;
				}
			}
		}
		return taskPerson.trim();
	}

	public static String parseTaskVenue(String parameter) {
		String [] messageArray = generateArray(parameter);
		String taskVenue = EMPTY_STRING;

		for (int i=0; i+1<=messageArray.length-1; i++) {
			for (int j=i+1; j<=messageArray.length-1; j++) {
				if ( (messageArray[i].equalsIgnoreCase(KEYWORD_AT) 
						|| messageArray[i].equalsIgnoreCase(KEYWORD_IN)) 
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_DAY_STARTING)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_DAY_ENDING)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_DEADLINE)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_RECURRING)
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_WITH)
						&& !messageArray[j].equalsIgnoreCase(SYMBOL_AT)
						&& !isInteger(messageArray[j])) {
					taskVenue = taskVenue + messageArray[j] + SINGLE_SPACE;
				} else {
					break;
				}
			}
		}
		return taskVenue.trim();
	}
	
//	public static void editTask (String parameter) {
//		String [] messageArray = generateArray(parameter);
//		int taskIndex = Integer.valueOf(messageArray[0]);
//
//		if (messageArray[1].equalsIgnoreCase("start") 
//				&& messageArray[2].equalsIgnoreCase("time")) {
//			int startTime = Integer.valueOf(messageArray[3]);
//		} else if (messageArray[1].equalsIgnoreCase("end") 
//				&& messageArray[2].equalsIgnoreCase("time")) {
//			int endTime = Integer.valueOf(messageArray[3]);
//		} else if (messageArray[1].equalsIgnoreCase("start") 
//				&& messageArray[2].equalsIgnoreCase("day")) {
//
//		} else if (messageArray[1].equalsIgnoreCase("end") 
//				&& messageArray[2].equalsIgnoreCase("day")) {
//
//		} else if (messageArray[1].equalsIgnoreCase("task") 
//				&& messageArray[2].equalsIgnoreCase("name")) {
//			String taskName = EMPTY_STRING;
//			for (int i=3; i<= messageArray.length-1; i++) {
//				taskName = messageArray[i] + SINGLE_SPACE;
//			}
//			taskName = taskName.trim();
//		} else if (messageArray[1].equalsIgnoreCase("person")) {
//			String taskPerson = EMPTY_STRING;
//			for (int i=3; i<= messageArray.length-1; i++) {
//				taskPerson = messageArray[i] + SINGLE_SPACE;
//			}
//			taskPerson = taskPerson.trim();
//		} else if (messageArray[1].equalsIgnoreCase("venue")) {
//			String taskVenue = EMPTY_STRING;
//			for (int i=3; i<= messageArray.length-1; i++) {
//				taskVenue = messageArray[i] + SINGLE_SPACE;
//			}
//			taskVenue = taskVenue.trim();
//		}
//	}

	public static DateTime parseTaskStart(String parameter) throws Exception {
		String[] messageArray = generateArray(parameter);
		int year = 1, month = 1, day = 1;
		boolean hasKeyword = false;
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
			|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)) {
				hasKeyword = true;
				if (isInteger(messageArray[i+1])) {
					year = parseYear(messageArray[i+1]);
					month = parseMonth(messageArray[i+1]);
					day = parseDayOfMonth(messageArray[i+1]);	
				} else {
					int dayOfWeek = parseDayOfWeek(messageArray[i+1]);
					DateTime today = new DateTime();
					if (dayOfWeek == TODAY) {
						year = today.year().get();
						month = today.monthOfYear().get();
						day = today.dayOfMonth().get();
					} else if (dayOfWeek == TOMORROW) {
						DateTime tomorrow = today.plusDays(1);
						year = tomorrow.year().get();
						month = tomorrow.monthOfYear().get();
						day = tomorrow.dayOfMonth().get();
					} else {
						int nextDayDistance = dayOfWeek - today.dayOfWeek().get();
						if (nextDayDistance < 0) {
							nextDayDistance+=7;
						}
						DateTime nextDay = today.plusDays(nextDayDistance);
						year = nextDay.year().get();
						month = nextDay.monthOfYear().get();
						day = nextDay.dayOfMonth().get();
					}
				}
			}
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)) {
				hasKeyword = true;
				DateTime today = new DateTime();
				year = today.year().get();
				month = today.monthOfYear().get();
				day = today.dayOfMonth().get();
			}
		}
		if (!hasKeyword) {
			hasKeyword = true;
			DateTime today = new DateTime();
			year = today.year().get();
			month = today.monthOfYear().get();
			day = today.dayOfMonth().get();
		}
		int time = parseTaskStartTime(parameter);
		int hour = time/100;
		int min = time%100;
		DateTime now = new DateTime();
		
		if (year > now.getYear()) {
			return new DateTime(year,month,day,hour,min);
		} else if (year == now.getYear() 
				&& month > now.getMonthOfYear()) {
			return new DateTime(year,month,day,hour,min);
		} else if (year == now.getYear() 
				&& month == now.getMonthOfYear()
				&& day > now.getDayOfMonth()) {
			return new DateTime(year,month,day,hour,min);
		} else if (year == now.getYear() 
				&& month == now.getMonthOfYear()
				&& day == now.getDayOfMonth()
				&& hour > now.getHourOfDay()) {
			return new DateTime(year,month,day,hour,min);
		} else if (year == now.getYear() 
				&& month == now.getMonthOfYear()
				&& day == now.getDayOfMonth()
				&& hour == now.getHourOfDay()
				&& min > now.getMinuteOfHour()) {
			return new DateTime(year,month,day,hour,min);
		} else {
		throw new Exception("End time cannot be earlier than Start time");
		}
		
	}

	public static DateTime parseTaskEnd(String parameter) throws Exception {
		String[] messageArray = generateArray(parameter);
		int year = 1, month = 1, day = 1;
		boolean hasKeyword = false;
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING) 
			|| messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)) {
				hasKeyword = true;
				if (isInteger(messageArray[i+1])) {
					year = parseYear(messageArray[i+1]);
					month = parseMonth(messageArray[i+1]);
					day = parseDayOfMonth(messageArray[i+1]);	
				} else {
					int dayOfWeek = parseDayOfWeek(messageArray[i+1]);
					DateTime today = new DateTime();
					if (dayOfWeek == TODAY) {
						year = today.year().get();
						month = today.monthOfYear().get();
						day = today.dayOfMonth().get();
					} else if (dayOfWeek == TOMORROW) {
						DateTime tomorrow = today.plusDays(1);
						year = tomorrow.year().get();
						month = tomorrow.monthOfYear().get();
						day = tomorrow.dayOfMonth().get();
					} else {
						int nextDayDistance = dayOfWeek - today.dayOfWeek().get();
						if (nextDayDistance < 0) {
							nextDayDistance+=7;
						}
						DateTime nextDay = today.plusDays(nextDayDistance);
						year = nextDay.year().get();
						month = nextDay.monthOfYear().get();
						day = nextDay.dayOfMonth().get();
					}
				}
			}
		}
		if (!hasKeyword) {
			DateTime start = parseTaskStart(parameter);
			year = start.year().get();
			month = start.monthOfYear().get();
			day = start.dayOfMonth().get();
			int hour = 23;
			int min = 59;
			return new DateTime(year,month,day,hour,min);
		}
		
		int time = parseTaskEndTime(parameter);
		int hour = time/100;
		int min = time%100;
		
		if (year > parseTaskStart(parameter).getYear()) {
			return new DateTime(year,month,day,hour,min);
		} else if (year == parseTaskStart(parameter).getYear() 
				&& month > parseTaskStart(parameter).getMonthOfYear()) {
			return new DateTime(year,month,day,hour,min);
		} else if (year == parseTaskStart(parameter).getYear() 
				&& month == parseTaskStart(parameter).getMonthOfYear()
				&& day > parseTaskStart(parameter).getDayOfMonth()) {
			return new DateTime(year,month,day,hour,min);
		} else if (year == parseTaskStart(parameter).getYear() 
				&& month == parseTaskStart(parameter).getMonthOfYear()
				&& day == parseTaskStart(parameter).getDayOfMonth()
				&& hour > parseTaskStart(parameter).getHourOfDay()) {
			return new DateTime(year,month,day,hour,min);
		} else if (year == parseTaskStart(parameter).getYear() 
				&& month == parseTaskStart(parameter).getMonthOfYear()
				&& day == parseTaskStart(parameter).getDayOfMonth()
				&& hour == parseTaskStart(parameter).getHourOfDay()
				&& min > parseTaskStart(parameter).getMinuteOfHour()) {
			return new DateTime(year,month,day,hour,min);
		} else {
		throw new Exception("End time cannot be earlier than Start time");
		}
	}
}
