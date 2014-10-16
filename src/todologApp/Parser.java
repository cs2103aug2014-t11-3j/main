package todologApp;

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
			int index = Integer.valueOf(restOfTheString);
			CommandDelete command = new CommandDelete(index);
			return command;
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
			CommandEdit command = new CommandEdit(index, restOfTheString);
			return command;
			//		} else if (firstWord.equalsIgnoreCase("search")) {
			//			CommandSearch command = new CommandSearch(restOfTheString);
			//			return command;
		} else {
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

		for (int i = 0; i+3<=messageArray.length; i++) {
			if ((messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING)
					|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2))
					&& messageArray[i+2].equalsIgnoreCase(SYMBOL_AT)) {
				try {
					int startTime = Integer.parseInt(messageArray[i+3]);
					if (startTime >= 0000 && startTime <= 2359) {
						return startTime;
					} else {
						return 0000;
					}
				} catch (NumberFormatException nfe) {
					throw new Exception("Invalid Time Format");
				}
			} else if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) && 
					messageArray[i+1].equalsIgnoreCase(SYMBOL_AT)) {
				try {
					int startTime = Integer.parseInt(messageArray[i+2]);
					if (startTime >= 0000 && startTime <= 2359) {
						return startTime;
					} else {
						return 0000;
					}
				} catch (NumberFormatException nfe) {
					throw new Exception("Invalid Time Format");
				}
			} 
		}

		return 0000;
	}

	public static String parseTaskEndDay(String parameter) {
		String [] messageArray = generateArray(parameter);

		for (int i = 0; i+1<=messageArray.length; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING) && !isInteger(messageArray[i+1])){
				String endDay = parseDay(messageArray[i+1]);
				return endDay;
			} else {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)){
					String endDay = parseDay(messageArray[i+1]);
					return endDay;
				}
			}
		}	
		return parseTaskStartDay(parameter);
	}

	public static String parseTaskStartDay(String parameter) {
		String [] messageArray = generateArray(parameter);

		for (int i = 0; i+1<=messageArray.length-1; i++) {
			if ( (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
					|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2))
					&& !isInteger(messageArray[i+1])){
				String startDay = parseDay(messageArray[i+1]);
				return startDay;
			} else {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)){
					return DAY_KEYWORD_TODAY;
				}
			}
		}
		return DAY_KEYWORD_TODAY;
	}


	public static int parseTaskStartDate(String parameter) throws Exception {
		String [] messageArray = generateArray(parameter);
		int _date = 1;
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			try {
				if ((messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
						|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2))
						&& isInteger(messageArray[i+1])) {
					_date = Integer.parseInt(messageArray[i+1]);
					_date = _date/10000;
					if (parseTaskStartMonth(parameter) == 2 && _date > 0 && _date <= 28) {
						return _date;
					} else if (parseTaskStartMonth(parameter) == 4 &&  _date > 0 && _date <= 30){
						return _date;
					}  else if (parseTaskStartMonth(parameter) == 6 && _date > 0 && _date <= 30){
						return _date;
					} else if (parseTaskStartMonth(parameter) == 9 && _date > 0 && _date <= 30){
						return _date;
					} else if (parseTaskStartMonth(parameter) == 11 && _date > 0 && _date <= 30){
						return _date;
					} else if (_date > 0 && _date <= 31){
						return _date;
					} else {
						throw new Exception("Invalid Date Format");
					}
				} 
			}	catch (NumberFormatException nfe) {
				throw new Exception("Invalid Date Format");
			}
		}
		return _date;

	}

	public static int parseTaskStartMonth(String parameter) throws Exception {
		String [] messageArray = generateArray(parameter);
		int _month = 1;
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			try {
				if ((messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
						|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2)) 
						&& isInteger(messageArray[i+1])) {
					_month = Integer.parseInt(messageArray[i+1]);
					_month = _month/100;
					_month = _month % 100;
					if (_month > 0 && _month <= 12) {
						return _month;
					} else {
						throw new Exception("Invalid Date Format");
					}
				} 
			} catch (NumberFormatException nfe) {
				throw new Exception("Invalid Date Format");
			}
		}
		return _month;
	}

	public static int parseTaskStartYear(String parameter) throws Exception {
		String [] messageArray = generateArray(parameter);
		int _year = 14;
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			try {
				if ((messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING) 
						|| messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING_2))
						&& isInteger(messageArray[i+1])){
					_year = Integer.parseInt(messageArray[i+1]);
					_year = _year % 100;
					if (_year >= 14) {
						return _year;
					} else {
						throw new Exception("Invalid Date Format");
					}
				} 
			} catch (NumberFormatException nfe) {
				throw new Exception("Invalid Date Format");
			}

		}
		return _year;

	}

	public static int parseTaskEndDate(String parameter) throws Exception {
		String [] messageArray = generateArray(parameter);
		int _date = 1;
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			try {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING) && isInteger(messageArray[i+1])){
					_date = Integer.parseInt(messageArray[i+1]);
					_date = _date/10000;
					if (parseTaskStartMonth(parameter) == 2 && _date > 0 && _date <= 28) {
						return _date;
					} else if (parseTaskStartMonth(parameter) == 4 &&  _date > 0 && _date <= 30){
						return _date;
					}  else if (parseTaskStartMonth(parameter) == 6 && _date > 0 && _date <= 30){
						return _date;
					} else if (parseTaskStartMonth(parameter) == 9 && _date > 0 && _date <= 30){
						return _date;
					} else if (parseTaskStartMonth(parameter) == 11 && _date > 0 && _date <= 30){
						return _date;
					} else if (_date > 0 && _date <= 31){
						return _date;
					} else {
						throw new Exception("Invalid Date Format");
					}
				} 
			} catch (NumberFormatException nfe) {
				throw new Exception("Invalid Date Format");
			}
		}

		return _date;
	}

	public static int parseTaskEndMonth(String parameter) throws Exception {
		String [] messageArray = generateArray(parameter);
		int _month = 1;
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			try {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING) && isInteger(messageArray[i+1])){
					_month = Integer.parseInt(messageArray[i+1]);
					_month = _month/100;
					_month = _month % 100;
					if (_month > 0 && _month <= 12) {
						return _month;
					} else {
						throw new Exception("Invalid Date Format");
					}
				} 
			} catch (NumberFormatException nfe) {
				throw new Exception("Invalid Date Format");
			} 
		}

		return _month;
	}

	public static int parseTaskEndYear(String parameter) throws Exception {
		String [] messageArray = generateArray(parameter);
		int _year = 14;
		for (int i = 0; i+1<=messageArray.length-1; i++) {
			try {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING) && isInteger(messageArray[i+1])){
					_year = Integer.parseInt(messageArray[i+1]);
					_year = _year % 100;
					if (_year >= 14) {
						return _year;
					} else {
						throw new Exception("Invalid Date Format");
					}
				} 
			} catch (NumberFormatException nfe) {
				throw new Exception("Invalid Date Format");
			}
		}

		return _year;
	}

	public static String parseDay(String parameter) {
		String day = parameter;

		if (day.equalsIgnoreCase(DAY_KEYWORD_TODAY) || day.equalsIgnoreCase(DAY_KEYWORD_TDY)) {
			return DAY_KEYWORD_TODAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_TOMORROW) || day.equalsIgnoreCase(DAY_KEYWORD_TMR)) {
			return DAY_KEYWORD_TOMORROW;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_MONDAY) || day.equalsIgnoreCase(DAY_KEYWORD_MON)) {
			return DAY_KEYWORD_MONDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_TUESDAY) || day.equalsIgnoreCase(DAY_KEYWORD_TUE) || 
				day.equalsIgnoreCase(DAY_KEYWORD_TUES)) {
			return DAY_KEYWORD_TUESDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_WEDNESDAY) || day.equalsIgnoreCase(DAY_KEYWORD_WED)) {
			return DAY_KEYWORD_WEDNESDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_THURSDAY) || day.equalsIgnoreCase(DAY_KEYWORD_THURS) || 
				day.equalsIgnoreCase(DAY_KEYWORD_THUR) || day.equalsIgnoreCase(DAY_KEYWORD_THU)) {
			return DAY_KEYWORD_THURSDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_FRIDAY) || day.equalsIgnoreCase(DAY_KEYWORD_FRI)) {
			return DAY_KEYWORD_FRIDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_SATURDAY) || day.equalsIgnoreCase(DAY_KEYWORD_SAT)) {
			return DAY_KEYWORD_SATURDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_SUNDAY) || day.equalsIgnoreCase(DAY_KEYWORD_SUN)) {
			return DAY_KEYWORD_SUNDAY;
		} else {
			return DAY_KEYWORD_TODAY;
		}
	}

	public static TaskType parseTaskType(String parameter) {
		String taskDateTime = parameter.substring(parameter.lastIndexOf(QUOTATION_MARK)+1);
		taskDateTime = taskDateTime.trim();
		//System.out.println(parameter);
		//System.out.println(taskDateTime);
		String[] analyseTask = taskDateTime.split(SINGLE_SPACE);
		if (taskDateTime.length() == 0) {
			return (TaskType.FLOATING);
		} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_DAY_STARTING) || analyseTask[0].equalsIgnoreCase(KEYWORD_DAY_ENDING)) {
			return (TaskType.TIMED);
		} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_DEADLINE)) {
			return (TaskType.DEADLINE);
		} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_RECURRING)) {
			return (TaskType.RECURRING);
		} else {
			return (TaskType.INVALID);
		}

	}

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
						&& !messageArray[j].equalsIgnoreCase(SYMBOL_AT)) {
					taskVenue = taskVenue + messageArray[j] + SINGLE_SPACE;
				} else {
					break;
				}
			}
		}
		return taskVenue.trim();
	}

}
