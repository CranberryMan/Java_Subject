package moolgeon;
import javax.swing.*;
public class LoginInfoPanel extends JPanel{
	Dao dao = new Dao();
	LoginInfoPanel(int isAdmin, String id){
		if (isAdmin == 1) {
			System.out.println("???");
			JLabel welcome = new JLabel("환영합니다 관리자모드입니다.");
			add(welcome);
		}
		
		else if(isAdmin == 0) {
			int rank = dao.getRank(id);
			String rankName = "일반";
			if (rank == 1) {
				rankName = "일반회원";
			}
			JLabel welcome = new JLabel(id + "님의 방문을 환영합니다.");
			JLabel welcome2 = new JLabel(id + "님은 " + rankName + "입니다.");
			add(welcome);
			add(welcome2);
		}
	}
}