package todologApp;

public class Task {
	private String _name;
	private TaskType _taskType;
	public Task(String parameter){
		_name = parseName(parameter);
		_taskType = parseTaskType(parameter);
	}
	public Task(TaskType taskType, String name) {
		_name = name;
		_taskType = taskType;
	}
	private TaskType parseTaskType(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}
	private String parseName(String parameter) {
		// TODO Auto-generated method stub
		return null;
	}
	public String getType() {
		return _taskType.toString();
	}
	public String getName() {
		return _name;
	}
	
}
