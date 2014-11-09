
//for now a listener for the textfield where you input your command is
//enough

package todologApp;
import java.awt.AWTException;
//import java.awt.AWTUtilities;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.RenderingHints;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import sun.swing.table.DefaultTableCellHeaderRenderer;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;
import com.melloware.jintellitype.JIntellitypeException;
//import com.sun.awt.AWTUtilities;

public class UserInterface extends JFrame { 
	

	private static final int TABLE_PAGE_SIZE = 16;
	private static final int FLOATINGTASK_TABLE_INDEX = 2;
	private static final int TODOTASK_TABLE_INDEX = 1;
	private static final float TABLE_FONT_SIZE = 12f;
	private static final float ENTRY_TEXT_FIELD_FONT_SIZE = 20f;
	private static final float HELP_TEXT_FONT_SIZE = 13f;
/**
	 * 
	 */
	private static final long serialVersionUID = 4308151724219875078L;	
	private static final int TODOLIST_HOLDER_PARAMETERS = 1;
	private static final int BOTTOM_PANEL_PARAMETERS = 2;
	private static final int COMMAND_ENTRY_PARAMETERS = 3;
	private static final int DYNAMIC_HELP_TEXT_PARAMETERS = 4;
	private static final int TODOTABLE_PARAMETERS = 5;
	private static final int CLOCK_PARAMETERS = 6;
	private static final int TOP_PANEL_PARAMETERS = 7;
	private static final int BUTTON_PANEL_PARAMETERS = 8;
	private static final int ICON_PARAMETERS = 9;
	private static final int FLOATINGTASKSTABLE_PARAMETERS = 10;
	private static final int TODOTABLE_LABEL_PARAMETERS = 11;
	private static final int FLOATINGTASKSTABLE_LABEL_PARAMETERS = 12;
	
	private JTextField commandEntryTextField;
	private JLayeredPane layerPane = new JLayeredPane();
	private JTextArea dynamicHelpText;
	private JTable toDoTaskTable;
	private JLabel toDoTaskLabel;
	private JTable floatingTaskTable;
	private JLabel floatingTaskLabel;
	private JTable focusTable;
	private JLabel closeButton;
	private JLabel minimizeButton;
	private LinkedList<Task> toDoListItems;
	private LinkedList<Task> floatingItems;
	private ToDoTasksListTableModel toDoTasksTableModel;
	private FloatingTasksListTableModel floatingTasksTableModel;
	private TrayIcon trayIcon;
	private boolean firstMinimize;

	private JScrollPane toDoTaskPane;
	private JScrollPane floatingTaskPane;

	private boolean invisibility = false;
	private static UserInterface window;
	private JLabel backgroundLabel;
	private JLabel iconPanel;
	private BufferedImage backgroundImage;
	private URL iconUrl;
	private Border dynamicHelpTextBorder;
	private DigitalClock clock;
	private static Point offset;
	
	/**
	 * Launch the application.
	 */
	@Override
	public void setVisible(boolean value) {
	    super.setVisible(value);
	    commandEntryTextField.requestFocusInWindow();
	}
	
