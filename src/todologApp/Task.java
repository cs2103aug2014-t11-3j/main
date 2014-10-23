package todologApp;

import org.joda.time.DateTime;

public class Task {
	
	//MESSAGE

	//Key Variables
	private String _taskName;
	private TaskType _taskType;
	private String _taskStartDay;
	private String _taskEndDay;
	private String _taskPerson;
	private String _taskVenue;
	private int _taskStartTime;
	private int _taskEndTime;
	private DateTime _taskStart;
	private DateTime _taskEnd;
	
	private boolean _taskStatus;
	
	public Task(String parameter) throws Exception{
		_taskName = Parser.parseTaskName(parameter);
		_taskType = Parser.parseTaskType(parameter);
		_taskStartDay = Parser.parseTaskStartDay(parameter);
		_taskEndDay = Parser.parseTaskEndDay(parameter);
		_taskPerson = Parser.parseTaskPerson(parameter);
		_taskVenue = Parser.parseTaskVenue(parameter);
		_taskStart = Parser.parseTaskStart(parameter);
		_taskEnd = Parser.parseTaskEnd(parameter);
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
		return _taskStart.hourOfDay().get()*100
				+ _taskStart.minuteOfHour().get();
	}

	public int getEndTime() {
		return _taskEnd.hourOfDay().get()*100
				+ _taskEnd.minuteOfHour().get();
	}
	
	public int getStartDate() {
		return _taskStart.dayOfMonth().get();
	}
	
	public int getStartMonth() {
		return _taskStart.monthOfYear().get();
	}
	
	public int getStartYear() {
		return _taskStart.year().get();
	}
	
	public int getEndDate() {
		return _taskEnd.dayOfMonth().get();
	}
	
	public int getEndMonth() {
		return _taskEnd.monthOfYear().get();
	}
	
	public int getEndYear() {
		return _taskEnd.monthOfYear().get();
	}
	
	public String getTaskVenue() {
		return _taskVenue;
	}
	
	public String getTaskPerson() {
		return _taskPerson;
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

	public void setStartDay(String _toBeEdited) throws Exception{
		int year = getStartYear();
		int month = getStartMonth();
		int day = Parser.parseTaskStartDate(_toBeEdited);
		int time = getStartTime();
		int hour = time/100;
		int min = time%100;
		_taskStart = new DateTime(year,month,day,hour,min);
	}
	
	public void setEndDay(String _toBeEdited) throws Exception {
		int year = getEndYear();
		int month = getEndMonth();
		int day = Parser.parseTaskEndDate(_toBeEdited);
		int time = getEndTime();
		int hour = time/100;
		int min = time%100;
		_taskEnd = new DateTime(year,month,day,hour,min);
	}
	
	public void setStartTime(String _toBeEdited) throws Exception {
		int year = getStartYear();
		int month = getStartMonth();
		int day = getStartDate();
		int time = Parser.parseTaskStartTime(_toBeEdited);
		int hour = time/100;
		int min = time%100;
		_taskStart = new DateTime(year,month,day,hour,min);
	}
	
	public void setEndTime(String _toBeEdited) throws Exception {
		int year = getEndYear();
		int month = getEndMonth();
		int day = getEndDate();
		int time = Parser.parseTaskEndTime(_toBeEdited);
		int hour = time/100;
		int min = time%100;
		_taskStart = new DateTime(year,month,day,hour,min);
	}
	
	public void setVenue(String _toBeEdited){
		_taskVenue = Parser.parseTaskVenue(_toBeEdited);
	}
	
	public void setPerson(String _toBeEdited){
		_taskPerson = Parser.parseTaskPerson(_toBeEdited);
	}
}
