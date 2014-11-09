package todologApp;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class CommandHelp implements Command{
	
	private Scanner inFile;

	public CommandHelp(){
	}
	
	public String execute(){
		//Scanner inFile;
		String everything="";
			
			 try {
				inFile = new Scanner(new FileReader("helper.txt"));
				while(inFile.hasNextLine()!=false){
					String check = inFile.nextLine();
					everything = everything + check + "\n"; 
				} 
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			 if(everything!=null){
				 HelpFrame generateHelpText = new HelpFrame(everything);
				 generateHelpText.execute();
			 }
		return "Help is here!";
	}
	
	public String undo(){
		return "Help cannot be undone";
	}
	
	public boolean isUndoable(){
		return false;
	}

	
	
}
