package todologApp;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.*;

public class CustomRenderer extends DefaultTableCellRenderer {
	private static final int NOT_DEADLINE = -1;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component cellComponent = super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);

		if (((String) table.getValueAt(row, 4)).equalsIgnoreCase("done")) {
			colorCell(cellComponent,new Color(46, 204, 113, 30));
		} else {
			int duePeriod = (int) table.getValueAt(row, 5);
			colorCell(cellComponent,new Color(231, 76, 60,computeAlpha(duePeriod)));
		} 
		if ((Boolean) table.getValueAt(row, 6) == true) {
			highlightCell(cellComponent);
		}
		return cellComponent;
	}
	private void highlightCell(Component cellComponent) {
		//Color color = Color.YELLOW;
		JPanel highlightCell = new JPanel()
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
		((JComponent) cellComponent).setBackground(new Color(255,255,0,200));
		((JComponent) cellComponent).setOpaque(true);
		((JComponent) cellComponent).add(highlightCell);
		
	}
	private int computeAlpha(int duePeriod) {
		if (duePeriod == NOT_DEADLINE) {
			return 0;
		}
		if (duePeriod > 4) {
			return 20;
		} 
		return 200-duePeriod*40;
		
	}
	private void colorCell(Component cellComponent, Color color) {
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
		((JComponent) cellComponent).setBackground(color);
		((JComponent) cellComponent).setOpaque(true);
		((JComponent) cellComponent).add(colorCell);
	}
}
