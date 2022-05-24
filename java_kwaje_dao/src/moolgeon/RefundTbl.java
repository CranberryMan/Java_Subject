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

public class RefundTbl extends JPanel {
	private ArrayList<BuyListMoklok> list;
	private ArrayList<CheckField> checkList = new ArrayList<>();
	JScrollPane scroll;
	private Vector<String> tableTitle;
	private DefaultTableModel model;
	private JTable table;
	JTextField[] jtf;
	Dao dao = new Dao();

	RefundTbl() {
		setLayout(new BorderLayout());
		BuyList buy = new BuyList();
		add(buy, BorderLayout.CENTER);
	}

	public void rep() {
		this.repaint();
		this.revalidate();
	}

	class BuyList extends JPanel {
		BuyList() {
			setLayout(new GridLayout(1, 1));
			list = dao.getBuyTbl(LoginUser.id);
			setBackground(Color.BLACK);
			tableTitle = new Vector<String>();
			tableTitle.addElement("구매번호");
			tableTitle.add("상품명");
			tableTitle.add("구매량");
			tableTitle.add("구매가격");
			tableTitle.add("환불");
			model = new DefaultTableModel(tableTitle, 0);
			table = new JTable(model);
			table.getColumnModel().getColumn(4).setCellRenderer(new TableCell());
			;
			table.getColumnModel().getColumn(4).setCellEditor(new TableCell());
			;

			scroll = new JScrollPane(table);

			for (BuyListMoklok data : list) {
				Vector<String> imsi = new Vector<String>();
				imsi.add(Integer.toString(data.getBuyNo()));
				imsi.add(data.getProduct());
				imsi.add(Integer.toString(data.getStock()));
				imsi.add(Double.toString(data.getPrice()));
				imsi.add("환불");
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
			jb = new JButton("환불하기");
			jb.addActionListener(e -> {
				int real = JOptionPane.showConfirmDialog(null, "정말 환불할꺼야?", "Confirm", JOptionPane.YES_NO_OPTION);
				if(real == JOptionPane.CLOSED_OPTION) {

				}
				else if(real == JOptionPane.YES_OPTION) {
					int buyNo = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
					String product = (String) table.getValueAt(table.getSelectedRow(), 1);
					int stock = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 2));
					if (Double.parseDouble((String) table.getValueAt(table.getSelectedRow(), 3)) != 0) {
						dao.refund(buyNo, stock, product);
						JButton imsi = (JButton) e.getSource();
						Start imsi2 = (Start)imsi.getTopLevelAncestor();
						imsi2.viewScreen(new RefundTbl());
					}
					else{
						JOptionPane.showMessageDialog(null, "이미 환불한 상품입니다!", "경고입니다",JOptionPane.ERROR_MESSAGE);

					}
					
				}
				else {
					
				}
//				String product = (String) table.getValueAt(table.getSelectedRow(), 0);
//				double price = Double.parseDouble((String) table.getValueAt(table.getSelectedRow(), 2));
//				price = price * Integer.parseInt(amount);
//				System.out.println(amount + " / " + product + " / " + price);
//
//				int stock = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 1));
//				if (Integer.parseInt(amount) > stock) {
//					System.out.println("구매불가");
//				} else if (Integer.parseInt(amount) < stock) {
//					stock = stock - Integer.parseInt(amount);
//					dao.buyProcess(LoginUser.id, product, Integer.parseInt(amount), price, stock);

//				}

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
class BuyListMoklok{
	int buyNo;
	String id;
	String product;
	int stock;
	Double price;
	BuyListMoklok(int buyNo, String id, String product, int stock, Double price){
		this.buyNo = buyNo;
		this.id = id;
		this.product = product;
		this.stock = stock;
		this.price = price;
	}
	public int getBuyNo() {
		return this.buyNo;
	}
	public String getId() {
		return this.id;
	}
	public String getProduct() {
		return this.product;
	}
	public int getStock() {
		return this.stock;
	}
	public Double getPrice() {
		return this.price;
	}
	
}