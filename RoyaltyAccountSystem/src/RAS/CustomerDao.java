package RAS;

import java.sql.*;

public class CustomerDao { 
	
	public static CustomerDao instance = null;
	
	private JdbcConnection Util = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private String query = "";
	public DialogMessage D = DialogMessage.getInstance();
	
	private CustomerDao() {}
	
	public static CustomerDao getInstance() {
		if(instance == null) {
			instance = new CustomerDao();
		}
		return instance;
	}
	
	// 고객처 DB 정보 
	public ResultSet cusSearchAll() {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		ResultSet rs = null;
		try {
			query = "select * from Customer order by cuscode";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("쿼리문 오류");
		} 
		return rs;
	}
	
	// 고객처 DB정보 (특정 거래처 지정)
	public ResultSet cusSearchOne(String searchKey) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		ResultSet rs = null;
		try {
			query = "select * from Customer where cusname like ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchKey);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			System.out.println("쿼리문 오류");
		} 
		return rs;
	}

	// 고객처 등록 
	public void cusAdd(String VenderPO, String cusname, long cuscopnum, String cusphone, String cusaddress, String cusemail,
			String cusbank, String cusown, long cusaccount, String cusforeign) {
		
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		
		try {
			switch(VenderPO) {
			case "매출" : query = "insert into customer values(?, VENDORC_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?,?)";
						 pstmt = conn.prepareStatement(query);
						 pstmt.setString(1, VenderPO);
						 break;
			case "매입" : query = "insert into customer values(?, POC_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?,?)";
						 pstmt = conn.prepareStatement(query);
			 			 pstmt.setString(1, VenderPO);
			 			 break;
			}
			pstmt.setString(2, cusname);
			pstmt.setLong(3, cuscopnum);
			pstmt.setString(4, cusphone);
			pstmt.setString(5, cusaddress);
			pstmt.setString(6, cusemail);
			pstmt.setString(7, cusbank);
			pstmt.setString(8, cusown);
			pstmt.setLong(9, cusaccount);
			pstmt.setString(10, cusforeign);
			int result = pstmt.executeUpdate();
			if(result == 1) { D.dialog("거래처 등록", "등록 완료되었습니다 .");}
			else { D.dialog("거래처 등록", "등록 실패하였습니다 .");}
		}catch(SQLIntegrityConstraintViolationException e){
			D.dialog("거래처 등록", "이미 등록된 사업자 등록번호 입니다.");
		}catch(SQLException e) {
			System.out.println("쿼리문 오류");
		}
		finally {
			Util.close(pstmt);
			Util.close(conn);
		}
			
	}

	
	// 고객 정보 수정 
	public void cusModify(int cuscode, String cusname, String cusphone, String cusaddress, String cusemail,
			String cusbank, String cusown, long cusaccount) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		try {
		
			query = "update customer set cusname=?, cusphone=?, cusaddress=?, cusemail=?, "
					+ "cusbank=?, cusown=?, cusaccount=? where cuscode=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, cusname);
			pstmt.setString(2, cusphone);
			pstmt.setString(3, cusaddress);
			pstmt.setString(4, cusemail);
			pstmt.setString(5, cusbank);
			pstmt.setString(6, cusown);
			pstmt.setLong(7, cusaccount);
			pstmt.setInt(8, cuscode);
			int result = pstmt.executeUpdate();
			if(result == 1) { D.dialog("거래처 정보 수정", "수정 완료되었습니다 ."); }
			else { D.dialog("거래처 정보 수정", "수정 실패하였습니다 ."); }
		}catch(SQLException e) { System.out.println("쿼리문 오류");}
	}
	
	public int checknumber(String VPO, long cuscopnum) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		ResultSet rs = null;
		int result = 0;
		try {
			query = "select * from customer where cuscopnum=? and vendorpo=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setLong(1, cuscopnum);
			pstmt.setString(2, VPO);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = 1;
			}
		}catch(SQLException e) {System.out.println("쿼리문오류");}
			return result;
	}
	
}
