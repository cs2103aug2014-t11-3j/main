package todologApp;

import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.*;

public class CustomRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component cellComponent = super.getTableCellRendererComponent(table,
				value, isSelected, hasFocus, row, column);

		if (((String) table.getValueAt(row, 3)).equalsIgnoreCase("High")) {
			cellComponent.setBackground(Color.RED);
		}

		else if (((String) table.getValueAt(row, 3)).equalsIgnoreCase("Medium")) {
			cellComponent.setBackground(Color.PINK);
		}
		else if (((Boolean) table.getValueAt(row, 4)) == true) {
			JPanel color = new JPanel()
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
			((JComponent) cellComponent).setBackground(new Color(0,255,0,170));
			((JComponent) cellComponent).setOpaque(true);
			((JComponent) cellComponent).add(color);
		}
		else {
			((JComponent) cellComponent).setOpaque(false);
		}

		return cellComponent;
	}
}
