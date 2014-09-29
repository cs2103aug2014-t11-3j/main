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
	private static String KEYWORD_LOCATION = "at";
	private static String KEYWORD_STARTING = "from";
	private static String KEYWORD_ENDING = "to";
	private static String KEYWORD_DEADLINE = "by";
	private static String KEYWORD_HOURS = "hrs";

	//DAY KEYWORDS
	private static String DAY_KEYWORD_TODAY = "today";
	private static String DAY_KEYWORD_TOMORROW = "tomorrow";
	private static String DAY_KEYWORD_TMR = "tmr";
	private static String DAY_KEYWORD_MONDAY = "monday";
	private static String DAY_KEYWORD_MON = "mon";
	private static String DAY_KEYWORD_TUESDAY = "tuesday";
	private static String DAY_KEYWORD_TUES = "tues";
	private static String DAY_KEYWORD_TUE = "tue";
	private static String DAY_KEYWORD_WEDNESDAY = "wednesday";
	private static String DAY_KEYWORD_WED = "wed";
	private static String DAY_KEYWORD_THURSDAY = "thursday";
	private static String DAY_KEYWORD_THURS = "thurs";
	private static String DAY_KEYWORD_THUR = "thur";
	private static String DAY_KEYWORD_THU = "thu";
	private static String DAY_KEYWORD_FRIDAY = "friday";
	private static String DAY_KEYWORD_FRI = "fri";
	private static String DAY_KEYWORD_SATURDAY = "saturday";
	private static String DAY_KEYWORD_SAT = "sat";
	private static String DAY_KEYWORD_SUNDAY = "sunday";
	private static String DAY_KEYWORD_SUN = "sun";

	private static String _name;
	private static TaskType _taskType;
	
	public Task(String parameter){
		_name = parseName(parameter);
		_taskType = parseTaskType(parameter);
	}
	
	private TaskType parseTaskType(String parameter) {
		int lastIndex = parameter.lastIndexOf(QUOTATION_MARK);
		if (parameter.length() == lastIndex+1) {
			return (TaskType.FLOATING);
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
			showToUser(INVALID_MESSAGE);
		} else if (lastIndex < firstIndex) {
			showToUser(INVALID_MESSAGE);
		} else if (firstIndex == -1) {
			showToUser(INVALID_MESSAGE);
		}
		
		return null;
	}

	public TaskType getTaskType() {
		return _taskType;
	}

	public String getTaskName() {
		return _name;
	}
	
	public static void showToUser(String message) {
		System.out.println(message);
	}
}
