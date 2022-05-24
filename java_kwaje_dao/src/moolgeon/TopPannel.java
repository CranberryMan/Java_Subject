package moolgeon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TopPannel extends JPanel {
	TopPannel() {
		setLayout(new GridLayout(1, 3, 5, 5));
		Font font = new Font("궁서체", Font.BOLD, 20);
		JButton buy = new JButton("구매");
		JButton refund = new JButton("환불");
		JButton logout = new JButton("로그아웃");
		BtnAction btn = new BtnAction();
		buy.addActionListener(btn);
		refund.addActionListener(btn);
		logout.addActionListener(btn);
		buy.setFont(font);
		refund.setFont(font);
		logout.setFont(font);
		add(buy);
		add(refund);
		add(logout);
	}
}

class AdminTopPannel extends JPanel {
	AdminTopPannel() {
		setLayout(new GridLayout(1, 3, 5, 5));
		Font font = new Font("궁서체", Font.BOLD, 20);
		JButton addProduct = new JButton("물건추가");
		JButton delProduct = new JButton("물건삭제");
		JButton ranking = new JButton("매출순위");
		JButton member = new JButton("회원관리");
		addProduct.setFont(font);
		delProduct.setFont(font);
		ranking.setFont(font);
		member.setFont(font);
		
		BtnAction btn = new BtnAction();
		addProduct.addActionListener(btn);
		delProduct.addActionListener(btn);
		ranking.addActionListener(btn);
		member.addActionListener(btn);

		add(addProduct);
		add(delProduct);
		add(ranking);
		add(member);

	}
}

class BtnAction implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton imsi = (JButton) e.getSource();
		Start imsi2 = (Start)imsi.getTopLevelAncestor();
		switch(e.getActionCommand()) {
		case "구매" : imsi2.viewScreen(new BuyTbl());break;
		case "환불" : imsi2.viewScreen(new RefundTbl());break;
//		case "매출순위" : imsi2.viewScreen(new ImsiPanel());break;
		case "로그아웃" : imsi2.viewScreen(new ImsiPanel());break;
		case "물건추가" : imsi2.viewScreen(new ImsiPanel());break;
		case "물건삭제" : imsi2.viewScreen(new ImsiPanel());break;
		case "매출순위" : imsi2.viewScreen(new ImsiPanel());break;
		case "회원관리" : imsi2.viewScreen(new ImsiPanel());break;
		}
	}
	
}