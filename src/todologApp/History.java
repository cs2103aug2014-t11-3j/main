package todologApp;

import java.util.LinkedList;

public class History {
	private LinkedList<Command> _commands;
	private int _position;
	public History() {
		_commands = new LinkedList<Command>();
		_position = -1;
	}
	public boolean addCommand(Command command) {
		
		for (int i = _commands.size()-1-_position; i>0; i--) {
			_commands.removeLast();
		}
		_position ++;
		return _commands.add(command);
	}
	public Command goBackwards() throws Exception {
		if (_position >= 0) {
			Command toBeUndone = _commands.get(_position);
			_position--;
			return toBeUndone;
		} else {
			throw new Exception("No commands to undo!");
		}
	}
	public Command goForwards() throws Exception {
		_position++;
		if (_position < _commands.size()) {			
			Command toBeRedone = _commands.get(_position);
			return toBeRedone;
		} else {
			_position--;
			throw new Exception("No commands to redo!");
		}
	}
}
