package RAS;

import java.net.*;
import java.sql.*;
import java.util.*;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class customerController implements Initializable{
	// ------------- 화면 컴포넌트 ----------------
	@FXML public TextField txtcstsearch;
	@FXML public Button btncussearch;
	@FXML public Button btncusadd;
	@FXML public Button btncusmodify;
	@FXML public TableView<CustomerVO> tvcustomer;
	@FXML public CheckBox check;
	@FXML public CheckBox foreign;
	@FXML public RadioButton radiovendor;
	@FXML public RadioButton radioPO;
	@FXML public ToggleGroup radioinout;
	@FXML public TextField  txtcuscode;
	@FXML public TextField  txtcusname;
	@FXML public TextField  txtcuscopnum;
	@FXML public TextField  txtcusphone;
	@FXML public TextField  txtcusaddress;
	@FXML public TextField  txtcusemail;
	@FXML public TextField  txtcusbank;
	@FXML public TextField  txtcusown;
	@FXML public TextField  txtcusaccount;
	@FXML public TableColumn<CustomerVO, String> Cvopo;
	@FXML public TableColumn<CustomerVO, Integer> Ccuscode;
	@FXML public TableColumn<CustomerVO, String> Ccusname;
	@FXML public TableColumn<CustomerVO, Long> Ccuscopnum;
	@FXML public TableColumn<CustomerVO, String> Ccusphone;
	@FXML public TableColumn<CustomerVO, String> Ccusaddress;
	@FXML public TableColumn<CustomerVO, String> Ccusemail;
	@FXML public TableColumn<CustomerVO, String> Ccusbank;
	@FXML public TableColumn<CustomerVO, String> Ccusown;
	@FXML public TableColumn<CustomerVO, Long> Ccusaccount;
	@FXML public TableColumn<CustomerVO, String> Cforeign;
	
	// -------------- 기타 컴포넌트 --------------------
	private ObservableList<CustomerVO> CustomerList = FXCollections.observableArrayList();
	private DialogMessage D = DialogMessage.getInstance();
	private CustomerDao Cdao = CustomerDao.getInstance();

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tvcustomer.setPlaceholder(new Label(" "));
		Cvopo.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("vendorPO"));
		Ccuscode.setCellValueFactory(new PropertyValueFactory<CustomerVO, Integer>("cuscode"));
		Ccusname.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("cusname"));
		Ccuscopnum.setCellValueFactory(new PropertyValueFactory<CustomerVO, Long>("cuscopnum"));
		Ccusphone.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("cusphone"));
		Ccusaddress.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("cusaddress"));
		Ccusemail.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("cusemail"));
		Ccusbank.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("cusbank"));
		Ccusown.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("cusown"));
		Ccusaccount.setCellValueFactory(new PropertyValueFactory<CustomerVO, Long>("cusaccount"));
		Cforeign.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("cusforeign"));
	}

	//---------------이벤트 -----------------------------
	
	// 고객사 조회
	@FXML
	public void handlebtncussearchAction() {
		tvcustomer.getItems().clear();
		custxtClear();
		setButton(true);
		ResultSet rs = null;
		if(txtcstsearch.getText().isEmpty() == false) {
			String searchkeytmp = txtcstsearch.getText();
			String searchkey = "%" + searchkeytmp + "%";
			rs = Cdao.cusSearchOne(searchkey);
			try {
				if(rs.next()) {
					String phone_str = String.valueOf(rs.getString(5));
					CustomerVO tmp = new CustomerVO(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getLong(4),
						  phone_str,rs.getString(6),rs.getString(7),rs.getString(8),
						  rs.getString(9),rs.getLong(10), rs.getString(11));
					CustomerList.add(tmp);
				}else { D.dialog("거래처조회", "등록된 거래처가 없습니다."); }
			} catch (SQLException e) { System.out.println("자료형 오류");}
		}// end if
		
		else if(txtcstsearch.getText().isEmpty() == true) {
		    rs = Cdao.cusSearchAll();
			try {
				while(rs.next()) {
					CustomerVO tmp =  new CustomerVO(rs.getString(1),rs.getInt(2),rs.getString(3),rs.getLong(4),
									  rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),
									  rs.getString(9),rs.getLong(10), rs.getString(11));
					CustomerList.add(tmp);
				}
			} catch (SQLException e) { System.out.println("자료형 오류");}
		}
			tvcustomer.setItems(CustomerList);
		}
	
	// 거래처 등록 ( 사업자 등록번호 검증 , 해외 국내에 따라 확인 내용 상이)
	@FXML
	public void handlebtncusaddAction() {
		try {
			// ---- 국내 사업자등록증 검증 누락 
			if(foreign.isSelected() == false && check.isSelected() == false) {
				D.dialog("거래처등록", "사업자등록 검증 누락");
			}
			// --- 국내 사업자등록증 검증 완 
	
			else if(foreign.isSelected() == false && check.isSelected() == true) {
				String VenderPO = null;
				boolean check = radiovendor.isSelected();
				if(check) { VenderPO = "매출";}
				else { VenderPO = "매입"; }
				String cusname = txtcusname.getText();
				long cuscopnum = Long.valueOf(txtcuscopnum.getText());
				String cusphone = txtcusphone.getText();
				String cusaddress = txtcusaddress.getText();
				String cusemail = txtcusemail.getText();
				String cusbank = txtcusbank.getText();
				String cusown = txtcusown.getText();
				long cusaccount = Long.valueOf(txtcusaccount.getText());
				String cusforeign = null;
				boolean forcheck = foreign.isSelected();
				if(forcheck) {cusforeign = "해외";}
				else {cusforeign = "국내";}
				
				Cdao.cusAdd(VenderPO, cusname, cuscopnum, cusphone, cusaddress, cusemail,
						cusbank, cusown, cusaccount, cusforeign);
			}
			// ---- 해외 거래처 사업자 등록증 검증 없이도 통과
			else if(foreign.isSelected() == true) { 
				String VenderPO = null;
				boolean check = radiovendor.isSelected();
				if(check) { VenderPO = "매출";}
				else { VenderPO = "매입"; }
				String cusname = txtcusname.getText();
				long cuscopnum = 0;
				String cusphone = "";
				String cusaddress = txtcusaddress.getText();
				String cusemail = txtcusemail.getText();
				String cusbank = txtcusbank.getText();
				String cusown = txtcusown.getText();
				long cusaccount = Long.valueOf(txtcusaccount.getText());
				String cusforeign = null;
				boolean forcheck = foreign.isSelected();
				if(forcheck) {cusforeign = "해외";}
				else {cusforeign = "국내";}
				
				Cdao.cusAdd(VenderPO, cusname, cuscopnum, cusphone, cusaddress, cusemail,
						cusbank, cusown, cusaccount, cusforeign);
			}
		}catch(NumberFormatException e) {
			D.dialog("거래처등록", "계좌,전화번호는 숫자로입력해주세요");
		}
		custxtClear();
	}
	
	// 고객사 정보 수정 
	@FXML
	public void handlebtncusmodifyAction() {
		int cuscode = Integer.parseInt(txtcuscode.getText());
		String cusname = txtcusname.getText();
		String cusphone = txtcusphone.getText();
		String cusaddress = txtcusaddress.getText();
		String cusemail = txtcusemail.getText();
		String cusbank = txtcusbank.getText();
		String cusown = txtcusown.getText();
		long cusaccount = Long.valueOf(txtcusaccount.getText());
		Cdao.cusModify(cuscode, cusname, cusphone, cusaddress, cusemail, cusbank, cusown, cusaccount);
		custxtClear();
		setButton(true);
	}
	
	// 테이블뷰 클릭시 해당 정보 가져오기 
	@FXML
	public void handleclickeditemsAction() {
		custxtClear();
		btncusadd.setDisable(true);
		btncusmodify.setDisable(false);
		int selected = 0 ;
		try {
			selected = tvcustomer.getSelectionModel().getSelectedIndex();

			CustomerVO data = tvcustomer.getItems().get(selected);
			if(data.getVendorPO().equals("매출")) { radiovendor.setSelected(true);}
			else {radioPO.setSelected(true);}
			txtcuscode.setText(String.valueOf(data.getCuscode()));
			txtcusname.setText(data.getCusname());
			txtcusphone.setText(data.getCusphone());
			txtcusaddress.setText(data.getCusaddress());
			txtcusemail.setText(data.getCusemail());
			txtcusbank.setText(data.getCusbank());
			txtcusown.setText(data.getCusown());
			txtcusaccount.setText(String.valueOf(data.getCusaccount()));
			if(data.getCusforeign().equals("해외")) {
				foreign.setSelected(true);
				check.setSelected(false);
			}else if(data.getCusforeign().equals("국내")){
				foreign.setSelected(false);
				check.setSelected(true);
				txtcuscopnum.setText(String.valueOf(data.getCuscopnum()));
			}
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("데이터가 없는 곳 틀릭");
		}
		setButton(false);
	}
	
	// ------------ 기타 검증 및 setting 메소드 
	public void checkcopnum() {
		try {
			String tmp = "매입";
			if(radiovendor.isSelected()) { tmp = "매출"; }
			Long cuscopnum_int = Long.parseLong(txtcuscopnum.getText());
			
		
			if(txtcuscopnum.getText().length() < 10 || txtcuscopnum.getText().length() > 10) {
				D.dialog("거래처등록", "사업자등록증은 10자리 입니다.");
				check.setSelected(false);
				return;
			}
			
			else {
				int result = Cdao.checknumber(tmp, cuscopnum_int);
				if(result == 1) {
					D.dialog("거래처등록", "등록된 사업자등록번호입니다.");
				}else {
					D.dialog("거래처등록", "검증완료");
				}
			}
		}catch(NumberFormatException e) {
			D.dialog("사업자등록검증", "숫자로입력해주세요");
		}
	}

	public void custxtClear() {
		radiovendor.setSelected(false);
		txtcusname.setText("");
		txtcuscode.setText("");
		txtcuscopnum.setText("");
		txtcusphone.setText("");
		txtcusaddress.setText("");
		txtcusemail.setText("");
		txtcusbank.setText("");
		txtcusown.setText("");
		txtcusaccount.setText("");
		check.setSelected(false);
		foreign.setSelected(false);
	}

	public void setButton(boolean onoff) {
		if(onoff == true) { // 등록
			btncusadd.setDisable(false);
			btncusmodify.setDisable(true);
			txtcuscode.setDisable(true);
			txtcusname.setDisable(false);
			txtcuscopnum.setDisable(false);
			txtcusphone.setDisable(false);
			txtcusaddress.setDisable(false);
			txtcusemail.setDisable(false);
			txtcusbank.setDisable(false);
			txtcusown.setDisable(false);
			txtcusaccount.setDisable(false);
			check.setSelected(false);
			radiovendor.setDisable(false);
			radioPO.setDisable(false);
			foreign.setDisable(false);
			check.setDisable(false);
		}
		if(onoff == false) { //수정
			btncusadd.setDisable(true);
			btncusmodify.setDisable(false);
			txtcuscode.setDisable(true);
			txtcusname.setDisable(false);
			txtcuscopnum.setDisable(true);
			txtcusphone.setDisable(false);
			txtcusaddress.setDisable(false);
			txtcusemail.setDisable(false);
			txtcusbank.setDisable(false);
			txtcusown.setDisable(false);
			txtcusaccount.setDisable(false);
			radiovendor.setDisable(true);
			radioPO.setDisable(true);
			foreign.setDisable(true);
			check.setDisable(true);
		}
	}
	
	public void handlecustPR() {
		txtcstsearch.setText("");
		btncussearch.setDisable(false);
		btncusadd.setDisable(false);
		btncusmodify.setDisable(true);
		tvcustomer.getItems().clear();
		check.setSelected(false);
		foreign.setSelected(false);
		radiovendor.setSelected(false);
		radioPO.setSelected(false);
		radiovendor.setDisable(false);
		radioPO.setDisable(false);
		txtcuscode.setText("");
		txtcusname.setText("");
		txtcuscopnum.setText("");
		txtcuscopnum.setDisable(false);
		txtcusphone.setText("");
		txtcusaddress.setText("");
		txtcusemail.setText("");
		txtcusbank.setText("");
		txtcusown.setText("");
		txtcusaccount.setText("");

	}
}
