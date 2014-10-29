package todologApp;

import java.util.LinkedList;

import org.joda.time.DateTime;
//import org.joda.time.DateTimeConstants;


public class CommandView implements Command {
	private String _toView;
	private DBStorage _storage;
	private static LinkedList<Task> _returnList;
	private int monthInIntegers;
	private static String DAY_KEYWORD_TODAY = "Today";
	private static String DAY_KEYWORD_THIS_DAY = "This day ";
	private static String DAY_KEYWORD_TOMORROW = "Tomorrow";
	private static String DAY_KEYWORD_TMR = "tmr";
	private static String DAY_KEYWORD_NEXT_DAY="next day";
	private static String DAY_KEYWORD_THIS_WEEK="this week";
	private static String DAY_KEYWORD_NEXT_WEEK="next week";	
	
	
	
	public CommandView(String toView ) {
		_toView = toView;
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
		
		feedback="Displaying tasks for "+ _toView;
		
		
		//checking for today/this day 
		if(_toView.equalsIgnoreCase("DAY_KEYWORD_TODAY")||_toView.equalsIgnoreCase(DAY_KEYWORD_THIS_DAY)){
			startDay=new DateTime(year,month,day,0,0);
			endDay=new DateTime(year,month,day,0,0);
			formViewList(startDay,endDay);
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
		}
		//checking for days 
		else if(isWeekDay()){
			int currentWeekDay=startDay.getDayOfWeek();
			int givenWeekDay=Parser.parseDayOfWeek(_toView);
			if(givenWeekDay>=currentWeekDay){
				startDay=startDay.plusDays(givenWeekDay-currentWeekDay);	
			}
			else {
				startDay=startDay.minusDays(currentWeekDay-givenWeekDay);
			}
			year=startDay.getYear();
			month=startDay.getMonthOfYear();
			day=startDay.getDayOfMonth();
			startDay=new DateTime(year,month,day,0,0);
			endDay=new DateTime(year,month,day,23,59);
			formViewList(startDay,endDay);	
		}
		//checking for date
		else if(Parser.checkDateFormat(_toView)){
			int ddmmyy=Integer.parseInt(_toView);
			year=ddmmyy%100;
			month=(ddmmyy/100)%100;
			day=(ddmmyy/10000);
			startDay=new DateTime(year,month,day,0,0);
			endDay=new DateTime(year,month,day,23,59);
			formViewList(startDay,endDay);
		}
		//checking for this week
		else if(_toView.equalsIgnoreCase(DAY_KEYWORD_THIS_WEEK)){
			startDay=startDay.weekOfWeekyear().roundFloorCopy();
			startDay=new DateTime(year,month,day,0,0);
			endDay=endDay.weekOfWeekyear().roundCeilingCopy();
			startDay.withHourOfDay(0);
			startDay.withMinuteOfHour(0);
			endDay.withHourOfDay(0);
			endDay.withMinuteOfHour(0);
			formViewList(startDay,endDay);
		}
		//checking for next week
		else if(_toView.equalsIgnoreCase(DAY_KEYWORD_NEXT_WEEK)){
			//changing to next week by adding 7 days
			DateTime nextWeekDay=startDay.plusDays(7);
			startDay=nextWeekDay.weekOfWeekyear().roundFloorCopy();
			endDay=nextWeekDay.weekOfWeekyear().roundCeilingCopy();
			startDay.withHourOfDay(0);
			startDay.withMinuteOfHour(0);
			endDay.withHourOfDay(0);
			endDay.withMinuteOfHour(0);
			formViewList(startDay,endDay);
		}
		//checking for month 
		else if(isMonth()){
			DateTime startOfThisMonth=startDay.dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
			int currentMonth=startOfThisMonth.getMonthOfYear();
			if(monthInIntegers>=currentMonth){
				startDay=startOfThisMonth.plusMonths(month-currentMonth).dayOfMonth().withMinimumValue();

				endDay=startOfThisMonth.plusMonths(month-currentMonth).dayOfMonth().withMaximumValue();	
			}
			else {
				startDay=startOfThisMonth.minusMonths(currentMonth-month).dayOfMonth().withMinimumValue();
				endDay=startOfThisMonth.minusMonths(currentMonth-month).dayOfMonth().withMaximumValue();	
			}
			startDay.withHourOfDay(0);
			startDay.withMinuteOfHour(0);
			endDay.withHourOfDay(0);
			endDay.withMinuteOfHour(0);
			formViewList(startDay,endDay);
		}
		else{
			feedback="invalid command";
		}
		
		return feedback;
		
		
	}

