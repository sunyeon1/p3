package TcpChatting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
// 강사님 6월 30일 채팅 GUI강의내용 참조

public class KajaClientGUI extends JFrame implements Runnable,ActionListener{

	DataOutputStream OutputStream;
	DataInputStream InputStream;
	String nickname;
	

	JLabel jlabel1 = new JLabel("\uB2E8\uD1A1\uBC29");
	JTextArea jtareal1 = new JTextArea();
	JTextField jtfield1 = new JTextField();
	JScrollPane jScrollpane = new JScrollPane(jtareal1);
	
	public KajaClientGUI(DataOutputStream outputStream,
							DataInputStream inputStream, String nickname) {//생성자
		this.OutputStream = outputStream;
		this.InputStream = inputStream;
		this.nickname = nickname;
		
		getContentPane().setLayout(new BorderLayout());

		
		jlabel1.setFont(new Font("굴림", Font.BOLD,22));
		getContentPane().add("North", jlabel1);
		
	
		jtareal1.setBackground(Color.WHITE);
		jtareal1.setForeground(Color.BLACK);
		jtareal1.setFont(new Font("굴림", Font.BOLD, 21));
							
		
		jtareal1.setEditable(false);
		getContentPane().add("Center",jScrollpane);

		
		jtfield1.setBackground(Color.white);
		jtfield1.setForeground(Color.BLACK);
		jtfield1.setFont(new Font("굴림", Font.BOLD,25));
		getContentPane().add("South",jtfield1);
		jtfield1.addActionListener(this);
		
		setSize(800,800);
		setVisible(true);
		

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);

				setVisible(false);
			}
		});
		

		Thread th1 = new Thread(this);
		th1.start();

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jtfield1) {
			try {
				OutputStream.writeUTF(nickname+"-->"+jtfield1.getText());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			jtfield1.setText("");
		}
	}
	

	Toolkit tk1 = Toolkit.getDefaultToolkit();
	@Override
	public void run() {
		try {
		while(true) {
				String strServer1 = InputStream.readUTF();
				if(strServer1 == null) {
					jtareal1.append("\n"+"종료");
					return;
				}
				jtareal1.append("\n"+strServer1);
				int kkeut=jtareal1.getText().length();
				jtareal1.setCaretPosition(kkeut);
				tk1.beep();
				}
			} catch (IOException eee) {
				jtareal1.append("\n"+eee);				
			}
		}
	}

