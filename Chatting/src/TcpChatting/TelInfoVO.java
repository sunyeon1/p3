package TcpChatting;
// 강사님 6월 28일 JDBC 강의 내용 참조
import java.util.Date;

public class TelInfoVO {
		private String nickname;			

		private String d; 

		public TelInfoVO(String nickname, String d) {
			
			this.nickname = nickname;
			this.d = d;
		}
		
		public TelInfoVO() {}
		

		
	public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getD() {
			return d;
		}

		public void setD(String d) {
			this.d = d;
		}
		
		
		

	public static void main(String[] args) {
		
	}

}
