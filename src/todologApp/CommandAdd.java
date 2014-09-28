package todologApp;

public class CommandAdd implements Command{
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
	private static Task _task;
	public CommandAdd(Task task) {
		_task = task;
	}
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	public void undo() {
		// TODO Auto-generated method stub
		
	}

}
