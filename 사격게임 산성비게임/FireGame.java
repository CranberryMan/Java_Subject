package 자기 패키지 입력하세용.
res 폴더 만들고 bullet, gun, tgt 넣으세용

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FireGame extends JFrame {
	Container c;
	JLabel tgt;
	JLabel gun;
	JLabel blt;
	int score;
	int speed = 4;
	Boolean fire = false;
	tgtSize size;
	Boolean heat = false;
	JLabel scr = new JLabel("???");

	FireGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLayout(null);
		setLocationRelativeTo(null);

		c = getContentPane();
		c.setBackground(Color.white);

		ImageIcon i1 = new ImageIcon("./res/tgt.jpg");
		ImageIcon i2 = new ImageIcon("./res/gun.jpg");
		ImageIcon i3 = new ImageIcon("./res/bullet.jpg");

		tgt = new JLabel(i1);
		gun = new JLabel(i2);
		blt = new JLabel(i3);
		tgt.setBounds(0, 0, 100, 100);
		size = new tgtSize(0, 100);
		gun.setBounds(340, 470, 100, 100);
		blt.setBounds(330, 420, 100, 100);

		add(tgt);
		add(gun);
		add(blt);
		Font font = new Font("궁서체", Font.BOLD, 30);
		scr.setFont(font);
		scr.setBounds(550, 400, 200, 200);
		add(scr);

		Thread thr1 = new Thread(new ThrTht());
		Thread thr2 = new Thread(new ThrGun());
		Thread thr3 = new Thread(new ThrBlt());
		Thread thr4 = new Thread(new Score());
		c.addMouseListener(new MouseMy());
		c.addKeyListener(new KeyMy());

		thr1.start();
		thr2.start();
		thr3.start();
		thr4.start();

		setVisible(true);
	}

	class ThrTht implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(10);
					tgt.setLocation(tgt.getX() + speed, tgt.getY());
					size.setSize(tgt.getX(), tgt.getX() + 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (tgt.getX() > 800) {
					tgt.setLocation(-100, tgt.getY());
				}
			}
		}
	}
	
	class Score implements Runnable {
		@Override
		public void run() {
			while (true) {
				scr.setText("SCORE : " + Integer.toString(score));
				
				scr.repaint();
			}
		}
	}

	class ThrGun implements Runnable {
		@Override
		public void run() {
			while (true) {
//				System.out.println("??");
				if (heat == true) {
					System.out.println("명중판정");
					tgt.setBounds(0, 0, 100, 100);
					blt.setBounds(330, 420, 100, 100);
					fire = false;
					heat = false;
					score += 1;
					speed +=2;

				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}
	}

	class ThrBlt implements Runnable {
		@Override
		public void run() {
			while (true) {
//				System.out.println("?");
				if (fire == true) {
					try {
						Thread.sleep(10);
						blt.setLocation(blt.getX(), blt.getY() - 10);

						if (blt.getY() < 80) {
							if (size.x > 280 && size.x < 380) {
								System.out.println("명중");
								heat = true;
							}
						}

						if (blt.getY() < -30) {
							blt.setLocation(330, 420);
							fire = false;
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class tgtSize {
		int x;
		int y;

		tgtSize(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void setSize(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	class MouseMy extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			if (fire == false) {
				fire = true;
				System.out.println(fire);
			}

		}
	}
	
	class KeyMy implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println(e.getExtendedKeyCode());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public static void main(String[] args) {
		new FireGame();
	}

}
