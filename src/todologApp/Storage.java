package todologApp;

import java.util.LinkedList;

public interface Storage {
	public LinkedList<Task> load();
	public void init();
	public void store(Task task);
}
