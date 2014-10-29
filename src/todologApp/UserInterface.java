
//for now a listener for the textfield where you input your command is
//enough

package todologApp;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.util.*;
//import java.lang.Object;


public class UserInterface extends JFrame implements WindowListener { /**
	 * 
	 */
	private static final long serialVersionUID = 4308151724219875078L;
//also I wanna put the action listener elsewhere
	
	private static final int TODOLIST_PARAMETERS = 1;
	private static final int BOTTOM_PANEL_PARAMETERS = 2;
	private static final int COMMAND_ENTRY_PARAMETERS = 3;
	private static final int DYNAMIC_HELP_TEXT_PARAMETERS = 4;
	private static final int LEGEND_PARAMETERS = 5;
	private static final int TODOLIST_SCROLLPANE_PARAMETERS = 6;
	private static final int CLOCK_PARAMETERS = 7;
	//private static final int BUTTON_PARAMETERS = 7;
	
	private JTextField commandEntryTextField;
	private JLayeredPane layerPane = new JLayeredPane();
	private JTextArea dynamicHelpText;
	private JTextArea toDoListText;
	private JTable toDoListTable;
	//private Controller controller;
	private LinkedList <Task> toDoListItems = new LinkedList<Task>();
	private ToDoListTableModel toDoListTableModel;
	
