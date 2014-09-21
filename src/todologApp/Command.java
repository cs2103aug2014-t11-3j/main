package todologApp;

public class Command {
	private static CommandType _commandType;
	private static Task _task;
	public Command(String userCommand) {
		_commandType = parseCommandType(userCommand);
		_task = parseTask(userCommand);
	}
	private Task parseTask(String userCommand) {
		String command = sc.next();
		return null;
	}
	private CommandType parseCommandType(String userCommand) {
		// TODO Auto-generated method stub
		return null;
	}

}