	public boolean isWeekDay(){
		if(_toView.equalsIgnoreCase("monday")||_toView.equalsIgnoreCase("mon")
				||_toView.equalsIgnoreCase("tuesday")||_toView.equalsIgnoreCase("tues")
				||_toView.equalsIgnoreCase("wednesday")||_toView.equalsIgnoreCase("wed")
				||_toView.equalsIgnoreCase("thursday")||_toView.equalsIgnoreCase("thurs")
				||_toView.equalsIgnoreCase("friday")||_toView.equalsIgnoreCase("fri")
				||_toView.equalsIgnoreCase("saturday")||_toView.equalsIgnoreCase("sat")
				||_toView.equalsIgnoreCase("sunday")||_toView.equalsIgnoreCase("sun")){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public boolean isMonth() {
		if(_toView.equalsIgnoreCase("january")||_toView.equalsIgnoreCase("jan")){
			monthInIntegers=1;
			return true;
		}
		else if(_toView.equalsIgnoreCase("february")||_toView.equalsIgnoreCase("feb")){
			monthInIntegers=2;
			return true;
		}
		else if(_toView.equalsIgnoreCase("march")||_toView.equalsIgnoreCase("mar")){
			monthInIntegers=3;
			return true;
		}
		else if(_toView.equalsIgnoreCase("april")||_toView.equalsIgnoreCase("apr")){
			monthInIntegers=4;
			return true;
		}
		else if (_toView.equalsIgnoreCase("may")){
			monthInIntegers=5;
			return true;
		}
		else if(_toView.equalsIgnoreCase("june")||_toView.equalsIgnoreCase("jun")){
			monthInIntegers=6;
			return true;
		}
		else if(_toView.equalsIgnoreCase("july")||_toView.equalsIgnoreCase("jul")){
			monthInIntegers=7;
			return true;
		}
		else if(_toView.equalsIgnoreCase("august")||_toView.equalsIgnoreCase("aug")){
			monthInIntegers=8;
			return true;
		}
		else if(_toView.equalsIgnoreCase("september")||_toView.equalsIgnoreCase("sept")){
			monthInIntegers=9;
			return true;
		}
		else if(_toView.equalsIgnoreCase("october")||_toView.equalsIgnoreCase("oct")){
			monthInIntegers=10;
			return true;
		}
		else if(_toView.equalsIgnoreCase("november")||_toView.equalsIgnoreCase("nov")){
			monthInIntegers=11;
			return true;
		}
		else if(_toView.equalsIgnoreCase("december")||_toView.equalsIgnoreCase("dec")){
			monthInIntegers=12;
			return true;
		}
		else{
			return false;
		}
		
	}

	
	public void formViewList(DateTime startDay, DateTime endDay){
		_storage = Controller.getDBStorage();
		LinkedList<Task> storageList = _storage.load();
		//LinkedList<Task> viewList=new LinkedList<Task>();
		for (int i = 0; i < storageList.size(); i++){
			if(((storageList.get(i).getStart().isAfter(startDay))||(storageList.get(i).getStart().isEqual(startDay)))
					&&((storageList.get(i).getStart().isBefore(endDay))||(storageList.get(i).getStart().isEqual(endDay)))){
				
				_returnList.add(storageList.get(i));
			}
			else if(((storageList.get(i).getEnd().isAfter(startDay))||(storageList.get(i).getEnd().isEqual(startDay)))
					&&((storageList.get(i).getEnd().isBefore(endDay))||(storageList.get(i).getEnd().isEqual(endDay)))){
				
				_returnList.add(storageList.get(i));	
			}
		}
		//setReturnList(viewList);
		
		
	}
	
	//private void setReturnList(LinkedList<Task> list) {
		//_returnList = list;
	//}

	public LinkedList<Task> getReturnList() {
		return _returnList;
	}

	@Override
	public String undo() {
		return "Unexpected Error";
	}

	@Override
	public boolean isUndoable() {
		return false;
	}

	
}