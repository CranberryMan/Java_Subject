package 자기 패키지 이름 넣어주세용ㅇ
res 폴더를 만들고 word.txt 파일을 넣어주셍요

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class SanRain extends JFrame {
	Container c;
	Container d;
	Vector<String> word = new Vector<String>();
	Vector<String> answ = new Vector<String>();
	int size;
	Font font = new Font("궁서체", Font.BOLD, 20);
	Random r = new Random();
	int score = 0;
	int speed = 1000;
	int hp = 5;

	SanRain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 700);
		setLocationRelativeTo(null);
		System.out.println();

		c = getContentPane();

		add(new MainPanel());
		add(new TextFi(), BorderLayout.SOUTH);

		setVisible(true);
	}

	class MainPanel extends JPanel {
		MainPanel() {
			new InputStream();
			setLayout(null);
			d = this;
			setBackground(Color.WHITE);
			Thread make = new Thread(new InfMT());
			Thread sco = new Thread(new Score(make));
			sco.start();
			make.start();
		}
	}

	class TextFi extends JPanel {
		TextFi() {
			setBackground(Color.getHSBColor(25, 56, 53));
			JTextField input = new JTextField(20);
			input.addActionListener(new MyAction());
			add(input);
		}
	}

	class InfMT implements Runnable {
		@Override
		public void run() {
			while (true) {
				Thread make = new Thread(new MakeText());
				make.start();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class MakeText implements Runnable {
		int make = 0;
		JLabel txt;
		int number = -1;

		@Override
		public void run() {
			while (true) {
				if (make == 0) {
					txt = new JLabel(word.get(r.nextInt(size)));
					txt.setFont(font);
					txt.setBounds(r.nextInt(350), r.nextInt(20), 250, 50);
					d.add(txt);
					make = 1;
					answ.add(txt.getText());
					this.number = answ.size() - 1;
				}

				if (answ.get(number).equals("")) {
					txt.setText("");
					repaint();
					break;
				}
				txt.setLocation(txt.getX(), txt.getY() + 10);
				repaint();
				if (txt.getY() > 550) {
					txt.setText("");
					repaint();
					hp--;
					break;
				}
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Score implements Runnable {
		Thread make;

		Score(Thread make) {
			this.make = make;
		}

		@Override
		public void run() {
			JLabel sco = new JLabel("점수 : 0 체력 : 5");
			Font fon = new Font("휴먼매직체", Font.BOLD, 40);
			sco.setForeground(Color.blue);
			sco.setFont(fon);
			sco.setBounds(20, 0, 350, 50);
			d.add(sco);
			while (true) {
				sco.setText("점수 : " + score + " 체력 : " + hp);
				if (score == 5) {
					speed = 600;
				} else if (score == 10) {
					speed = 300;
				} else if (score == 15) {
					speed = 100;
				} else if (score == 20) {
					speed = 10;
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (hp == 0) {
					d.removeAll();
					d.repaint();
					JLabel end = new JLabel("끝~ 당신의 점수는 " + score + "점");
					end.setBounds(d.getWidth() / 2 - 100, d.getHeight() / 2, 150, 50);
					d.add(end);
					make.stop();
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		new SanRain();
	}

	class MyAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JTextField imsi = (JTextField) e.getSource();
			if (e.getActionCommand().equals("")) {

			} else {
				for (int i = 0; i < answ.size(); i++) {
					if (answ.get(i).equals(e.getActionCommand())) {
						answ.set(i, "");
						imsi.setText("");
						score++;
						break;
					} else {
						imsi.setText("");
					}
				}
			}
		}
	}

	class InputStream {
		public InputStream() {
			String path = "./res/word.txt";
			File file = new File(path);
			if (file.isFile()) {
				BufferedReader br = null;
				try {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
					String s;
					while ((s = br.readLine()) != null) {
						word.add(s);
						size++;
					}
					br.close();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}