	public static void main(String[] args) {
		
		startReminderTimer();
			
		EventQueue.invokeLater(new Runnable() {
			public void run() {  
				try {
					window = new UserInterface(); 	
					
					window.dispose();
					window.setVisible(true);
					window.addMouseListener(window.new ScreenDraggingMouseListener());
					window.addMouseMotionListener(window.new ScreenDraggingMouseMotionListener());
					
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
		
		ArrayList<Image> images = new ArrayList<Image>();
		URL url = this.getClass().getClassLoader().getResource("icon-16x16.gif");
		Image image = Toolkit.getDefaultToolkit().getImage(url);
		images.add(image);
		url = this.getClass().getClassLoader().getResource("icon-32x32.gif");
		image = Toolkit.getDefaultToolkit().getImage(url);
		images.add(image);
		UserInterface.setIconImages(images);
		UserInterface.setTitle("ToDoLog");
		UserInterface.setResizable(false);
		UserInterface.setBounds(325,140,700, 610);					
		UserInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	//this method consists of setting the different sections within the frame of ToDoLog
	private void fillUpTheJFrame(JFrame UserInterface){
		UserInterface.setUndecorated(true);
		UserInterface.setBackground(new Color(255,255,255,255));
		Container contentPane = UserInterface.getContentPane();
		contentPane.add(layerPane);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 700, 590);
		mainPanel.setLayout(new GridBagLayout());
		
		try {
			URL url = this.getClass().getClassLoader().getResource("photos/seagull.jpg");
			backgroundImage = ImageIO.read(url);
			backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
			backgroundLabel.setBounds(0,0,700, 610);
			layerPane.add(backgroundLabel,new Integer(0));
			
		} catch (IOException e) {
			//TODO some notifying
			//dynamicHelpText.append("Cannot load image");
		}
		
		mainPanel.setOpaque(false);
		createToDoListHolder(mainPanel);
		createBottomPanel(mainPanel); 
		createTopPanel(mainPanel);
		layerPane.add(mainPanel,new Integer(2));
		
	}
	
	private void createTopPanel(Container mainPanel){
		JPanel topPanel = new JPanel(new GridBagLayout());
		
		//give settings to the top panel
		topPanel.setPreferredSize(new Dimension(650,50));
		topPanel.setOpaque(false);
		
		//determine the position of top panel in main panel
		GridBagConstraints parameters;
		parameters = setParameters(TOP_PANEL_PARAMETERS);
		
		//fill the top panel with the icon, clock and button
		createIcon(topPanel);
		createClockPanel(topPanel);
		createButtonPanel(topPanel);
		
		mainPanel.add(topPanel, parameters);
		
	}
	
	private void createClockPanel(Container topPanel){
		
		 
		 clock = new DigitalClock();
		 GridBagConstraints clockPanelParameters = setParameters(CLOCK_PARAMETERS);
		 topPanel.add(clock.getTime(),clockPanelParameters); //clock.getTime() refers to the JLabel within DigitalClock class
		 clock.start();
	}
	
	private void createButtonPanel(Container topPanel) {
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		
		//set up the close button
		closeButton = new JLabel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				@Override
				protected void paintComponent(Graphics g){
			        g.setColor( getBackground() );
			        ((Graphics2D) g).setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			        g.fillOval(0, 0, getWidth(), getHeight());
			        super.paintComponent(g);
			    }
				//paintComponent is used to give the button a circled-shape
			};
			
		closeButton.setBackground(new Color(242, 38, 19, 255));
		closeButton.setOpaque(false);
		closeButton.setPreferredSize(new Dimension(17,17));
		closeButton.addMouseListener(new CloseButtonMouseListener());
		
		//set up the minimize button
		minimizeButton = new JLabel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g)
		    {
		        g.setColor( getBackground() );
		        ((Graphics2D) g).setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g.fillOval(0, 0, getWidth(), getHeight());
		        super.paintComponent(g);
		    }
		};
		
		minimizeButton.setBackground(new Color(241, 196, 15, 255));
		minimizeButton.setOpaque(false);
		minimizeButton.setPreferredSize(new Dimension(17,17));
		minimizeButton.addMouseListener(new MinimizeButtonMouseListener());
		
		
		GridBagConstraints padParameters =
				new GridBagConstraints(0,0,3,1,1.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		GridBagConstraints minimizeButtonParameters = 
				new GridBagConstraints(3,0,1,1,0.0,0.0,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE,new Insets(0,0,10,7),0,0);
		GridBagConstraints closeButtonParameters =
				new GridBagConstraints(4,0,1,1,0.0,0.0,GridBagConstraints.NORTHEAST,GridBagConstraints.NONE,new Insets(0,0,10,10),0,0);
		
		//add the buttons to a button panel
		buttonPanel.add(Box.createGlue(),padParameters);
		buttonPanel.add(minimizeButton,minimizeButtonParameters);
		buttonPanel.add(closeButton,closeButtonParameters);
		buttonPanel.setOpaque(false);
		
		//add the button panel to the top panel
		GridBagConstraints ButtonPanelParameters = setParameters(BUTTON_PANEL_PARAMETERS);
		topPanel.add(buttonPanel,ButtonPanelParameters);
	}
	
	private void createIcon(JPanel topPanel) {
		iconPanel = new JLabel();
		
		iconUrl = this.getClass().getClassLoader().getResource("icon-40x40.gif");
		ImageIcon icon = new ImageIcon(iconUrl);
		iconPanel.setIcon(icon);
		
		//add iconPanel to topPanel
		GridBagConstraints parameters;
		parameters = setParameters(ICON_PARAMETERS);
		topPanel.add(iconPanel,parameters);
		
	}
	
	private void createToDoListHolder(Container mainPanel){
		GridBagConstraints panelParameters;   
		panelParameters = setParameters(TODOLIST_HOLDER_PARAMETERS); 
	//panelParameters are values for how the toDoTaskHolder panel will fit into the main 
	//frame of ToDoLog
		
		JPanel toDoListHolder = new JPanel(new GridBagLayout());
		toDoListHolder.setPreferredSize(new Dimension(650, 310));
		
		//add toDoTasksTable and floatingTasksTable into the toDoTaskHolder
		createToDoListLabel(toDoListHolder);
		createToDoList(toDoListHolder);
		createFloatingTaskListLabel(toDoListHolder);
		createFloatingTaskList(toDoListHolder);
		
		//add the toDoListHolder to the mainPanel
		mainPanel.add(toDoListHolder, panelParameters);
		toDoListHolder.setOpaque(false);
		
		
	}
	
	private void createFloatingTaskListLabel(JPanel toDoListHolder) {
		floatingTaskLabel = new JLabel("Flexible tasks:") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			protected void paintComponent(Graphics g)
		    {	
				int arc = 5;
		        g.setColor( getBackground() );
		        ((Graphics2D) g).setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		        super.paintComponent(g);
		    }
		};
		Border paddingBorder = BorderFactory.createEmptyBorder(4,4,4,4);
		Border border = BorderFactory.createMatteBorder(2, 2, 0, 2, Color.GRAY);
		Border compoundBorder = BorderFactory.createCompoundBorder(border, paddingBorder);
		floatingTaskLabel.setBorder(compoundBorder);
		floatingTaskLabel.setPreferredSize(new Dimension(40,25));
		floatingTaskLabel.setOpaque(false);
		floatingTaskLabel.setBackground(new Color(255,255,255,220));
		
		//add the floatingTaskLabel to the toDoListHolder
		GridBagConstraints floatingTaskListLabelParameters;
		floatingTaskListLabelParameters = setParameters(FLOATINGTASKSTABLE_LABEL_PARAMETERS);
		toDoListHolder.add(floatingTaskLabel,floatingTaskListLabelParameters);
		
	}

	private void createToDoListLabel(JPanel toDoListHolder) {
		toDoTaskLabel = new JLabel("This week's events and deadlines:") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			protected void paintComponent(Graphics g)
		    {	
				int arc = 5;
		        g.setColor( getBackground() );
		        ((Graphics2D) g).setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		        super.paintComponent(g);
		    }
		};
		Border paddingBorder = BorderFactory.createEmptyBorder(4,4,4,4);
		Border border = BorderFactory.createMatteBorder(2, 2, 0, 2, new Color(241, 196, 15, 255));
		Border compoundBorder = BorderFactory.createCompoundBorder(border, paddingBorder);
		toDoTaskLabel.setBorder(compoundBorder);
		toDoTaskLabel.setPreferredSize(new Dimension(40,25));
		toDoTaskLabel.setOpaque(false);
		toDoTaskLabel.setBackground(new Color(255,255,255,220));
		
		//add the toDoTaskLabel to the toDoListHolder
		GridBagConstraints toDoListLabelParameters;
		toDoListLabelParameters = setParameters(TODOTABLE_LABEL_PARAMETERS);
		toDoListHolder.add(toDoTaskLabel,toDoListLabelParameters);
		
	}
	
