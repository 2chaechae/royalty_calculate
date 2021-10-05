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

public class purchaseSearchController implements Initializable{
	//-----------------화면 컴포넌트 ----------------------
	@FXML public ComboBox<String> cbsalescondition;
	@FXML public TextField txtsalescondition;
	@FXML public DatePicker dpsalesstartdate;
	@FXML public DatePicker dpsalesenddate;
	@FXML public Button btnsalessearch;
	@FXML public Button btnsalesdelete;
	@FXML public TableView<PurchaseVO> tvsalessearch;
		
	@FXML public TableColumn<PurchaseVO, Integer> CPSsearchnum;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchdocdate;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchdate;
	@FXML public TableColumn<PurchaseVO, Integer> CPSsearchcusC;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchcusN;
	@FXML public TableColumn<PurchaseVO, Integer> CPSsearchtitleC;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchtitleN;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchSprice;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchwon;
	@FXML public TableColumn<PurchaseVO, Integer> CPSsearchroyalty;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchprice;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchvat;
	@FXML public TableColumn<PurchaseVO, String> CPSsearchwriter;

	//----------------기타 컴포넌트 
	private DialogMessage D = DialogMessage.getInstance();
	private ObservableList<String> listCondition = FXCollections.observableArrayList("거래처", "타이틀");
	private ObservableList<PurchaseVO> SalesSearchlist = FXCollections.observableArrayList();
	private PurchaseDao Pdao = PurchaseDao.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbsalescondition.setItems(listCondition);
		CPSsearchnum.setCellValueFactory(new PropertyValueFactory<PurchaseVO, Integer>("purchasenum"));
		CPSsearchdocdate.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasedocdate"));
		CPSsearchdate.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasedate"));
		CPSsearchcusC.setCellValueFactory(new PropertyValueFactory<PurchaseVO, Integer>("purchasecode"));
		CPSsearchcusN.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasecodeN"));
		CPSsearchtitleC.setCellValueFactory(new PropertyValueFactory<PurchaseVO, Integer>("purchasetitleC"));
		CPSsearchtitleN.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasetitleN"));
		CPSsearchSprice.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchaseSprice"));
		CPSsearchwon.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchaseSpricewon"));
		CPSsearchroyalty.setCellValueFactory(new PropertyValueFactory<PurchaseVO, Integer>("purchaseRoyalty"));
		CPSsearchprice.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchaseprice"));
		CPSsearchvat.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasepricevat"));
		CPSsearchwriter.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasewriter"));
		tvsalessearch.setPlaceholder(new Label(" "));
	}

	// ----------------------- 이벤트 --------------------------
	// 구매전표 조회
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
			rs = Pdao.salessearch(condition1, condition2);
			inputtotableV(rs);
		}
		
		else {
			String condition3 = dpsalesstartdate.getValue().toString();
			String condition4 = dpsalesenddate.getValue().toString();
			rs = Pdao.salessearch(condition1, condition2, condition3, condition4);
			inputtotableV(rs);
		}
	}
	
	// 구매 전표 삭제
	public void handledeletesales() {
		int selected = tvsalessearch.getSelectionModel().getSelectedIndex();
		PurchaseVO data = SalesSearchlist.get(selected);
		
			// 전표일자 체크
			Date now = new Date();
			SimpleDateFormat format1 = new SimpleDateFormat("yyyyMM");
			String nowdate = format1.format(now);
			String docdate_tmp = data.getPurchasedocdate().toString().substring(0, 7);
			String[] docdate_arry= docdate_tmp.split("-");
			String docdate = (docdate_arry[0] + docdate_arry[1]);
			
			if(nowdate.equals(docdate)) {
				Pdao.salesdelte(data.getPurchasenum());
				Pdao.pWriteBack();
			}else {
				D.dialog("전표삭제", "당월전표만 삭제가능합니다.");
			}
	}
	
	// 테이블뷰에 자료 넣기 
	public PurchaseVO inputtotableV(ResultSet rs) {
		PurchaseVO data = null;
		try {
			while(rs.next()) {
				try {
					int purchasenum = rs.getInt(1);
					String purchasDocdate = rs.getString(2);
					String purchasdate = rs.getString(3);
					int purchasecode = rs.getInt(4);
					String purchasecoden = rs.getString(5);
					int purchasetitlec = rs.getInt(6);
					String purchasetitlen = rs.getString(7);
					String purchasesprice = rs.getString(8);
					String purchasespricewon = rs.getString(9);
					int purchaseroyalty = rs.getInt(10);
					String purchaseprice = rs.getString(11);
					String purchasevat = rs.getString(12);
					String saleswriter = rs.getString(13);

					
					data = new PurchaseVO(purchasenum, purchasDocdate, purchasdate, purchasecode,  purchasecoden, purchasetitlec,
							purchasetitlen,  purchasesprice, purchasespricewon, purchaseroyalty, purchaseprice, purchasevat,
							saleswriter);
					
					SalesSearchlist.add(data);
					tvsalessearch.setItems(SalesSearchlist);
				} catch(NullPointerException e) {continue;}
			}// end while
		} catch (SQLException e) {
			 System.out.println("자료형 오류");
		}
		return data;
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