	/**
	 * Launch the application.
	 */
	@Override
	public void setVisible(boolean value) {
	    super.setVisible(value);
	    commandEntryTextField.requestFocusInWindow();
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {  
				try {
					UserInterface window = new UserInterface();
					window.setVisible(true);   		
			//		window.addKeyListener(new UserInterfaceListener());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		});
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	//this initialize method sets up the main frame for ToDoLog
	private void initialize(JFrame UserInterface) { 
		//initializeLinkedList();
		
		UserInterface.setTitle("ToDoLog");
		UserInterface.setResizable(false);
		UserInterface.setBounds(100,100,700, 570);					
		UserInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	//this method consists of setting the different sections within the frame of ToDoLog
	private void fillUpTheJFrame(JFrame UserInterface){
		Container contentPane = UserInterface.getContentPane();
		contentPane.add(layerPane);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0,0,700, 550);
		mainPanel.setLayout(new GridBagLayout());
		BufferedImage img;
		try {
			img = ImageIO.read(new File("src/seagull.jpg"));
			JLabel background = new JLabel(new ImageIcon(img));
			background.setBounds(0,0,700, 570);
			layerPane.add(background,new Integer(0));
		} catch (IOException e) {
			//TODO some notifying
			dynamicHelpText.setText("Cannot load image");
		}
		mainPanel.setOpaque(false);
		createToDoListTable(mainPanel);
		createBottomPanel(mainPanel); 
		createClockPanel(mainPanel);
		layerPane.add(mainPanel,new Integer(2));
		
		
	}
	
	private void createClockPanel(Container mainPanel){
		 DigitalClock clock = new DigitalClock();
		 GridBagConstraints clockPanelParameters = setParameters(CLOCK_PARAMETERS);
		 
		 mainPanel.add(clock.getTime(),clockPanelParameters);
		 clock.start();
	}
	
	private void createToDoListTable(Container mainPanel){                        
		JPanel toDoListHolder = new JPanel(new GridBagLayout());
		GridBagConstraints panelParameters;      //panelParameters are values for how the top panel will fit into the main frame of ToDoLog
		GridBagConstraints scrollPaneParameters; //scrollPaneParameters are values for how the scrollPane will be placed within the top panel,toDoListHolder
		toDoListHolder.setPreferredSize(new Dimension(650, 300));
		
		panelParameters = setParameters(TODOLIST_PARAMETERS);
		scrollPaneParameters = setParameters(TODOLIST_SCROLLPANE_PARAMETERS);
		
		toDoListTableModel = new ToDoListTableModel(toDoListItems);
		toDoListTable = new JTable(toDoListTableModel);    
		toDoListTable.setPreferredSize(new Dimension(650,300));
		adjustTableColumns(toDoListTable);
		changeTableColors(toDoListTable);
		toDoListTable.getTableHeader().setResizingAllowed(false);
		//toDoListTable.addKeyListener(new ToDoListTableListener());
		//updateToDoListTable(toDoListTable,toDoListItems,toDoListHeaders);
		
		JScrollPane toDoList = new JScrollPane(toDoListTable)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		toDoList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		toDoList.setPreferredSize(new Dimension(650,300));
		toDoList.setOpaque(false);
		toDoList.setBackground(new Color(255,255,255,220));
		toDoListHolder.add(toDoList,scrollPaneParameters);
		mainPanel.add(toDoListHolder, panelParameters);
		toDoListHolder.setOpaque(false);
		toDoListTable.setOpaque(false);
		((DefaultTableCellRenderer)toDoListTable.getDefaultRenderer(Object.class)).setOpaque(false);
	
		toDoList.getViewport().setOpaque(false);
		toDoList.getViewport().setBackground(new Color(255,255,255,220));
		
		toDoListTable.setShowGrid(false);
		toDoListTable.setIntercellSpacing(new Dimension(0, 0));
//		BufferedImage img;
//		try {
//			img = ImageIO.read(new File("src/black-white.jpg"));
//			JLabel background = new JLabel();
//			background.setOpaque(false);
//			background.setBackground(new Color(255,255,255,170));
//			background.setBounds(25,20,650,225);
//			layerPane.add(background,new Integer(1));
//		} catch (IOException e) {
//			//TODO some notifying
//			dynamicHelpText.setText("Cannot load image");
//		}
		
	}
	private void createToDoList(Container mainPanel){
		JPanel toDoListHolder = new JPanel(new GridBagLayout());
		toDoListHolder.setBackground(Color.WHITE);
		GridBagConstraints panelParameters;      //panelParameters are values for how the top panel will fit into the main frame of ToDoLog
		GridBagConstraints scrollPaneParameters; //scrollPaneParameters are values for how the scrollPane will be placed within the top panel,toDoListHolder
		toDoListHolder.setPreferredSize(new Dimension(650, 225));
		
		panelParameters = setParameters(TODOLIST_PARAMETERS);
		scrollPaneParameters = setParameters(TODOLIST_SCROLLPANE_PARAMETERS);
		toDoListText = new JTextArea(10,10);
		toDoListText.setPreferredSize(new Dimension(532,225));
		toDoListHolder.add(toDoListText,scrollPaneParameters);
		mainPanel.add(toDoListHolder, panelParameters);
	}
	//private void updateToDoListTable(JTable toDoListTable,)
	
	
	//this method creates the bottom section of ToDoLog which consists of the command entry
	//line, the dynamic help text area and the legend
	private void createBottomPanel(Container mainPanel){
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setPreferredSize(new Dimension(650,170));
		GridBagConstraints parameters;
		
		parameters = setParameters(BOTTOM_PANEL_PARAMETERS);
		
		createCommandEntryTextBox(bottomPanel);
		createTextArea(bottomPanel);
		createLegend(bottomPanel);
		//createButton(bottomPanel);
		mainPanel.add(bottomPanel, parameters);
		bottomPanel.setOpaque(false);
	}
	
	private void createCommandEntryTextBox(JPanel bottomPanel) {
		//TODO implement this
		//for layout, google for "java layout..."
		GridBagConstraints bottomPanelParameters;
		bottomPanelParameters = setParameters(COMMAND_ENTRY_PARAMETERS);
		commandEntryTextField = new JTextField(20);
		bottomPanel.add(commandEntryTextField,bottomPanelParameters);
		commandEntryTextField.addActionListener(new CommandEntryTextFieldActionListener());
		commandEntryTextField.addKeyListener(new CommandEntryTextFieldKeyListener());
	}
	
	private void createTextArea(JPanel bottomPanel){
		GridBagConstraints dynamicHelpTextParameters;
		dynamicHelpTextParameters = setParameters(DYNAMIC_HELP_TEXT_PARAMETERS);
		
		//characterize the text area box into the bottom panel
		Border dynamicHelpTextBorder = new LineBorder(Color.BLUE);
		dynamicHelpText = new JTextArea(3,33);
		dynamicHelpText.setMaximumSize(dynamicHelpText.getSize());
		dynamicHelpText.setBorder(dynamicHelpTextBorder);
		dynamicHelpText.setLineWrap(true);
		
		//put the dynamic area into a scroll pane
		JScrollPane dynamicHelpTextScrollPane = new JScrollPane(dynamicHelpText);
		dynamicHelpTextScrollPane.setBorder(dynamicHelpTextBorder);
		bottomPanel.add(dynamicHelpTextScrollPane,dynamicHelpTextParameters);
		//dynamicHelpText.addActionListener(new DynamicHelpTextAreaListener());
	
	}
	
	private void createLegend(JPanel bottomPanel){ //this one will be put at borderlayout.east
		GridBagConstraints LegendParameters;
		LegendParameters = setParameters(LEGEND_PARAMETERS);
		//Border borderLineForLegend = new LineBorder(Color.BLACK);
		
		JPanel legendMainPanel = new JPanel(new GridBagLayout());          //must find a better way of organising the legend
		legendMainPanel.setBackground(Color.WHITE);                     //looks ugly now    
		//legendMainPanel.setBorder(borderLineForLegend);
		legendMainPanel.setPreferredSize(new Dimension(100,100));
		
//		arrangeLegend(legendMainPanel);
		
		bottomPanel.add(legendMainPanel, LegendParameters);
		legendMainPanel.setOpaque(false);
	}
	
//	private void arrangeLegend(JPanel legendMainPanel){
//		Font fontForLegend = new Font("SansSerif",Font.BOLD,8);
//		Border borderLineForText = new EmptyBorder(0,0,0,0);
//		GridBagConstraints legendPanelLayout;
//		GridBagConstraints legendTextLayout;
//		Insets insets = new Insets(5,5,5,5);
//		
//		//this is the "priority: high" colored box
//		JPanel priorityHighPanel = new JPanel();
//		priorityHighPanel.setPreferredSize(new Dimension(20,5));
//		priorityHighPanel.setBackground(Color.RED);	
//		legendPanelLayout = new GridBagConstraints(0,0,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
//		legendMainPanel.add(priorityHighPanel,legendPanelLayout);
//	
//		//the text for "priority: high"
//		JTextField priorityHigh = new JTextField();
//		priorityHigh.setPreferredSize(new Dimension(30,15));
//		priorityHigh.setText("Priority: High");
//		priorityHigh.setFont(fontForLegend);
//		priorityHigh.setEnabled(false);
//		priorityHigh.setDisabledTextColor(Color.WHITE);
//		priorityHigh.setBorder(borderLineForText);
//		priorityHigh.setOpaque(false);
//		legendTextLayout = new GridBagConstraints(1,0,5,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
//		legendMainPanel.add(priorityHigh,legendTextLayout);
//        
//		//this is the "priority: medium" colored box
//		JPanel priorityMediumPanel = new JPanel();
//		priorityMediumPanel.setPreferredSize(new Dimension(20,5));
//		priorityMediumPanel.setBackground(Color.PINK);
//		legendPanelLayout = new GridBagConstraints(0,1,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
//		legendMainPanel.add(priorityMediumPanel,legendPanelLayout);
//		
//		//the text for "priority: medium"
//		JTextField priorityMedium = new JTextField();
//		priorityMedium.setPreferredSize(new Dimension(30,15));
//		priorityMedium.setText("Priority: Medium");
//		priorityMedium.setFont(fontForLegend);
//		priorityMedium.setEnabled(false);
//		priorityMedium.setDisabledTextColor(Color.WHITE);
//		priorityMedium.setBorder(borderLineForText);
//		priorityMedium.setOpaque(false);
//		legendTextLayout = new GridBagConstraints(1,1,5,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
//		legendMainPanel.add(priorityMedium,legendTextLayout);
//		
//		//this is the "priority: low" colored box
//		JPanel priorityLowPanel = new JPanel();
//		priorityLowPanel.setPreferredSize(new Dimension(20,5));
//		priorityLowPanel.setBackground(Color.WHITE);
//		Border borderLineForLow = new LineBorder(Color.BLACK);
//		priorityLowPanel.setBorder(borderLineForLow);
//		legendPanelLayout = new GridBagConstraints(0,2,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
//		legendMainPanel.add(priorityLowPanel,legendPanelLayout);
//		
//		//the text for "priority: low"
//		JTextField priorityLow = new JTextField();
//		priorityLow.setPreferredSize(new Dimension(30,15));
//		priorityLow.setText("Priority: Low");
//		priorityLow.setFont(fontForLegend);
//		priorityLow.setEnabled(false);
//		priorityLow.setDisabledTextColor(Color.WHITE);
//		priorityLow.setBorder(borderLineForText);
//		priorityLow.setOpaque(false);
//		legendTextLayout = new GridBagConstraints(1,2,5,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
//		legendMainPanel.add(priorityLow,legendTextLayout); 
//		
		//this is the "done" colored box
//		JPanel donePanel = new JPanel();
//		donePanel.setPreferredSize(new Dimension(20,5));
//		donePanel.setBackground(Color.GREEN);
//		legendPanelLayout = new GridBagConstraints(0,3,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
//		legendMainPanel.add(donePanel,legendPanelLayout);
		
		//the text for "done"
//		JTextField done = new JTextField();
//		done.setPreferredSize(new Dimension(30,15));
//		done.setText("Done");
//		done.setFont(fontForLegend);
//		done.setEnabled(false);
//		done.setDisabledTextColor(Color.WHITE);
//		done.setBorder(borderLineForText);
//		done.setOpaque(false);
//		legendTextLayout = new GridBagConstraints(1,3,5,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
//		legendMainPanel.add(done,legendTextLayout); 
//	}
	
	/*	private void createButton(JPanel bottomPanel){
			GridBagConstraints buttonParameters = setParameters(BUTTON_PARAMETERS);
			JButton magicButton = new JButton("");
			magicButton.setPreferredSize(new Dimension(10,10));
			
			bottomPanel.add(magicButton,buttonParameters);
		}
		
	/*	
	/**
	 * Create the application.
	 */
	public UserInterface() {
		
		initialize(this); 
		fillUpTheJFrame(this);
		
		Controller.init();
		toDoListItems = Controller.getDBStorage().load();
		toDoListTableModel = new ToDoListTableModel(toDoListItems);
		toDoListTable.setModel(toDoListTableModel);
		adjustTableColumns(toDoListTable);
		dynamicHelpText.setText(Controller.getFeedback());
		changeTableColors(toDoListTable);
		
		// create more here
	}
	
	// remember to write unit test as you code
	private class CommandEntryTextFieldActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent evt) {
			//TODO This is for actions and stuffs, sending the action
			//(I think for this is when typing (to guess the input)
			// and for pressing enter then send the text to Parser)
			String commandString = commandEntryTextField.getText();
			
		
//			Controller.acceptUserCommand(commandString);	    
//			commandEntryTextField.setText("");
//			toDoListText.setText(Controller.getOutput());
//			dynamicHelpText.setText(Controller.getFeedBack());
			//PreviousCommand.addInput(commandString);
			Controller.acceptUserCommand(commandString);
			commandEntryTextField.setText("");
			dynamicHelpText.setText(Controller.getFeedback());	
			toDoListItems = Controller.getDisplayList();
			toDoListTableModel.setTableData(toDoListItems);
			toDoListTableModel.fireTableDataChanged();
			
			//I want the screen to show the previous screen if the next screen has no more items to display
			if(Parser.getFirstWord(commandString).equalsIgnoreCase("delete") && ((toDoListTableModel.getActualRowCount() % toDoListTableModel.getPageSize()) == 0) && toDoListTableModel.getActualRowCount() > 0){
				toDoListTableModel.pageUp();
				
			}
			
			//show the screen where the new item has been added
			if(Parser.getFirstWord(commandString).equalsIgnoreCase("add") && toDoListTableModel.getPageCount() >= 2){
				int i = 1;
				while( i < toDoListTableModel.getPageCount()){
				toDoListTableModel.pageDown();
				i++;
				}
			}
			
			

		}
	}
	
	private class CommandEntryTextFieldKeyListener implements KeyListener {
		
		@Override
		public void keyPressed(KeyEvent e){
			String commandString = commandEntryTextField.getText();
			LinkedList<String> entryHelper = Controller.getCommandEntryHelperDetailsFromInput(commandString);
			String helperText = dynamicHelpText.getText();
			if (!entryHelper.isEmpty()) {
				helperText += "\n";
				String commandType = entryHelper.poll();
				if (commandType.equals("add")) {
					helperText = UIFeedbackHelper.createCmdAddHelpText(entryHelper);
				} else if (commandType.equals("edit")) {
					helperText = UIFeedbackHelper.createCmdEditHelpText(entryHelper);
				}
			}
			dynamicHelpText.setText(helperText);
		}
		
		@Override
		public void keyReleased(KeyEvent e){
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_PAGE_UP){
			toDoListTableModel.pageUp();
			}
			
			if(keyCode == KeyEvent.VK_PAGE_DOWN){
				toDoListTableModel.pageDown();
			}
			String commandString = commandEntryTextField.getText();
			LinkedList<String> entryHelper = Controller.getCommandEntryHelperDetailsFromInput(commandString);
			String helperText = dynamicHelpText.getText();
			if (!entryHelper.isEmpty()) {
				helperText += "\n";
				String commandType = entryHelper.poll();
				if (commandType.equals("add")) {
					helperText = UIFeedbackHelper.createCmdAddHelpText(entryHelper);
				} else if (commandType.equals("edit")) {
					helperText = UIFeedbackHelper.createCmdEditHelpText(entryHelper);
				}
			}
			dynamicHelpText.setText(helperText);
			
		}
		
		@Override
		public void keyTyped(KeyEvent e){
			String commandString = commandEntryTextField.getText();
			LinkedList<String> entryHelper = Controller.getCommandEntryHelperDetailsFromInput(commandString);
			String helperText = dynamicHelpText.getText();
			if (!entryHelper.isEmpty()) {
				helperText += "\n";
				String commandType = entryHelper.poll();
				if (commandType.equals("add")) {
					helperText = UIFeedbackHelper.createCmdAddHelpText(entryHelper);
				} else if (commandType.equals("edit")) {
					helperText = UIFeedbackHelper.createCmdEditHelpText(entryHelper);
				}
			}
			dynamicHelpText.setText(helperText);
		}

		
	}
	