	/* here is where the table displaying the user's tasks is implemented
	 * the table is formed in reference to a table model, and then placed into a scroll pane 
	 * which is then added to the toDoListHolder
	 */
	private void createToDoList(Container toDoListHolder) {
		//GridBagConstraints to position the scrollPane within toDoListHolder
		GridBagConstraints scrollPaneParameters; 
		scrollPaneParameters = setParameters(TODOTABLE_PARAMETERS);
		
		/* a Table Model is used to adjust the table information for ToDoLog
		 */
		toDoTasksTableModel = new ToDoTasksListTableModel(toDoListItems);
		toDoTaskTable = new JTable(toDoTasksTableModel);    
		toDoTaskTable.setPreferredSize(new Dimension(500,280));
		
		//fix the width of the columns of the table and color the rows
		//according to the different due dates of tasks
		adjustToDoTaskTableColumns(toDoTaskTable);
		changeToDoTableColors(toDoTaskTable, new CustomRenderer());
		toDoTaskTable.getTableHeader().setResizingAllowed(false);
		toDoTaskTable.getTableHeader().setBackground(new Color(0,0,0,0));
		toDoTaskTable.getTableHeader().setReorderingAllowed(false);
        
        //get rid of the usual grid in the JTable
		toDoTaskTable.setShowGrid(false);
		toDoTaskTable.setIntercellSpacing(new Dimension(0, 0));
		toDoTaskTable.setOpaque(false);
		toDoTaskTable.setEnabled(false);
		
		focusTable = toDoTaskTable;
		((DefaultTableCellRenderer)toDoTaskTable.getDefaultRenderer(Object.class)).setOpaque(false);
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("fonts/OpenSans-Regular.ttf");
		try {
			Font font;
			font = Font.createFont(Font.TRUETYPE_FONT, in);
			Font sizedFont = font.deriveFont(TABLE_FONT_SIZE);
			toDoTaskTable.setFont(sizedFont);
			toDoTaskTable.getTableHeader().setFont(sizedFont);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        toDoTaskPane = new JScrollPane(toDoTaskTable)

		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			protected void paintComponent(Graphics g)
		    {	
				int arc = 20;
		        g.setColor( getBackground() );
		        ((Graphics2D) g).setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		        super.paintComponent(g);
		    }
		};
		
		//settings of the scroll pane
		toDoTaskPane.setBorder(BorderFactory.createLineBorder(new Color(241, 196, 15), 3, false));
		toDoTaskPane.setPreferredSize(new Dimension(500,280));
		toDoTaskPane.setOpaque(false);
		toDoTaskPane.setBackground(new Color(255,255,255,220));
		toDoTaskPane.getViewport().setOpaque(false);
		toDoTaskPane.getViewport().setBackground(new Color(255,255,255,220));
		toDoTaskPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		//to disable borders at the table headers and also to shift the  headings to the left
        toDoTaskTable.getTableHeader().setDefaultRenderer(new DefaultTableCellHeaderRenderer() {            
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(
                                                           JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                DefaultTableCellHeaderRenderer rendererComponent = (DefaultTableCellHeaderRenderer)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                rendererComponent.setBorder(null);
                rendererComponent.setHorizontalAlignment(LEFT);
                
                return rendererComponent;
            }     
        });
       
		toDoListHolder.add(toDoTaskPane,scrollPaneParameters);
	}
	
