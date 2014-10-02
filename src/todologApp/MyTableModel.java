
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.util.*;
import java.lang.Object;

public class ToDoListTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"No.","Name","Time","Category","Remarks"};
	private LinkedList<E> tableData;
	
	public ToDoListTableModel(LinkedList<E> currentToDoLogData){
		
	}
	
	public int getColumnCount(){
		return columnNames.length;
	}
	
	public int getRowCount(){
		
	}
	
	public Object getValueAt(int row,int col){
		
	}
}
