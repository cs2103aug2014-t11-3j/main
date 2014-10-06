
package todologApp;
import java.util.LinkedList;

public class CommandDelete implements Command{
	private static Task _task;
	private static DBStorage _storage;
	public CommandDelete(Task task) {
		_task = task;
	}
	public String execute() {
		String feedback;
		_storage=Controller.getDBStorage();
		LinkedList<Task> taskList= _storage.load();
		if(taskList.indexOf(_task)==-1){
			feedback="Invalid cannot delete";
		}
		else{
			taskList.remove(_task);
			feedback="Deleted "+ _task.getTaskName()+" from toDoLog";
		}	
		return feedback;
	}

	public String undo() {
		String feedback;
		CommandAdd undoDelete=new CommandAdd(_task);
		undoDelete.execute();
		feedback="undone the delete command";
		return feedback;
		
		
	}

}
