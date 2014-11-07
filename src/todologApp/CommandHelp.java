package todologApp;

import java.awt.Color;
import java.awt.Dimension;

import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class CommandHelp implements Command{
	
	private Scanner inFile;

	public CommandHelp(){
	}
	
	public String execute(){
		//Scanner inFile;
		String everything="";
			try{
			 inFile = new Scanner(new FileReader("temp.txt"));
			 while(inFile.nextLine()!=null){
				String check = inFile.nextLine();
				everything = everything + check + "\n"; 
			} 
			}catch(Exception e){
				;
			}
			
		if(everything!=null){
			JTextArea ta = new JTextArea();
		    ta.setLineWrap(true);
		    ta.setWrapStyleWord(true);
		    JScrollPane scroll = new JScrollPane(ta);
		    scroll.setBackground(Color.BLACK);
		    ta.append(everything);
		    ta.setForeground(Color.WHITE);
		    
			JFrame frame = new JFrame("HELPER");
			
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            
            Dimension d = new Dimension(500,500);
            frame.setPreferredSize(d);
            frame.pack();
            frame.getContentPane().add(scroll, java.awt.BorderLayout.CENTER);
            ta.setBackground(Color.DARK_GRAY);
            frame.setResizable(false);
            frame.setVisible(true);
		}
		
		return "Help is here!";
	}
	
	public String undo(){
		return "unexpected error!";
	}
	
	public boolean isUndoable(){
		return false;
	}

	
	
}
