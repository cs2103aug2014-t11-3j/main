package todologApp;

import java.util.LinkedList;

public class CommandSearch implements Command {
	private static String _searchKey;
	private static LinkedList<Task> _returnList;
	private static DBStorage _storage;
	private static LinkedList<Task> _storageList;
	
	private static final String FEEDBACK_VALID_SEARCH = "Searching for \"%1$s\" is completed";
	private static final String FEEDBACK_INVALID_UNDO = "Search cannot be undone";
 	
	public CommandSearch(String searchKey) {
		_searchKey = searchKey;
		_storage = Controller.getDBStorage();
		_storageList = _storage.load();
 	}
	
	public String getSearchKey() {
		return _searchKey;
	}
	
	private void setReturnList(LinkedList <Task> list) {
		_returnList = list;
	}

	public LinkedList<Task> getReturnList() {
		return _returnList;
	}
	
	@Override
	public String execute() {
		String feedback;
		Controller.setFocusTask( null ); // set focus task to change UI's page
		searchName(_searchKey);
		feedback = String.format(FEEDBACK_VALID_SEARCH, _searchKey);
		return feedback;
	}
	
	public void searchName(String searchKey) {
		LinkedList<Task> searchList = new LinkedList<Task>();
		for (int i = 0; i < _storageList.size(); i++ ) {
			if (_storageList.get(i).getTaskName().toUpperCase().contains(searchKey.toUpperCase())) {
				searchList.add(_storageList.get(i));
			}
		}
		setReturnList(searchList);
	}
	
	@Override
	public String undo() {
		return FEEDBACK_INVALID_UNDO;
	}
	
	@Override
	public boolean isUndoable() {
		return false;
	}
}
