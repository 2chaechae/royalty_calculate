package RAS;

import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class salesSearchController implements Initializable{
	// ----------------- 화면 컴포넌트 ---------------------------
	@FXML public ComboBox<String> cbsalescondition;
	@FXML public TextField txtsalescondition;
	@FXML public DatePicker dpsalesstartdate;
	@FXML public DatePicker dpsalesenddate;
	@FXML public Button btnsalessearch;
	@FXML public Button btnsalesdelete;
	@FXML public TableView<SalesVO> tvsalessearch;
		
	@FXML public TableColumn<SalesVO, Integer> CSsearchnum;
	@FXML public TableColumn<SalesVO, String> CSsearchdocdate;
	@FXML public TableColumn<SalesVO, String> CSsearchdate;
	@FXML public TableColumn<SalesVO, Integer> CSsearchcusC;
	@FXML public TableColumn<SalesVO, String> CSsearchcusN;
	@FXML public TableColumn<SalesVO, Integer> CSsearchtitleC;
	@FXML public TableColumn<SalesVO, String> CSsearchtitleN;
	@FXML public TableColumn<SalesVO, String> CSsearchprice;
	@FXML public TableColumn<SalesVO, String> CSsearchcurr;
	@FXML public TableColumn<SalesVO, Integer> CSsearchrate;
	@FXML public TableColumn<SalesVO, String> CSsearchpricewon;
	@FXML public TableColumn<SalesVO, String> CSsearchvatC;
	@FXML public TableColumn<SalesVO, String> CSsearchvat;
	@FXML public TableColumn<SalesVO, String> CSsearchwriter;
	@FXML public TableColumn<SalesVO, String> CSsearchpwrite;
	
	//------------------- 기타 컴포넌트 --------------------------
	private DialogMessage D = DialogMessage.getInstance();
	private ObservableList<String> listCondition = FXCollections.observableArrayList("거래처", "타이틀");
	private ObservableList<SalesVO> SalesSearchlist = FXCollections.observableArrayList();
	private SalesDao Sdao = SalesDao.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbsalescondition.setItems(listCondition);
		
		CSsearchnum.setCellValueFactory(new PropertyValueFactory<SalesVO, Integer>("salesnum"));
		CSsearchdocdate.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesdocdate"));
		CSsearchdate.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesdate"));
		CSsearchcusC.setCellValueFactory(new PropertyValueFactory<SalesVO, Integer>("salescuscode"));
		CSsearchcusN.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salescusN"));
		CSsearchtitleC.setCellValueFactory(new PropertyValueFactory<SalesVO, Integer>("salestitleC"));
		CSsearchtitleN.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salestitleN"));
		CSsearchprice.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesprice"));
		CSsearchcurr.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salescurrency"));
		CSsearchrate.setCellValueFactory(new PropertyValueFactory<SalesVO, Integer>("salesrate"));
		CSsearchpricewon.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("saleswon"));
		CSsearchvatC.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesVatC"));
		CSsearchvat.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesVat"));
		CSsearchwriter.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesWriter"));
		CSsearchpwrite.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("saleswriteP"));
	
		tvsalessearch.setPlaceholder(new Label(" "));
	}

	// ----------------- 이벤트 --------------------------
	// 매출 전표 조회
	public void handlesearchsales() {
		removelist();
		ResultSet rs = null;
		String condition1 = "";
		String condition2 = "";
		condition1 = cbsalescondition.getValue();
		condition2 = txtsalescondition.getText();
		condition2 = "%" + condition2 + "%";
		
		if(condition1 == null || condition2 == null) {
			return;
		}
		
		if(dpsalesstartdate.getValue() == null || dpsalesenddate.getValue() == null) {
			rs = Sdao.salessearch(condition1, condition2);
			inputtotableV(rs);
		}
		
		else {
			String condition3 = dpsalesstartdate.getValue().toString();
			String condition4 = dpsalesenddate.getValue().toString();
			rs = Sdao.salessearch(condition1, condition2, condition3, condition4);
			inputtotableV(rs);
		}
	}
	
	// 매출 전표 삭제
	public void handledeletesales() {
		int selected = tvsalessearch.getSelectionModel().getSelectedIndex();
		SalesVO data = SalesSearchlist.get(selected);
		
			// 전표일자 체크
			Date now = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyyMM");
			String nowdate = format1.format(now);
			String docdate_tmp = data.getSalesdocdate().toString().substring(0, 7);
			String[] docdate_arry= docdate_tmp.split("-");
			String docdate = (docdate_arry[0] + docdate_arry[1]);
			
			// 매입작성여부 체크
			String writecheck = data.getSaleswriteP();
			if(nowdate.equals(docdate)) {
				if(writecheck.equals("미작성")) {
					Sdao.salesdelte(data.getSalesnum());
				}else { D.dialog("전표삭제", "작성된 매입 전표가 있습니다.");}
			}else {D.dialog("전표삭제", "당월전표만 삭제가능합니다.");}
	}
	
	// 테이블뷰에 자료 입력 
	public void inputtotableV(ResultSet rs) {
		SalesVO data = null;
		try {
			while(rs.next()) {
				try {
					int salesrate = 0;
					String salesVatC ="";
					String salesVat	= "";			
					
					int salesnum = 	rs.getInt(1);
					String salesdocdate = rs.getString(2);
					String salesdate = rs.getString(3);
					int salescuscode = rs.getInt(4);
					String salescusN = rs.getString(5);
					int salestitleC = rs.getInt(6);
					String salestitleN = rs.getString(7);
					String salesprice = rs.getString(8);
					String salescurrency = rs.getString(9);
					salesrate = rs.getInt(10);
					String saleswon = rs.getString(11);
					salesVatC = rs.getString(12);
					salesVat = rs.getString(13);
					String salesWriter = rs.getString(14);
					String saleswriteP = rs.getString(15);
					
					data = new SalesVO( salesnum, salesdocdate, salesdate, salescuscode, 
							salescusN, salestitleC, salestitleN, salesprice, salescurrency, salesrate, 
							saleswon, salesVatC, salesVat, salesWriter, saleswriteP);
					SalesSearchlist.add(data);
					tvsalessearch.setItems(SalesSearchlist);
				} catch(NullPointerException e) {continue;}
			}// end while
		} catch (SQLException e) { System.out.println("자료형 변환 오류 ");}
	}
	
	public void removelist() {
		SalesSearchlist.remove(0, SalesSearchlist.size());
		tvsalessearch.setItems(SalesSearchlist);
	}
	
	public void handlesalesSearchPR() {
		cbsalescondition.setValue(null);
		txtsalescondition.setText("");
		dpsalesstartdate.setValue(null);
		dpsalesenddate.setValue(null);
		removelist();
	}
}
