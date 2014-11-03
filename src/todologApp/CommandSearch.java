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
		searchName(storageList,_searchKey);
		feedback = "Searching for \"" + _searchKey + "\" is completed";
		return feedback;
	}
	
	public void searchName(LinkedList<Task> storageList, String searchKey) {
		LinkedList<Task> searchList = new LinkedList<Task>();
		for (int i = 0; i < storageList.size(); i++) {
			if (storageList.get(i).getTaskName().toUpperCase().contains(searchKey.toUpperCase())) {
				searchList.add(storageList.get(i));
			}
		}
		setReturnList(searchList);
	}
	
	private void setReturnList(LinkedList<Task> list) {
		_returnList = list;
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
