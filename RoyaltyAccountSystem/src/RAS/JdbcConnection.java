package RAS;

import java.sql.*;

public class JdbcConnection {
	public static JdbcConnection instance = null;
	
	private JdbcConnection(){	// 드라이버 로드
		 try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); 

		 }catch(ClassNotFoundException e) {
			 System.out.println("드라이버 로드 실패");
		 }
	};
	
	public static JdbcConnection getInstance() {	// 연결 객체 생성 
		if(instance == null) {
			instance = new JdbcConnection();
			return instance;
		}
		return instance;
	}
	
	
	public Connection getConnection() {	// DB연결
		Connection conn = null;
		try {
			String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
			conn = DriverManager.getConnection(url, "admin", "admin");
		}catch(SQLException e) {
			System.out.println("DB 연결 실패");
		}
		return conn;
	}
	
	// 자원 해제
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
