package todologApp;

import java.util.LinkedList;

public class PreviousCommand {
	private static LinkedList<String> _inputs;
	private static int _position;
	public PreviousCommand() {
		_inputs = new LinkedList<String>();
		_position = -1;
	}
	public static void addInput(String input) {
		
	for (int i = _inputs.size()-1-_position; i>0; i--) {
			_inputs.add(input);
		}
		_position ++;
	}
	
	public static String UpArrow() throws Exception {
		if (_position >= 0) {
			String toBeUndone = _inputs.get(_position);
			_position--;
			return toBeUndone;
		} else {
			throw new Exception("No commands to undo!");
		}
	}
	public static String DownArrow() throws Exception {
		_position++;
		if (_position < _inputs.size()) {			
			String toBeRedone = _inputs.get(_position);
			return toBeRedone;
		} else {
			_position--;
			throw new Exception("No commands to redo!");
		}
	}
}
