package storage;

import java.io.IOException;
import java.util.LinkedList;

import common.Task;

public class DBStorageStub implements Storage {
	private LinkedList<Task> _tasks;
	private FileStorage _fileStorage;
	public DBStorageStub() {
		init();
	}
	public LinkedList<Task> load() {
		return _tasks;
	}

	public FileStorage getFileStorage(){
		return _fileStorage;
	}
	@Override
	public void init() {
		_tasks = new LinkedList<Task>();
	}

	@Override
	public void store(LinkedList<Task> tasks) throws IOException {
		_tasks = tasks;
	}
	

}