	//this method is to set up the parameters of the gridbagconstraints
	//to put the different panels into the right positions on the JFrame
	//here we use the constructor GridBagConstraints(gridx,gridy,gridwidth,gridheight,weightx,weighty,anchor,fill,insets,ipadx,ipady)
	private GridBagConstraints setParameters(int panelParameters){
		GridBagConstraints parameters;
		Insets insets = new Insets(0,0,0,0);
		Insets clockInsets = new Insets(0,0,0,0);
		Insets toDoListInsets = new Insets(0,0,0,0);
		Insets commandEntryTextFieldInsets = new Insets(10,25,5,25);
		Insets dynamicHelpTextInsets = new Insets(10,25,20,20);
		Insets legendInsets = new Insets(0,0,0,10);
		//Insets buttonInsets = new Insets(10,0,0,20);
		
		if(panelParameters == CLOCK_PARAMETERS){
			parameters = new GridBagConstraints(0,0,3,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,clockInsets,0,0);
			
			return parameters;
		}
		else if(panelParameters == TODOLIST_PARAMETERS){
			parameters = new GridBagConstraints(0,1,3,4,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,toDoListInsets,0,0);
			
			
			return parameters;
		}
		
		else if(panelParameters == BOTTOM_PANEL_PARAMETERS){
			parameters = new GridBagConstraints(0,5,3,3,0.0,0.3,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
			
			return parameters;
		}
		
		else if(panelParameters == COMMAND_ENTRY_PARAMETERS){
			parameters = new GridBagConstraints(0,0,3,1,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,commandEntryTextFieldInsets,0,0);
			
			return parameters;
		}
		
		else if(panelParameters == DYNAMIC_HELP_TEXT_PARAMETERS){
			parameters = new GridBagConstraints(0,1,2,1,0.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,dynamicHelpTextInsets,0,0);
			return parameters;
		}
		
		else if(panelParameters == LEGEND_PARAMETERS){
			parameters = new GridBagConstraints(2,1,1,1,0.0,0.1,GridBagConstraints.EAST,GridBagConstraints.BOTH,legendInsets,0,0);
			return parameters;
		}
		
		/*else if(panelParameters == BUTTON_PARAMETERS){
			parameters = new GridBagConstraints(5,1,1,1,0.0,0.1,GridBagConstraints.EAST,GridBagConstraints.BOTH,buttonInsets,0,0);
			return parameters;
		}*/
		
		else if(panelParameters == TODOLIST_SCROLLPANE_PARAMETERS){
			parameters = new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
			return parameters;
		}
		
		return null;
		
		
	} 
	
	//convert linked lists into data for the table, go find out how to
	//dynamic help text will also be 

	
	private void adjustTableColumns(JTable toDoListTable){
		TableColumn tableColumn = null;
		
		for(int columnHeaders = 0; columnHeaders < 5;columnHeaders++){
			tableColumn = toDoListTable.getColumnModel().getColumn(columnHeaders);
			
			switch(columnHeaders){
			case 0:
				tableColumn.setPreferredWidth(30);
				break;
			case 1:
				tableColumn.setPreferredWidth(180);
				break;
			case 2:
				tableColumn.setPreferredWidth(210);
				break;
			case 3:
				tableColumn.setPreferredWidth(180);
				break;
			case 4:
				tableColumn.setPreferredWidth(50);
				break;
			}
		}
	}
	
	private void changeTableColors(JTable toDoListTable){
		toDoListTable.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
		toDoListTable.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());
		toDoListTable.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
		toDoListTable.getColumnModel().getColumn(3).setCellRenderer(new CustomRenderer());
		toDoListTable.getColumnModel().getColumn(4).setCellRenderer(new CustomRenderer());
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		System.out.println("Windows Opened");
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		System.out.println("Windows Closed");
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("Windows Closing");
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		System.out.println("Windows Maximised");
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		System.out.println("Windows Minimized");
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
