package storage;

import java.io.IOException;
import java.util.LinkedList;

import common.Task;

public interface Storage {
	public LinkedList<Task> load();

	public void init();

	public void store(LinkedList<Task> tasks) throws IOException;
}
