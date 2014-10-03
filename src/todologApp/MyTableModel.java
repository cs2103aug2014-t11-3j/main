package todologApp;


import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

import java.util.*;
import java.lang.Object;

public class MyTableModel extends AbstractTableModel{
	
	private String[] columnNames = {"No.","Name","Time","Category","Remarks"};
	private LinkedList<String> tableData;
	
	public MyTableModel(LinkedList<String> currentToDoLogData){
		
	}
	
	public int getColumnCount(){
		return columnNames.length;
	}
	
	public int getRowCount(){
		return 0;
		
	}
	
	public Object getValueAt(int row,int col){
		return col;
		
	}
}
