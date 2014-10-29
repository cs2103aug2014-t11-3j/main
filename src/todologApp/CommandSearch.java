package todologApp;

import java.util.LinkedList;

public class CommandSearch implements Command {
	private static String _searchKey;
	private static Storage _storage;
	private static LinkedList<Task> _returnList;
	public CommandSearch(String searchKey) {
		_searchKey = searchKey;
	}

	public String execute() {
		String feedback;
		_storage = Controller.getDBStorage();
		LinkedList<Task> storageList = _storage.load();
		searchName(storageList);
		feedback = "Searching for \"" + _searchKey + "\" is completed";
		return feedback;
	}
	
	public void searchName(LinkedList<Task> storageList) {
		LinkedList<Task> searchList = new LinkedList<Task>();
		for (int i = 0; i < storageList.size(); i++) {
			if (storageList.get(i).getTaskName().contains(_searchKey)) {
				searchList.add(storageList.get(i));
			}
		}
		_returnList = searchList;
	}
	
	public LinkedList<Task> getReturnList() {
		return _returnList;
	}
	public String undo(){
		return "unexpected error";
	}
	public boolean isUndoable(){
		return false;
	}

}
