package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public class DBStorage implements Storage {
	private LinkedList<Task> _tasks;
	private FileStorage _fileStorage;
	public DBStorage() {
		_fileStorage = new FileStorage();
		init();
	}
	public DBStorage(String fileName) {
		_fileStorage = new FileStorage(fileName);
		init();
	}
	public LinkedList<Task> load() {
		return _tasks;
	}

	@Override
	public void init() {
		_tasks = new LinkedList<Task>();
		_tasks = _fileStorage.load();
	}

	@Override
	public void store(LinkedList<Task> tasks) throws IOException {
		_tasks = tasks;
		_fileStorage.store(tasks);
	}

}
