package todologApp;

/*import java.awt.*;
 import java.awt.event.*;
 import java.util.Vector;

 import javax.swing.*;
 import javax.swing.border.*;*/
import javax.swing.table.*;

import java.util.*;
import java.lang.Object;

<<<<<<< HEAD:src/todologApp/ToDoListTableModel.java
public class ToDoListTableModel extends AbstractTableModel{
	
=======
public class MyTableModel extends AbstractTableModel {
>>>>>>> origin/vani's_branch:src/todologApp/MyTableModel.java

	/**
	 * 
	 */
	private static final long serialVersionUID = -4665613032022419075L;
	/**
	 * 
	 */
<<<<<<< HEAD:src/todologApp/ToDoListTableModel.java
	
	private final static String[] columnNames = {"No.","Name","Time","Category","Venue","Who"};
	private LinkedList<Task> tableData;
	
	public ToDoListTableModel(LinkedList<Task> toDoListItems){
=======

	private final static String[] columnNames = { "No.", "Name", "Time",
			"Category", "Done" };
	private LinkedList<Task> tableData;

	public MyTableModel(LinkedList<Task> toDoListItems) {
>>>>>>> origin/vani's_branch:src/todologApp/MyTableModel.java
		tableData = toDoListItems;
	}

	public void setTableData(LinkedList<Task> toDoListItems) {
		tableData = toDoListItems;
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		return tableData.size();
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int row, int col) {

		Task task = tableData.get(row);

		if (task == null) {
			return null;
		}

		switch (col) {

		case 0:
			return row + 1;

		case 1:
			return task.getTaskName();

		case 2:
			switch (task.getTaskType()) {
			case FLOATING:
				return "-";
			case TIMED:
				return task.getStartDay() + " " + task.getStartTime() + " - "
						+ task.getEndDay() + " " + task.getEndTime();
			case DEADLINE:
				return "by " + task.getEndDay() + " " + task.getEndTime();
			}

		case 3:
			return "nil";

		case 4:
<<<<<<< HEAD:src/todologApp/ToDoListTableModel.java
			//here must return the venue of the task
			
		case 5:
			//here must return the person value of the task
			
=======
			return task.getTaskStatus();

>>>>>>> origin/vani's_branch:src/todologApp/MyTableModel.java
		default:
			return null;

		}
	}
}
