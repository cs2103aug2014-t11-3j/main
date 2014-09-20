package todologApp;

// remember to write unit test as you code
public class Controller {
	
	private static Storage storage;

	public static String parseUserCommand(String userCommand) {
		Command command = new Command(userCommand);
		String feedback = executeCommand(command);
		return feedback;
	}

	private static String executeCommand(Command command) {
		// TODO Auto-generated method stub
		return null;
	}
}
