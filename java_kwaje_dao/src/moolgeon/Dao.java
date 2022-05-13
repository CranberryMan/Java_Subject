package moolgeon;
import java.sql.*;

public class Dao {
	Connection conn = accessDb();
	String sql = "";
	String userId = "";
	
	public Connection accessDb() {
		Connection conn = null;
		String url = "jdbc:mysql://localhost/kwaje";
		String id = "root";
		String pw = "1234";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, id, pw);
			System.out.println("연결됨");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeConn() {
		try {
			conn.close();
			System.out.println("DB접속 해제");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int login(String id, String pw) {
		sql = "SELECT * FROM user where id = ?";
		int isAdmin = 2;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString(1) + rs.getString(2) + rs.getInt(3));
				if(pw.equals(rs.getString(2))) {
					System.out.println("로그인 성공");
					userId = rs.getString(1);
					isAdmin = rs.getInt(3);
					return isAdmin;
				}		
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isAdmin;
	}
}
