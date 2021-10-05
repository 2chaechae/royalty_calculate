package RAS;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.time.*;
import java.util.*;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.*;

import javafx.stage.*;

public class contractController implements Initializable{
	
	//------------------- 계약 관련 컴포넌트 ----------
	@FXML public TextField txtcontsearch;
	@FXML public TextField txtcontnum;
	@FXML public TextField txtcontcuscode;
	@FXML public TextField txtcontcusname;
	@FXML public TextField txtconttitle;
	@FXML public TextField txtcontipcode;
	@FXML public TextField txtcontprice;
	@FXML public TextField txtcontroyalty;
	@FXML public RadioButton radiocontV;
	@FXML public RadioButton radiocontP;
	@FXML public ToggleGroup contract;
	@FXML public DatePicker txtcontstart;
	@FXML public DatePicker txtcontend;
	@FXML public Button btncontsearch;
	@FXML public Button btncontadd;
	@FXML public Button btncontmodify;
	@FXML public TableView<ContractVO> tvcont;
	@FXML public ChoiceBox<String> boxcurrency;
	@FXML public ChoiceBox<String> searchvalue1;
	@FXML public ChoiceBox<String> searchvalue2;
	@FXML public TableColumn<ContractVO, String> Cvopo_cont;
	@FXML public TableColumn<ContractVO, String> contnum;
	@FXML public TableColumn<ContractVO, Integer> contcuscode;
	@FXML public TableColumn<ContractVO, String> contcusname;
	@FXML public TableColumn<ContractVO, Integer> contipcode;
	@FXML public TableColumn<ContractVO, String> conttitle;
	@FXML public TableColumn<ContractVO, String> contstart;
	@FXML public TableColumn<ContractVO, String> contend;
	@FXML public TableColumn<ContractVO, String> contcurrency;
	@FXML public TableColumn<ContractVO, Integer> contprice;
	@FXML public TableColumn<ContractVO, Integer> controyalty;
	@FXML public ImageView plus;
	

	private DialogMessage D = DialogMessage.getInstance();
	private ObservableList<ContractVO> Contractlist = FXCollections.observableArrayList();
	ContractDao Contdao = ContractDao.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		searchvalue1.setItems (FXCollections.observableArrayList ( "매출", "매입"));
		searchvalue2.setItems (FXCollections.observableArrayList ( "계약번호", "타이틀명", "계약거래처"));
		boxcurrency.setItems (FXCollections.observableArrayList ( "USD", "KRW"));

