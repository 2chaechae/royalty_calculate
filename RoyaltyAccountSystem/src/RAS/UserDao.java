package RAS;

import java.sql.*;

public class UserDao { 
	public static UserDao instance = null;
	public static boolean Manager_check = false;
	
	private JdbcConnection Util = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private String query = "";
	private DialogMessage D = DialogMessage.getInstance();
	
	private UserDao() {}
	
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	// 계정 등록
	public int add(String name, String strCheck) { 
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		int result = 0;
		try {
			int check = managecheck();
			if(check == 1 && strCheck.equals("YES")) {
				result = 3;
			}
			else {
				query = "insert into userManage values(usernumber.nextval, 'S' || userpw.nextval, userpw.nextval, ?, ?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, strCheck);
				result = pstmt.executeUpdate();
			}
		}catch(SQLException e){
			System.out.println("쿼리문 오류");
		}finally {
			Util.close(pstmt);
			Util.close(conn);
		}
		return result;
	}
	
	// 계정 정보 수정 
	public int modify(String name, String pw, String id, String strCheck) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		int result = 0;
		try {
			int check = managecheck();
			if(check == 1 && strCheck.equals("YES")) {
				result = 3; 
			}else { 
				query = "update usermanage set name=?, pw=?, managec=? where id=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, pw);
				pstmt.setString(3, strCheck);
				pstmt.setString(4, id);
				result = pstmt.executeUpdate();
			}
		}catch(SQLException e){
			System.out.println("쿼리문 오류");
		}finally {
			Util.close(pstmt);
			Util.close(conn);
		}
		return result;
	}
	
	// 계정 삭제 
	public int delete(String id) { 
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		int result = 0;
		try {
			query = "delete usermanage where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			result = pstmt.executeUpdate();
		}catch(SQLException e){
			System.out.println("쿼리문 오류");
		}finally {
			Util.close(pstmt);
			Util.close(conn);
		}
		return result;
	}
	
	// 계정 정보 조회
	public ResultSet search() {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		ResultSet rs = null;
		try {
			query = "select * from userManage order by pw";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("쿼리문 오류");
		} 
		return rs;
	}
	
	// 로그인 - 계정 관리자 여부 확인 
	public boolean login(String id, String pw) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		boolean result = true;
		try {
			query = "select * from userManage";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				if(rs.getString(2).equals(id) && rs.getString(3).equals(pw)) {
					result = true;
					if(rs.getString(5).equals("yes") || rs.getString(5).equals("YES")) {
						Manager_check = true;
						break;
					}else {Manager_check = false;} 
				}else { result = false;}
			}
			
		} catch (SQLException e) {
			System.out.println("쿼리문 오류");
		} finally {
			Util.close(stmt);
			Util.close(conn);
		}
		return result;
		
	}
	
	// 사용자 계정 PW 변경
	public void usermodify(String id,String nowpw, String newpw, String newpwcheck) {
		ResultSet rs = search();
		
		try {
			while(rs.next()) {
				if(rs.getString("id").equals(id) && rs.getString("pw").equals(nowpw)) {
					if(newpw.equals(newpwcheck)) {
					Util = JdbcConnection.getInstance();
					conn = Util.getConnection();
					try {
						query = "update usermanage set pw=? where id=?";
						pstmt = conn.prepareStatement(query);
						pstmt.setString(1, newpw);
						pstmt.setString(2, id);
						int result = pstmt.executeUpdate();
						if(result == 1) {
							D.dialog("비밀번호 변경", "비밀번호 변경되었습니다 .");
							return;
						}
					} catch (SQLException e) {
						System.out.println("쿼리문 오류");
					} finally {
						Util.close(stmt);
						Util.close(conn);
						}
					}
					else {
						D.dialog("비밀번호 변경", "비밀번호 확인이 일치하지 않습니다.");
						return;
					}
				}//end if
				else if(rs.getString("id").equals(id)) {
					D.dialog("비밀번호 변경", "현재 비밀번호가 일치하지 않습니다.");
					return;
				}
				
				else if(rs.getString("pw").equals(nowpw)) {
					D.dialog("비밀번호 변경", "등록된 아이디가 아닙니다.");
					return;
				}
			}// end while
		}catch(SQLException e) {
			System.out.println("쿼리문 오류");
		}
	}
	
	public int managecheck() {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		int result = 0;
		try {
			query = "select * from usermanage where managec='YES' ";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) { result = 1;}
			else { result = 0; }
		}catch(SQLException e)  {System.out.println("쿼리문 오류");}
	 return result;
	}
}



