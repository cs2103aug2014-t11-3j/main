package todologApp;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class HelpFrame {

	String _text;
	
	public HelpFrame (String text){
		_text = text;
	}
	
	public void execute(){
		JTextArea ta = new JTextArea();

        DefaultCaret caret1 = (DefaultCaret) ta.getCaret();
        caret1.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
	    ta.setLineWrap(true);
	    ta.setWrapStyleWord(true);
	    JScrollPane scroll = new JScrollPane(ta);
	    scroll.setBackground(Color.BLACK);
	    ta.append(_text);
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
}
