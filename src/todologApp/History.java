package todologApp;

import java.util.LinkedList;

public class History {
	private static LinkedList<Command> _commands;
	public static boolean addCommand(Command command) {
		return _commands.add(command);
	}
	public static Command removeCommand() {
		return _commands.pop();
	}
}
