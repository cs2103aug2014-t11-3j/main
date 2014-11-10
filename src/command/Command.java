package command;

public interface Command {
	public String execute() ;
	public String undo();
	public boolean isUndoable();
}
