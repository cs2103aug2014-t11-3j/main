
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;

import com.apple.eawt.Application;

import java.util.*;
//import java.lang.Object;


public class UserInterface extends JFrame implements WindowListener { 
	

	private static final float TABLE_FONT_SIZE = 12f;
	private static final float ENTRY_TEXT_FIELD_FONT_SIZE = 20f;
	private static final float HELP_TEXT_FONT_SIZE = 15f;
/**
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
	private static final int BUTTON_PANEL_PARAMETERS = 9;
	private static final int TOP_PANEL_PARAMETERS = 8;
	private static final int CLOSE_BUTTON_PARAMETERS = 11;
	private static final int BLANK_SPACE_BUTTON_PARAMETERS = 10;
	private static final int MINIMIZE_BUTTON_PARAMETERS = 11;
	
	private JTextField commandEntryTextField;
	private JLayeredPane layerPane = new JLayeredPane();
	private JTextArea dynamicHelpText;
	private JTextArea toDoListText;
	private JTable toDoListTable;
	private JButton closeButton;
	private JButton minimizeButton;
	//private Controller controller;
	private LinkedList <Task> toDoListItems = new LinkedList<Task>();
	private ToDoListTableModel toDoListTableModel;
	
	private static UserInterface window;
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
					window = new UserInterface(); 		
					window.dispose();
					window.setUndecorated(true);
					window.setVisible(true);
					//window.addKeyListener(new UserInterfaceListener());
					
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
		UserInterface.setBounds(100,100,700, 600);					
		UserInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	//this method consists of setting the different sections within the frame of ToDoLog
	private void fillUpTheJFrame(JFrame UserInterface){
		Container contentPane = UserInterface.getContentPane();
		contentPane.add(layerPane);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 700, 580);
		mainPanel.setLayout(new GridBagLayout());
		BufferedImage img;
		try {
			img = ImageIO.read(new File("src/photos/seagull.jpg"));
			JLabel background = new JLabel(new ImageIcon(img));
			background.setBounds(0,0,700, 600);
			layerPane.add(background,new Integer(0));
		} catch (IOException e) {
			//TODO some notifying
			dynamicHelpText.setText("Cannot load image");
		}
		mainPanel.setOpaque(false);
		createToDoListTable(mainPanel);
		createBottomPanel(mainPanel); 
		createTopPanel(mainPanel);
		layerPane.add(mainPanel,new Integer(2));
		
	}
	
	private void createClockPanel(Container topPanel){
		 DigitalClock clock = new DigitalClock();
		 GridBagConstraints clockPanelParameters = setParameters(CLOCK_PARAMETERS);
		 JLabel clockLabel = clock.getTime();
		 topPanel.add(clockLabel,clockPanelParameters);
		 clock.start();
	}
	private void colorComponent(Component component, Color color) {
		JPanel colorCell = new JPanel()
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		((JComponent) component).setBackground(color);
		((JComponent) component).setOpaque(false);
		((JComponent) component).add(colorCell);
	}
	private void createButtonPanel(Container topPanel) {
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		closeButton = new JButton() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				@Override
				protected void paintComponent(Graphics g)
			    {
			        g.setColor( getBackground() );
			        g.fillRect(0, 0, getWidth(), getHeight());
			        super.paintComponent(g);
			    }
			};
		minimizeButton = new JButton() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        g.fillRect(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		closeButton.setBackground(new Color(255,0,0,255));
		closeButton.setOpaque(false);
		colorComponent(closeButton,new Color(255,0,0,255));
		colorComponent(minimizeButton,new Color(255,255,0,255));
		closeButton.setPreferredSize(new Dimension(23,23));
		minimizeButton.setPreferredSize(new Dimension(23,23));
		closeButton.addActionListener(new CloseButtonActionListener());
		minimizeButton.addActionListener(new MinimizeButtonActionListener());
		GridBagConstraints padParameters =
				new GridBagConstraints(0,0,3,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		GridBagConstraints minimizeButtonParameters = 
				new GridBagConstraints(3,0,1,1,0.0,0.0,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE,new Insets(0,0,5,5),0,0);
		GridBagConstraints closeButtonParameters =
				new GridBagConstraints(4,0,1,1,0.0,0.0,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE,new Insets(0,0,5,10),0,0);
		buttonPanel.add(Box.createGlue(),padParameters);
		buttonPanel.add(minimizeButton,minimizeButtonParameters);
		buttonPanel.add(closeButton,closeButtonParameters);
		buttonPanel.setOpaque(false);
		GridBagConstraints ButtonPanelParameters = setParameters(BUTTON_PANEL_PARAMETERS);
		topPanel.add(buttonPanel,ButtonPanelParameters);
	}
	private void createTopPanel(Container mainPanel){
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setBackground(Color.WHITE);
		topPanel.setPreferredSize(new Dimension(650,50));
		GridBagConstraints parameters;
		
		parameters = setParameters(TOP_PANEL_PARAMETERS);
		
		createClockPanel(topPanel);
		createButtonPanel(topPanel);
		//createButton(bottomPanel);
		mainPanel.add(topPanel, parameters);
		topPanel.setOpaque(false);
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
		toDoListTable.getTableHeader().setBackground(new Color(0,0,0,0));
		toDoListTable.getTableHeader().setReorderingAllowed(false);
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
		toDoListTable.setEnabled(false);
		((DefaultTableCellRenderer)toDoListTable.getDefaultRenderer(Object.class)).setOpaque(false);
	
		toDoList.getViewport().setOpaque(false);
		toDoList.getViewport().setBackground(new Color(255,255,255,220));
		
		toDoListTable.setShowGrid(false);
		toDoListTable.setIntercellSpacing(new Dimension(0, 0));
		
		File fontFile = new File("src/fonts/OpenSans-Regular.ttf");
		try {
			Font font;
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			Font sizedFont = font.deriveFont(TABLE_FONT_SIZE);
			toDoListTable.setFont(sizedFont);
			toDoListTable.getTableHeader().setFont(sizedFont);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		File fontFile = new File("src/fonts/BPmono.ttf");
		try {
			Font font;
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			Font sizedFont = font.deriveFont(ENTRY_TEXT_FIELD_FONT_SIZE);
			commandEntryTextField.setFont(sizedFont);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		commandEntryTextField.addActionListener(new CommandEntryTextFieldActionListener());
		commandEntryTextField.addKeyListener(new CommandEntryTextFieldKeyListener());
		commandEntryTextField.getDocument().addDocumentListener(new CommandEntryTextFieldDocumentListener());
		commandEntryTextField.getDocument().putProperty("name", "Text Field");
	}
	
	private void createTextArea(JPanel bottomPanel){
		GridBagConstraints dynamicHelpTextParameters;
		dynamicHelpTextParameters = setParameters(DYNAMIC_HELP_TEXT_PARAMETERS);
		
		//characterize the text area box into the bottom panel
		Border dynamicHelpTextBorder = new LineBorder(Color.GRAY);
		dynamicHelpText = new JTextArea(3,33);
		dynamicHelpText.setMaximumSize(dynamicHelpText.getSize());
		dynamicHelpText.setBorder(dynamicHelpTextBorder);
		dynamicHelpText.setLineWrap(true);
		dynamicHelpText.setWrapStyleWord(false);
		dynamicHelpText.setEditable(false);
		File fontFile = new File("src/fonts/OpenSans-Regular.ttf");
		try {
			Font font;
			font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
			Font sizedFont = font.deriveFont(HELP_TEXT_FONT_SIZE);
			dynamicHelpText.setFont(sizedFont);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//put the dynamic area into a scroll pane
		dynamicHelpText.setBorder(dynamicHelpTextBorder);
		bottomPanel.add(dynamicHelpText,dynamicHelpTextParameters);
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
		ImageIcon img = new ImageIcon("src/ToDoLog logo.gif");
		this.setIconImage(img.getImage());
		Application application = Application.getApplication();
		Image image = Toolkit.getDefaultToolkit().getImage("src/ToDoLog.gif");
		application.setDockIconImage(image);
		initialize(this); 
		fillUpTheJFrame(this);
		makeTrayIcon(this);
		Controller.init();
		toDoListItems = Controller.getDBStorage().load();
		toDoListTableModel = new ToDoListTableModel(toDoListItems);
		toDoListTable.setModel(toDoListTableModel);
		adjustTableColumns(toDoListTable);
		dynamicHelpText.setText(Controller.getFeedback());
		changeTableColors(toDoListTable);
		
		// create more here
	}
	
	private void makeTrayIcon(JFrame userInterface) {
		//Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        ImageIcon img = new ImageIcon("src/ToDoLog logo.gif");
        final PopupMenu popup = new PopupMenu();
        final TrayIcon trayIcon =
                new TrayIcon(img.getImage());
        final SystemTray tray = SystemTray.getSystemTray();
       
        // Create a pop-up menu components
        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");
       
        //Add components to pop-up menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);
       
        trayIcon.setPopupMenu(popup);
       
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
		
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

	private class CommandEntryTextFieldDocumentListener implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			String helperText = readKeyForHelperFeedback();
			dynamicHelpText.setText(helperText);
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			String helperText = readKeyForHelperFeedback();
			dynamicHelpText.setText(helperText);
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			
		}
		private String readKeyForHelperFeedback() {
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
			return helperText;
		}
		
	}
	
	private class CommandEntryTextFieldKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e){

			int keyCode = e.getKeyCode();
			int modifiers = e.getModifiers();
			if ((keyCode == KeyEvent.VK_PAGE_UP) || (keyCode == KeyEvent.VK_F9)){
				toDoListTableModel.pageUp();
			}
			String helperText = readKeyForHelperFeedback();
			dynamicHelpText.setText(helperText);
			// META_MASK: Mac's Command key.
			if (((keyCode == KeyEvent.VK_B) && ((modifiers & KeyEvent.META_MASK)!=0)) ||
					((keyCode == KeyEvent.VK_B) && ((modifiers & KeyEvent.ALT_MASK)!=0))) {
				
				window.setOpacity(0.5f);
			}

		}
	
		@Override
		public void keyReleased(KeyEvent e){
			int keyCode = e.getKeyCode();
			if(keyCode == KeyEvent.VK_PAGE_UP){
			toDoListTableModel.pageUp();

			}
			
			if ((keyCode == KeyEvent.VK_PAGE_DOWN) || (keyCode == KeyEvent.VK_F10)){
				toDoListTableModel.pageDown();
			}
		}
			
	
			
		@Override
		public void keyTyped(KeyEvent e){

		}
		private String readKeyForHelperFeedback() {
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
			return helperText;
		}
		
	}
	public class MinimizeButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			window.setState(Frame.ICONIFIED);

		}

	}

	public class CloseButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));

		}

	}
	//this method is to set up the parameters of the gridbagconstraints
	//to put the different panels into the right positions on the JFrame
	//here we use the constructor GridBagConstraints(gridx,gridy,gridwidth,gridheight,weightx,weighty,anchor,fill,insets,ipadx,ipady)
	private GridBagConstraints setParameters(int panelParameters){
		GridBagConstraints parameters;
		Insets topPanelInsets = new Insets(0,0,0,0);
		Insets bottomPanelInsets = new Insets(0,0,0,0);
		Insets buttonPanelInsets = new Insets(0,0,0,0);
		Insets closebuttonInsets = new Insets(0,0,0,0);
		Insets clockInsets = new Insets(15,0,0,0);
		Insets toDoListInsets = new Insets(10,0,0,0);
		Insets toDoListScrollPaneInsets = new Insets(0,0,0,0);
		Insets commandEntryTextFieldInsets = new Insets(10,25,5,25);
		Insets dynamicHelpTextInsets = new Insets(10,25,20,20);
		Insets legendInsets = new Insets(0,0,0,10);
		//Insets buttonInsets = new Insets(10,0,0,20);
		
		if(panelParameters == CLOCK_PARAMETERS){
			parameters = new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,clockInsets,0,0);
			return parameters;
		}
		else if(panelParameters == TODOLIST_PARAMETERS){
			parameters = new GridBagConstraints(0,1,3,4,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,toDoListInsets,0,0);
			return parameters;
		}
		else if(panelParameters == TOP_PANEL_PARAMETERS){
			parameters = new GridBagConstraints(0,0,1,1,0.1,0.0,GridBagConstraints.NORTHEAST,GridBagConstraints.BOTH,topPanelInsets,0,0);
			return parameters;
		}
		else if(panelParameters == BUTTON_PANEL_PARAMETERS){
			parameters = new GridBagConstraints(3,0,1,1,0.1,0.0,GridBagConstraints.NORTHEAST,GridBagConstraints.BOTH,buttonPanelInsets,0,0);
			return parameters;
		}
		else if(panelParameters == BOTTOM_PANEL_PARAMETERS){
			parameters = new GridBagConstraints(0,5,3,3,0.0,0.3,GridBagConstraints.CENTER,GridBagConstraints.BOTH,bottomPanelInsets,0,0);
			return parameters;
		}
		
		
		else if(panelParameters == COMMAND_ENTRY_PARAMETERS){
			parameters = new GridBagConstraints(0,0,3,1,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,commandEntryTextFieldInsets,0,0);
			
			return parameters;
		}
		
		else if(panelParameters == DYNAMIC_HELP_TEXT_PARAMETERS){
			parameters = new GridBagConstraints(0,1,3,1,0.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,dynamicHelpTextInsets,0,0);
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
			parameters = new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,toDoListScrollPaneInsets,0,0);
			return parameters;
		}
		
		return null;
		
		
	} 
	
	//convert linked lists into data for the table, go find out how to
	//dynamic help text will also be 

	
	private void adjustTableColumns(JTable toDoListTable){
		TableColumn tableColumn = null;
		
		for(int columnHeaders = 0; columnHeaders < 6;columnHeaders++){
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
				tableColumn.setPreferredWidth(140);
				break;
			case 4:
				tableColumn.setPreferredWidth(90);
				break;
			case 5:
				tableColumn.setPreferredWidth(0);
				tableColumn.setMinWidth(0);
				tableColumn.setMaxWidth(0);
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
