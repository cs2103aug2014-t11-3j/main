package todologApp;

//import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.*;

public class CustomRenderer extends DefaultTableCellRenderer {
	private static final int OVERDUE = -1;
	private static final int NOT_DEADLINE = Integer.MIN_VALUE;
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
			colorCell(cellComponent,computeColor(duePeriod));
		} 
		if ((Boolean) table.getValueAt(row, 6) == true) {
			highlightCell(cellComponent);
		}
		return cellComponent;
	}
	private Color computeColor(int duePeriod) {
		Color original = new Color(231, 76, 60, 240);
		int red = original.getRed();
		int green = original.getGreen();
		int blue = original.getBlue();
		if (duePeriod != NOT_DEADLINE) {
			red = (red + 255*(duePeriod+1))/(duePeriod+2);
			green = (green + 255*(duePeriod+1))/(duePeriod+2);
			blue = (blue + 255*(duePeriod+1))/(duePeriod+2); 
		} else {
			return new Color(255, 255, 255, 220);
		}
		return new Color(red, green, blue, 220);
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
		if (duePeriod == OVERDUE) {
			return 240;
		}
		if (duePeriod > 6) {
			return 0;
		} 
		return 210-duePeriod*30;
		
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
