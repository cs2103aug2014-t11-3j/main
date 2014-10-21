package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class CommandAdd implements Command {
	private static Task _task;
	private static DBStorage _storage;
	public CommandAdd(Task task) {
		_task = task;
		}

	public String execute(){
		String feedback;
		_storage= Controller.getDBStorage();
		LinkedList<Task> newList = new LinkedList<Task>();
		newList=_storage.load();
		sortByDate(newList);
		//newList.add(_task);
		try {
			_storage.store(newList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		feedback = "Added " + _task.getTaskName() + " to ToDoLog";
		return feedback;
	}
	
	public void sortByDate(LinkedList<Task> newList){
	    if(_task.getTaskType().equals("FLOATING")){
	    	newList.add(0,_task);
	    }
	    
	    
	    else{
		for(int i=0;i<newList.size()-1;i++){
			//if the date is to be inserted between two years
			if((newList.get(i).getEndYear()<_task.getEndYear())&&(newList.get(i+1).getEndYear()>_task.getEndYear())){
				newList.add(i+1,_task);
			}
			
			
			//
			else if(newList.get(i).getEndYear()==_task.getEndYear()&&newList.get(i+1).getEndYear()>_task.getEndYear()){
				if(newList.get(i).getEndMonth()<_task.getEndMonth()){
					newList.add(i,_task);
				}
				else if(newList.get(i).getEndMonth()==_task.getEndMonth()){
					if((newList.get(i).getEndDate()<=_task.getEndDate())&&(newList.get(i+1).getEndDate()>=_task.getEndDate())){
						newList.add(i+1,_task);
					}
				}
				else{
					newList.add(i,_task);	
				}	
			}
			
			//when both years are equal, the difference can be by month or date
			else if(newList.get(i).getEndYear()==_task.getEndYear()&&newList.get(i+1).getEndYear()==_task.getEndYear()){
				if((newList.get(i).getEndMonth()<_task.getEndMonth())&&(newList.get(i+1).getEndMonth()>_task.getEndMonth())){
					newList.add(i+1,_task);
					}
				else if(newList.get(i).getEndMonth()==_task.getEndMonth()&&newList.get(i+1).getEndMonth()==_task.getEndMonth()){
					if((newList.get(i).getEndDate()<=_task.getEndDate())&&(newList.get(i+1).getEndDate()>=_task.getEndDate())){
					newList.add(i+1,_task);
						}
				}
			}
			
			//add towards the end
			else{
				newList.add(_task);	
			}
		}
	}
	}

	public String undo() {
		String feedback;
		_storage = Controller.getDBStorage();
		LinkedList<Task> taskList = _storage.load();
		taskList.remove(_task);
		try {
			_storage.store(taskList);
		} catch (IOException e) {
			feedback = "Cannot store the list to ToDoLog";
			return feedback;
		}
		feedback = "Undone the add comand";
		return feedback;

	}

}
