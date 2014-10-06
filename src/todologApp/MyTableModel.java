package todologApp;


/*import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;*/
import javax.swing.table.*;

import java.util.*;
import java.lang.Object;

public class MyTableModel extends AbstractTableModel{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4665613032022419075L;
	/**
	 * 
	 */
	
	private final static String[] columnNames = {"No.","Name","Time","Category","Remarks"};
	private LinkedList<Task> tableData;
	
	public MyTableModel(LinkedList<Task> toDoListItems){
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
			return task.getStartTime();
			
		case 3:
			return "Nothing";
			
		case 4:
			return task.getTaskStatus();
			
		default:
			return null;
			
		}
	}
}
