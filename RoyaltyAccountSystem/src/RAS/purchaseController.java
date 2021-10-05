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

public class purchaseController implements Initializable{
	// ------------- 화면 컴포넌트 ------------------------
	@FXML public Button btnpurchasePR;
	@FXML public Button btnpurchasecreate;
	@FXML public Button btnpurchaseapply;
	@FXML public Button btnpurchasesearch;
	@FXML public DatePicker dppurchasedocdate;
	@FXML public DatePicker dppurchasedate;
	@FXML public TextField tatalpurchase;
	@FXML public TableView<PurchaseVO> tvpurchase;

	@FXML public TableColumn<PurchaseVO, Integer> Cpurchasenum;
	@FXML public TableColumn<PurchaseVO, String> CpurchaseSdate;
	@FXML public TableColumn<PurchaseVO, Integer> CpurchasetitleC;
	@FXML public TableColumn<PurchaseVO, String> CpurchasetitleN;
	@FXML public TableColumn<PurchaseVO, String> Cpurchasecurr;
	@FXML public TableColumn<PurchaseVO, String> CpurchaseSprice;
	@FXML public TableColumn<PurchaseVO, String> CpurchaseSpricewon;
	@FXML public TableColumn<PurchaseVO, String> Cpurchasesep;
	@FXML public TableColumn<PurchaseVO, Integer> Cpurchasecode;
	@FXML public TableColumn<PurchaseVO, String> CpurchasecodeN;
	@FXML public TableColumn<PurchaseVO, Integer> CpurchaseRoyalty;
	@FXML public TableColumn<PurchaseVO, String> Cpurchaseprice;
	@FXML public TableColumn<PurchaseVO, String> Cpurchasepricevat;
	@FXML public TableColumn<PurchaseVO, String> CpurchasepriceA;
	@FXML public TableColumn<PurchaseVO, String> Cpurchasewriter;
	
