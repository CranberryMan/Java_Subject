package moolgeon;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Start {
	Dao dao = new Dao();
	JTextField idTf;
	JTextField pwTf;

	Start(){
		JFrame main = new JFrame();
		main.setSize(800,600);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setLocationRelativeTo(null);
		
		// 여기부터
		
//		main.add(new TopMenu(), BorderLayout.NORTH);
		main.add(new Login());
		
		
		// 여기까지 // 수정되나 테스트
		
		main.setVisible(true);
	}
	
	class Login extends JPanel{
		Login(){
			setLayout(null);
			Font font = new Font("궁서체", Font.BOLD, 20);
			JLabel id = new JLabel("아이디");
			JLabel pw = new JLabel("비밀번호");
			idTf = new JTextField(40);
			pwTf = new JTextField(40);
			JButton login = new JButton("로그인");
			JButton gaip = new JButton("회원가입");
			JButton exit = new JButton("종료");
			
			id.setFont(font);
			pw.setFont(font);
			login.setFont(font);
			gaip.setFont(font);
			exit.setFont(font);
			
			id.setBounds(240,140,100,100);
			pw.setBounds(240,170,100,100);
			idTf.setBounds(350,175,250,30);
			pwTf.setBounds(350,205,250,30);
			login.setBounds(200,300,150,50);
			gaip.setBounds(350,300,150,50);
			exit.setBounds(500,300,150,50);
			
			// 이벤트리스너 연결 //
			LoginButton loginBtn = new LoginButton();
			login.addActionListener(loginBtn);
			gaip.addActionListener(loginBtn);
			exit.addActionListener(loginBtn);

			// 애드
			
			add(id);
			add(pw);
			add(idTf);
			add(pwTf);
			add(login);
			add(gaip);
			add(exit);
			
			// 삭제 //
			JLabel imsi = new JLabel("이 로그인 창은 임시입니다");
			imsi.setFont(font);
			imsi.setBounds(240, 100, 500, 50);
			add(imsi);			
		}
	}
	
	class TopMenu extends JPanel {
		TopMenu(int admin){
			if (admin == 0) {
				setLayout(new GridLayout(1, 3, 5, 5));
				Font font = new Font("궁서체", Font.BOLD, 20);
				JButton buy = new JButton("구매");
				JButton refund = new JButton("환불");
				JButton logout = new JButton("로그아웃");
				buy.setFont(font);
				refund.setFont(font);
				logout.setFont(font);
				add(buy);
				add(refund);
				add(logout);
			}
			else {
				
			}
		}
	}
	
	


	public static void main(String[] args) {
		new Start();
	}
	
	// 이벤트 처리
	class LoginButton implements ActionListener {
		String id, pw;
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton imsi = (JButton) e.getSource();
			if(imsi.getText().equals("종료")) {
				System.exit(0);
			}
			else if(imsi.getText().equals("로그인")) {
				System.out.println("여기 로그인처리");
				id = idTf.getText();
				pw = pwTf.getText();
				int isAdmin = dao.login(id, pw);
				if(isAdmin == 0) {
					System.out.println("회원");
				}
				else if(isAdmin == 1) {
					System.out.println("어드민");
				}
				else {
					System.out.println("??");
				}
				
			}
			else if(imsi.getText().equals("회원가입")) {
				System.out.println("여기 회원가입처리");
			}
		}
	}
	
}

