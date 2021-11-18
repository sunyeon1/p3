package TcpChatting;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TcpGuiClient extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TcpGuiClient frame = new TcpGuiClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TcpGuiClient() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("아고라(Agora)");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(67, 10, 250, 45);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Server_ip   :");
		lblNewLabel_1.setBounds(71, 118, 81, 15);
		contentPane.add(lblNewLabel_1);
		
		JLabel label = new JLabel("port_num   :");
		label.setBounds(71, 159, 81, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(164, 115, 116, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(164, 156, 116, 21);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(164, 195, 116, 21);
		contentPane.add(textField_2);
		
		JButton btnNewButton = new JButton("종료");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});


		btnNewButton.setBounds(5, 298, 97, 23);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("접속");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String u_ip = textField.getText();
				String u_port = textField_1.getText();
				String u_nick = textField_2.getText();
				
				try { // 클라이언트 구조는 강사님 6월 30일 강의 참조
					Socket s1 = new Socket(u_ip, Integer.parseInt(u_port));
					DataOutputStream outputStream = new DataOutputStream(s1.getOutputStream());
					DataInputStream inputStream = new DataInputStream(s1.getInputStream());
					outputStream.writeUTF("##"+u_nick);
					
					new KajaClientGUI(outputStream, inputStream, u_nick) {
						public void closeWork() throws IOException{
							outputStream.close();
							inputStream.close();
							System.exit(0);
						}
					
					};
				} catch (NumberFormatException | IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				setVisible(false);
			}
		});
		button.setBounds(275, 298, 97, 23);
		contentPane.add(button);
		
		
		
		JLabel label_1 = new JLabel("nick_name   :");
		label_1.setBounds(71, 198, 81, 15);
		contentPane.add(label_1);
	}
}
