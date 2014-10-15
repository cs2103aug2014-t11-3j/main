package todologApp;

public class Task {
	
	//MESSAGE

	//Key Variables
	private String _taskName;
	private TaskType _taskType;
	private String _taskStartDay;
	private String _taskEndDay;
	private int _taskStartTime;
	private int _taskEndTime;
	private int _taskStartDate;
	private int _taskStartMonth;
	private int _taskStartYear;
	private int _taskEndDate;
	private int _taskEndMonth;
	private int _taskEndYear;
	
	private boolean _taskStatus;
	
	public Task(String parameter) throws Exception{
		_taskName = Parser.parseTaskName(parameter);
		_taskType = Parser.parseTaskType(parameter);
		_taskStartDay = Parser.parseTaskStartDay(parameter);
		_taskEndDay = Parser.parseTaskEndDay(parameter);
		_taskStartTime = Parser.parseTaskStartTime(parameter);
		_taskEndTime = Parser.parseTaskEndTime(parameter);
		_taskStartDate = Parser.parseTaskStartDate(parameter);
		_taskStartMonth = Parser.parseTaskStartMonth(parameter);
		_taskStartYear = Parser.parseTaskStartYear (parameter);
		_taskEndDate = Parser.parseTaskEndDate(parameter);
		_taskEndMonth = Parser.parseTaskEndMonth(parameter);
		_taskEndYear = Parser.parseTaskEndYear (parameter);
	}

	
	public Task(TaskType floating, String name, boolean status) {
		_taskType = floating;
		_taskName = name;
		_taskStatus = status;
	}

	public Task(TaskType deadline, String name, String endDay, int endTime,
			boolean status) {
		_taskType = deadline;
		_taskName = name;
		_taskEndDay = endDay;
		_taskEndTime = endTime;
		_taskStatus = status;
	}


	public Task(TaskType timed, String name, String startDay, String endDay,
			int startTime, int endTime, boolean status) {
		_taskType = timed;
		_taskName = name;
		_taskStartDay = startDay;
		_taskEndDay = endDay;
		_taskStartTime = startTime;
		_taskEndTime = endTime;
		_taskStatus = status;
	}

	public void setTaskName(String name) {
		_taskName = name;
	}
	
	public String getTaskName() {
		return _taskName;
	}
	
	public TaskType getTaskType() {
		return _taskType;
	}
	
	public String getStartDay() {
		return _taskStartDay;
	}
	
	public String getEndDay() {
		return _taskEndDay;
	}

	public int getStartTime() {
		return _taskStartTime;
	}

	public int getEndTime() {
		return _taskEndTime;
	}
	
	public int getStartDate() {
		return _taskStartDate;
	}
	
	public int getStartMonth() {
		return _taskStartMonth;
	}
	
	public int getStartYear() {
		return _taskStartYear;
	}
	
	public int getEndDate() {
		return _taskEndDate;
	}
	
	public int getEndMonth() {
		return _taskEndMonth;
	}
	
	public int getEndYear() {
		return _taskEndYear;
	}
	
	public static void showToUser(String message) {
		System.out.println(message);
	}

	public boolean getTaskStatus() {
		return _taskStatus;
	}


	public void toggleTaskStatus() {
		if (_taskStatus) {
			_taskStatus = false;
		} else {
			_taskStatus = true;
		}
		
	}
}