	//implementation is about the same as toDoTaskTable
	private void createFloatingTaskList(JPanel toDoListHolder) {
		
		//set position of folatingTaskTable in toDoListHolder
		GridBagConstraints floatingTaskListParameters; 
		floatingTaskListParameters = setParameters(FLOATINGTASKSTABLE_PARAMETERS);
		
		//use a table model to set the table
		floatingTasksTableModel = new FloatingTasksListTableModel(floatingItems);
		floatingTaskTable = new JTable(floatingTasksTableModel); 
		floatingTaskTable.setPreferredSize(new Dimension(130,270));
		
		//adjust the columns and rows
		adjustFloatingTaskTableColumns(floatingTaskTable);
		changeToDoTableColors(floatingTaskTable, new CustomRenderer());
		floatingTaskTable.getTableHeader().setResizingAllowed(false);
		floatingTaskTable.getTableHeader().setBackground(new Color(0,0,0,0));
		floatingTaskTable.getTableHeader().setReorderingAllowed(false);
		floatingTaskTable.getTableHeader().setDefaultRenderer(new DefaultTableCellHeaderRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
            		boolean isSelected, boolean hasFocus, int row, int column) {
                DefaultTableCellHeaderRenderer rendererComponent = 
                		(DefaultTableCellHeaderRenderer)super.getTableCellRendererComponent(table, value, 
                				isSelected, hasFocus, row, column);
                rendererComponent.setBorder(null);
                rendererComponent.setHorizontalAlignment(LEFT);
                
                return rendererComponent;
            }
            
        });
		
		//get rids of the normal grid that tables have
		floatingTaskTable.setOpaque(false);
		floatingTaskTable.setEnabled(false);
		floatingTaskTable.setShowGrid(false);
		floatingTaskTable.setIntercellSpacing(new Dimension(0, 0));
		((DefaultTableCellRenderer)floatingTaskTable.getDefaultRenderer(Object.class)).setOpaque(false);

		InputStream in = this.getClass().getClassLoader().getResourceAsStream("fonts/OpenSans-Regular.ttf");
		try {
			Font font;
			font = Font.createFont(Font.TRUETYPE_FONT, in);
			Font sizedFont = font.deriveFont(TABLE_FONT_SIZE);
			floatingTaskTable.setFont(sizedFont);
			floatingTaskTable.getTableHeader().setFont(sizedFont);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		floatingTaskPane = new JScrollPane(floatingTaskTable)
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			protected void paintComponent(Graphics g)
		    {	
				int arc = 20;
		        g.setColor( getBackground() );
		        ((Graphics2D) g).setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		        super.paintComponent(g);
		    }
		};
		
		floatingTaskPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, false));
		floatingTaskPane.setPreferredSize(new Dimension(130,270));
		floatingTaskPane.setOpaque(false);
		floatingTaskPane.setBackground(new Color(255,255,255,220));
		floatingTaskPane.getViewport().setOpaque(false);
		floatingTaskPane.getViewport().setBackground(new Color(255,255,255,220));
		floatingTaskPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		
		toDoListHolder.add(floatingTaskPane,floatingTaskListParameters);
		
	}

	/* this method creates the bottom section of ToDoLog which consists of the command entry
	 *	line and the dynamic help text area
	 */
	private void createBottomPanel(Container mainPanel){
		
		//settings of the bottom panel
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setBackground(new Color(255,255,255,0));
		bottomPanel.setPreferredSize(new Dimension(650,170));
		bottomPanel.setOpaque(true);
		
		//position of the bottom panel within the main panel
		GridBagConstraints parameters;
		parameters = setParameters(BOTTOM_PANEL_PARAMETERS);
		
		createCommandEntryTextBox(bottomPanel);
		createTextArea(bottomPanel);

		mainPanel.add(bottomPanel, parameters);
	}
	
	private void createCommandEntryTextBox(JPanel bottomPanel) {
		
		//position of the commandEntryTextField within bottom panel
		GridBagConstraints bottomPanelParameters;
		bottomPanelParameters = setParameters(COMMAND_ENTRY_PARAMETERS);
		
		commandEntryTextField = new JTextField(20){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g){
				int arc = 10;
		        g.setColor( getBackground() );
		        ((Graphics2D) g).setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		        g.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
		        super.paintComponent(g);
			}
		};
		bottomPanel.add(commandEntryTextField,bottomPanelParameters);
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("fonts/BPmono.ttf");
		
		try {
			Font font;
			font = Font.createFont(Font.TRUETYPE_FONT, in);
			Font sizedFont = font.deriveFont(ENTRY_TEXT_FIELD_FONT_SIZE);
			commandEntryTextField.setFont(sizedFont);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		commandEntryTextField.setBorder(new LineBorder(Color.BLUE,2,false));
		commandEntryTextField.setFocusTraversalKeysEnabled(false);
		
		//actionListener to take in the user's input and keylistener to enable features
		//such as flipping through pages of the user's tasks, getting the previous user 
		//input, and changing focus between the toDoTaskTable and floatingTaskTable
		commandEntryTextField.addActionListener(new CommandEntryTextFieldActionListener());
		commandEntryTextField.addKeyListener(new CommandEntryTextFieldKeyListener());
		commandEntryTextField.getDocument().addDocumentListener(new CommandEntryTextFieldDocumentListener());
		commandEntryTextField.getDocument().putProperty("name", "Text Field");
	}
	
	private void createTextArea(JPanel bottomPanel){
		
		//position of the dynamicHelpText within the bottomPanel
		GridBagConstraints dynamicHelpTextParameters;
		dynamicHelpTextParameters = setParameters(DYNAMIC_HELP_TEXT_PARAMETERS);
		
		//characterize the text area box into the bottom panel
		dynamicHelpTextBorder = new LineBorder(Color.GRAY);
		dynamicHelpText = new JTextArea(5,33);
		dynamicHelpText.setMaximumSize(dynamicHelpText.getSize());
		dynamicHelpText.setBorder(dynamicHelpTextBorder);
		dynamicHelpText.setLineWrap(true);
		dynamicHelpText.setWrapStyleWord(false);
		dynamicHelpText.setEditable(false);
		dynamicHelpText.setHighlighter(null);
		
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("fonts/OpenSans-Regular.ttf");
		try {
			Font font;
			font = Font.createFont(Font.TRUETYPE_FONT, in);
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
		
	
	}
	
	public UserInterface() {
		initialize(this); 
		fillUpTheJFrame(this);
		useJIntellitype();
		makeTrayIcon(this);
		this.addWindowListener(new ToDoLogWindowListener());
		Controller.init();
		//TODO Seperate 2 lists
		toDoListItems = Controller.getCurrentView();
		floatingItems = Controller.getFloatingTasksList();
		toDoTasksTableModel = new ToDoTasksListTableModel(toDoListItems);
		toDoTaskTable.setModel(toDoTasksTableModel);
		adjustToDoTaskTableColumns(toDoTaskTable);
		dynamicHelpText.append(Controller.getFeedback());
		changeToDoTableColors(toDoTaskTable, new CustomRenderer());
		floatingTasksTableModel = new FloatingTasksListTableModel(floatingItems);
		floatingTaskTable.setModel(floatingTasksTableModel);
		focusTable = toDoTaskTable;
		adjustFloatingTaskTableColumns(floatingTaskTable);
		changeFloatingTableColors(floatingTaskTable, new CustomRenderer());
	}
	
	/*we use JIntellitype to enable the use of hotkeys to enable features like minimizing
	 * and maximizing, and to hide the application and leave the commandTextField visible
	 */
	private void useJIntellitype() {
		try {
			JIntellitype.getInstance();
			
			//register the hotkeys "Alt-B" and "Alt-N"
			JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_ALT, (int) 'B');
			JIntellitype.getInstance().registerHotKey(2, JIntellitype.MOD_ALT, (int) 'N');
			
			JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
	            @Override
	            public void onHotKey(int combination) {
	            	
	            	//if "Alt-B" is pressed, then minimize or maximize the window
	                if (combination == 1)
	                	if (window.getState() == JFrame.ICONIFIED) {
	                		window.setState(JFrame.NORMAL);
	                	} else {
	                		window.setState(JFrame.ICONIFIED);
	                	}
	                
	                //if "Alt-N" is pressed, then make everything except the 
	                //commandTextField invisible
	                if (combination == 2){

	                	if(invisibility == false){
	                		commandEntryTextField.setBorder(new LineBorder(Color.BLUE,4,true));
	                		backgroundLabel.setIcon(null);
	                		iconPanel.setIcon(null);
	                		minimizeButton.setVisible(false);
	                		closeButton.setVisible(false);
	                		window.setBackground(new Color(255,255,255,20));
	                		
	                		dynamicHelpText.setBackground(new Color(255,255,255,0));
	                		dynamicHelpText.setBorder(null);
	                		dynamicHelpText.setForeground(new Color(255,255,255,0));
	                		
	                		toDoTaskLabel.setBackground(new Color(255,255,255,20));
	                		toDoTaskLabel.setForeground(new Color(255,255,255,20));
	                		toDoTaskLabel.setBorder(null);
	                		
	                		floatingTaskLabel.setBackground(new Color(255,255,255,20));
	                		floatingTaskLabel.setForeground(new Color(255,255,255,20));
	                		floatingTaskLabel.setBorder(null);
	                		
	                		toDoTaskPane.setBackground(new Color(255,255,255,20));
	                		toDoTaskPane.setBorder(null);
	                		toDoTaskTable.setForeground(new Color(255,255,255,20));
	                		toDoTaskTable.getTableHeader().setForeground(new Color(255,255,255,20));
	                		changeToDoTableColors(toDoTaskTable,new InvisibleRenderer());
	                		
	                		floatingTaskPane.setBackground(new Color(255,255,255,20));
	                		floatingTaskPane.setBorder(null);
	                		floatingTaskTable.setForeground(new Color(255,255,255,20));
	                		floatingTaskTable.getTableHeader().setForeground(new Color(255,255,255,20));
	                		changeFloatingTableColors(floatingTaskTable,new InvisibleRenderer());
	                		
	                		clock.getTime().setForeground(new Color(255,255,255,20));
	                		
	                		invisibility = true;
	                		
	                	}
	                	
	                	//make everything reappear with the same hotkey
	                	else{
	                		commandEntryTextField.setBorder(new LineBorder(Color.BLUE,2,true));
	                		backgroundLabel.setIcon(new ImageIcon(backgroundImage));
	                		iconPanel.setIcon(new ImageIcon(iconUrl));
	                		minimizeButton.setVisible(true);
	                		closeButton.setVisible(true);
	                		window.setBackground(new Color(255,255,255,255));
	                		
		                	dynamicHelpText.setBackground(new Color(255,255,255,255));
		                	dynamicHelpText.setBorder(dynamicHelpTextBorder);
		                	dynamicHelpText.setForeground(new Color(0,0,0,255));
		                	
		                	toDoTaskPane.setBackground(new Color(255,255,255,220));
		                	toggleFocusTable();toggleFocusTable();
		                	
		                	toDoTaskLabel.setBackground(new Color(255,255,255,220));
		                	toDoTaskLabel.setForeground(new Color(0,0,0,255));
		                	
		                	floatingTaskLabel.setBackground(new Color(255,255,255,220));
		                	floatingTaskLabel.setForeground(new Color(0,0,0,255));
		             
		                	toDoTaskTable.setForeground(Color.BLACK);
		                	toDoTaskTable.getTableHeader().setForeground(Color.BLACK);
		                	changeToDoTableColors(toDoTaskTable,new CustomRenderer());
		                	
		                	floatingTaskPane.setBackground(new Color(255,255,255,220));
		                	floatingTaskTable.setForeground(Color.BLACK);
		                	floatingTaskTable.getTableHeader().setForeground(Color.BLACK);
		                	changeFloatingTableColors(floatingTaskTable,new CustomRenderer());
		                	
		                	clock.getTime().setForeground(Color.WHITE);
		           
	                		invisibility = false;
	                	}
	                	
	                
	                
	                }
	            }
	            
			});
		} catch (JIntellitypeException jie) {
			dynamicHelpText.append("Cannot load hotkey settings.\n");
		}
	}

	private void makeTrayIcon(JFrame userInterface) {
		//Check the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        URL url = this.getClass().getClassLoader().getResource("icon-16x16.gif");
        ImageIcon img = new ImageIcon(url);
        PopupMenu popup = new PopupMenu();
        trayIcon =
                new TrayIcon(img.getImage());
        SystemTray tray = SystemTray.getSystemTray();
       
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
        firstMinimize = false;
	}

	// remember to write unit test as you code
	private class CommandEntryTextFieldActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent evt) {
			
			String commandString = commandEntryTextField.getText();
			//for the case where user exits ToDoLog
			if (commandString.equalsIgnoreCase("exit")) {
				window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
			}
			
			//this is where ToDoLog takes in the user's value
			Controller.acceptUserCommand(commandString);
			commandEntryTextField.setText("");
			
			//feedback is sent to the dynamicHelpText to assist the user in using ToDoLog
			dynamicHelpText.setText(Controller.getFeedback());	
			//TODO Seperate 2 lists
			toDoListItems = Controller.getCurrentView();
			floatingItems = Controller.getFloatingTasksList();
			
			//to inform the user which sets of tasks ToDoLog will be displaying
			toDoTaskLabel.setText(Controller.getViewOrSearchType());
			
			//constantly changes the table model and updates the changes
			toDoTasksTableModel.setTableData(toDoListItems);
			toDoTasksTableModel.fireTableDataChanged();
			floatingTasksTableModel.setTableData(floatingItems);
			floatingTasksTableModel.fireTableDataChanged();
			//I want the toDoListItems to show the previous screen if the next screen has no more items to display
			flipPages();

		}
	}
	
	private void flipPages() {
		if (focusTable == toDoTaskTable) {
			if (Controller.getFocusTask() == null) {
				((ToDoLogTableModel) focusTable.getModel()).goToPage(0);
				return;
			}
			Task focusTask = Controller.getFocusTask();
			//boolean found = false;
			for (int index = 0; index < toDoListItems.size(); index ++){
				Task task = toDoListItems.get(index);
				if (task == focusTask) {
					
					((ToDoLogTableModel) focusTable.getModel()).goToPage((index)/TABLE_PAGE_SIZE);
				//	found = true;

				}
			}
		} else {
			if (Controller.getFocusTask() == null) {
				((ToDoLogTableModel) focusTable.getModel()).goToPage(0);
				return;
			}
			Task focusTask = Controller.getFocusTask();
			//boolean found = false;
			for (int index = 0; index < floatingItems.size(); index ++){
				Task task = floatingItems.get(index);
				if (task == focusTask) {
					
					((ToDoLogTableModel) focusTable.getModel()).goToPage((index)/TABLE_PAGE_SIZE);
				//	found = true;

				}
			}
		}
		
	}
	
	//this method highlights the table which is in focus by the user. the table focused on
	// has a yellow border surrounding it
	private void setFocusTable(int tableIndex) {
		Border paddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		Border grayBorder = BorderFactory.createMatteBorder(2, 2, 0, 2, Color.GRAY);
		Border yellowBorder = BorderFactory.createMatteBorder(2, 2, 0, 2, new Color(241, 196, 15));
		Border compoundGrayBorder = BorderFactory.createCompoundBorder(grayBorder, paddingBorder);
		Border compoundYellowBorder = BorderFactory.createCompoundBorder(yellowBorder, paddingBorder);
		
		switch (tableIndex) {
			case TODOTASK_TABLE_INDEX: 
				
				floatingTaskPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, false));
				floatingTaskLabel.setBorder(compoundGrayBorder);
				focusTable = toDoTaskTable;
				toDoTaskPane.setBorder(BorderFactory.createLineBorder(new Color(241, 196, 15), 3, false));
				toDoTaskLabel.setBorder(compoundYellowBorder);
				break;
			case FLOATINGTASK_TABLE_INDEX: 
				toDoTaskPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3, false));
				toDoTaskLabel.setBorder(compoundGrayBorder);
				focusTable = floatingTaskTable;
				floatingTaskPane.setBorder(BorderFactory.createLineBorder(new Color(241, 196, 15), 3, false));
				floatingTaskLabel.setBorder(compoundYellowBorder);
				
				break;
			default: break;
		}
	}
	private void toggleFocusTable() {
		if (focusTable == toDoTaskTable) {
			setFocusTable(FLOATINGTASK_TABLE_INDEX);
		} else {
			setFocusTable(TODOTASK_TABLE_INDEX);
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
				helperText = UIFeedbackHelper.createCmdHelpText(entryHelper);
				switch (UIFeedbackHelper.getProcessingTaskType()) {
					case FLOATING:
						setFocusTable(FLOATINGTASK_TABLE_INDEX);
						break;
					case DEADLINE:
					case TIMED:
						setFocusTable(TODOTASK_TABLE_INDEX);
						break;
					default:
						break;		
				}
				flipPages();
			} 
			return helperText;
		}
	}
	
	private class CommandEntryTextFieldKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			//press the pageup and pagedown buttons to look through the pages of
			//the user's tasks
			if ((keyCode == KeyEvent.VK_PAGE_UP) || (keyCode == KeyEvent.VK_F9)){
				((ToDoLogTableModel) focusTable.getModel()).pageUp();
			}
			if ((keyCode == KeyEvent.VK_PAGE_DOWN) || (keyCode == KeyEvent.VK_F10)){
				((ToDoLogTableModel) focusTable.getModel()).pageDown();
			}
			
			//press the arrow keys to look through past user inputs
			if (keyCode == KeyEvent.VK_UP){
				try {
					commandEntryTextField.setText(Controller.getInput().getBackwards());
	//				Controller.getInput().goBackwards();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if (keyCode == KeyEvent.VK_DOWN){
				try {
					commandEntryTextField.setText(Controller.getInput().getForwards());
//					Controller.getInput().goForwards();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			//press tab to switch focus between the two tables
			if ((keyCode == KeyEvent.VK_TAB)) {
				toggleFocusTable();
				
			}
		}
	
		@Override
		public void keyReleased(KeyEvent e) {
			
		}
			
		@Override
		public void keyTyped(KeyEvent e){

		}
	}
	
	//the two mouseListener classes below enable the user to drag ToDoLog around 
	//the computer screen
	private class ScreenDraggingMouseListener implements MouseListener {
	
			@Override
			public void mousePressed(final MouseEvent e) {
				offset = new Point();
				offset.setLocation(e.getPoint());
			}
			
			@Override
			public void mouseReleased(final MouseEvent e){
				
			}
			
			@Override
			public void mouseClicked(final MouseEvent e){
				
			}
			
			@Override
			public void mouseEntered(final MouseEvent e){
				
			}
			
			@Override
			public void mouseExited(final MouseEvent e){
				
			}
			
		
	}
	private class ScreenDraggingMouseMotionListener implements MouseMotionListener{
        @Override
        public void mouseDragged(final MouseEvent e) {
            window.setLocation(e.getXOnScreen()-offset.x, e.getYOnScreen()-offset.y);
        }
		
        @Override
        public void mouseMoved(final MouseEvent e){
        	
        }
	}
	
	//listeners added to the minimize button and the close button to enable
	//functions such as minimize and exit respectively, also the button will change
	//its color when the mouse goes over it
	private class MinimizeButtonMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// do nothing
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			minimizeButton.setBackground(minimizeButton.getBackground().darker());
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			minimizeButton.setBackground(new Color(241, 196, 15, 255));
			repaint();
			
			window.setState(JFrame.ICONIFIED);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			minimizeButton.setBackground(minimizeButton.getBackground().brighter());
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			minimizeButton.setBackground(new  Color(241, 196, 15, 255));
			repaint();
		}

	

	}

	private class CloseButtonMouseListener implements MouseListener {
	
		@Override
		public void mouseClicked(MouseEvent e) {
			// do nothing
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			closeButton.setBackground(closeButton.getBackground().darker());
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			closeButton.setBackground(new Color(242, 38, 19, 255));
			repaint();
			window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			closeButton.setBackground(closeButton.getBackground().brighter());
			repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			closeButton.setBackground(new Color(242, 38, 19, 255));
			repaint();
		}

		

	}
	
	// a pop-up to tell the user that ToDoLog has been minimized
	private class ToDoLogWindowListener implements WindowListener{
		public void windowActivated(WindowEvent e) {
	
		}

		@Override
		public void windowClosed(WindowEvent e) {
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			if (!firstMinimize) {
				trayIcon.displayMessage("ToDoLog", 
					"ToDoLog is minimized. To open use combination ALT+B", TrayIcon.MessageType.INFO);
				firstMinimize = true;
			}
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			
		}
	}
	
	/*this method is to set up the parameters of the gridbagconstraints
	to put the different panels into the right positions on the JFrame
	here we use the constructor GridBagConstraints(gridx,gridy,gridwidth,gridheight,weightx,weighty,anchor,fill,insets,ipadx,ipady)
	*/
	private GridBagConstraints setParameters(int panelParameters){
		GridBagConstraints parameters;
		Insets topPanelInsets = new Insets(0,0,0,0);
		Insets bottomPanelInsets = new Insets(0,0,0,0);
		Insets buttonPanelInsets = new Insets(0,0,0,0);
		Insets iconInsets = new Insets(10,30,0,0);
		Insets clockInsets = new Insets(15,0,0,53);
		Insets toDoListHolderInsets = new Insets(10,0,0,0);
		Insets toDoTableInsets = new Insets(0,0,0,0);
		Insets floatingTasksTableInsets = new Insets(0,10,0,0);
		Insets commandEntryTextFieldInsets = new Insets(10,25,5,25);
		Insets dynamicHelpTextInsets = new Insets(10,25,10,20);
		Insets toDoTableLabelInsets= new Insets(0,10,0,220);
		Insets floatingTasksTableLabelInsets= new Insets(0,15,0,15);
		
		if(panelParameters == CLOCK_PARAMETERS){
			parameters = new GridBagConstraints(1,0,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,clockInsets,0,0);
			return parameters;
		}
		else if(panelParameters == TODOLIST_HOLDER_PARAMETERS){
			parameters = new GridBagConstraints(0,1,3,4,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,toDoListHolderInsets,0,0);
			return parameters;
		}
		else if(panelParameters == TOP_PANEL_PARAMETERS){
			parameters = new GridBagConstraints(0,0,1,1,0.1,0.0,GridBagConstraints.NORTHEAST,GridBagConstraints.BOTH,topPanelInsets,0,0);
			return parameters;
		}
		else if(panelParameters == BUTTON_PANEL_PARAMETERS){
			parameters = new GridBagConstraints(2,0,1,1,0.2,0.0,GridBagConstraints.NORTHEAST,GridBagConstraints.BOTH,buttonPanelInsets,0,0);
			return parameters;
		}
		else if(panelParameters == ICON_PARAMETERS){
			parameters = new GridBagConstraints(0,0,1,1,0.2,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,iconInsets,0,0);
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
		
		else if(panelParameters == TODOTABLE_PARAMETERS){
			parameters = new GridBagConstraints(0,1,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,toDoTableInsets,0,0);
			return parameters;
		} else if (panelParameters == FLOATINGTASKSTABLE_PARAMETERS) {
			parameters = new GridBagConstraints(1,1,1,1,0.0,0.0,GridBagConstraints.EAST,GridBagConstraints.BOTH,floatingTasksTableInsets,0,0);
			return parameters;
		} else if (panelParameters == TODOTABLE_LABEL_PARAMETERS) {
			
			parameters = new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.WEST,GridBagConstraints.BOTH,toDoTableLabelInsets,0,0);
			return parameters;
		} else if (panelParameters == FLOATINGTASKSTABLE_LABEL_PARAMETERS) {
			parameters = new GridBagConstraints(1,0,1,1,0.0,0.0,GridBagConstraints.EAST,GridBagConstraints.BOTH,floatingTasksTableLabelInsets,0,0);
			return parameters;
		}
		return null;
		
		
	} 
	
	/*	adjust and fix the width of the columns of the toDoTaskTable
	 */	
	private void adjustToDoTaskTableColumns(JTable toDoListTable){
		TableColumn tableColumn = null;
		
		for(int columnHeaders = 0; columnHeaders < 7;columnHeaders++){
			tableColumn = toDoListTable.getColumnModel().getColumn(columnHeaders);
			
			switch(columnHeaders){
			case 0:
				tableColumn.setPreferredWidth(30);
				break;
			case 1:
				tableColumn.setPreferredWidth(130);
				break;
			case 2:
				tableColumn.setPreferredWidth(150);
				break;
			case 3:
				tableColumn.setPreferredWidth(100);
				break;
			case 4:
				tableColumn.setPreferredWidth(90);
				break;
			case 5:
				tableColumn.setPreferredWidth(0);
				tableColumn.setMinWidth(0);
				tableColumn.setMaxWidth(0);
				break;
			case 6:
				tableColumn.setPreferredWidth(0);
				tableColumn.setMinWidth(0);
				tableColumn.setMaxWidth(0);
				tableColumn.setWidth(0);
				break;
			}
		}
	}
	
	//adjust the width of the floatingTaskTable's columns
	private void adjustFloatingTaskTableColumns(JTable floatingListTable){
		TableColumn tableColumn = null;
		
		for(int columnHeaders = 0; columnHeaders < 7;columnHeaders++){
			tableColumn = floatingListTable.getColumnModel().getColumn(columnHeaders);
			
			switch(columnHeaders){
			case 0:
				tableColumn.setPreferredWidth(30);
				break;
			case 1:
				tableColumn.setPreferredWidth(100);
				break;
			case 2:
				tableColumn.setPreferredWidth(0);
				tableColumn.setMinWidth(0);
				tableColumn.setMaxWidth(0);
				tableColumn.setWidth(0);
				break;
			case 3:
				tableColumn.setPreferredWidth(0);
				tableColumn.setMinWidth(0);
				tableColumn.setMaxWidth(0);
				tableColumn.setWidth(0);
				break;
			case 4:
				tableColumn.setPreferredWidth(0);
				tableColumn.setMinWidth(0);
				tableColumn.setMaxWidth(0);
				tableColumn.setWidth(0);
				break;
			case 5:
				tableColumn.setPreferredWidth(0);
				tableColumn.setMinWidth(0);
				tableColumn.setMaxWidth(0);
				tableColumn.setWidth(0);
				break;
			case 6:
				tableColumn.setPreferredWidth(0);
				tableColumn.setMinWidth(0);
				tableColumn.setMaxWidth(0);
				tableColumn.setWidth(0);
				break;
			}
		}
	}
	
	//a renderer to change the color of the rows of the table to show different priorities
	private void changeToDoTableColors(JTable toDoListTable, DefaultTableCellRenderer renderer){
		toDoListTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
		toDoListTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
		toDoListTable.getColumnModel().getColumn(2).setCellRenderer(renderer);
		toDoListTable.getColumnModel().getColumn(3).setCellRenderer(renderer);
		toDoListTable.getColumnModel().getColumn(4).setCellRenderer(renderer);	
	}
	
	private void changeFloatingTableColors(JTable toDoListTable, DefaultTableCellRenderer renderer){
		toDoListTable.getColumnModel().getColumn(0).setCellRenderer(renderer);
		toDoListTable.getColumnModel().getColumn(1).setCellRenderer(renderer);
	}
	
	private static void startReminderTimer(){
		final Timer timer = new Timer( 60000 ,
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						ReminderLogic one = new ReminderLogic();
						one.execute();
					}
				});
		timer.setInitialDelay(0);
		timer.start();
		
	}
}
