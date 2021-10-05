package RAS;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import javafx.collections.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

public class profitController implements Initializable{
	//------------- 화면 컴포넌트 ------------------
	@FXML private TextField txtcust;
	@FXML private DatePicker startdate;
	@FXML private DatePicker endtdate;
	@FXML private Button getcharts;
	@FXML private PieChart pieC;
	@FXML private BarChart< String, Integer> BarC;
	@FXML private Label Lcaption;
	
	// ----------- 기타 컴포넌트 ------------------
	private JdbcConnection Util = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs1 = null;
	private ResultSet rs2 = null;
	private StringBuffer query = new StringBuffer();
	public DialogMessage D = DialogMessage.getInstance();
	public ContractDao Cdao = ContractDao.getInstance();
	public List<PurchaseVO> result = new ArrayList<PurchaseVO>();


	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	//------------------- 이벤트 ----------------------------
	
	@FXML
	public void handledownexcel() {
		String searchkey = txtcust.getText();
		String Sdate = startdate.getValue().toString();
		String Edate = endtdate.getValue().toString();

		rs1 = getPurchasedata(searchkey, Sdate, Edate); 
		excel(rs1);
	}
	
	@FXML
	public void handlemakecharts() {
		String searchkey = txtcust.getText();
		String Sdate = startdate.getValue().toString();
		String Edate = endtdate.getValue().toString();
		rs1 = getdataforpieC(searchkey, Sdate, Edate);
		rs2 = getdataforbarC(searchkey, Sdate, Edate);
		PieChartM(rs1);
		BarChartM(rs2);
		
	}
	
