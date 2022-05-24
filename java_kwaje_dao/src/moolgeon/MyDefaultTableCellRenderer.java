package moolgeon;

import java.awt.Component;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MyDefaultTableCellRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component comp = null;
		if (column == 1) {
			comp = new JTextField();

		}
		return comp;
	}
}