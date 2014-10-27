package todologApp;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class CommandView implements Command {
	private String _toView;
	private DBStorage _storage;
	private boolean validity;
	private static String DAY_KEYWORD_TODAY = "Today";
	private static String DAY_KEYWORD_THIS_DAY = "This day ";
	private static String DAY_KEYWORD_TOMORROW = "Tomorrow";
	private static String DAY_KEYWORD_TMR = "tmr";
	private static String DAY_KEYWORD_NEXT_DAY="next day"
	private static String DAY_KEYWORD_NEXT_DAY="next day";
	private static String DAY_KEYWORD_THIS_WEEK="this week";
	private static String DAY_KEYWORD_NEXT_WEEK="next week";
	
	
	
	
	public CommandView(String toView ) {
		_toView = toView;
		}

	@Override
	public String execute() {
		
		//checking for today/this day 
		if(_toView.equalsIgnoreCase(DAY_KEYWORD_TODAY)||_toView.equalsIgnoreCase(DAY_KEYWORD_THIS_DAY)){
			startDay=
			endDay=
			viewList(startDay,endDay);
		}
		//checking for tomorrow/tmr/next day
		else if(_toView.equalsIgnoreCase(DAY_KEYWORD_TOMORROW)
				||_toView.equalsIgnoreCase(DAY_KEYWORD_TMR)
				||_toView.equalsIgnoreCase(DAY_KEYWORD_NEXT_DAY)){
			startDay=
			EndDay=
			viewList=
		}
		//checking for date
		else if(_toView.equalsIgnoreCase(DAY_KEYWORD_TOMORROW)
				||_toView.equalsIgnoreCase(DAY_KEYWORD_TMR)
				||_toView.equalsIgnoreCase(DAY_KEYWORD_NEXT_DAY)){
			startDay=
			EndDay=
			viewList=
		}

		
		//checking for this week
		
		//checking for next week
		
		//checking for month 
		
	}

	@Override
	public String undo() {
		return "Unexpected Error";
	}

	@Override
	public boolean isUndoable() {
		return validity;
	}

	
}
