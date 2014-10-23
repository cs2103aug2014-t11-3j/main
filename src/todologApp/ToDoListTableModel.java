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
	
	private final static String[] columnNames = {"No.","Name","Time","Person / Venue","Done"};
	private LinkedList<Task> tableData;
	
	public ToDoListTableModel(LinkedList<Task> toDoListItems){
		tableData = toDoListItems;
	}
	
	public void setTableData(LinkedList<Task> toDoListItems){
		tableData = toDoListItems;
	}
	public int getColumnCount(){
		return columnNames.length;
	}
	
	public int getRowCount(){
		return tableData.size();
	}
	
	public String getColumnName(int col){
		return columnNames[col];
	}
	public Object getValueAt(int row,int col){

		Task task = tableData.get(row);
		
		if(task == null){
			return null;
		}
		
		switch(col){
		
		case 0:
			return row+1;
		
		case 1: 
			return task.getTaskName();
			
		case 2:
			switch (task.getTaskType()) {
				case FLOATING:
					return "-";
				case TIMED:
					return task.getStartDate()+"/"
							+ task.getStartMonth()+"/"
							+ task.getStartYear()+" " 
							+ task.getStartTimeStr() + " - " 
							+ task.getEndDate()+"/"
							+ task.getEndMonth()+"/"
							+ task.getEndYear()+" "
							+ task.getEndTimeStr();
				case DEADLINE:
					return "by "+ task.getEndDate()+"/"
					+ task.getEndMonth()+"/"
					+ task.getEndYear()+" "
					+ task.getEndTimeStr();
				case RECURRING:
					break;
				default:
					break;
			}
			
			
		case 3: {
			String col4 = "";
			if (!task.getTaskPerson().isEmpty()) {
				col4 = col4.concat("with ").concat(task.getTaskPerson().concat(" "));
			}
			if (!task.getTaskVenue().isEmpty()) {
				col4 = col4.concat("@").concat(task.getTaskVenue());
			}
			return col4;
		}
		case 4:
			return task.getTaskStatus();
			
		default:
			return null;
			
		}
	}
}
