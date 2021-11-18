package TcpChatting;
//강사님 6월 28일 JDBC 강의 내용 참조
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TelInfoDBConn {
	private Connection con;
	
	public Connection getConnection() {
		return con;
	}
	public TelInfoDBConn()
			throws ClassNotFoundException, SQLException {
	Class.forName ("oracle.jdbc.driver.OracleDriver");
	con = DriverManager.getConnection
			("jdbc:oracle:thin:@127.0.0.1:1521:xe","hr","hr");
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
