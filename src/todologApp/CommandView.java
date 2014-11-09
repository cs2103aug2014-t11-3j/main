package todologApp;

import java.util.LinkedList;

import org.joda.time.DateTime;


public class CommandView implements Command {
	private static final String POSSESSIVE_CASE = "'s";
	private String _toView;
	private String _viewType;
	private DBStorage _storage;
	private static LinkedList<Task> _returnList;
	private int monthInIntegers;
	//private boolean _isMonth;
	private static String DAY_KEYWORD_TODAY = "Today";
	private static String DAY_KEYWORD_THIS_DAY = "This day ";
	private static String DAY_KEYWORD_TOMORROW = "Tomorrow";
	private static String DAY_KEYWORD_TMR = "tmr";
	private static String DAY_KEYWORD_NEXT_DAY="Next day";
	private static String DAY_KEYWORD_THIS_WEEK="This week";
	private static String DAY_KEYWORD_NEXT_WEEK="Next week";	
	
	
	
	public CommandView(String toView ) {
		_toView = toView;
		_storage=Controller.getDBStorage();

		}

	@Override
	public String execute() {
		String feedback;
		int year,month,day;
		DateTime startDay = new DateTime();
		DateTime endDay= new DateTime();
		year=startDay.getYear();
		month=startDay.getMonthOfYear();
		day=startDay.getDayOfMonth();
		
		
	
		//checking for today/this day 
		if(_toView.equalsIgnoreCase(DAY_KEYWORD_TODAY)||_toView.equalsIgnoreCase(DAY_KEYWORD_THIS_DAY)){
			
			startDay=new DateTime(year,month,day,0,0);
			endDay=new DateTime(year,month,day,23,59);
			formViewList(startDay,endDay);
			_viewType = DAY_KEYWORD_TODAY + POSSESSIVE_CASE;
			feedback="Displaying tasks for "+ _viewType;
		}
		//checking for tomorrow/tmr/next day
		else if(_toView.equalsIgnoreCase(DAY_KEYWORD_TOMORROW)
				||_toView.equalsIgnoreCase(DAY_KEYWORD_TMR)
				||_toView.equalsIgnoreCase(DAY_KEYWORD_NEXT_DAY)){
		
			startDay=startDay.plusDays(1);
			year=startDay.getYear();
			month=startDay.getMonthOfYear();
			day=startDay.getDayOfMonth();
			startDay=new DateTime(year,month,day,0,0);
			endDay=new DateTime(year,month,day,23,59);
			formViewList(startDay,endDay);
			_viewType = DAY_KEYWORD_TOMORROW + POSSESSIVE_CASE;
			feedback="Displaying tasks for "+ _viewType;
		}
		//checking for days 
		else if(isWeekDay()!= null){
			
			int currentWeekDay=startDay.getDayOfWeek();
			int givenWeekDay=Parser.parseDayOfWeek(_toView);
			if(givenWeekDay>=currentWeekDay){
				startDay=startDay.plusDays(givenWeekDay-currentWeekDay);	
			}
			else {
				startDay=startDay.plusDays(givenWeekDay-currentWeekDay+7);
			}
			year=startDay.getYear();
			month=startDay.getMonthOfYear();
			day=startDay.getDayOfMonth();
			startDay=new DateTime(year,month,day,0,0);
			endDay=new DateTime(year,month,day,23,59);
			formViewList(startDay,endDay);	
			_viewType = isWeekDay() + POSSESSIVE_CASE;
			feedback="Displaying tasks for "+ _viewType;
			
		}
		//checking for date
		else if(Parser.checkDateFormat(_toView)){
		
			int ddmmyy=Integer.parseInt(_toView);
			year=ddmmyy%100+2000;
			month=(ddmmyy/100)%100;
			day=(ddmmyy/10000);
			startDay=new DateTime(year,month,day,0,0);
			endDay=new DateTime(year,month,day,23,59);
			formViewList(startDay,endDay);
			_viewType = _toView + POSSESSIVE_CASE;
			feedback="Displaying tasks for "+ _viewType;
		}
		//checking for this week
		else if(_toView.equalsIgnoreCase(DAY_KEYWORD_THIS_WEEK)){
		
			endDay=startDay.plusDays(7);
			startDay.withHourOfDay(0);
			startDay.withMinuteOfHour(0);
			endDay.withHourOfDay(23);
			endDay.withMinuteOfHour(59);

			formViewList(startDay,endDay);
			_viewType = DAY_KEYWORD_THIS_WEEK + POSSESSIVE_CASE;
			feedback="Displaying tasks for "+ _viewType;
		}
		//checking for next week
		else if(_toView.equalsIgnoreCase(DAY_KEYWORD_NEXT_WEEK)){
			
			//changing to next week by adding 7 days
			
			startDay=startDay.plusDays(8);
			endDay=endDay.plusDays(15);

			startDay.withHourOfDay(0);
			startDay.withMinuteOfHour(0);
			endDay.withHourOfDay(23);
			endDay.withMinuteOfHour(59);

			formViewList(startDay,endDay);
			_viewType = DAY_KEYWORD_NEXT_WEEK;
			feedback="Displaying tasks for "+ _viewType;
		}
		//checking for month 
		else if(checkMonth() != null ){
			
			DateTime startOfThisMonth=startDay.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
			int currentMonth=startOfThisMonth.getMonthOfYear();
			if(monthInIntegers>=currentMonth){
				startDay=startOfThisMonth.plusMonths(monthInIntegers-currentMonth).dayOfMonth().withMinimumValue();

				endDay=startOfThisMonth.plusMonths(monthInIntegers-currentMonth).dayOfMonth().withMaximumValue();	
			}
			else {
				startDay=startOfThisMonth.minusMonths(currentMonth-monthInIntegers).dayOfMonth().withMinimumValue();
				endDay=startOfThisMonth.minusMonths(currentMonth-monthInIntegers).dayOfMonth().withMaximumValue();	
			}
			startDay.withHourOfDay(0);
			startDay.withMinuteOfHour(0);
			endDay.withHourOfDay(23);
			endDay.withMinuteOfHour(59);

			formViewList(startDay,endDay);
			_viewType = checkMonth() + POSSESSIVE_CASE;
			feedback="Displaying tasks for "+ _viewType;
		}
		else if(_toView.equalsIgnoreCase("all")){
			setReturnList(_storage.load());
			feedback="Displaying all tasks";
			_viewType = "All";
			feedback="Displaying tasks for "+ _viewType;
		}
		else if(_toView.equalsIgnoreCase("overdue")||_toView.equalsIgnoreCase("pending")){
			endDay=startDay.minusMinutes(1);
			viewOverDueTasks(endDay);
			feedback="OVERDUE TASKS";
			_viewType = "Past";
			feedback="Displaying tasks for "+ _viewType;
			
		}
		else{
			
			feedback="invalid command";
		}
		
		
		
		//Controller.setFocusTask(null); // set focus task to change UI's page
		return feedback;
		
		
	}

