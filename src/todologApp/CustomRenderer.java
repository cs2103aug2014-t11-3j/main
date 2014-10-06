import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.*;

public class CustomRenderer extends DefaultTableCellRenderer{
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        if(((String) table.getValueAt(row, 3)).equalsIgnoreCase("High")){
        	cellComponent.setBackground(Color.RED);
        }
        
        else if(((String) table.getValueAt(row,3)).equalsIgnoreCase("Medium")){
        	cellComponent.setBackground(Color.PINK);
        }
        
        else{
        	cellComponent.setBackground(Color.WHITE);
        }
        
        return cellComponent;
    }
}
