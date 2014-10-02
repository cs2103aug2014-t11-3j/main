
package todologApp;
import java.util.LinkedList;

public class CommandDelete implements Command{
	private static Task _task;
	private static Storage _storage;
	public CommandDelete(Task task) {
		_task = task;
	}
	public void execute() {
		_storage=Controller.getDBStorage();
		LinkedList<Task> taskList= _storage.load();
		if(taskList.indexOf(_task)==-1){
			System.out.println("Invalid cannot delete");
		}
		else{
			taskList.remove(_task);
		}	
	}

	public void undo() {
		CommandAdd undoDelete=new CommandAdd(_task);
		undoDelete.execute();
		
	}

}