	public String isWeekDay(){
	
		if(_toView.equalsIgnoreCase("monday")||_toView.equalsIgnoreCase("mon")){
			return "Monday";
		}else if(_toView.equalsIgnoreCase("tuesday")||_toView.equalsIgnoreCase("tues")){
			return "Tuesday";
		}else if(_toView.equalsIgnoreCase("wednesday")||_toView.equalsIgnoreCase("wed")){
			return "Wednesday";
		}else if(_toView.equalsIgnoreCase("thursday")||_toView.equalsIgnoreCase("thurs")){
			return "Thursday";
		}else if(_toView.equalsIgnoreCase("friday")||_toView.equalsIgnoreCase("fri")){
			return "Friday";
		}else if(_toView.equalsIgnoreCase("saturday")||_toView.equalsIgnoreCase("sat")){
			return "Saturday";
		}else if(_toView.equalsIgnoreCase("sunday")||_toView.equalsIgnoreCase("sun")){
			return "Sunday";
		}
		else{
			return null;
		}
		
	}
	
	public String checkMonth() {
		
		if(_toView.equalsIgnoreCase("january")||_toView.equalsIgnoreCase("jan")){
			monthInIntegers=1;
		
			return "January";
		}
		else if(_toView.equalsIgnoreCase("february")||_toView.equalsIgnoreCase("feb")){
			monthInIntegers=2;

			return "February";
		}
		else if(_toView.equalsIgnoreCase("march")||_toView.equalsIgnoreCase("mar")){
			monthInIntegers=3;
		
			return "March";
		}
		else if(_toView.equalsIgnoreCase("april")||_toView.equalsIgnoreCase("apr")){
			monthInIntegers=4;
		
			return "April";
		}
		else if (_toView.equalsIgnoreCase("may")){
			monthInIntegers=5;
		
			return "May";
		}
		else if(_toView.equalsIgnoreCase("june")||_toView.equalsIgnoreCase("jun")){
			monthInIntegers=6;
	
			return "June";
		}
		else if(_toView.equalsIgnoreCase("july")||_toView.equalsIgnoreCase("jul")){
			monthInIntegers=7;
	
			return "July";
		}
		else if(_toView.equalsIgnoreCase("august")||_toView.equalsIgnoreCase("aug")){
			monthInIntegers=8;
		
			return "August";
		}
		else if(_toView.equalsIgnoreCase("september")||_toView.equalsIgnoreCase("sept")){
			monthInIntegers=9;
		
			return "September";
		}
		else if(_toView.equalsIgnoreCase("october")||_toView.equalsIgnoreCase("oct")){
			monthInIntegers=10;
		
			return "October";
		}
		else if(_toView.equalsIgnoreCase("november")||_toView.equalsIgnoreCase("nov")){
			monthInIntegers=11;
			
			return "November";
		}
		else if(_toView.equalsIgnoreCase("december")||_toView.equalsIgnoreCase("dec")){
			monthInIntegers=12;
		
			return "December";
		}
		else{
			
			return null;
		}
		
	}

	
	public void formViewList(DateTime startDay, DateTime endDay){
		
		_storage = Controller.getDBStorage();
		//LinkedList<Task> storageList = _storage.load();
		LinkedList<Task> viewList=new LinkedList<Task>();

		for (int i = 0; i < _storage.load().size(); i++){
			if(_storage.load().get(i).getTaskType()==TaskType.TIMED){
				
				if(((_storage.load().get(i).getStart().isAfter(startDay))||(_storage.load().get(i).getStart().isEqual(startDay)))
						&&((_storage.load().get(i).getStart().isBefore(endDay))||(_storage.load().get(i).getStart().isEqual(endDay)))){
					
					viewList.add(_storage.load().get(i));
				}
				else if(((_storage.load().get(i).getEnd().isAfter(startDay))||(_storage.load().get(i).getEnd().isEqual(startDay)))
						&&((_storage.load().get(i).getEnd().isBefore(endDay))||(_storage.load().get(i).getEnd().isEqual(endDay)))){
					
					viewList.add(_storage.load().get(i));	
				}
			}
			else if(_storage.load().get(i).getTaskType()==TaskType.DEADLINE){
			
				if(_storage.load().get(i).getEnd()!=null){
				
					if(((_storage.load().get(i).getEnd().isAfter(startDay))||(_storage.load().get(i).getEnd().isEqual(startDay)))
							&&((_storage.load().get(i).getEnd().isBefore(endDay))||(_storage.load().get(i).getEnd().isEqual(endDay)))){
					
						viewList.add(_storage.load().get(i));
						
					}
				}
			}
			
		}
		setReturnList(viewList);
	
		
	}
	public void viewOverDueTasks(DateTime endDay){
		
		LinkedList<Task> viewList= new LinkedList<Task>();
		for(int i=0;i<_storage.load().size();i++){
			
			if(_storage.load().get(i).getTaskType()==TaskType.FLOATING){
				break;
				
			}
			if(((_storage.load().get(i).getEnd().isBefore(endDay))||(_storage.load().get(i).getEnd().isEqual(endDay)))
					&&(_storage.load().get(i).getTaskStatus()==false)){
				viewList.add(_storage.load().get(i));
			}
		}
		setReturnList(viewList);
	}
	private void setReturnList(LinkedList<Task> list) {
		LinkedList<Task> returnList = new LinkedList<Task>(list);
		for (Task task : _storage.load()) {
			if (task.getTaskType() == TaskType.FLOATING) {
				returnList.add(task);
			}
		}
		_returnList = returnList;
	}

	public LinkedList<Task> getReturnList() {
		return _returnList;
	}

	@Override
	public String undo() {
		return "View cannot be undone";
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	public String getViewType() {
		return _viewType;
	}

	
}