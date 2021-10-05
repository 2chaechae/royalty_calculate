package RAS;

import java.net.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import java.util.Date;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class salesController implements Initializable {
	// ------------------- 화면컴포넌트 --------------------------
	@FXML public Button btnsalessearch;
	@FXML public Button btnsalesadd;
	@FXML public Button btnsalesdelete;
	@FXML public Button btnsalescreate;
	@FXML public Button btnsearchdata;
	@FXML public TableView<SalesVO> tvsales;
	@FXML public TableColumn<SalesVO, String> CsalesCD;
	@FXML public TableColumn<SalesVO, String> Csalesdocdate;
	@FXML public TableColumn<SalesVO, String> Csalesdate;
	@FXML public TableColumn<SalesVO, String> CsalesaccountN;
	@FXML public TableColumn<SalesVO, Integer> Csalescuscode;
	@FXML public TableColumn<SalesVO, String> CsalescusN;
	@FXML public TableColumn<SalesVO, Integer> CsalestitleC;
	@FXML public TableColumn<SalesVO, String> CsalestitleN;
	@FXML public TableColumn<SalesVO, String> Csalesprice;
	@FXML public TableColumn<SalesVO, String> Csalescurrency;
	@FXML public TableColumn<SalesVO, Integer> Csalesrate;
	@FXML public TableColumn<SalesVO, String> Csaleswon;
	@FXML public TableColumn<SalesVO, String> CsalesVatC;
	@FXML public TableColumn<SalesVO, String> CsalesVat;
	@FXML public TableColumn<SalesVO, String> CsalesWriter;
	@FXML public TableColumn<SalesVO, String> CsaleswriteP;
	@FXML public DatePicker dpsalesdocdate;
	@FXML public DatePicker dpsalesdate;
	@FXML public TextField txtsalescontnum;
	@FXML public TextField txtsalesnum;
	@FXML public TextField txtsalescuscode;
	@FXML public TextField txtsalescusN;
	@FXML public TextField txtsalestitleC;
	@FXML public TextField txtsalestitleN;
	@FXML public TextField txtsalesVat;
	@FXML public TextField txtsalesWt;
	@FXML public TextField txtsalesrate;
	@FXML public TextField txtsalesprice;
	@FXML public TextField txtsaleswon;
	@FXML public TextField extra;
	@FXML public ComboBox<String> cbsalesCD;
	@FXML public ComboBox<String> cbsalesAccountN;
	@FXML public ComboBox<String> cbsalesVatC;
	@FXML public ComboBox<String> cbsalesCurr;
	private ObservableList<String> listCD = FXCollections.observableArrayList("차변", "대변");
	private ObservableList<String> listAccountN = null;
	private ObservableList<String> listVatC = FXCollections.observableArrayList("부가세10%", "불공제", "영세율");
	private ObservableList<String> listCurr = FXCollections.observableArrayList("KRW", "USD");
	
	// -------------- 기타 컴포넌트 ----------------------
	private DialogMessage D = DialogMessage.getInstance();
	private ObservableList<SalesVO> SalesList = FXCollections.observableArrayList();
	private SalesDao Sdao = SalesDao.getInstance();
	private DecimalFormat formatter = new DecimalFormat( "###,###");
	private int saleshap = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbsalesCD.setItems(listCD);
		cbsalesVatC.setItems(listVatC);
		cbsalesCurr.setItems(listCurr);
		
		CsalesCD.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesCD"));
		Csalesdocdate.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesdocdate"));
		Csalesdate.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesdate"));
		CsalesaccountN.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesaccountN"));
		Csalescuscode.setCellValueFactory(new PropertyValueFactory<SalesVO, Integer>("salescuscode"));
		CsalescusN.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salescusN"));
		CsalestitleC.setCellValueFactory(new PropertyValueFactory<SalesVO, Integer>("salestitleC"));
		CsalestitleN.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salestitleN"));
		Csalesprice.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesprice"));
		Csalescurrency.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salescurrency"));
		Csalesrate.setCellValueFactory(new PropertyValueFactory<SalesVO, Integer>("salesrate"));
		Csaleswon.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("saleswon"));
		CsalesVatC.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesVatC"));
		CsalesVat.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesVat"));
		CsalesWriter.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("salesWriter"));
		CsaleswriteP.setCellValueFactory(new PropertyValueFactory<SalesVO, String>("saleswriteP"));
	
		tvsales.setPlaceholder(new Label(" "));
	
	}
	
	
	//--------------------------- 이벤트 ----------------------
	
	@FXML
	public void handlebtnsalessearch() {
		SalestxtClear(4);
		saleshap = 0;
		extra.setText(String.valueOf(saleshap));
		if(SalesList.size() > 0 ) {
			removelist();
		}
		String searchkey = txtsalescontnum.getText();
		ResultSet rs = Sdao.salessearchcont(searchkey);
		
		try {
			while(rs.next()) {
				txtsalescuscode.setText(String.valueOf(rs.getInt(3)));
				txtsalescusN.setText(rs.getString(4));
				txtsalestitleC.setText(String.valueOf(rs.getInt(5)));
				txtsalestitleN.setText(rs.getString(6));
				txtsalesprice.setText(String.valueOf(rs.getInt(10)));
				txtsalesWt.setText(LoginController.login_id.toString());
			}
		} catch (SQLException e) {
			System.out.println("자료형 오류");
		}
	}
	
	@FXML
	public void handlebtnsalescreate() {
		int [] result = new int[2];
		if(saleshap == 0) {
			for(int i=0; i < SalesList.size(); i++) {
				SalesVO data = SalesList.get(i);
				String salesCD = "";
				String salesdocdate = "";
				String salesdate = "";
				String salesAccountN = "";
				int salescuscode = 0;
				String salescusN = "";
				int salestitleC = 0;
				String salestitleN = "";
				String salesprice = "";
				String salesCurr = "";
				String salesWt = "";
				String pwrite = "미작성";
				String salesVatC = "";
				String salesVat = "";
				int salesrate = 0;
				String saleswon = "";
				try {
					salesCD = data.getSalesCD();
					salesdocdate = data.getSalesdocdate().toString();
					salesdate = data.getSalesdate().toString();
					salesAccountN = data.getSalesaccountN();
					salescuscode = data.getSalescuscode();
					salescusN = data.getSalescusN();
					salestitleC = data.getSalestitleC();
					salestitleN = data.getSalestitleN();
					salesprice = data.getSalesprice();
					salesCurr = data.getSalescurrency();
					salesWt = data.getSalesWriter();
					pwrite = "미작성";
					salesVatC = data.getSalesVatC();
					salesVat = data.getSalesVat();
					salesrate = data.getSalesrate();
					saleswon = data.getSaleswon();
				}catch(NullPointerException e) {
					continue;
				}
				
				result = Sdao.salescreate(salesCD, salesdocdate, salesdate, salesAccountN, salescuscode, salescusN, salestitleC, salestitleN,
						salesprice, salesCurr, salesWt, pwrite, salesVatC, salesVat, salesrate, saleswon, i);			
			}
			saleshap = 0;
			txtsalesnum.setText(String.valueOf(result[0]));
			if(result[1] == 1) { D.dialog("전표생성", "매출 전표 생성 되었습니다.");}
			else {	D.dialog("전표생성", "매출 전표 생성 실패하였습니다.");
			}
		}
		else { D.dialog("전표생성", "차대변합이 맞지않습니다.");}
		
		removelist();
		SalestxtClear(3);
		Sdao.sequenceup();
		
	}
	
	@FXML
	public void handlebtnsalesadd() {
		try {
			String salesCD = cbsalesCD.getValue();
			String salesdocdate = dpsalesdocdate.getValue().toString();
			String salesdate = dpsalesdate.getValue().toString();
			String salesAccountN = cbsalesAccountN.getValue();
			int salescuscode = Integer.parseInt(txtsalescuscode.getText());
			String salescusN = txtsalescusN.getText();
			int salestitleC = Integer.parseInt(txtsalestitleC.getText());
			String salestitleN = txtsalestitleN.getText();
			String salesprice = txtsalesprice.getText();
			String salesCurr = cbsalesCurr.getValue();
			String salesWt = txtsalesWt.getText();
			String pwrite = "미작성";
			String salesVatC = "";
			String salesVat = "";
			int salesrate = 0;
			String saleswon = "";
	
			boolean check = checkstmt(salesdocdate, salesdate);
			
			if(salesCD.equals("대변")) {
				if(salesCurr.equals("KRW")) {
						salesVatC  = cbsalesVatC.getValue();
						double tmp = Double.parseDouble(salesprice);
						if(salesVatC.equals("부가세10%")) {
							tmp = Math.round(tmp * 0.1);
							salesVat = String.format("%.0f", tmp);
						}
						else if(salesVatC.equals("불공제")) {
								salesVat = "0";
								txtsalesVat.setText(salesVat);
								tmp = Math.round(tmp * 0.1);
								salesprice = String.format("%.0f", (Integer.parseInt(salesprice) + tmp));
								saleshap = saleshap + (Integer.parseInt(salesprice));
						}
						else if(salesVatC.equals("영세율")) {
							salesVat = "0";
							txtsalesVat.setText(salesVat);

						}	
						saleswon = salesprice;
						saleshap = (int) (saleshap + (Integer.parseInt(salesprice) + Integer.parseInt(salesVat)));
						salesVat = formatter.format(Integer.parseInt(salesVat));	
						txtsalesVat.setText(salesVat);
					}
					
					else {
						salesrate = Integer.parseInt(txtsalesrate.getText());
						saleswon = txtsaleswon.getText();
						saleshap = saleshap + (Integer.parseInt(saleswon));
					}
				
					
			}else if (salesCD.equals("차변")) {
					if(salesCurr.equals("KRW")) {
						saleswon = salesprice;
						saleshap = saleshap - (Integer.parseInt(salesprice));
					}
					else {
						salesrate = Integer.parseInt(txtsalesrate.getText());
						saleswon = txtsaleswon.getText();
						saleshap = saleshap - (Integer.parseInt(saleswon));
					}
				}
		
			salesprice = formatter.format(Integer.parseInt(salesprice));
			saleswon = formatter.format(Integer.parseInt(saleswon));
			txtsaleswon.setText(saleswon);
			extra.setText(String.valueOf(saleshap));
	
			if(check) {
				SalesVO addtmp = new SalesVO( salesCD, salesdocdate, salesdate, salesAccountN, salescuscode, 
						salescusN, salestitleC, salestitleN, salesprice, salesCurr, salesrate, 
						saleswon, salesVatC, salesVat, salesWt, pwrite);
						SalesList.add(addtmp);
						tvsales.setItems(SalesList);
					}
			
			SalestxtClear(1);
			txtsalesrate.setDisable(true);
			txtsalesVat.setDisable(true);
		
		}catch(NullPointerException e) {
			D.dialog("매출등록", "미입력란이 있습니다");
		}catch(NumberFormatException e) {
			D.dialog("매출등록", "숫자,문자 입력형태를 확인해주세요.");
		}
	}
	
	@FXML
	public void handlebtnsalesdelete() {
		int selected = tvsales.getSelectionModel().getSelectedIndex();
		SalesVO data = SalesList.get(selected);
		String won = data.getSaleswon();
		int won_int = D.Stringintchang(won);
		if(data.getSalesCD().equals("차변")) {
			saleshap += won_int;
		}else {
			saleshap -= won_int;
		}
	
		SalesList.remove(selected);
		tvsales.setItems(SalesList);
		SalestxtClear(2);
		Salesbuttonset(true);
	}
	
	@FXML
	public void handletvclickAction() {
		try {
			int selected = tvsales.getSelectionModel().getSelectedIndex();
			SalesVO data = SalesList.get(selected);
				cbsalesCD.setValue(data.getSalesCD());
				dpsalesdocdate.setValue(LocalDate.parse(data.getSalesdocdate()));
				dpsalesdate.setValue(LocalDate.parse(data.getSalesdate()));
				cbsalesAccountN.setValue(data.getSalesaccountN());
				txtsalescuscode.setText(String.valueOf(data.getSalescuscode()));
				txtsalescusN.setText(data.getSalescusN());
				txtsalestitleC.setText(String.valueOf(data.getSalestitleC()));
				txtsalestitleN.setText(data.getSalestitleN());
				txtsalesprice.setText(data.getSalesprice());
				txtsaleswon.setText(data.getSaleswon());
						
				String salesAccountN = data.getSalesaccountN();
				String salesCurr = data.getSalescurrency();
					if(salesAccountN.equals("매출")){
						if(salesCurr.equals("KRW")) {
							cbsalesVatC.setValue(data.getSalesVatC());
							txtsalesVat.setText(data.getSalesVat());
						}else if(salesCurr.equals("USD")){ 
							cbsalesCurr.setValue(data.getSalescurrency());
							txtsalesrate.setText(String.valueOf(data.getSalesrate()));
						}
					}else { // 매출 외 계정
						cbsalesCurr.setValue(data.getSalescurrency());
						txtsalesrate.setText(String.valueOf(data.getSalesrate()));
					}
					Salesbuttonset(false);
				
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("빈줄 클릭");
		}
	}
	
	
	
	
	//전표 셋팅(계정, 통화)
	
	@FXML
	public void setCD() {
		try {
			String salesCD = cbsalesCD.getValue();
			if(salesCD.equals("차변")) {
				cbsalesVatC.setDisable(true);
				listAccountN = FXCollections.observableArrayList("외상매출금", "현금");
				cbsalesAccountN.setItems(listAccountN);
			}
			else if(salesCD.equals("대변")) {
				cbsalesVatC.setDisable(false);
				 listAccountN = FXCollections.observableArrayList("매출");
				 cbsalesAccountN.setItems(listAccountN);
			}
		}catch(NullPointerException e) {
			System.out.println("등록 버튼 클릭시 combobox null값으로 셋팅, 이에 nullpointer 발생 ");
		}
	}
	

	@FXML
	public void setcurrency() {
		try {
			String salesCurr = cbsalesCurr.getValue();
			String salesCD = cbsalesCD.getValue();
			System.out.println(salesCurr + "," + salesCD);
			if(salesCurr.equals("KRW") && salesCD.equals("대변")) {
				txtsalesrate.setDisable(true);
				txtsaleswon.setDisable(true);
				cbsalesVatC.setDisable(false);
				txtsalesVat.setDisable(false);
				txtsalesrate.setText("");
				txtsaleswon.setText("");
				txtsalesprice.setText("");
	
			}else if(salesCurr.equals("USD")) {
				txtsalesrate.setDisable(false);
				txtsaleswon.setDisable(false);
				cbsalesVatC.setDisable(true);
				txtsalesVat.setDisable(true);
				txtsalesrate.setText("");
				txtsaleswon.setText("");
				txtsalesprice.setText("");
			}
			else if(salesCurr.equals("KRW")) {
				txtsalesrate.setDisable(true);
				txtsaleswon.setDisable(true);
				txtsalesrate.setText("");
				txtsaleswon.setText("");
				txtsalesprice.setText("");
			}
		}catch(NullPointerException e) {
			System.out.println("등록 버튼 클릭시 combobox null값으로 셋팅, 이에 nullpointer 발생 ");
		}
	}
	
	// vat 계산 
	@FXML
	public void culcprice() {
		int salesrate = Integer.parseInt(String.valueOf(txtsalesrate.getText()));
		int salesprice = Integer.parseInt(txtsalesprice.getText());
		int tmp = salesprice * salesrate;
		txtsaleswon.setText(String.valueOf(tmp));
		}

	
	public boolean checkstmt(String salesdocdate, String salesdate) {
		boolean result = true;
		
		// 전표 일자 검증
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM");
		Date nowdate = new Date();
		String date = format1.format(nowdate);
		String docdateYM = salesdocdate.substring(0,7);
		String dateYM = salesdate.substring(0,7);
		
		if((date.equals(docdateYM) && docdateYM.equals(dateYM)) == false) {
			result = false;
			D.dialog("매출전표작성", "당월 전표만 작성가능합니다.");
		}
		return result;
	}
	
	//------------------- 버튼 및 텍스트 셋팅 ----------------------
	public void SalestxtClear(int number) {
		if(number == 1) { //등록
			txtsalescontnum.setText("");
			txtsalesnum.setText("");
			txtsalesVat.setText("");
			txtsalesprice.setText("");
			txtsaleswon.setText("");
			cbsalesCD.setValue(null);
			cbsalesAccountN.setValue(null);
			cbsalesVatC.setValue(null);
			cbsalesCurr.setDisable(true);
			dpsalesdate.setDisable(true);
			dpsalesdocdate.setDisable(true);
			
		}
		if(number == 2) { // 삭제
			dpsalesdocdate.setDisable(true);
			dpsalesdate.setDisable(true);
			txtsalescontnum.setText("");
			txtsalesnum.setText("");
			txtsalescuscode.setDisable(true);
			txtsalescusN.setDisable(true);
			txtsalestitleC.setDisable(true);
			txtsalestitleN.setDisable(true);
			txtsalesVat.setText("");
			txtsalesrate.setText("");
			txtsalesprice.setText("");
			txtsaleswon.setText("");
			cbsalesCD.setValue(null);
			cbsalesAccountN.setValue(null);
			cbsalesVatC.setValue(null);
			cbsalesCurr.setDisable(true);
		}
		if(number == 3) { // 전표 생성 후 초기화
			dpsalesdocdate.setDisable(false);
			dpsalesdate.setDisable(false);
			dpsalesdocdate.setValue(null);
			dpsalesdate.setValue(null);
			txtsalescontnum.setText("");
			txtsalescuscode.setText("");
			txtsalescusN.setText("");
			txtsalestitleC.setText("");
			txtsalestitleN.setText("");
			txtsalesnum.setText("");
			txtsalesVat.setText("");
			txtsalesWt.setText("");
			txtsalesrate.setText("");
			txtsalesprice.setText("");
			txtsaleswon.setText("");
			cbsalesCD.setValue(null);
			cbsalesAccountN.setValue(null);
			cbsalesVatC.setValue(null);
			cbsalesCurr.setDisable(true);
		}
		if(number == 4) { // 조회
			cbsalesCurr.setValue(null);
			cbsalesCurr.setDisable(false);
			txtsalescuscode.setDisable(true);
			txtsalescusN.setDisable(true);
			txtsalestitleC.setDisable(true);
			txtsalestitleN.setDisable(true);
			dpsalesdate.setValue(null);
			dpsalesdocdate.setValue(null);
			dpsalesdocdate.setDisable(false);
			dpsalesdate.setDisable(false);
		}
	}
	
	public void Salesbuttonset(boolean onoff) {
		if(onoff) {
			btnsalesadd.setDisable(false);
			btnsalesdelete.setDisable(true);
		}
		if(onoff == false) {
			btnsalesadd.setDisable(true);
			btnsalesdelete.setDisable(false);
		}
	}
	
	public void removelist() {
		SalesList.remove(0, SalesList.size());
		tvsales.setItems(SalesList);
	}
	
	public void handlesalesPR() {
		removelist();
		btnsalesadd.setDisable(false);
		dpsalesdocdate.setValue(null);
		dpsalesdate.setValue(null);
		txtsalescontnum.setText("");
		txtsalesnum.setText("");
		txtsalescuscode.setText("");
		txtsalescusN.setText("");
		txtsalestitleC.setText("");
		txtsalestitleN.setText("");
		txtsalesVat.setText("");
		txtsalesWt.setText("");
		txtsalesrate.setText("");
		txtsalesprice.setText("");
		txtsaleswon.setText("");
		extra.setText("");
		cbsalesCD.setValue(null);
		cbsalesAccountN.setValue(null);
		cbsalesVatC.setValue(null);
		cbsalesCurr.setValue(null);
	}
}
