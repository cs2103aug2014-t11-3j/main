
//for now a listener for the textfield where you input your command is
//enough

package todologApp;
import java.awt.*;
import java.awt.event.*;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.util.*;
//import java.lang.Object;


public class UserInterface extends JFrame { /**
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
	
	private JTextField commandEntryTextField;
	private JTextArea dynamicHelpText;
	//private Controller controller;
	private LinkedList <Task> toDoListItems = new LinkedList<Task>();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {  
				try {
					UserInterface window = new UserInterface();
					window.setVisible(true);
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
		Container contentPane = UserInterface.getContentPane();	
		contentPane.setLayout(new GridBagLayout()); 
		//initializeLinkedList();
		
		UserInterface.setTitle("ToDoLog");
		UserInterface.setResizable(false);
		UserInterface.setBounds(100,100,550, 375);					
		UserInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	//this method consists of setting the different sections within the frame of ToDoLog
	private void fillUpTheJFrame(JFrame UserInterface){
		Container mainPanel = UserInterface.getContentPane();
		createToDoList(mainPanel);
		createBottomPanel(mainPanel); 
							 
	}
	
	private void createToDoList(Container mainPanel){                        
		JPanel toDoListHolder = new JPanel(new GridBagLayout());
		toDoListHolder.setBackground(Color.WHITE);
		GridBagConstraints panelParameters;      //panelParameters are values for how the top panel will fit into the main frame of ToDoLog
		GridBagConstraints scrollPaneParameters; //scrollPaneParameters are values for how the scrollPane will be placed within the top panel,toDoListHolder
		toDoListHolder.setPreferredSize(new Dimension(540, 225));
		
		panelParameters = setParameters(TODOLIST_PARAMETERS);
		scrollPaneParameters = setParameters(TODOLIST_SCROLLPANE_PARAMETERS);
		
		JTable toDoListTable = new JTable(new MyTableModel(toDoListItems));    
		toDoListTable.setPreferredSize(new Dimension(532,225));
		adjustTableColumns(toDoListTable);
		changeTableColors(toDoListTable);
		//updateToDoListTable(toDoListTable,toDoListItems,toDoListHeaders);
		
		JScrollPane toDoList = new JScrollPane(toDoListTable);
		toDoList.setPreferredSize(new Dimension(532,225));
		
		toDoListHolder.add(toDoList,scrollPaneParameters);
		mainPanel.add(toDoListHolder, panelParameters);
		
	}
	
	//private void updateToDoListTable(JTable toDoListTable,)
	
	
	//this method creates the bottom section of ToDoLog which consists of the command entry
	//line, the dynamic help text area and the legend
	private void createBottomPanel(Container mainPanel){
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setPreferredSize(new Dimension(500,100));
		GridBagConstraints parameters;
		
		parameters = setParameters(BOTTOM_PANEL_PARAMETERS);
		
		createCommandEntryTextBox(bottomPanel);
		createTextArea(bottomPanel);
		createLegend(bottomPanel);
		
		mainPanel.add(bottomPanel, parameters);
		
	}
	private void createCommandEntryTextBox(JPanel bottomPanel) {
		//TODO implement this
		//for layout, google for "java layout..."
		GridBagConstraints bottomPanelParameters;
		bottomPanelParameters = setParameters(COMMAND_ENTRY_PARAMETERS);
		commandEntryTextField = new JTextField(20);
		bottomPanel.add(commandEntryTextField,bottomPanelParameters);
		commandEntryTextField.addActionListener(new CommandEntryTextFieldListener());
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
		
		arrangeLegend(legendMainPanel);
		
		bottomPanel.add(legendMainPanel, LegendParameters);
	}
	
	private void arrangeLegend(JPanel legendMainPanel){
		Font fontForLegend = new Font("SansSerif",Font.BOLD,8);
		Border borderLineForText = new LineBorder(Color.WHITE);
		GridBagConstraints legendPanelLayout;
		GridBagConstraints legendTextLayout;
		Insets insets = new Insets(5,5,5,5);
		
		//this is the "priority: high" colored box
		JPanel priorityHighPanel = new JPanel();
		priorityHighPanel.setPreferredSize(new Dimension(20,5));
		priorityHighPanel.setBackground(Color.RED);
		legendPanelLayout = new GridBagConstraints(0,0,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		legendMainPanel.add(priorityHighPanel,legendPanelLayout);
		
		//the text for "priority: high"
		JTextField priorityHigh = new JTextField();
		priorityHigh.setPreferredSize(new Dimension(30,15));
		priorityHigh.setText("Priority: High");
		priorityHigh.setFont(fontForLegend);
		priorityHigh.setBorder(borderLineForText);
		legendTextLayout = new GridBagConstraints(1,0,5,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		legendMainPanel.add(priorityHigh,legendTextLayout);
        
		//this is the "priority: medium" colored box
		JPanel priorityMediumPanel = new JPanel();
		priorityMediumPanel.setPreferredSize(new Dimension(20,5));
		priorityMediumPanel.setBackground(Color.PINK);
		legendPanelLayout = new GridBagConstraints(0,1,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		legendMainPanel.add(priorityMediumPanel,legendPanelLayout);
		
		//the text for "priority: medium"
		JTextField priorityMedium = new JTextField();
		priorityMedium.setPreferredSize(new Dimension(30,15));
		priorityMedium.setText("Priority: Medium");
		priorityMedium.setFont(fontForLegend);
		priorityMedium.setBorder(borderLineForText);
		legendTextLayout = new GridBagConstraints(1,1,5,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		legendMainPanel.add(priorityMedium,legendTextLayout);
		
		//this is the "priority: low" colored box
		JPanel priorityLowPanel = new JPanel();
		priorityLowPanel.setPreferredSize(new Dimension(20,5));
		priorityLowPanel.setBackground(Color.WHITE);
		Border borderLineForLow = new LineBorder(Color.BLACK);
		priorityLowPanel.setBorder(borderLineForLow);
		legendPanelLayout = new GridBagConstraints(0,2,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		legendMainPanel.add(priorityLowPanel,legendPanelLayout);
		
		//the text for "priority: low"
		JTextField priorityLow = new JTextField();
		priorityLow.setPreferredSize(new Dimension(30,15));
		priorityLow.setText("Priority: Low");
		priorityLow.setFont(fontForLegend);
		priorityLow.setBorder(borderLineForText);
		legendTextLayout = new GridBagConstraints(1,2,5,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		legendMainPanel.add(priorityLow,legendTextLayout); 
		
		//this is the "done" colored box
		JPanel donePanel = new JPanel();
		donePanel.setPreferredSize(new Dimension(20,5));
		donePanel.setBackground(Color.GREEN);
		legendPanelLayout = new GridBagConstraints(0,3,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		legendMainPanel.add(donePanel,legendPanelLayout);
		
		//the text for "done"
		JTextField done = new JTextField();
		done.setPreferredSize(new Dimension(30,15));
		done.setText("Done");
		done.setFont(fontForLegend);
		done.setBorder(borderLineForText);
		legendTextLayout = new GridBagConstraints(1,3,5,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
		legendMainPanel.add(done,legendTextLayout); 
	}
	/**
	 * Create the application.
	 */
	public UserInterface() {
		
		initialize(this); 
		fillUpTheJFrame(this);
		
		
		// create more here
	}
	
