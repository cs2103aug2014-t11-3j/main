package todologApp;

import java.util.LinkedList;

public class InputHistory {
	private LinkedList<String> _String;
	private int _position;
	public InputHistory() {
		_String = new LinkedList<String>();
		_position = -1;
	}
	public Command getBackwards() throws Exception {
		if (_position >= 0) {
			return _commands.get(_position);
		} else {
			return null;
		}
	}
	public void goBackwards() throws Exception {
		if (_position >= 0) {
			_position--;
		} else {
			throw new Exception("No commands to undo!");
		}
	}
	public Command getForwards() throws Exception {
		if (_position < _commands.size()) {			
			Command toBeRedone = _commands.get(_position+1);
			return toBeRedone;
		} else {
			_position--;
			return null;
		}
	}
	public void goForwards() throws Exception {
		_position++;
		if (_position >= _commands.size()) {
			_position--;
			throw new Exception("No commands to redo!");
		}
	}
	
}
