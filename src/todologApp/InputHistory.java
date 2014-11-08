package todologApp;

import java.util.LinkedList;

public class InputHistory {
	private LinkedList<String> _strings;
	private int _position;
	public InputHistory() {
		_strings = new LinkedList<String>();
		_position = -1;
	}
	
	public boolean addInput (String string) {
		_position = _strings.size();
		return _strings.add(string);
	}
	public String getBackwards() throws Exception {
		if (_position >= 0) {
			String toBeReShown = _strings.get(_position);
			_position--;
			return toBeReShown;
		} else {
			return _strings.get(_position);
		}
	}
	public void goBackwards() throws Exception {
		if (_position >= 0) {
			_position--;
		} else {
			throw new Exception("No commands to undo!");
		}
	}
	public String getForwards() throws Exception {
		if (_position < _strings.size()-1) {
			_position++;
			String toBeReShown = _strings.get(_position+1);
			return toBeReShown;
		} else {
			return null;
		}
	}
	public void goForwards() throws Exception {
		_position++;
		if (_position >= _strings.size()) {
			_position--;
			throw new Exception("No commands to redo!");
		}
	}
	
}