	// remember to write unit test as you code
	private class CommandEntryTextFieldListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent evt) {
			//TODO This is for actions and stuffs, sending the action
			//(I think for this is when typing (to guess the input)
			// and for pressing enter then send the text to Parser)
			String commandString = commandEntryTextField.getText();
		//	Controller.parseUserCommand(commandString);
			
			commandEntryTextField.setText("");
		//	dynamicHelpText.setText(Controller.getOutput());
		//	toDoListItems = Controller.getDBStorage().load();
		}
	}
	
	/*private class DynamicHelpTextAreaListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent evt) {
			
		}
	}*/
	
	//this method is to set up the parameters of the gridbagconstraints
	//to put the different panels into the right positions on the JFrame
	//here we use the constructor GridBagConstraints(gridx,gridy,gridwidth,gridheight,weightx,weighty,anchor,fill,insets,ipadx,ipady)
	private GridBagConstraints setParameters(int panelParameters){
		GridBagConstraints parameters;
		Insets insets = new Insets(0,0,0,0);
		Insets dynamicHelpTextInsets = new Insets(10,10,10,10);
		
		if(panelParameters == TODOLIST_PARAMETERS){
			parameters = new GridBagConstraints(0,0,3,3,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,insets,0,0);
			
			
			return parameters;
		}
		
		
		
		else if(panelParameters == BOTTOM_PANEL_PARAMETERS){
			parameters = new GridBagConstraints(0,3,3,1,0.0,0.3,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
			
			return parameters;
		}
		
		else if(panelParameters == COMMAND_ENTRY_PARAMETERS){
			parameters = new GridBagConstraints(0,0,3,1,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,insets,0,0);
			
			return parameters;
		}
		
		else if(panelParameters == DYNAMIC_HELP_TEXT_PARAMETERS){
			parameters = new GridBagConstraints(0,1,2,1,0.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,dynamicHelpTextInsets,0,0);
			return parameters;
		}
		
		else if(panelParameters == LEGEND_PARAMETERS){
			parameters = new GridBagConstraints(2,1,1,1,0.0,0.1,GridBagConstraints.EAST,GridBagConstraints.BOTH,insets,0,0);
			return parameters;
		}
		
		else if(panelParameters == TODOLIST_SCROLLPANE_PARAMETERS){
			parameters = new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
			return parameters;
		}
		
		return null;
		
		
	} 
	
	//convert linked lists into data for the table, go find out how to
	//dynamic help text will also be 
	/*private void initializeLinkedList(){
		toDoListItems.addFirst(new Task("1","Pink","11/09/2001","High","lol"));
		toDoListItems.add(new Task("2","Blue","Bright boy","Low","hahhah"));
		toDoListItems.add(new Task("3","Brilliant meeting with the incredible hulk","02/12/1992","Medium","Remember to bring shotgun"));
	}*/
	
	private void adjustTableColumns(JTable toDoListTable){
		TableColumn tableColumn = null;
		
		for(int columnHeaders = 0; columnHeaders < 5;columnHeaders++){
			tableColumn = toDoListTable.getColumnModel().getColumn(columnHeaders);
			
			switch(columnHeaders){
			case 0:
				tableColumn.setPreferredWidth(20);
				break;
			case 1:
				tableColumn.setPreferredWidth(190);
				break;
			case 2:
				tableColumn.setPreferredWidth(70);
				break;
			case 3:
				tableColumn.setPreferredWidth(40);
				break;
			case 4:
				tableColumn.setPreferredWidth(150);
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

}