	// ------------------ 기타 컴포넌트 ---------------------
	private ObservableList<PurchaseVO> PurchaseList = FXCollections.observableArrayList();
	private DialogMessage D = DialogMessage.getInstance();
	private PurchaseDao Pdao = PurchaseDao.getInstance();
	private DecimalFormat formatter = new DecimalFormat( "###,###");
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Cpurchasenum.setCellValueFactory(new PropertyValueFactory<PurchaseVO, Integer>("purchaseSnum"));
		CpurchaseSdate.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchaseSdate"));
		CpurchasetitleC.setCellValueFactory(new PropertyValueFactory<PurchaseVO, Integer>("purchasetitleC"));
		CpurchasetitleN.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasetitleN"));
		Cpurchasecurr.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasecurr"));
		CpurchaseSprice.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchaseSprice"));
		CpurchaseSpricewon.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchaseSpricewon"));
		Cpurchasesep.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasesep"));
		Cpurchasecode.setCellValueFactory(new PropertyValueFactory<PurchaseVO, Integer>("purchasecode"));
		CpurchasecodeN.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasecodeN"));
		CpurchaseRoyalty.setCellValueFactory(new PropertyValueFactory<PurchaseVO, Integer>("purchaseRoyalty"));
		Cpurchaseprice.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchaseprice"));
		Cpurchasepricevat.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasepricevat"));
		Cpurchasewriter.setCellValueFactory(new PropertyValueFactory<PurchaseVO, String>("purchasewriter"));
		tvpurchase.setPlaceholder(new Label(" "));
	}
	
	//---------------------- 이벤트 -------------------------
	
	// 매입 전표 대상 조회
	public void handlesearchpurchase() {
		String purchasedocdate = "";
		String purchasedate = "";
		try {
			purchasedocdate = dppurchasedocdate.getValue().toString();
			purchasedate = dppurchasedate.getValue().toString();
		}catch(NullPointerException e) {
			D.dialog("매입전표", "작성일자를 선택해주세요.");
			return;
		}
		String startdate = "";
		String enddate = "";
		ResultSet rs = null;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		Date nowdate = new Date();
		
		String date = format1.format(nowdate);
		String docdateYM = purchasedocdate.substring(0,7);
		String dateYM = purchasedate.substring(0,7);

		String date_tmp = date.substring(5,7);
		if(date_tmp.equals("01")|| date_tmp.equals("03")|| date_tmp.equals("05")|| date_tmp.equals("07")|| 
				date_tmp.equals("08")|| date_tmp.equals("10")|| date_tmp.equals("12")) {
				startdate = date + "-01";
				enddate =  date + "-31";
		}else if(date_tmp.equals("02")){
				startdate = date + "-01";
				enddate =  date + "-28";
		}else {
			startdate = date + "-01";
			enddate =  date + "-30";
		}
	
		if((date.equals(docdateYM) && docdateYM.equals(dateYM)) == true) {
			rs = Pdao.purchasesearch(startdate, enddate);
			tableVset(rs);
		}else { D.dialog("매입전표작성", "당월 전표만 작성가능합니다.");}
	}
	
	// 매입 전표 생성 
	public void handlecreatepurchase() {
		String purchasedocdate = dppurchasedocdate.getValue().toString();
		String purchasedate = dppurchasedate.getValue().toString();
		int result = 0;
			for(int i=0; i<PurchaseList.size(); i++) {
				PurchaseVO data = PurchaseList.get(i);
				int price = D.Stringintchang(data.getPurchaseprice());
				int vat = D.Stringintchang(data.getPurchasepricevat());
				int Salesprice = D.Stringintchang(data.getPurchaseSprice());
				int pricewon = D.Stringintchang(data.getPurchaseSpricewon());
				result = Pdao.purchaseadd(data, purchasedocdate, purchasedate, price, vat, Salesprice, pricewon);
			}
			if(result == 1) {
				D.dialog("매입전표", "매입전표 작성 완료하였습니다.");
				Pdao.pWritechange();
			}else if(result == 0) {
				D.dialog("매입전표", "매입전표 작성 실패하였습니다.");
			}
	}
	
	public void handleclearpuchase() {
		btnpurchasePR.setVisible(true);
		btnpurchasecreate.setVisible(true);
		btnpurchasesearch.setVisible(true);
		dppurchasedocdate.setValue(null);
		dppurchasedate.setValue(null);
		tvpurchase.getItems().clear();
	}
	
	//테이블에 자료 넣기 
	public void tableVset(ResultSet rs) {
		tvpurchase.getItems().clear();
		int hap = 0;
		try {
				while(rs.next()) {
					String purchasewriter = LoginController.login_id;
					String salesdocdate = rs.getString(1); String titleN = rs.getString(2); 
					int titlecode = rs.getInt(3); String currency = rs.getString(4); 
					String salesprice = rs.getString(5); String saleswon = rs.getString(6); 
					String cusforeign = rs.getString(7); int cuscode = rs.getInt(8); 
					String cusname = rs.getString(9); int royalty = rs.getInt(10); int purchaseSnum = rs.getInt(11);
					
					int Salespricewon = D.Stringintchang(saleswon);
					double royalty_db = royalty * 0.01;
					int pPrice = (int) Math.round(Salespricewon * royalty_db);
					double pVat = pPrice * 0.1;
					hap += pPrice;
					
					String pPrice_str = formatter.format(pPrice);
					String pVat_str = formatter.format(pVat);
					
					
					PurchaseVO data = new PurchaseVO(purchaseSnum, salesdocdate, titlecode, titleN, currency, salesprice,
							saleswon, cusforeign, cuscode, cusname, royalty,  pPrice_str,
							pVat_str, purchasewriter);
					
					PurchaseList.add(data);
				}
			tvpurchase.setItems(PurchaseList);
			tatalpurchase.setText(String.valueOf(hap));
		}catch (SQLException e) { System.out.println("자료형 오류");}
	}

}
