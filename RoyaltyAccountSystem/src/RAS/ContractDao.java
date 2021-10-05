package RAS;

import java.sql.*;

import javafx.collections.*;

public class ContractDao{

	public static ContractDao instance = null;
	
	private JdbcConnection Util = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private Statement stmt = null;
	private StringBuffer query = new StringBuffer();
	public DialogMessage D = DialogMessage.getInstance();
	private ObservableList<ContractVO> ContractList = FXCollections.observableArrayList();
	
	private ContractDao() {}
	
	public static ContractDao getInstance() {
		if(instance == null) {
			instance = new ContractDao();
		}
		return instance;
	}
	
	// 조건 없이 계약 조회 
	public ObservableList<ContractVO> contractsearchAll(){
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		ResultSet rs = null;
		query.delete(0, query.length());
		try {
			query.append("select VendorPO, contnum, contcuscode, contcusname, contipcode, conttitle, ");
			query.append("contstart,contend, currency,");
			query.append(" TO_CHAR(contprice, '999,999,999,999,999'), controyalty from Contract order by contnum");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query.toString());
		} catch (SQLException e) {
			System.out.println("쿼리문 오류");
		} 
		try {
			while(rs.next()) {
				ContractVO tmp = new ContractVO(rs.getString(1), rs.getString(2),rs.getInt(3),
						rs.getString(4),rs.getInt(5), rs.getString(6),rs.getString(7),
						rs.getString(8), rs.getString(9),rs.getString(10),rs.getInt(11));
				ContractList.add(tmp);
			}
		} catch (SQLException e) {
			System.out.println("자료형 오류");
		}
		return ContractList;

	}
	
	public ObservableList<ContractVO> contractsearch(String value1){
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		ResultSet rs = null;
		try {
			query.append("select VendorPO, contnum, contcuscode, contcusname, contipcode, conttitle, ");
			query.append("contstart,contend, currency,");
			query.append(" TO_CHAR(contprice, '999,999,999,999,999'), controyalty from Contract where vendorpo=?");
			
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, value1);	
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ContractVO tmp = new ContractVO(rs.getString(1), rs.getString(2),rs.getInt(3),
						rs.getString(4),rs.getInt(5), rs.getString(6),rs.getString(7),
						rs.getString(8), rs.getString(9),rs.getString(10),rs.getInt(11));
				ContractList.add(tmp);
			}
		} catch (SQLException e) {
			System.out.println("쿼리문 오류");
		} 
	
		return ContractList;
	}
	
	public ObservableList<ContractVO> contractsearch(String value1, String value2){
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		ResultSet rs = null;
		String value2_str = "%" + value2 + "%";
		try {
			query.append("select VendorPO, contnum, contcuscode, contcusname, contipcode, conttitle, ");
			query.append("contstart,contend, currency,");
			if(value1 == "계약번호") {
				query.append(" TO_CHAR(contprice, '999,999,999,999,999'), controyalty from Contract where contnum=?");	
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, value2);
			}else if(value1 == "계약거래처") {
				query.append(" TO_CHAR(contprice, '999,999,999,999,999'), controyalty from Contract where contcusname like ?");	
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, value2_str);	
			}else if(value1 == "타이틀명") {
				query.append(" TO_CHAR(contprice, '999,999,999,999,999'), controyalty from Contract where conttitle like ?");	
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, value2_str);	
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ContractVO tmp = new ContractVO(rs.getString(1), rs.getString(2),rs.getInt(3),
						rs.getString(4),rs.getInt(5), rs.getString(6),rs.getString(7),
						rs.getString(8), rs.getString(9),rs.getString(10),rs.getInt(11));
				ContractList.add(tmp);
			}
		} catch (SQLException e) {
			System.out.println("쿼리문 오류");
		} 
	 
		return ContractList;
	}
	
	public ObservableList<ContractVO> contractsearch(String value1, String value2, String value3){
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		ResultSet rs = null;
		String value3_str = "%" + value3 + "%";
		try {
			query.append("select VendorPO, contnum, contcuscode, contcusname, contipcode, conttitle, ");
			query.append("contstart,contend, currency,");
			query.append(" TO_CHAR(contprice, '999,999,999,999,999'), controyalty from Contract where vendorpo=?");
			
			if(value2 == "계약번호") {
				query.append(" and contnum=?");	
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, value1);	
				pstmt.setString(2, value3);
			}else if(value2 == "계약거래처") {
				query.append(" and contcusname LIKE ?");	
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, value1);	
				pstmt.setString(2, value3_str);
			}else if(value2 == "타이틀명") {
				query.append(" and conttitle LIKE ?");		
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ContractVO tmp = new ContractVO(rs.getString(1), rs.getString(2),rs.getInt(3),
						rs.getString(4),rs.getInt(5), rs.getString(6),rs.getString(7),
						rs.getString(8), rs.getString(9),rs.getString(10),rs.getInt(11));
				ContractList.add(tmp);
			}
		} catch (SQLException e) {
			System.out.println("쿼리문 오류");
		} 
	
		return ContractList;
	}

	public void contractadd(String VenderPO,String cusname, String contcuscode, String conttitle, int contipcode, String contstart
			,String contend, String currency, int contprice, int controyalty) {
		
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		int result = 0;
		try {
			if(VenderPO.equals("매출")) {
				query.append("insert into contract values(?, 'V' || VCONTNUM_SEQ.NEXTVAL, ?,?,?,?,?,?,?,?,?)");
			}else {
				query.append("insert into contract values(?, 'P' || PCONTNUM_SEQ.NEXTVAL, ?,?,?,?,?,?,?,?,?)");
			}
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, VenderPO);
			pstmt.setString(2, contcuscode);
			pstmt.setString(3, cusname);
			pstmt.setInt(4, contipcode);
			pstmt.setString(5, conttitle);
			pstmt.setString(6, contstart);
			pstmt.setString(7, contend);
			pstmt.setString(8, currency);
			pstmt.setInt(9, contprice);
			pstmt.setInt(10, controyalty);
			result = pstmt.executeUpdate();
			if(result == 1) { D.dialog("계약 등록", "등록 완료되었습니다 .");}
			else { D.dialog("계약 등록", "등록 실패하였습니다 .");}
		}catch(SQLException e){
			System.out.println("쿼리문 오류");
		}finally {
			Util.close(pstmt);
			Util.close(conn);
		}
	}

	public void contractmodify(String contnum, String contend, int controyalty) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		int result = 0;
		try {
			query.append("update contract set contend=?, controyalty=? where contnum=?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, contend);
			pstmt.setInt(2, controyalty);
			pstmt.setString(3, contnum);
			result = pstmt.executeUpdate();
			if(result == 1) { D.dialog("계약 수정", "수정 완료되었습니다 ."); }
			else { D.dialog("계약 수정","수정 실패하였습니다 .");}
		}catch(SQLException e){
			System.out.println("쿼리문 오류");
		}finally {
			Util.close(pstmt);
			Util.close(conn);
		}
	}
	
	public int programadd(String programN) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		pstmt = null;
		query.delete(0, query.length());
		ResultSet rs = null;
		int code = 0;
		int result = 0;
		try {
			query.append("insert into program values(ipcode_seq.nextval, ?)");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, programN);
			result = pstmt.executeUpdate();
			if(result == 1) {
				D.dialog("프로그램등록 ", "등록 완료되었습니다 .");
				query.delete(0, query.length());
				query.append("select ipcode from program where programname=? ");
				pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, programN);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					code = rs.getInt(1);
				}
			}
			else { D.dialog("프로그램등록","등록 실패하였습니다 .");}
		}catch(SQLException e) {
			System.out.println("쿼리문오류");
		}
		return code;
	}
	
	public String[] customersearch(String name, String tmp) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		ResultSet rs = null;
		String[] result = new String[2];
		try {
			query.append("select cuscode, cusname from customer where cusname like ? and vendorpo=?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, name);
			pstmt.setString(2, tmp);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result[0] = String.valueOf(rs.getInt(1));
				result[1] = rs.getString(2);
				System.out.println(result);
			}
		}catch(SQLException e) {
			System.out.println("쿼리문오류");
		}
		return result;
	}
	
	public String[] programsearch(String name) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		ResultSet rs = null;
		String[] result = new String[2];
		try {
			query.append("select ipcode, programname from program where programname like ? ");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				result[0] = String.valueOf(rs.getInt(1));
				result[1] = rs.getString(2);
			}
		}catch(SQLException e) {
			System.out.println("쿼리문오류");
		}
		return result;
	}
	
	public ObservableList<ContractVO>  programsearch() {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		ResultSet rs = null;
		query.delete(0, query.length());
		try {
			query.append("select program.ipcode, program.programname, contract.contstart, contract.contend ");
			query.append("from program LEFT OUTER JOIN contract on program.ipcode=contract.contipcode AND contract.vendorpo='매입' ");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query.toString());
			while(rs.next()) {
				ContractVO tmp = new ContractVO(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4));
				ContractList.add(tmp);
			}
		}catch(SQLException e) {
			System.out.println("쿼리문오류");
		}
		return ContractList;
	}
}
