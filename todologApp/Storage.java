package todologApp;

import java.io.IOException;
import java.util.LinkedList;

public interface Storage {
	public LinkedList<Task> load();

	public void init();

	public void store(LinkedList<Task> tasks) throws IOException;
}
