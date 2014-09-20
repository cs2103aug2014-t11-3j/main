package todologApp;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;


public class UserInterface implements ActionListener {

	private JFrame frame;
	private JTextField commandEntryTextField;
	private Controller controller;
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
	
	private void createTextBox() {
		//TODO implement this
		//for layout, google for "java layout..."
		commandEntryTextField = new JTextField(20);
		commandEntryTextField.addActionListener(this);
		frame.add(commandEntryTextField);
	}
	
	/**
	 * Create the application.
	 */
	public UserInterface() {
		initialize();
		createTextBox();
		// create more here
	}
	
	// remember to write unit test as you code
	public void actionPerformed(ActionEvent evt) {
		//TODO This is for actions and stuffs, sending the action
		//(I think for this is when typing (to guess the input)
		// and for pressing enter then send the text to Parser)
	    String commandString = commandEntryTextField.getText();
	    Controller.parseUserCommand(commandString);	    
	    commandEntryTextField.setText("");
	    
	}
	

}
