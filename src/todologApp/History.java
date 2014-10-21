package todologApp;

import java.util.LinkedList;

public class History {
	private LinkedList<Command> _commands;
	public History() {
		_commands = new LinkedList<Command>();
	}
	public boolean addCommand(Command command) {
		return _commands.add(command);
	}
	public Command removeCommand() {
		return _commands.pop();
	}
}
