package RAS;

import java.sql.*;

public class PurchaseDao {
	
	public static PurchaseDao instance = null;
	private JdbcConnection Util = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private StringBuffer query = new StringBuffer();
	private DialogMessage D = DialogMessage.getInstance();
	
	
	private PurchaseDao() {}
	
	public static PurchaseDao getInstance() {
		if(instance == null) {
			instance = new PurchaseDao();
		}
		return instance;
	}
	
	// 매입내역 조회
	public ResultSet purchasesearch(String datestart, String dateend) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		ResultSet rs = null;
		query.delete(0, query.length());
		try {
			query.append("SELECT sales.salesdocdate, SALES.salestitlen, SALES.salestitlec, SALES.salescurrency, TO_CHAR(SALES.salesprice,'999,999,999'), TO_CHAR(SALES.SALESWON, '999,999,999'), ");
			query.append("CUSTOMER.CUSFOREIGN, CONTRACT.contcuscode, CONTRACT.contcusname, CONTRACT.controyalty, SALES.SALESNUM ");
			query.append("FROM SALES JOIN CONTRACT ON SALES.SALESTITLEC = CONTRACT.CONTIPCODE LEFT JOIN CUSTOMER ON contract.contcuscode = customer.cuscode ");
			query.append("WHERE SALES.SALESACCOUNTN ='매출' AND SALES.SALESWRITEP='미작성' AND SALES.SALESDOCDATE BETWEEN ? AND ? AND CONTRACT.CONTCUSCODE >= 50000");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, datestart);
			pstmt.setString(2, dateend);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			System.out.println("쿼리문 오류 ");
		}
		return rs;
	}
	
	// 매입 등록 
	public int purchaseadd(PurchaseVO data, String docdate, String date, int price, double vat, int Salesprice, int pricewon) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		int result = 0;
			try {
				query.append("insert into purchase ");
				query.append("values(purchase_seq.nextval, ?, ? ,? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, data.getPurchaseSdate());
				pstmt.setInt(2, data.getPurchasetitleC());
				pstmt.setString(3, data.getPurchasetitleN());
				pstmt.setString(4, data.getPurchasecurr());
				pstmt.setInt(5, Salesprice);
				pstmt.setInt(6, pricewon);
				pstmt.setString(7, data.getPurchasesep());
				pstmt.setInt(8, data.getPurchasecode());
				pstmt.setString(9, data.getPurchasecodeN());
				pstmt.setInt(10, data.getPurchaseRoyalty());
				pstmt.setInt(11, price);
				pstmt.setDouble(12, vat);
				pstmt.setString(13, LoginController.login_id);
				pstmt.setString(14, docdate);
				pstmt.setString(15, date);
				pstmt.setString(16, "매출원가_로열티");
				pstmt.setInt(17, data.getPurchaseSnum());
				result = pstmt.executeUpdate();
			}catch(SQLException e) {
				System.out.println("쿼리문 오류 ");
			}
			return result;

	}
	
	// 작성 , 미작성 여부 전환 
	public void pWritechange() {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		try {
			query.append("update sales set saleswritep='작성' ");
			query.append("where exists (select purchasenum from purchase where sales.salesnum=purchase.salesnum)");
			stmt = conn.createStatement();
			stmt.execute(query.toString());
		}catch(SQLException e) {
			System.out.println("쿼리문 오류 ");
		}
	}
	
	public void pWriteBack() {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		try {
			query.append("update sales set saleswritep='미작성' ");
			query.append("where not exists (select purchasenum from purchase where sales.salesnum=purchase.salesnum)");
			stmt = conn.createStatement();
			stmt.execute(query.toString());
		}catch(SQLException e) {
			System.out.println("쿼리문 오류 ");
		}
	}
	
	//------------------매입 조회/삭제  테이블 메서드 ------------------------
	// 생성된 매입전표 조회
	public ResultSet salessearch(String condition1, String condition2, String condition3, String condition4) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		ResultSet rs = null;
		query.delete(0, query.length());
			try {
				if(condition1.equals("거래처")) {
					query.append("select purchasenum, purchasDocdate, purchasdate, purchasecode, purchasecoden, purchasetitlec, purchasetitlen, TO_CHAR(purchasesprice,'999,999,999,999'), ");
					query.append("TO_CHAR(purchasespricewon,'999,999,999,999'), purchaseroyalty ,TO_CHAR(purchaseprice,'999,999,999'), TO_CHAR(purchasevat, '999,999,999'), PURCHASEWRITER FROM purchase ");
					query.append("WHERE purchasDocdate BETWEEN ? AND ? AND purchaseaccountn='매출원가_로열티' AND purchasecoden like ?");
					pstmt = conn.prepareStatement(query.toString());
					pstmt.setString(1, condition3);
					pstmt.setString(2, condition4);
					pstmt.setString(3, condition2);
					rs = pstmt.executeQuery();
				}
				if(condition1.equals("타이틀")) {
					query.append("select purchasenum, purchasDocdate, purchasdate, purchasecode, purchasecoden, purchasetitlec, purchasetitlen, TO_CHAR(purchasesprice,'999,999,999,999'), ");
					query.append("TO_CHAR(purchasespricewon,'999,999,999,999'), purchaseroyalty ,TO_CHAR(purchaseprice,'999,999,999'), TO_CHAR(purchasevat, '999,999,999'), PURCHASEWRITER FROM purchase ");
					query.append("WHERE purchasDocdate BETWEEN ? AND ? AND purchaseaccountn='매출원가_로열티' AND purchasetitlen like ?");;
					pstmt = conn.prepareStatement(query.toString());
					pstmt.setString(1, condition3);
					pstmt.setString(2, condition4);
					pstmt.setString(3, condition2);
					rs = pstmt.executeQuery();	
				}
			}catch(SQLException e) {
				System.out.println("쿼리문 오류 ");
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
					query.append("select purchasenum, purchasDocdate, purchasdate, purchasecode, purchasecoden, purchasetitlec, purchasetitlen, TO_CHAR(purchasesprice,'999,999,999,999'), ");
					query.append("TO_CHAR(purchasespricewon,'999,999,999,999'), purchaseroyalty ,TO_CHAR(purchaseprice,'999,999,999'), TO_CHAR(purchasevat, '999,999,999'), PURCHASEWRITER FROM purchase ");
					query.append("WHERE purchaseaccountn='매출원가_로열티' AND purchasecoden like ?");
					pstmt = conn.prepareStatement(query.toString());
					pstmt.setString(1, condition2);

					rs = pstmt.executeQuery();
				}
				if(condition1.equals("타이틀")) {
					query.append("select purchasenum, purchasDocdate, purchasdate, purchasecode, purchasecoden, purchasetitlec, purchasetitlen, TO_CHAR(purchasesprice,'999,999,999,999'), ");
					query.append("TO_CHAR(purchasespricewon,'999,999,999,999'), purchaseroyalty ,TO_CHAR(purchaseprice,'999,999,999'), TO_CHAR(purchasevat, '999,999,999'), PURCHASEWRITER FROM purchase ");
					query.append("WHERE purchaseaccountn='매출원가_로열티' AND purchasetitlen like ?");
					pstmt = conn.prepareStatement(query.toString());
					pstmt.setString(1, condition2);
					rs = pstmt.executeQuery();	
				}
			}catch(SQLException e) {
				System.out.println("쿼리문 오류 ");
			}
			return rs;
	}

	// 매입전표 삭제 
	public void salesdelte(int purchasenum) {
		int result = 0;
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		try {
			query.append("delete from purchase where purchasenum=?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setInt(1, purchasenum);
			result = pstmt.executeUpdate();
			if(result == 0) { D.dialog("매입전표삭제", "전표 삭제 실패하였습니다.");}
			else { D.dialog("매입전표삭제", result +"개의 전표를 삭제하였습니다.");}
		}catch(SQLException e) {
			System.out.println("쿼리문 오류 ");
		}
	}
	


}

