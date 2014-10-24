package todologApp;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class Task {
	
	//MESSAGE

	//Key Variables
	private String _taskName;
	private TaskType _taskType;
	private String _taskStartDay;
	private String _taskEndDay;
	private String _taskPerson;
	private String _taskVenue;
	private DateTime _taskStart;
	private DateTime _taskEnd;
	
	private boolean _taskStatus;
	public Task(String parameter) throws Exception{
		_taskName = Parser.parseTaskName(parameter);
		_taskType = Parser.parseTaskType(parameter);
		_taskPerson = Parser.parseTaskPerson(parameter);
		_taskVenue = Parser.parseTaskVenue(parameter);
		_taskStart = Parser.parseTaskStart(parameter);
		_taskEnd = Parser.parseTaskEnd(parameter);
	}

	
	public Task(TaskType floating, String name, boolean status, String person, String venue) {
		_taskType = floating;
		_taskName = name;
		_taskStatus = status;
		_taskPerson = person;
		_taskVenue = venue;
	}

	public Task(TaskType deadline, String name, DateTime end, boolean status, String person, String venue) {
		_taskType = deadline;
		_taskName = name;
		_taskEnd = end;
		_taskStatus = status;
		_taskPerson = person;
		_taskVenue = venue;
	}

	public Task(TaskType timed, String name, DateTime start, DateTime end,
			boolean status, String person, String venue) {
		_taskType = timed;
		_taskName = name;
		_taskStart = start;
		_taskEnd = end;
		_taskStatus = status;
		_taskPerson = person;
		_taskVenue = venue;
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
		return _taskEnd.year().get();
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
	public String getStart() {
		return _taskStart.toString(ISODateTimeFormat.dateTime());
	}
	public String getEnd() {
		return _taskEnd.toString(ISODateTimeFormat.dateTime());
	}

	public void toggleTaskStatus() {
		if (_taskStatus) {
			_taskStatus = false;
		} else {
			_taskStatus = true;
		}
		
	}

	public void setStartDay(String editInfo) throws Exception{
		int year = Parser.parseYear(editInfo);
		int month = Parser.parseMonth(editInfo);
		int day = Parser.parseDayOfMonth(editInfo);
		int time = getStartTime();
		int hour = time/100;
		int min = time%100;
		_taskStart = new DateTime(year,month,day,hour,min);
	}
	
	public void setEndDay(String editInfo) throws Exception {
		int year = Parser.parseYear(editInfo);
		int month = Parser.parseMonth(editInfo);
		int day = Parser.parseDayOfMonth(editInfo);
		int time = getEndTime();
		int hour = time/100;
		int min = time%100;
		_taskEnd = new DateTime(year,month,day,hour,min);
	}
	
	public void setStartTime(String editInfo) throws Exception {
		int year = getStartYear();
		int month = getStartMonth();
		int day = getStartDate();
		int time = Integer.parseInt(editInfo);
		int hour = time/100;
		int min = time%100;
		_taskStart = new DateTime(year,month,day,hour,min);
	}
	
	public void setEndTime(String editInfo) throws Exception {
		int year = getEndYear();
		int month = getEndMonth();
		int day = getEndDate();
		int time =  Integer.parseInt(editInfo);
		int hour = time/100;
		int min = time%100;
		_taskEnd = new DateTime(year,month,day,hour,min);
	}
	
	public void setVenue(String _toBeEdited){
		_taskVenue = Parser.parseTaskVenue(_toBeEdited);
	}
	
	public void setPerson(String _toBeEdited){
		_taskPerson = Parser.parseTaskPerson(_toBeEdited);
	}

	// for sorting in CommandAdd
	public DateTime getEndDateTime() {
		return _taskEnd;
	}


	public String getStartTimeStr() {
		LocalTime time = new LocalTime(getStartTime()/100,getStartTime()%100);
		return time.toString("HH:mm");
	}
	public String getEndTimeStr() {
		LocalTime time = new LocalTime(getEndTime()/100,getEndTime()%100);
		return time.toString("HH:mm");
	}


	public Task copy() {
		// TODO Auto-generated method stub
		return new Task(_taskType, _taskName, new DateTime(_taskStart), new DateTime(_taskEnd),
				_taskStatus, _taskPerson, _taskVenue); 
	}
}
