package todologApp;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class CommandHelp implements Command{
	
	private Scanner inFile;

	public CommandHelp(){
	}
	
	public String execute(){
		//Scanner inFile;
		String everything="";
			try{
			 inFile = new Scanner(new FileReader("helper.txt"));
			
			 while(inFile.hasNextLine()!=false){
				String check = inFile.nextLine();
				everything = everything + check + "\n"; 
			} 
			}catch(Exception e){
				;
			}
			
		if(everything!=null){
			JTextArea ta = new JTextArea();

            DefaultCaret caret1 = (DefaultCaret) ta.getCaret();
            caret1.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		    ta.setLineWrap(true);
		    ta.setWrapStyleWord(true);
		    JScrollPane scroll = new JScrollPane(ta);
		    scroll.setBackground(Color.BLACK);
		    ta.append(everything);
		    ta.setForeground(Color.WHITE);
		    
			JFrame frame = new JFrame("HELPER");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            
            Dimension d = new Dimension(700,500);
            frame.setPreferredSize(d);
            frame.pack();
            frame.getContentPane().add(scroll, java.awt.BorderLayout.CENTER);
            ta.setBackground(Color.DARK_GRAY);
            frame.setResizable(false);
            frame.setVisible(true);
            ta.setAutoscrolls(false);
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
