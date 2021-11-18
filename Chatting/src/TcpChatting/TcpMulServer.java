package TcpChatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
// ������ ���� ��ü���� Ʋ�� �������  6�� 30�� ���ǳ��� ����
 
class ServerClass{
	ArrayList<ThreadServerClass> threadList = new ArrayList<ThreadServerClass>();
	Socket socket;
	DataOutputStream outputStream;
	ArrayList<String> arr1 =new ArrayList<String>();
	
	public ServerClass(int portno) throws IOException { 
		Socket s1 = null;
		ServerSocket ss1 = new ServerSocket(portno);
		System.out.println("��������....");
		while (true) {
			s1 = ss1.accept();
			System.out.println("�����ּ�: "+s1.getInetAddress()
											+", ������Ʈ:"+s1.getPort());
			ThreadServerClass tServer1 = new ThreadServerClass(s1);//s1�ʱ�ġ
			tServer1.start();
			threadList.add(tServer1);
			System.out.println("������ �� : "+threadList.size()+" ��");
		}
	}
	
	public void sendChat(String chat) throws IOException {
		for (int i = 0; i<threadList.size(); i++)
			threadList.get(i).outputStream.writeUTF(chat);
	}
	
	
	class ThreadServerClass extends Thread{
		Socket socket1;
		DataInputStream inputStream;
		DataOutputStream outputStream;
		
		//
		
		public ThreadServerClass(Socket s1) throws IOException {
			socket1 = s1;
			inputStream = new DataInputStream(s1.getInputStream());
			outputStream = new DataOutputStream(s1.getOutputStream());
		}
		
		public void run() {
			String nickname = "";
			
			String msg="";
			try{
				if(inputStream != null) {
				nickname=inputStream.readUTF();
				sendChat(nickname+" �� ����~~~~~~~(^^) (^^) (^^)");
				nickname=nickname.replaceAll("[#]","").trim();
				// sql�ȿ� ���̺��� ��¥���� �����ϱ����� ��¥�� ���ϴ� ���� 
				// ��������Ʈ :
				// https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=javaking75&logNo=140176424969
				// https://jamesdreaming.tistory.com/94
				Calendar today = Calendar.getInstance(); 
				String year = Integer.toString(today.get(Calendar.YEAR));
				String month = ""; 
				if (today.get(Calendar.MONTH) + 1 < 10)
				{
				String mon = "0" + Integer.toString(today.get(Calendar.MONTH) + 1);
				month = mon;
				} else { month = Integer.toString(today.get(Calendar.MONTH) + 1);
				}
				String day = "";
				if (today.get(Calendar.DATE) < 10) {
				String d = "0" + Integer.toString(today.get(Calendar.DATE));
				day = d;
				} else { day = Integer.toString(today.get(Calendar.DATE)); }
				String sDate = year + month + day ;
				String ti = Integer.toString(today.get(Calendar.HOUR_OF_DAY));;
				String mi = "";
				if (today.get(Calendar.MINUTE) < 10)
				{
				String min = "0" + Integer.toString(today.get(Calendar.MINUTE));
				mi = min;
				} else { mi = Integer.toString(today.get(Calendar.MINUTE));
				}
				String se = "";
				if (today.get(Calendar.SECOND) < 10)
				{
				String sec = "0" + Integer.toString(today.get(Calendar.SECOND));
				se = sec;
				} else { se = Integer.toString(today.get(Calendar.SECOND) );}
				String sDate1 = ti + mi + se;
				
				TelInfoDAO tidao = new TelInfoDAO();
				// TelinfoDAO�� ���� ������ �ְ� ������ ���� �� TelinfoDAO�� ������ ������ִ� ����
				// ��ó : 6�� 30�� ���� ������ TelInfoMain ����
				boolean b1 = tidao.create_Tcp(sDate);
				if (b1 == true) {
					System.out.println("table�� �����Ǿ��ֽ��ϴ�");
					System.out.println(sDate1);
					boolean b3 = tidao.insert_Tcp(nickname,sDate,sDate1);
					if(b3 == true) {
						System.out.print(nickname + "��");
						System.out.println("insert ok");}
					else {
						System.out.println("insert error");}
				}else {
					System.out.println("table�� �������� �ʾ� �� table�������մϴ�");
					boolean b2 = tidao.create1_Tcp(sDate);
				if(b2 == true) {
					System.out.println("table ���� ok");
					
					boolean b3 = tidao.insert_Tcp(nickname,sDate,sDate1);
					
				if(b3 == true) {
					System.out.print(nickname + "��");
					System.out.println("insert ok");}
				else {
					System.out.println("insert error");}
				}else {
					System.out.println("table ���� error");
				}
				}
				
				arr1.add(nickname);
							
				}	
				// �ӼӸ� ��ɾ� 
				// ��������Ʈ :
				// https://m.blog.naver.com/PostView.nhn?isHttpsRedirect=true&blogId=javaking75&logNo=140189868916&categoryNo=64&proxyReferer=
				// https://justbaik.tistory.com/46
				while(inputStream != null) {
					msg=inputStream.readUTF();
					if(msg.startsWith(nickname+"-->/w")) {
						String[] msgArr = msg.split(" ",3);
						for(int i=0; i<arr1.size(); i++) {
							if(arr1.get(i).equals(msgArr[1].replace("-->",""))) {
								threadList.get(i).outputStream.writeUTF("From "+nickname+"-->"+msgArr[2]);
							}
							if(arr1.get(i).equals(nickname)){
								threadList.get(i).outputStream.writeUTF("To "+msgArr[1]+"-->"+msgArr[2]);
							}
							}
						
					}else {
						
						sendChat(msg); 
					}

				}
			}catch(IOException e){
				//e.printStackTrace();
				//System.out.println("server error : " + e.toString());
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
				//System.out.println("jdbc ���� error : " + e.toString());
			} catch (SQLException e) {
				//System.out.println("sql error : " + e.toString());
			}finally {// ���� thread�� ã�� �ش� thread�� threadList���� ����
					// ����� ���ǳ��� �ο�
					for(int i=0; i<threadList.size(); i++) {
						if (socket1.equals(threadList.get(i).socket1)) {
							threadList.remove(i);
							try { 
								sendChat(nickname+" �� ����~~~ (��.��) (��.��) (��.��) ");
							}catch(IOException e) {
								System.out.println("server error : " + e.toString());
								//e.printStackTrace();
							}
						}
					}
				System.out.println("������ �� :"+threadList.size()+" ��");
			}
		}
	}
}


public class TcpMulServer {

	public static void main(String[] args) throws NumberFormatException, IOException {
		if(args.length !=1) {
			System.out.println("���� : �������� \'java ��Ű����.���ϸ� ��Ʈ��ȣ\' �������� �Է�");
		}					
		new ServerClass(Integer.parseInt(args[0]));
	}

}
