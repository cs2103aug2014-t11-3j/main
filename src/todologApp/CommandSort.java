package todologApp;

import java.util.LinkedList;

public class CommandSort implements Command{
	private String _type;
	private DBStorage _storage;
	LinkedList<Task> newList;
	public CommandSort(String type) {
	_type = type;
	
}
	public String execute() {
		String feedback;
		_storage = Controller.getDBStorage();
		newList = _storage.load();
		if(_type.equals("name")){
			LinkedList<Task> sortedByName=sortByName();
			feedback="";
		}
		else if(_type.equals("date")){
			LinkedList<Task> sortedByDate=sortByDate();
			feedback="";
		}
		else{
			feedback="";
		}
		return feedback;
	}
	public LinkedList<Task> sortByName(){
		
	}
	public LinkedList<Task> sortByDate(){
		
	}
	

	public String undo() {
	String feedback;
	feedback="";
	return feedback;
	}

}

