package todologApp;

import java.util.LinkedList;

public class DBStorage implements Storage {
	private LinkedList<Task> _tasks;
	private FileStorage _fileStorage;
	public DBStorage() {
		_fileStorage = new FileStorage();
		_tasks = _fileStorage.load();
	}
	public DBStorage(String fileName) {
		_fileStorage = new FileStorage(fileName);
		_tasks = _fileStorage.load();
	}
	public LinkedList<Task> load() {
		return _tasks;
	}

	@Override
	public void init() {
		_fileStorage.init();
		_tasks = _fileStorage.load();
	}

	@Override
	public void store(LinkedList<Task> tasks) {
		_tasks = tasks;
		_fileStorage.store(tasks);
	}

}
