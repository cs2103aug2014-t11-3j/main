package todologApp;

public interface Command {
	public String execute();

	public String undo();
	public boolean isUndoable();
}
