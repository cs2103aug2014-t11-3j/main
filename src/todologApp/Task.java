package todologApp;

public class Task {
	
	//MESSAGE
	private static String INVALID_MESSAGE = "Invalid Input!";
	
	//MISC
	private static String EMPTY_STRING = "";
	private static String SINGLE_SPACE = " ";
	private static String DATE_SEPARATOR = "/";
	private static String SYMBOL_DASH = "-";
	private static String QUOTATION_MARK = "\"";

	//KEYWORDS
	private static String KEYWORD_TIME_STARTING = "from";
	private static String KEYWORD_TIME_ENDING = "to";
	private static String KEYWORD_DEADLINE = "by";
	private static String KEYWORD_RECURRING = "every";
	
	//DAY KEYWORDS
	private static String DAY_KEYWORD_TODAY = "Today";
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

	//Key Variables
	private static String _taskName;
	private static TaskType _taskType;
	private static String _taskStartDay;
	private static String _taskEndDay;
	private static String _taskStartTime;
	private static String _taskEndTime;
	
	public Task(String parameter){

		_taskName = parseName(parameter);
		_taskType = parseTaskType(parameter);
		
	}
	
	private static String parseDay(String parameter) {
		String day = parameter;
		
		if (day.equalsIgnoreCase(DAY_KEYWORD_TODAY)) {
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
		} 
		return null;
	}

	private TaskType parseTaskType(String parameter) {
		String[] analyseTask = parameter.split(SINGLE_SPACE);
	
		if (analyseTask.length == 1) {
			return (TaskType.FLOATING);
		} else if (analyseTask[1].equalsIgnoreCase(KEYWORD_TIME_STARTING)) {
			return (TaskType.TIMED);
		} else if (analyseTask[1].equalsIgnoreCase(KEYWORD_DEADLINE)) {
			return (TaskType.DEADLINE);
		} else if (analyseTask[1].equalsIgnoreCase(KEYWORD_DEADLINE)) {
			return (TaskType.RECURRING);
		}
		
		return null;
	}
	
	private static String parseName(String parameter) {
		int firstIndex = parameter.indexOf(QUOTATION_MARK);
		int lastIndex = parameter.lastIndexOf(QUOTATION_MARK);
		
		if (lastIndex > firstIndex) {
			String taskName = parameter.substring(firstIndex+1, lastIndex);
			return taskName;
		} else if (lastIndex == firstIndex) {
			return INVALID_MESSAGE;
		} else if (lastIndex < firstIndex) {
			return INVALID_MESSAGE;
		} else if (firstIndex == -1) {
			return INVALID_MESSAGE;
		} else {
			String taskName = parameter.substring(firstIndex+1, lastIndex);
			String[] timeAndDay = parameter.split(SINGLE_SPACE, 3);
			_taskDay = parseDay(timeAndDay[2]);
			return taskName;
		}
	}

	public TaskType getTaskType() {
		return _taskType;
	}
	
	public String getTaskDay() {
		return _taskDay;
	}

	public String getTaskName() {
		return _taskName;
	}
	
	public static void showToUser(String message) {
		System.out.println(message);
	}
}