	// 차트 및 엑셀 데이터베이스 받기 
	public ResultSet getPurchasedata(String searchkey, String Sdate, String Edate) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		ResultSet rs = null;
		try {
			query.append("select sales.salesdocdate, sales.salescusn, sales.salestitlen, sales.saleswon,purchase.purchasecoden, purchase.purchaseroyalty, purchase.purchaseprice, purchase.purchasevat ");
			query.append("from purchase left join sales on purchase.salesnum = sales.salesnum where sales.salesaccountn='매출' and purchasecoden LIKE ? and salesdocdate between ? and ?");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, searchkey);
			pstmt.setString(2, Sdate);
			pstmt.setString(3, Edate);
			rs = pstmt.executeQuery();

			}catch(SQLException e) { System.out.println("쿼리문 오류");}
		return rs;
	}

	public ResultSet getdataforpieC(String searchkey, String Sdate, String Edate) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		ResultSet rs = null;
		try {
			query.append("select purchasecoden, purchasetitlen, sum(purchaseprice) from purchase ");
			query.append("where purchasecoden=? AND purchasdocdate between ? and ? ");
			query.append("GROUP by purchasecoden, purchasetitlen ");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, searchkey);
			pstmt.setString(2, Sdate);
			pstmt.setString(3, Edate);
			rs = pstmt.executeQuery();
			}catch(SQLException e) { System.out.println("쿼리문 오류");}
		return rs;
	}
	
	// ---------------------엑셀 파일 만들기 ------------------------------
	public void excel(ResultSet rs) {
		result.removeAll(result);
		try {
			while(rs.next()) {
				result.add(new PurchaseVO(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getInt(8)));
			}
		} catch (SQLException e) { System.out.println("자료형 오류");}
		
		try {
			 HSSFWorkbook workbook = new HSSFWorkbook();
			 HSSFSheet sheet = workbook.createSheet("로열티 정산 정보");
			 
			 //create heading
			 Row rowHeading = sheet.createRow(0);
			 rowHeading.createCell(0).setCellValue("판매일자");
			 rowHeading.createCell(1).setCellValue("판매처");
			 rowHeading.createCell(2).setCellValue("타이틀명");
			 rowHeading.createCell(3).setCellValue("판매가격");
			 rowHeading.createCell(4).setCellValue("원작사");
			 rowHeading.createCell(5).setCellValue("로열티율");
			 rowHeading.createCell(6).setCellValue("지급액");
			 rowHeading.createCell(7).setCellValue("VAT");
			 for( int i = 0; i < 8; i++) {
				 CellStyle stylerowHeading = workbook.createCellStyle();
				 Font font = workbook.createFont();
				 font.setBold(true);
				 font.setFontName(HSSFFont.FONT_ARIAL);
				 font.setFontHeightInPoints((short) 11);
				 stylerowHeading.setFont(font);
				 stylerowHeading.setVerticalAlignment(CellStyle.ALIGN_CENTER);
				 rowHeading.getCell(i).setCellStyle(stylerowHeading);
				 
			 }
			
			 int r = 1;
			 for(PurchaseVO p : result) {
				 Row row = sheet.createRow(r);
				// 판매일자
				 Cell cellPdate = row.createCell(0);
				 cellPdate.setCellValue(p.getPurchaseSdate());
				 CellStyle styleCreationDate = workbook.createCellStyle();
				 HSSFDataFormat PCreationDate = workbook.createDataFormat();
				 styleCreationDate.setDataFormat(PCreationDate.getFormat("yyyy/MM/DD"));
				 cellPdate.setCellStyle(styleCreationDate);
				
				 
				// 판매처
				 Cell cellPScuscode = row.createCell(1);
				 cellPScuscode.setCellValue(p.getSalescusn());
			
				 
				// 타이틀명
				 Cell cellPtitle = row.createCell(2);
				 cellPtitle.setCellValue(p.getPurchasetitleN());
			
				
				 
				// 판매가격
				 Cell cellPSprice = row.createCell(3);
				 cellPSprice.setCellValue(p.getPurchaseSpricewon_int());
				 CellStyle stylePrice = workbook.createCellStyle();
				 HSSFDataFormat price = workbook.createDataFormat();
				 stylePrice.setDataFormat(price.getFormat("#,###"));
				 cellPSprice.setCellStyle(stylePrice);
				 
				// 원작사
				 Cell cellPcuscode = row.createCell(4);
				 cellPcuscode.setCellValue(p.getPurchasecodeN());
				
				 
				// 로열티율
				 Cell cellProyalty = row.createCell(5);
				 cellProyalty.setCellValue(p.getPurchaseRoyalty());
				 
 
				// 지급액
				 Cell cellPprice = row.createCell(6);
				 cellPprice.setCellValue(p.getPurchaseprice_int());
				 cellPprice.setCellStyle(stylePrice);
				 
				// 지급VAT
				 Cell cellVAT = row.createCell(7);
				 cellVAT.setCellValue(p.getPurchasepricevat_int());
				 cellVAT.setCellStyle(stylePrice);
				 r++; 
			 }
			 //total
			 Row rowTotal = sheet.createRow(result.size()+1);
			 Cell cellTextTotal = rowTotal.createCell(0);
			 cellTextTotal.setCellValue("Total");

			 int num = result.size()+2;
			 int totalnum = result.size()+1;
			 CellRangeAddress region = CellRangeAddress.valueOf("A" + num + ":" + "F" + num);
			 sheet.addMergedRegion(region);
			 CellStyle styleTotal = workbook.createCellStyle();
			 
			 Font fontTextTotal = workbook.createFont();
			 fontTextTotal.setBold(true);
			 fontTextTotal.setFontHeightInPoints((short)11);
			 fontTextTotal.setColor(HSSFColor.DARK_RED.index);
			 styleTotal.setFont(fontTextTotal);
			 cellTextTotal.setCellStyle(styleTotal);
			 styleTotal.setVerticalAlignment(CellStyle.ALIGN_RIGHT);
			 HSSFDataFormat price = workbook.createDataFormat();
			 styleTotal.setDataFormat(price.getFormat("#,###"));
			 cellTextTotal.setCellStyle(styleTotal);
			 
			 //TOTAL VALUE
			 Cell cellTotalValue = rowTotal.createCell(6);
			 cellTotalValue.setCellFormula("sum(G2:G" + totalnum + ")"); 
			 cellTotalValue.setCellStyle(styleTotal);
			 
			 
			 // VAT TOTAL VALUE
			 Cell cellVATValue = rowTotal.createCell(7);
			 cellVATValue.setCellFormula("sum(H2:H" + totalnum + ")"); 
			 cellVATValue.setCellStyle(styleTotal);
			
			 //Autofit
			 for (int i = 0; i < 8; i++) {
				 sheet.autoSizeColumn(i);
			 }
			 
			 FileOutputStream out = new FileOutputStream(new File("C:\\Users\\leeay\\OneDrive\\바탕 화면\\TEST\\ROYALTY.xls"));
			 workbook.write(out);
			 out.close();
			 workbook.close();
			 System.out.println("엑셀 생성 완료 ");
		}catch(Exception e) {
			System.out.println("엑셀 생성 실패 ");
		}
		
	}
	
	// ----------------- 차트 만들기 -----------------------------
	public void PieChartM(ResultSet rs) {
		int total = 0;
		String purchasecuscode = null;
		List <String> result = new ArrayList<String>();
		List <Integer> resultP = new ArrayList<Integer>(); 
		try {
			while(rs.next()) {
				purchasecuscode = rs.getString(1);
				result.add((rs.getString(2)));
				total += rs.getInt(3);
				resultP.add((rs.getInt(3)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		pieC.setTitle(purchasecuscode + "작품별 로열티 비율 (%)");
		ObservableList<PieChart.Data> pieCdata = FXCollections.observableArrayList();
		for( int i=0; i < result.size(); i++) {
			double Percent = Math.round((resultP.get(i)/(double)total) * 100);
			pieCdata.add(new PieChart.Data(result.get(i), Percent));
		}
		pieC.setData(pieCdata);
		pieC.setLabelLineLength(20);
		pieC.setLegendSide(Side.BOTTOM);
		
		Lcaption.setTextFill(Color.DARKCYAN);
		Lcaption.setStyle("-fx-font: 13 arial;");
		
		for( final PieChart.Data data : pieC.getData()) {
			data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					Lcaption.setTranslateX(e.getSceneX() - Lcaption.getLayoutX());
					Lcaption.setTranslateY(e.getSceneY() - Lcaption.getLayoutY());
					Lcaption.setText(String.valueOf(data.getPieValue()) + "%");
				}
			});
		}
	}
	
	
	public ResultSet getdataforbarC(String searchkey, String Sdate, String Edate) {
		Util = JdbcConnection.getInstance();
		conn = Util.getConnection();
		query.delete(0, query.length());
		ResultSet rs = null;
		try {
			query.append("SELECT PURCHASECODEN, purchasetitlen, SUM(PURCHASESPRICEWON), SUM(PURCHASEPRICE) FROM PURCHASE ");
			query.append("WHERE PURCHASDOCDATE BETWEEN ? AND ? AND PURCHASECODEN=? ");
			query.append("GROUP by PURCHASECODEN, purchasetitlen ");
			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, Sdate);
			pstmt.setString(2, Edate);
			pstmt.setString(3, searchkey);
			rs = pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public void BarChartM(ResultSet rs) {
		if(BarC.getData().size() > 0) {
			BarC.getData().remove(0, 2);
		}
		int Stotal = 0;
		int Ptotal = 0;
		String purchasecuscode = null;
		List <String> title = new ArrayList<String>();
		List <Integer> sales = new ArrayList<Integer>(); 
		List <Integer> purchase = new ArrayList<Integer>(); 
		try {
			while(rs.next()) {
				purchasecuscode = rs.getString(1);
				title.add(rs.getString(2));
				sales.add(rs.getInt(3));
				purchase.add(rs.getInt(4));
				Stotal += rs.getInt(3);
				Ptotal += rs.getInt(4);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		BarC.setTitle(purchasecuscode + "판매 및 로열티 ");
		CategoryAxis xAxis = new CategoryAxis(); 
		NumberAxis yAxis = new NumberAxis();
		
		yAxis.setLabel("금액");
		xAxis.setLabel("타이틀");
		
		XYChart.Series Ssales = new XYChart.Series();
		XYChart.Series Spurchase = new XYChart.Series();
		Ssales.setName("판매"); 
		Spurchase.setName("로열티"); 
		
			// 총합계 추가
			Ssales.getData().add(new XYChart.Data("총합계", Stotal));			
			for(int i=0; i < title.size(); i++) {
				Ssales.getData().add(new XYChart.Data(title.get(i), sales.get(i)));
			}
			
			Spurchase.getData().add(new XYChart.Data("총합계", Ptotal));
			for(int i=0; i < title.size(); i++) {
				Spurchase.getData().add(new XYChart.Data(title.get(i), purchase.get(i)));
			}
			
		BarC.getData().addAll(Ssales, Spurchase);
		BarC.setCategoryGap(20);
	}
}
