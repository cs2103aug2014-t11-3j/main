package todologApp;

import java.util.LinkedList;

public class CommandSearch implements Command {
	private static String _searchKey;
	private static LinkedList<Task> _returnList;
	private static DBStorage _storage;
	private static LinkedList<Task> _storageList;
 	public CommandSearch(String searchKey) {
		_searchKey = searchKey;
		_storage = Controller.getDBStorage();
		_storageList = _storage.load();
 	}
		
	public String execute() {
		String feedback;
		Controller.setFocusTask(null); // set focus task to change UI's page
		searchName(_searchKey);
		feedback = "Searching for \"" + _searchKey + "\" is completed";
		return feedback;
	}
	
	public void searchName(String searchKey) {
		LinkedList<Task> searchList = new LinkedList<Task>();
		for (int i = 0; i < _storageList.size(); i++) {
			if (_storageList.get(i).getTaskName().toUpperCase().contains(searchKey.toUpperCase())) {
				searchList.add(_storageList.get(i));
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
