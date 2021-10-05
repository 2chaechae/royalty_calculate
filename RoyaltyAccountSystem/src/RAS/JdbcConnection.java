package RAS;

import java.sql.*;

public class JdbcConnection {
	public static JdbcConnection instance = null;
	
	private JdbcConnection(){	// ����̹� �ε�
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 

		 }catch(ClassNotFoundException e) {
			 System.out.println("����̹� �ε� ����");
		 }
	};
	
	public static JdbcConnection getInstance() {	// ���� ��ü ���� 
		if(instance == null) {
			instance = new JdbcConnection();
			return instance;
		}
		return instance;
	}
	
	
	public Connection getConnection() {	// DB����
		Connection conn = null;
		try {
			String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
			conn = DriverManager.getConnection(url, "admin", "admin");
		}catch(SQLException e) {
			System.out.println("DB ���� ����");
		}
		return conn;
	}
	
	// �ڿ� ����
	public void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void close(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