		Cvopo_cont.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("vendorPO"));
		contnum.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("contnum"));
		contcuscode.setCellValueFactory(new PropertyValueFactory<ContractVO, Integer>("contcuscode"));
		contcusname.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("contcusname"));
		contipcode.setCellValueFactory(new PropertyValueFactory<ContractVO, Integer>("contipcode"));
		conttitle.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("conttitle"));
		contstart.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("contstart"));
		contend.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("contend"));
		contcurrency.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("currency"));
		contprice.setCellValueFactory(new PropertyValueFactory<ContractVO, Integer>("contprice"));
		controyalty.setCellValueFactory(new PropertyValueFactory<ContractVO, Integer>("controyalty"));
		tvcont.setPlaceholder(new Label(" "));
	
	}
	
	//--------------------------이벤트----------------------------
	
	//조건 조회
	@FXML
	public void handlebtncontsearchAction() {
		setContButton(true);
		tvcont.getItems().clear();
		String value1 = searchvalue1.getValue();
		String value2 = searchvalue2.getValue();
		String value3 = txtcontsearch.getText();
		if(value1 == null && value2 == null ) {
			Contractlist = Contdao.contractsearchAll();
			tvcont.setItems(Contractlist);
		}
		
		else if(value1 == null) {
			Contractlist = Contdao.contractsearch(value2, value3);
			tvcont.setItems(Contractlist);
			}
		else if(value2 == null) {
			Contractlist = Contdao.contractsearch(value1);
			tvcont.setItems(Contractlist);
			}
		else if(value1 != null && value2 != null) {
			Contractlist = Contdao.contractsearch(value1, value2, value3);
			tvcont.setItems(Contractlist);
		}
		

	}
	
	// 매출 매입 계약 등록
	@FXML
	public void handlebtncontaddAction() {
		try {
			String VendorPO = null;
			boolean check = radiocontV.isSelected();
			if(check) { VendorPO = "매출";}
			else { VendorPO = "매입"; }
			String cusname = txtcontcusname.getText();
			String contcuscode = txtcontcuscode.getText();
			if((VendorPO.equals("매출") && contcuscode.charAt(0) == 49) || (VendorPO.equals("매입") && contcuscode.charAt(0) == 53)){
				String conttitle = txtconttitle.getText();
				int contipcode = Integer.parseInt(txtcontipcode.getText());
				String contstart = txtcontstart.getValue().toString();
				String contend = txtcontend.getValue().toString();
				String currency = boxcurrency.getValue();
				String contprice = txtcontprice.getText();
				int contprice_int = makenu(contprice);
				int controyalty = Integer.parseInt(txtcontroyalty.getText());
					if(datecheck(contstart, contend) == true) {
						Contdao.contractadd(VendorPO, cusname, contcuscode, conttitle, contipcode, contstart, contend, currency, contprice_int, controyalty);
					}
					else {
						D.dialog("계약 등록", "만료일자 확인해주세요.");
					}
			}else {
				D.dialog("계약등록", "거래처코드와 계약종류가 일치해야합니다.");
			}
		}catch(NumberFormatException e) {
			D.dialog("계약 등록", "로열티와 금액은 숫자로 입력하세요.");
		}

	}
	
	// 계약 수정
	@FXML
	public void handlebtncontmodifyAction() {	
		String cuntnum = txtcontnum.getText();
		String contend = txtcontend.getValue().toString();
		int controyalty = Integer.parseInt(txtcontroyalty.getText());
		Contdao.contractmodify(cuntnum, contend, controyalty);
		conttxtClear();
		setContButton(true);
	}
	
	// 테이블뷰 클릭시 정보 가져오기 
	@FXML
	public void handletvcontclickAction() throws InvocationTargetException {
		setContButton(false);
		int selected = tvcont.getSelectionModel().getSelectedIndex();
		ContractVO data = tvcont.getItems().get(selected);
			if(data.getVendorPO().equals("매출")) { radiocontV.setSelected(true);}
			else { radiocontV.setSelected(false);}
			txtcontnum.setText(data.getContnum());
			txtcontcusname.setText(data.getContcusname());
			txtcontcuscode.setText(String.valueOf(data.getContcuscode()));
			txtconttitle.setText(data.getConttitle());
			txtcontipcode.setText(String.valueOf(data.getContipcode()));
			txtcontstart.setValue(LocalDate.parse(data.getContstart()));
			txtcontend.setValue(LocalDate.parse(data.getContend()));
			boxcurrency.setValue(data.getCurrency());
			txtcontprice.setText(data.getContprice());
			txtcontroyalty.setText(String.valueOf(data.getControyalty()));
	}	
	
	// 날짜 검증, 버튼셋팅, 초기화
	public boolean datecheck(String S, String E) {
		boolean result = true;
		String[] start = S.split("-");
		String[] end = E.split("-");
		StringBuffer tmpS = new StringBuffer();
		StringBuffer tmpE = new StringBuffer();
		
		for (int i=0 ; i < start.length; i++) {
			 tmpS.append(start[i]);
		}
		for (int i=0 ; i < end.length; i++) {
			 tmpE.append(end[i]);
		}
		
		int a = Integer.parseInt(tmpS.toString());
		int b = Integer.parseInt(tmpE.toString());
		
		if((a-b) > 0) {
			result = false;
		}
		return result;
	}
	
	public void setContButton(boolean onoff) {
		if(onoff == true) {
			btncontadd.setDisable(false);
			btncontmodify.setDisable(true);
			radiocontP.setDisable(false);
			radiocontV.setDisable(false);
			txtcontstart.setDisable(false);
			txtcontprice.setDisable(false);
			txtcontcuscode.setDisable(false);
			txtcontcusname.setDisable(false);
			txtconttitle.setDisable(false);
			txtcontipcode.setDisable(false);
			boxcurrency.setDisable(false);
		}
		else if(onoff == false) {
			btncontadd.setDisable(true);
			btncontmodify.setDisable(false);
			radiocontP.setDisable(true);
			radiocontV.setDisable(true);
			txtcontstart.setDisable(true);
			txtcontprice.setDisable(true);
			txtcontcuscode.setDisable(true);
			txtcontcusname.setDisable(true);
			txtconttitle.setDisable(true);
			txtcontipcode.setDisable(true);
			boxcurrency.setDisable(true);
		
		}
	}
	
	public void conttxtClear() {
		txtcontstart.setValue(null);
		txtcontend.setValue(null);
		txtcontnum.setText("");
		txtcontcuscode.setText("");
		txtcontcusname.setText("");
		txtconttitle.setText("");
		txtcontipcode.setText("");
		txtcontprice.setText("");
		txtcontroyalty.setText("");
		radiocontV.setSelected(false);
		radiocontP.setSelected(false);
	}
	
	public void handlecontPR() {
		txtcontsearch.setText("");
		txtcontnum.setText("");
		txtcontcuscode.setText("");
		txtcontcusname.setText("");
		txtconttitle.setText("");
		txtcontipcode.setText("");
		txtcontprice.setText("");
		txtcontroyalty.setText("");
		radiocontV.setSelected(false);
		radiocontP.setSelected(false);
		txtcontstart.setValue(null);
		txtcontend.setValue(null);
		btncontsearch.setDisable(false);
		btncontadd.setDisable(false);
		btncontmodify.setDisable(true);
		tvcont.getItems().clear();
		boxcurrency.setValue(null);
		searchvalue1.setValue(null);
		searchvalue2.setValue(null);

	}
	
	// 문자 -> 숫자 변환 
	public int makenu(String num) {
		String tmp = "";
		num = num.trim();
		String[] num_arry = num.split(",");
		for (int i=0; i < num_arry.length; i++) {
			tmp += num_arry[i];
		}
		int result = Integer.parseInt(tmp.toString());
		return result;
	}
	
	// 계약 화면 다이얼로그 불러오기.
	@FXML
	public void handleopenD() {
		if(radiocontV.isSelected()) { Dialog_programController.vpo = "매출"; }
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setTitle("타이틀조회");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("dialog_program.fxml"));
			Parent parentDialog = loader.load();
			Scene scene = new Scene(parentDialog);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
		} catch (IOException e) {
			System.out.println("로드실패");
		}
	}
	
	// 거래처, 프로그램 조회해서 텍스트 적용
	@FXML
	public void handlesearchcode() {
		String programN  = txtconttitle.getText();
		String programN_str = "%" + programN + "%";
		String [] result = Contdao.programsearch(programN_str);
		txtcontipcode.setText(result[0]);
		txtconttitle.setText(result[1]);
	}
	
	@FXML
	public void handlesearchcuscode() {
		String tmp = "매입";
		if (radiocontV.isSelected()) {tmp="매출";}
		String customerN  = txtcontcusname.getText();
		String customerN_str = "%" + customerN + "%";
		String [] result = Contdao.customersearch(customerN_str, tmp);
		txtcontcuscode.setText(result[0]);
		txtcontcusname.setText(result[1]);
	}

	
}
