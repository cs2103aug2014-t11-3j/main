package todologApp;

import java.util.LinkedList;

public class CommandSearch implements Command {
	private static String _searchKey;
	private static Storage _storage;

	public CommandSearch(String searchKey) {
		_searchKey = searchKey;
	}

	public String execute() {
		String feedback;
		_storage = Controller.getDBStorage();
		LinkedList<Task> storageList = _storage.load();
		LinkedList<Task> searchList = searchName(storageList);
		System.out.println(searchList);
		feedback = "searching for " + _searchKey + " is completed";
		return feedback;
	}

	public LinkedList<Task> searchName(LinkedList<Task> storageList) {
		LinkedList<Task> searchList = new LinkedList<Task>();
		for (int i = 0; i < storageList.size(); i++) {
			if (storageList.get(i).getTaskName().equalsIgnoreCase(_searchKey)) {
				searchList.add(storageList.get(i));
			}
		}
		return searchList;
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}
}
