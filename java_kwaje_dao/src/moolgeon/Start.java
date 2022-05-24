package moolgeon;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Start extends JFrame {
	TopPannel top = new TopPannel();
	AdminTopPannel topAdmin = new AdminTopPannel();
	Dao dao = new Dao();
	JTextField idTf;
	JTextField pwTf;
	Container c;
	JFrame main;
	int isAdmin=-1;
	Start(){
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		c = getContentPane();
		// 여기부터
		
//		main.add(new TopMenu(), BorderLayout.NORTH);
//		main.add(new Login());
		viewScreen(new Login());
		
		
		// 여기까지 // 수정되나 테스트
		
		setVisible(true);
	}
	
	void viewScreen(JPanel p) {
		c.removeAll();
		add(p);
		if(isAdmin == 0) {
			add(top, BorderLayout.NORTH);
		}		
		else if(isAdmin == 1) {
			add(topAdmin, BorderLayout.NORTH);
		}
		c.revalidate();
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
				isAdmin = dao.login(id, pw);
				if(isAdmin == 0) {
					System.out.println("회원");
					LoginUser.id = id;
					viewScreen(new LoginInfoPanel(isAdmin, id));
				}
				else if(isAdmin == 1) {
					System.out.println("어드민");
					LoginUser.id = id;
					viewScreen(new LoginInfoPanel(isAdmin, id));
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

