package command;

import gui.HelpFrame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class CommandHelp implements Command {
	
	private Scanner inFile;
	
	private static final String FEEDBACK_HELP = "Help is here!";
	private static final String FEEDBACK_UNDO = "Help cannot be undone";
	
	//@Author A0118899E
	@Override
	public String execute() {
		String inFileText = "";
		try {
			inFile = new Scanner( new FileReader("helper.txt"));
			while(inFile.hasNextLine() != false ) {
				String line = inFile.nextLine();
				inFileText = inFileText + line + "\n"; 
			} 	
		} catch (FileNotFoundException e) {
				e.printStackTrace();
		}	
		if (inFileText != null ) {
			HelpFrame generateHelpText = new HelpFrame(inFileText);
			generateHelpText.execute();
		}
		return FEEDBACK_HELP;
	}
	
	//@Author A0118899E
	@Override
	public String undo() {
		return FEEDBACK_UNDO ;
	}
	
	//@Author A0118899E
	@Override
	public boolean isUndoable() {
		return false;
	}
}
