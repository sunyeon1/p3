package TcpChatting;
//����� 6�� 28�� JDBC ���� ���� ����
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import TcpChatting.TelInfoDBConn;
import TcpChatting.TelInfoVO;

public class TelInfoDAO {

	private Connection con;

	
	PreparedStatement pstmt = null;
	ResultSet rs = null; 
	String tn;
	
	public TelInfoDAO()
			throws ClassNotFoundException, SQLException {
		con = new TelInfoDBConn().getConnection();}

		public boolean create_Tcp(String sDate) {
			String tn =  "chatting_" + sDate;
			// �ؿ� ������ ���̺��� �̸��� ������ �����ϸ� ������ ���ڿ��� ġȯ�Ͽ� ��ȯ�ϱ� ���Ͽ� �ۼ��� ����
			// String ntn = tn.replace(" ","").replace(";","").replace("'", ""); 
			// ��������Ʈ: https://coding-factory.tistory.com/128 
			String ntn = tn.replace(" ","").replace(";","").replace("'", ""); 
			String sql = "select * from " + ntn;
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
				
			}catch(SQLException e) {
				return false;
			}
			return true;
		}
		
		public boolean create1_Tcp(String sDate) {
			String tn =  "chatting_" + sDate;
			String ntn = tn.replace(" ","").replace(";","").replace("'", ""); 
			String sql = "create table " + ntn + " (nickname varchar2(20), time varchar2(20))";
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();
			}catch(SQLException e) {
				System.out.println(e.toString());
				return false;
			}
			return true;
		}

		public boolean insert_Tcp(String nickname, String sDate, String sDate1) {
		String tn =  "chatting_" + sDate;
		String ntn = tn.replace(" ","").replace(";","").replace("'", "");
		String sql = "insert into " + ntn + " values(?,?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, nickname);
			
			String hour = sDate1.substring(0,2); 
			String min = sDate1.substring(2,4) ; 
			String sec = sDate1.substring(4,6); 
			String d = hour +":" + min + ":" + sec; 
			pstmt.setString(2, d);
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			System.out.println("insert Exception");
			return false;
		}
		return true;
		}
		
		
		
	public static void main(String[] args) {

	}


}
