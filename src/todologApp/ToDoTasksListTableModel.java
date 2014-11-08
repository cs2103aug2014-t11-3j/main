package todologApp;


/*import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;*/
import javax.swing.table.*;

import java.util.*;
import java.lang.Object;

public class ToDoTasksListTableModel extends AbstractTableModel implements ToDoLogTableModel{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4665613032022419075L;
	/**
	 * 
	 */
	
	private final static String[] columnNames = {"No.","Name","Time","Person / Venue","Status","<hidden>","<hidden>"};
	private LinkedList<Task> tableData;
	private final static int pageSize = 17;
	//private static final int NOT_DEADLINE = -1;
	private int pageOffSet = 0;
	
	public ToDoTasksListTableModel(LinkedList<Task> toDoListItems){
		tableData = toDoListItems;
	}
	
	public void setTableData(LinkedList<Task> toDoListItems){
		tableData = toDoListItems;
	}
	public int getColumnCount(){
		return columnNames.length;
	}
	
	
	public int getRowCount(){
		//return tableData.size();
		if(pageOffSet == getPageCount() -1){  
			if(tableData.size() % pageSize == 0){
				return pageSize;
			}
			
			else{
				return tableData.size() % pageSize;
			}
		}
		return Math.min(pageSize, tableData.size());
	}
	
	public int getActualRowCount(){
		return tableData.size();
	}
	
	public int getPageSize(){
		return pageSize;
	}
	public String getColumnName(int col){
		return columnNames[col];
	}
	public Object getValueAt(int row,int col){
		
		int actualRow = row + (pageOffSet * pageSize);
		Task task = tableData.get(actualRow);
		
		if(task == null){
			return null;
		}
		
		switch(col){
		
		case 0:
			return actualRow+1;
		
		case 1: 
			return task.getTaskName();
			
		case 2:
			switch (task.getTaskType()) {
				case FLOATING:
					return "-";
				case TIMED:
					return String.format("%02d",task.getStartDate())+"/"
							+ task.getStartMonth()+" " 
							+ task.getStartTimeStr() + " - " 
							+ String.format("%02d",task.getEndDate())+"/"
							+ task.getEndMonth()+" "
							+ task.getEndTimeStr();
				case DEADLINE:
					return String.format("%02d",task.getEndDate())+"/"
					+ task.getEndMonth()+" "
					+ task.getEndTimeStr();
				case RECURRING:
					break;
				default:
					break;
			}
			
			
		case 3: {
			String col4 = "";
			if (!task.getTaskPerson().isEmpty()) {
				col4 = col4.concat(task.getTaskPerson().concat(" "));
			}
			if (!task.getTaskVenue().isEmpty()) {
				col4 = col4.concat("@ ").concat(task.getTaskVenue());
			}
			return col4;
		}
		case 4:
			if(task.getTaskStatus() == true){
				return "Done";
			}
			else{
				int duePeriod = task.duePeriod();
				
				if (task.getTaskType() == TaskType.DEADLINE) {
					if (duePeriod == -1){
						return "Overdue!";
					} else if (duePeriod == 0) {
						return "Due today";
					} else if (duePeriod == 1) {
						return "Due tomorrow";
					} else if (duePeriod > 7) {
						return "Due Later";
					} else {
						return String.format("Due in %d days", duePeriod);
					}
				} else if (task.getTaskType() == TaskType.TIMED){
					if (duePeriod == -1){
						return "Past event";
					} else if (duePeriod == 0) {
						return "Today";
					} else if (duePeriod == 1) {
						return "Tomorrow";
					} else if (duePeriod > 7) {
						return "Later";
					} else {
						return String.format("In %d days", duePeriod);
					}
				}
				return "";	
			} 
		case 5:
			return task.duePeriod();
		case 6:
			if (task == Controller.getFocusTask()) {
				return true;
			}
			else {
				return false;
			}
		default:
			return null;
			
		}
	}
	
	public int getPageOffSet(){
		return pageOffSet;
	}
	
	public int getPageCount() {
	    return (int) Math.ceil((double) tableData.size() / pageSize);
	}
	
	public void setPageOffSet(int input){
		pageOffSet = input;
	}
	
	 public void pageDown() {
		    if (pageOffSet < getPageCount() - 1) {
		      pageOffSet++;
		      fireTableDataChanged();
		    }
		  }

	 public void pageUp() {
		    if (pageOffSet > 0) {
		      pageOffSet--;
		      fireTableDataChanged();
		    }
		  }

	public void goToPage(int page) {
		pageOffSet = page;
		fireTableDataChanged();
		
	}
}
