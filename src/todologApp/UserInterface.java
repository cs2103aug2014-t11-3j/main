package todologApp;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;


public class UserInterface implements ActionListener {

	private JFrame frame;
	private JTextField commandEntryTextField;
	private Controller controller;
	final static boolean RIGHT_TO_LEFT = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface window = new UserInterface();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//TODO change layout
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void addComponentsToFrame(Container pane) {
		if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		commandEntryTextField = new JTextField(20);
		c.fill = GridBagConstraints.HORIZONTAL;
		commandEntryTextField.addActionListener(this);
		frame.add(commandEntryTextField);
	}
	
	/**
	 * Create the application.
	 */
	public UserInterface() {
		initialize();
		addComponentsToFrame(frame.getContentPane());
		// create more here
		
	}
	
	// remember to write unit test as you code
	public void actionPerformed(ActionEvent evt) {
		//TODO This is for actions and stuffs, sending the action
		//(I think for this is when typing (to guess the input)
		// and for pressing enter then send the text to Parser)
	    String commandString = commandEntryTextField.getText();
	    Controller.acceptUserCommand(commandString);	    
	    commandEntryTextField.setText("");
	    
	}
	

}
