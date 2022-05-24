package moolgeon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import java.util.*;

public class BuyTbl extends JPanel {
	private ArrayList<Product> list;
	private ArrayList<CheckField> checkList = new ArrayList<>();
	JScrollPane scroll;
	private Vector<String> tableTitle;
	private DefaultTableModel model;
	private JTable table;
	JTextField[] jtf;
	Dao dao = new Dao();

	BuyTbl() {
		setLayout(new BorderLayout());
		BuyTblScroll buy = new BuyTblScroll();
		add(buy, BorderLayout.CENTER);
	}

	public void rep() {
		this.repaint();
		this.revalidate();
	}

	class BuyTblScroll extends JPanel {
		BuyTblScroll() {
			setLayout(new GridLayout(1, 1));
			list = dao.getProductTbl();
			setBackground(Color.BLACK);
			tableTitle = new Vector<String>();
			tableTitle.addElement("상품명");
			tableTitle.add("재고");
			tableTitle.add("가격");
			tableTitle.add("구매");
			model = new DefaultTableModel(tableTitle, 0);
			table = new JTable(model);
			table.getColumnModel().getColumn(3).setCellRenderer(new TableCell());
			;
			table.getColumnModel().getColumn(3).setCellEditor(new TableCell());
			;

			scroll = new JScrollPane(table);

			for (Product data : list) {
				Vector<String> imsi = new Vector<String>();
				imsi.add(data.getProduct());
				imsi.add(Integer.toString(data.getStock()));
				imsi.add(Integer.toString(data.getPrice()));
				imsi.add("구매");
				model.addRow(imsi);
			}

			add(scroll);
		}

	}

	class ConfirmList extends JPanel {
		ConfirmList() {
			JButton btn1 = new JButton("일단크기");
			add(btn1);
		}
	}

	class TableCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		JButton jb;

		public TableCell() {
			jb = new JButton("구매하기");
			jb.addActionListener(e -> {
				String amount = JOptionPane.showInputDialog("몇 개 살꺼야?");
				if(amount != null) {
					String product = (String) table.getValueAt(table.getSelectedRow(), 0);
					double price = Double.parseDouble((String) table.getValueAt(table.getSelectedRow(), 2));
					price = price * Integer.parseInt(amount);
					System.out.println(amount + " / " + product + " / " + price);

					int stock = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 1));
					if (Integer.parseInt(amount) > stock) {
						System.out.println("구매불가");
						JOptionPane.showMessageDialog(null, "수량을 잘 확인하세요", "경고입니다",JOptionPane.ERROR_MESSAGE);
					} else if (Integer.parseInt(amount) < stock) {
						stock = stock - Integer.parseInt(amount);
						dao.buyProcess(LoginUser.id, product, Integer.parseInt(amount), price, stock);
						JButton imsi = (JButton) e.getSource();
						Start imsi2 = (Start)imsi.getTopLevelAncestor();
						imsi2.viewScreen(new BuyTbl());
					}
				}

			});
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return jb;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			return jb;
		}
	}
}

class Product {
	String product;
	int stock;
	int price;

	Product(String p, int s, int pri) {
		this.product = p;
		this.stock = s;
		this.price = pri;
	}

	public String getProduct() {
		return product;
	}

	public int getStock() {
		return stock;
	}

	public int getPrice() {
		return price;
	}
}

class CheckField {
	JTextField jtf;
	JButton jbt;

	CheckField(JTextField jtf, JButton jbt) {
		this.jtf = jtf;
		this.jbt = jbt;
	}

	public JTextField getJtf() {
		return this.jtf;
	}

	public JButton getJbt() {
		return this.jbt;
	}
}