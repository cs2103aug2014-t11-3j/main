package todologApp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class Parser {

	//private static final String INVALID_MESSAGE = "Invalid Input!";

	//MISC
	private static final String EMPTY_STRING = "";
	private static final String SINGLE_SPACE = " ";
//	private static final String DATE_SEPARATOR = "/";
//	private static final String SYMBOL_DASH = "-";
//	private static final String SYMBOL_AT = "@";
//	private static final String QUOTATION_MARK = "\"";

	//KEYWORDS
	private static final String KEYWORD_DAY_STARTING = "from";
	private static final String KEYWORD_DAY_STARTING_2 = "on";
	private static final String KEYWORD_DAY_ENDING = "to";
	private static final String KEYWORD_DEADLINE = "by";
	private static final String KEYWORD_RECURRING = "every";
	private static final String KEYWORD_WITH = "with";
	private static final String KEYWORD_AT = "at";
	private static final String KEYWORD_IN = "in";

	//DAY KEYWORDS
	private static final String DAY_KEYWORD_TODAY = "Today";
	private static final String DAY_KEYWORD_TDY = "Tdy";
	private static final String DAY_KEYWORD_TOMORROW = "Tomorrow";
	private static final String DAY_KEYWORD_TMR = "tmr";
	private static final String DAY_KEYWORD_MONDAY = "Monday";
	private static final String DAY_KEYWORD_MON = "mon";
	private static final String DAY_KEYWORD_TUESDAY = "Tuesday";
	private static final String DAY_KEYWORD_TUES = "tues";
	private static final String DAY_KEYWORD_TUE = "tue";
	private static final String DAY_KEYWORD_WEDNESDAY = "Wednesday";
	private static final String DAY_KEYWORD_WED = "wed";
	private static final String DAY_KEYWORD_THURSDAY = "Thursday";
	private static final String DAY_KEYWORD_THURS = "thurs";
	private static final String DAY_KEYWORD_THUR = "thur";
	private static final String DAY_KEYWORD_THU = "thu";
	private static final String DAY_KEYWORD_FRIDAY = "Friday";
	private static final String DAY_KEYWORD_FRI = "fri";
	private static final String DAY_KEYWORD_SATURDAY = "Saturday";
	private static final String DAY_KEYWORD_SAT = "sat";
	private static final String DAY_KEYWORD_SUNDAY = "Sunday";
	private static final String DAY_KEYWORD_SUN = "sun";


	private static final String FEEDBACK_TYPE = "Type in a command: add, delete, edit, done.";
	//private static final String HELP_TEXT_ADD = "To add, enter:\n - add \"[task name]\" (from [date] @ [time] to "
	//		+ "[date] @ [time]).\n - add \"[task name]\" by [date] @ [time] if you want to create a\ndeadline.";
	private static final String HELP_TEXT_DELETE = "To delete, enter:\n - delete [task number].";
	private static final String HELP_TEXT_DONE = "To mark/unmark a task as done, enter:\n - done [task number].";
	//private static final String HELP_TEXT_EDIT = "To edit task name, enter:\n - edit [task number] \"[new name]\"";

	private static final int TODAY = 0;
	private static final int TOMORROW = -1;


	public static Command createCommand(String userCommand) throws Exception{
		userCommand = userCommand.trim();
		String firstWord = getFirstWord(userCommand);
		if (firstWord.equalsIgnoreCase("add")) {
			
				Task task = createTask(userCommand);
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
		} else if (firstWord.equalsIgnoreCase("edit")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			if (restOfTheString == null) {
				return new CommandEdit();
			}
			restOfTheString = restOfTheString.trim();
			int index = Integer.valueOf(getFirstWord(restOfTheString));
			restOfTheString = getTheRestOfTheString(restOfTheString);
			if (restOfTheString == null) {
				return new CommandEdit(index);
			}
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
		} else if (firstWord.equalsIgnoreCase("search")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			CommandSearch command = new CommandSearch(restOfTheString);
			return command;
		} else if (firstWord.equalsIgnoreCase("view")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			CommandView command = new CommandView(restOfTheString);
			return command;
		} else if (firstWord.equalsIgnoreCase("undo")) {
			History history = Controller.getHistory();
			Command toBeUndone = history.getBackwards();
			CommandUndo command = new CommandUndo(toBeUndone);
			return command;
		} else if (firstWord.equalsIgnoreCase("redo")) {
			History history = Controller.getHistory();
			Command toBeUndone = history.getForwards();
			CommandRedo command = new CommandRedo(toBeUndone);
			return command;
		} else if (firstWord.equalsIgnoreCase("load")) {
			String restOfTheString = getTheRestOfTheString(userCommand);
			CommandLoad command = new CommandLoad(restOfTheString);
			return command;
		}
		else {
			throw new Exception("Invalid command.\n"+FEEDBACK_TYPE);
		}
	}
	public static Task createTask(String userInput) throws Exception{
		String restOfTheString = getTheRestOfTheString(userInput);
		if (restOfTheString == null) {
			return null;
		}
		Task task = new Task(restOfTheString);
		return task;
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

		for (int i = 0; i<=messageArray.length-1; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING)) {
				for (int j=i+1; j<=messageArray.length-1; j++) {
					if (messageArray[j].equalsIgnoreCase(KEYWORD_AT) 
							&& isInteger(messageArray[j+1])) {
						try {
							int endTime = Integer.parseInt(messageArray[j+1]);
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
			} else if (messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)) {
				for (int j=i+1; j<=messageArray.length-1; j++) {
					if (messageArray[j].equalsIgnoreCase(KEYWORD_AT) 
							&& isInteger(messageArray[j+1])) {
						try {
							int endTime = Integer.parseInt(messageArray[j+1]);
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
			} else if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
						|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)) {
				for (int j=i+1; j<=messageArray.length-1; j++) {
					if (messageArray[j].equalsIgnoreCase(KEYWORD_DAY_ENDING) 
							&& isInteger(messageArray[j+1])
							&& messageArray[j+1].length() == 4) {
						try {
							int endTime = Integer.parseInt(messageArray[j+1]);
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
			}
		}
		return 2359;
	}

	public static int parseTaskStartTime(String parameter) throws Exception  {
		String [] messageArray = generateArray(parameter);

		for (int i = 0; i<=messageArray.length-1; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING)
					|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)) {
				for (int j=i+1; j<=messageArray.length-1; j++) {
					if (messageArray[j].equalsIgnoreCase(KEYWORD_AT) 
							&& isInteger(messageArray[j+1])) {
						try {
							int startTime = Integer.parseInt(messageArray[j+1]);
							if (startTime >= 0000 && startTime <= 2359) {
								return startTime;
							} else {
								return 0000;
							}
						} catch (NumberFormatException nfe) {
							throw new Exception("Invalid Time Format");
						} 
					} else if (messageArray[j].equalsIgnoreCase(KEYWORD_DAY_ENDING)) {
						return 0000;
					}
				}
			}
		}

		return 0000;
	}
	public static boolean checkDateFormat(String dateInString){
		try{
			parseYear(dateInString);
			parseMonth(dateInString);
			parseDayOfMonth(dateInString);
		}
		catch(Exception e){
			return false;
		}
		return true;
		
		
	}

	public static int parseYear(String dateInString) throws Exception {
		int _year = DateTime.now().year().get();
		try {
			_year = Integer.parseInt(dateInString);
		} catch (NumberFormatException nfe) {
			return _year;
		}
		if (_year<65) {
		_year = _year % 100 + 2000 ;
		} else {
			_year = _year %100 + 1900;
		}
		return _year;
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
						&& !messageArray[i].equalsIgnoreCase(KEYWORD_AT) 
						&& !messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING)){
					return (TaskType.FLOATING);
				} 
			}
		}

		return (TaskType.INVALID);
	}

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
					&& !messageArray[i].equalsIgnoreCase(KEYWORD_IN)) {
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
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_IN)) {
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
						&& !messageArray[j].equalsIgnoreCase(KEYWORD_AT)
						&& !isInteger(messageArray[j])) {
					taskVenue = taskVenue + messageArray[j] + SINGLE_SPACE;
				} else {
					break;
				}
			}
		}
		return taskVenue.trim();
	}

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
				DateTime taskStart = new DateTime(0);
				return taskStart;
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
		//		DateTime now = new DateTime();

		//		if (year > now.getYear()) {
		//			return new DateTime(year,month,day,hour,min);
		//		} else if (year == now.getYear() 
		//				&& month > now.getMonthOfYear()) {
		//			return new DateTime(year,month,day,hour,min);
		//		} else if (year == now.getYear() 
		//				&& month == now.getMonthOfYear()
		//				&& day > now.getDayOfMonth()) {
		//			return new DateTime(year,month,day,hour,min);
		//		} else if (year == now.getYear() 
		//				&& month == now.getMonthOfYear()
		//				&& day == now.getDayOfMonth()
		//				&& hour > now.getHourOfDay()) {
		//			return new DateTime(year,month,day,hour,min);
		//		} else if (year == now.getYear() 
		//				&& month == now.getMonthOfYear()
		//				&& day == now.getDayOfMonth()
		//				&& hour == now.getHourOfDay()
		//				&& min > now.getMinuteOfHour()) {
		return new DateTime(year,month,day,hour,min);
		//		} else {
		//		throw new Exception("End time cannot be earlier than Start time");
		//		}

	}
	public static DateTime parseTaskEnd(DateTime taskStart, String parameter) throws Exception {
		String[] messageArray = generateArray(parameter);
		int year = 1, month = 1, day = 1;
		boolean hasKeyword = false;
		//Parse date
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING) 
					|| messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)) {
				hasKeyword = true;
				if (isInteger(messageArray[i+1])
						&& messageArray[i+1].length() == 6) {
					year = parseYear(messageArray[i+1]);
					month = parseMonth(messageArray[i+1]);
					day = parseDayOfMonth(messageArray[i+1]);	
				} else if (isInteger(messageArray[i+1])
							&& messageArray[i+1].length() == 4) {
						year = taskStart.getYear();
						month = taskStart.getMonthOfYear();
						day = taskStart.getDayOfMonth();	 
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
						if (!taskStart.isEqual(new DateTime(0))) {
							int nextDayDistance = dayOfWeek - taskStart.dayOfWeek().get();
							if (nextDayDistance < 0) {
								nextDayDistance+=7;
							}
							DateTime nextDay = taskStart.plusDays(nextDayDistance);
							year = nextDay.year().get();
							month = nextDay.monthOfYear().get();
							day = nextDay.dayOfMonth().get();
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
		}
		if (!hasKeyword) {
			year = taskStart.year().get();
			month = taskStart.monthOfYear().get();
			day = taskStart.dayOfMonth().get();
			int hour = 23;
			int min = 59;
			return new DateTime(year,month,day,hour,min);
		}
		//parse time
		int time = parseTaskEndTime(parameter);
		int hour = time/100;
		int min = time%100;
		DateTime taskEnd = new DateTime(year,month,day,hour,min);
		if (taskEnd.compareTo(taskStart)<0) {
//
//		if (year > parseTaskStart(parameter).getYear()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (year == parseTaskStart(parameter).getYear() 
//				&& month > parseTaskStart(parameter).getMonthOfYear()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (year == parseTaskStart(parameter).getYear() 
//				&& month == parseTaskStart(parameter).getMonthOfYear()
//				&& day > parseTaskStart(parameter).getDayOfMonth()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (year == parseTaskStart(parameter).getYear() 
//				&& month == parseTaskStart(parameter).getMonthOfYear()
//				&& day == parseTaskStart(parameter).getDayOfMonth()
//				&& hour > parseTaskStart(parameter).getHourOfDay()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (year == parseTaskStart(parameter).getYear() 
//				&& month == parseTaskStart(parameter).getMonthOfYear()
//				&& day == parseTaskStart(parameter).getDayOfMonth()
//				&& hour == parseTaskStart(parameter).getHourOfDay()
//				&& min > parseTaskStart(parameter).getMinuteOfHour()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (parseTaskType(parameter) == TaskType.FLOATING 
//				|| parseTaskType(parameter) == TaskType.DEADLINE) {
//			return new DateTime(year,month,day,hour,min);
//		} else {

			throw new Exception("End time cannot be earlier than Start time");
		}
		return taskEnd;
//		if (year > taskStart.getYear()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (year == taskStart.getYear() 
//				&& month > taskStart.getMonthOfYear()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (year == taskStart.getYear() 
//				&& month == taskStart.getMonthOfYear()
//				&& day > taskStart.getDayOfMonth()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (year == taskStart.getYear() 
//				&& month == taskStart.getMonthOfYear()
//				&& day == taskStart.getDayOfMonth()
//				&& hour > taskStart.getHourOfDay()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (year == taskStart.getYear() 
//				&& month == taskStart.getMonthOfYear()
//				&& day == taskStart.getDayOfMonth()
//				&& hour == taskStart.getHourOfDay()
//				&& min > taskStart.getMinuteOfHour()) {
//			return new DateTime(year,month,day,hour,min);
//		} else if (parseTaskType(parameter) == TaskType.FLOATING 
//				||parseTaskType(parameter) == TaskType.DEADLINE) {
//			return new DateTime(year,month,day,hour,min);
//		} else {
//			throw new Exception("End time cannot be earlier than Start time");
//		}	
	}
	
	public static DateTime parseTaskEnd(String parameter) throws Exception {
		DateTime taskStart = new DateTime(0);
		return parseTaskEnd(taskStart, parameter);
		
	}
}
