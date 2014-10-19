package todologApp;

/*import java.awt.*;
 import java.awt.event.*;
 import java.util.Vector;

 import javax.swing.*;
 import javax.swing.border.*;*/
import javax.swing.table.*;

import java.util.*;
import java.lang.Object;

public class ToDoListTableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4665613032022419075L;
	/**
	 * 
	 */
	private final static String[] columnNames = {"No.","Name","Time","Category","Venue","Who"};
	private LinkedList<Task> tableData;
	
	public ToDoListTableModel(LinkedList<Task> toDoListItems){



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

			return task.getTaskVenue();
			
		case 5:
			return task.getTaskPerson();
			

		default:
			return null;

		}
	}
}
