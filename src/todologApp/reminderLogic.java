package todologApp;
import java.util.LinkedList;
import org.joda.time.DateTime;

public class reminderLogic {
	
	private DBStorage _storage;
	private LinkedList<Task> _toBeReminded;
	
public reminderLogic(){
	_storage=Controller.getDBStorage();
	_toBeReminded=new LinkedList<Task>();
}

public void execute(){
	try{
	LinkedList<Task> _storageList=_storage.load();
	for(int i=0;i<_storageList.size();i++){
		if(check24hrsBefore(_storageList.get(i))==true){
			_toBeReminded.add(_storageList.get(i));
		}
	}
	}
	catch(Exception e){
		
	}
	
	for(int i=0;i<_toBeReminded.size();i++){
		ShowMessageDialog one = new ShowMessageDialog(_toBeReminded.get(i));
		one.execute();
	}
	
}

public boolean check24hrsBefore(Task task){
	DateTime currentTime= new DateTime();
	int year = currentTime.getYear();
	int month = currentTime.getMonthOfYear();
	int day = currentTime.getDayOfMonth();
	int hour= currentTime.getHourOfDay();
	int min=currentTime.getMinuteOfHour();
	currentTime= new DateTime(year,month,day,hour,min);
	
	if(task.getTaskType()==TaskType.DEADLINE){
		DateTime dueTime = task.getEndDateTime();
		DateTime tempTime= dueTime;
		if(tempTime.minusHours(24).equals(currentTime)){
			return true;
		}
	}
	else if(task.getTaskType()==TaskType.TIMED){
		DateTime startTime =task.getStartDateTime();
		DateTime tempTime =startTime;
		if(tempTime.minusHours(24).equals(currentTime)){
			return true;
		}
	}
	return false;
}

}
