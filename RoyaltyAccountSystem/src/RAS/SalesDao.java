package RAS;

import java.sql.*;

import javafx.collections.*;

public class SalesDao {
		public static SalesDao instance = null;
		
		private JdbcConnection Util = null;
		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private StringBuffer query = new StringBuffer();
		private DialogMessage D = DialogMessage.getInstance();
		private ObservableList<SalesVO> SalesSearchlist = FXCollections.observableArrayList();
		int [] result = new int[2]; 
		
		private SalesDao() {}
		
		public static SalesDao getInstance() {
			if(instance == null) {
				instance = new SalesDao();
			}
			return instance;
		}
		
		
		// 매출 전표 생성
		public int[] salescreate(String salesCD, String salesdocdate, String salesdate, String salesAccountN, int salescuscode, String salescusN, int salestitleC, String salestitleN
				,String salesprice, String salesCurr, String salesWt, String pwrite, String salesVatC, String salesVat, int salesrate, String saleswon, int i) {
			
			int salesprice_db = D.Stringintchang(salesprice);
			int saleswon_db = D.Stringintchang(saleswon);
			int salesVat_db = 0;
			int SF =0;
			if(salesVat != "") {
				salesVat_db = D.Stringintchang(salesVat);
			}
				Util = JdbcConnection.getInstance();
				conn = Util.getConnection();
				ResultSet rs = null;
				query.delete(0, query.length());
				try {
						
					if (i == 0) {
						query.append("SELECT LAST_NUMBER FROM USER_SEQUENCES WHERE SEQUENCE_NAME='SALES_SEQ'");
						pstmt = conn.prepareStatement(query.toString());
						rs = pstmt.executeQuery();
						if(rs.next()) {result[0] = rs.getInt(1);}
						query.delete(0, query.length());
						query.append("insert into sales values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? )");
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, result[0]);
						pstmt.setString(2, salesCD);
						pstmt.setString(3, salesdocdate);
						pstmt.setString(4, salesdate);
						pstmt.setString(5, salesAccountN);
						pstmt.setInt(6, salescuscode);
						pstmt.setString(7, salescusN);
						pstmt.setInt(8, salestitleC);
						pstmt.setString(9, salestitleN);
						pstmt.setInt(10, salesprice_db);
						pstmt.setString(11, salesCurr);
						pstmt.setInt(12, salesrate);
						pstmt.setInt(13, saleswon_db);
						pstmt.setString(14, salesVatC);
						pstmt.setInt(15, salesVat_db);
						pstmt.setString(16, salesWt);
						pstmt.setString(17, pwrite);
						SF = pstmt.executeUpdate();
					}else {

						query.append("insert into sales values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? )");
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setInt(1, result[0]);
						pstmt.setString(2, salesCD);
						pstmt.setString(3, salesdocdate);
						pstmt.setString(4, salesdate);
						pstmt.setString(5, salesAccountN);
						pstmt.setInt(6, salescuscode);
						pstmt.setString(7, salescusN);
						pstmt.setInt(8, salestitleC);
						pstmt.setString(9, salestitleN);
						pstmt.setInt(10, salesprice_db);
						pstmt.setString(11, salesCurr);
						pstmt.setInt(12, salesrate);
						pstmt.setInt(13, saleswon_db);
						pstmt.setString(14, salesVatC);
						pstmt.setInt(15, salesVat_db);
						pstmt.setString(16, salesWt);
						pstmt.setString(17, pwrite);
						SF =pstmt.executeUpdate();
					}
				} catch (SQLException e) {
					System.out.println("쿼리문 오류");
				}
				result[1] = SF;
				return result;
		}
		
		// 전표번호 동일 시퀀스 입력 
		public void sequenceup() {
			Util = JdbcConnection.getInstance();
			conn = Util.getConnection();
			query.delete(0, query.length());
			try {
				query.append("SELECT SALES_SEQ.nextval from dual");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.executeQuery();
			}catch(SQLException e) {
				System.out.println("쿼리문 오류");
			}
		}
		
		// 매출 전표 조회
		public ResultSet salessearchcont(String contnum) {
			Util = JdbcConnection.getInstance();
			conn = Util.getConnection();
			ResultSet rs = null;
			query.delete(0, query.length());
			try {
				query.append("select * from contract where contnum=?");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, contnum);
				rs = pstmt.executeQuery();
			} catch (SQLException e) {
				System.out.println("쿼리문 오류");
			}
			return rs; 
		}

		//------------------------------ 매출 조회/삭제 화면 메서드----------------------
		// 매출 전표 조회
		public ResultSet salessearch(String condition1, String condition2, String condition3, String condition4) {
			Util = JdbcConnection.getInstance();
			conn = Util.getConnection();
			ResultSet rs = null;
			query.delete(0, query.length());
				try {
					if(condition1.equals("거래처")) {
						query.append("select salesnum, salesdocdate, salesdate, salescuscode, salescusn, salestitlec, salestitlen, TO_CHAR(salesprice,'999,999,999,999'), salescurrency,");
						query.append("salesrate, TO_CHAR(saleswon,'999,999,999,999'), salesvatc, TO_CHAR(salesvat, '999,999,999'), saleswriter, saleswritep FROM sales ");
						query.append("WHERE SALESDOCDATE BETWEEN ? AND ? AND SALESACCOUNTN='매출' AND SALESCUSN LIKE ?");
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1, condition3);
						pstmt.setString(2, condition4);
						pstmt.setString(3, condition2);
						rs = pstmt.executeQuery();
					}
					else if(condition1.equals("타이틀")) {
						query.append("select salesnum, salesdocdate, salesdate, salescuscode, salescusn, salestitlec, salestitlen, TO_CHAR(salesprice,'999,999,999,999'), salescurrency,");
						query.append("salesrate, TO_CHAR(saleswon,'999,999,999,999'), salesvatc, TO_CHAR(salesvat, '999,999,999'), saleswriter, saleswritep FROM sales ");
						query.append("WHERE SALESDOCDATE BETWEEN ? AND ? AND SALESACCOUNTN='매출' AND SALEStitlen LIKE ?");
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1, condition3);
						pstmt.setString(2, condition4);
						pstmt.setString(3, condition2);
						rs = pstmt.executeQuery();	
					}
				}catch(SQLException e) {
					System.out.println("쿼리문 오류");
				}
				return rs;
		}
		
		public ResultSet salessearch(String condition1, String condition2) {
			Util = JdbcConnection.getInstance();
			conn = Util.getConnection();
			ResultSet rs = null;
			query.delete(0, query.length());
				try {
					if(condition1.equals("거래처")) {
						query.append("select salesnum, salesdocdate, salesdate, salescuscode, salescusn, salestitlec, salestitlen, TO_CHAR(salesprice,'999,999,999,999'), salescurrency,");
						query.append("salesrate, TO_CHAR(saleswon,'999,999,999,999'), salesvatc, TO_CHAR(salesvat, '999,999,999'), saleswriter, saleswritep FROM sales ");
						query.append("WHERE SALESACCOUNTN='매출' AND SALESCUSN like ?");
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1, condition2);
						rs = pstmt.executeQuery();
					}
					if(condition1.equals("타이틀")) {
						query.append("select salesnum, salesdocdate, salesdate, salescuscode, salescusn, salestitlec, salestitlen, TO_CHAR(salesprice,'999,999,999,999'), salescurrency,");
						query.append("salesrate, TO_CHAR(saleswon,'999,999,999,999'), salesvatc, TO_CHAR(salesvat, '999,999,999'), saleswriter, saleswritep FROM sales ");
						query.append("WHERE SALESACCOUNTN='매출' AND SALEStitlen like ?");
						pstmt = conn.prepareStatement(query.toString());
						pstmt.setString(1, condition2);
						rs = pstmt.executeQuery();	
					}
				}catch(SQLException e) {
					System.out.println("쿼리문 오류");
				}
				return rs;
		}
		// 매출 전표 삭제 
		public void salesdelte(int salesnum) {
			int result = 0;
			Util = JdbcConnection.getInstance();
			conn = Util.getConnection();
			query.delete(0, query.length());
			try {
				query.append("delete from sales where salesnum=?");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setInt(1, salesnum);
				result = pstmt.executeUpdate();
				if(result == 0) { D.dialog("매출전표삭제", "전표 삭제 실패하였습니다.");}
				else { D.dialog("매출전표삭제", result-1 +"개의 전표를 삭제하였습니다.");}
			}catch(SQLException e) {
				System.out.println("쿼리문 오류");
			}
		}
}	
