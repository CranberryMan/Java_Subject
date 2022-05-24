package moolgeon;

import java.sql.*;
import java.util.ArrayList;

public class Dao {
	Connection conn = accessDb();
	String sql = "";
	String userId = "";
	private ArrayList <Product> list = new ArrayList<>();
	private ArrayList <BuyListMoklok> buyList = new ArrayList<>();

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
			while (rs.next()) {
				System.out.println(rs.getString(1) + rs.getString(2) + rs.getInt(3));
				if (pw.equals(rs.getString(2))) {
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

	public int getRank(String id) {
		sql = "select * from user where id = ?";
		int rank = -1;
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rank = rs.getInt(4);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rank;
	}

	public ArrayList<Product> getProductTbl() {
		sql = "select * from product";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
//				System.out.println(rs.getString(1) + rs.getInt(2) + rs.getInt(3));
				list.add(new Product(rs.getString(1), rs.getInt(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void buyProcess(String id, String product, int amount, Double price, int stock) {
		sql = "INSERT INTO buytbl (`buyUser`, `buyProduct`, `buyStock`, `buyPrice`) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, product);
			pstmt.setInt(3, amount);
			pstmt.setDouble(4, price);
			if(pstmt.executeUpdate() == 1) {
				System.out.println("정상업로드");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "UPDATE product SET `stock` = ? WHERE (`productName` =?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, stock);
			pstmt.setString(2, product);
			if(pstmt.executeUpdate()==1) {
				System.out.println("스톡처리도 정상");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ArrayList<BuyListMoklok> getBuyTbl(String id) {
		sql = "SELECT * FROM buytbl where buyUser = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				buyList.add(new BuyListMoklok(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getDouble(5)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return buyList;
	}
	
	public void refund(int id, int stock, String product) {
		int sto = 0;
		sql = "UPDATE buytbl SET `buyPrice` = '0' WHERE (`buyNo` = ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			if(pstmt.executeUpdate() == 1) {
				System.out.println("환불완료");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "SELECT * FROM product where productName = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, product);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				sto = rs.getInt(2);
				sto = sto + stock;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		sql = "UPDATE product SET `stock` = ? WHERE (`productName` = ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sto);
			pstmt.setString(2, product);
			if(pstmt.executeUpdate() == 1) {
				System.out.println("재고확보완료");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}


		

	}
}
