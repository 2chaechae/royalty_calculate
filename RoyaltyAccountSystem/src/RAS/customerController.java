package RAS;

import java.net.*;
import java.sql.*;
import java.util.*;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class customerController implements Initializable{
	// ------------- ȭ�� ������Ʈ ----------------
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
	
	// -------------- ��Ÿ ������Ʈ --------------------
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

	//---------------�̺�Ʈ -----------------------------
	
	// ���� ��ȸ
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
				}else { D.dialog("�ŷ�ó��ȸ", "��ϵ� �ŷ�ó�� �����ϴ�."); }
			} catch (SQLException e) { System.out.println("�ڷ��� ����");}
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
			} catch (SQLException e) { System.out.println("�ڷ��� ����");}
		}
			tvcustomer.setItems(CustomerList);
		}
	
	// �ŷ�ó ��� ( ����� ��Ϲ�ȣ ���� , �ؿ� ������ ���� Ȯ�� ���� ����)
	@FXML
	public void handlebtncusaddAction() {
		try {
			// ---- ���� ����ڵ���� ���� ���� 
			if(foreign.isSelected() == false && check.isSelected() == false) {
				D.dialog("�ŷ�ó���", "����ڵ�� ���� ����");
			}
			// --- ���� ����ڵ���� ���� �� 
	
			else if(foreign.isSelected() == false && check.isSelected() == true) {
				String VenderPO = null;
				boolean check = radiovendor.isSelected();
				if(check) { VenderPO = "����";}
				else { VenderPO = "����"; }
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
				if(forcheck) {cusforeign = "�ؿ�";}
				else {cusforeign = "����";}
				
				Cdao.cusAdd(VenderPO, cusname, cuscopnum, cusphone, cusaddress, cusemail,
						cusbank, cusown, cusaccount, cusforeign);
			}
			// ---- �ؿ� �ŷ�ó ����� ����� ���� ���̵� ���
			else if(foreign.isSelected() == true) { 
				String VenderPO = null;
				boolean check = radiovendor.isSelected();
				if(check) { VenderPO = "����";}
				else { VenderPO = "����"; }
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
				if(forcheck) {cusforeign = "�ؿ�";}
				else {cusforeign = "����";}
				
				Cdao.cusAdd(VenderPO, cusname, cuscopnum, cusphone, cusaddress, cusemail,
						cusbank, cusown, cusaccount, cusforeign);
			}
		}catch(NumberFormatException e) {
			D.dialog("�ŷ�ó���", "����,��ȭ��ȣ�� ���ڷ��Է����ּ���");
		}
		custxtClear();
	}
	
	// ���� ���� ���� 
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
	
	// ���̺�� Ŭ���� �ش� ���� �������� 
	@FXML
	public void handleclickeditemsAction() {
		custxtClear();
		btncusadd.setDisable(true);
		btncusmodify.setDisable(false);
		int selected = 0 ;
		try {
			selected = tvcustomer.getSelectionModel().getSelectedIndex();

			CustomerVO data = tvcustomer.getItems().get(selected);
			if(data.getVendorPO().equals("����")) { radiovendor.setSelected(true);}
			else {radioPO.setSelected(true);}
			txtcuscode.setText(String.valueOf(data.getCuscode()));
			txtcusname.setText(data.getCusname());
			txtcusphone.setText(data.getCusphone());
			txtcusaddress.setText(data.getCusaddress());
			txtcusemail.setText(data.getCusemail());
			txtcusbank.setText(data.getCusbank());
			txtcusown.setText(data.getCusown());
			txtcusaccount.setText(String.valueOf(data.getCusaccount()));
			if(data.getCusforeign().equals("�ؿ�")) {
				foreign.setSelected(true);
				check.setSelected(false);
			}else if(data.getCusforeign().equals("����")){
				foreign.setSelected(false);
				check.setSelected(true);
				txtcuscopnum.setText(String.valueOf(data.getCuscopnum()));
			}
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("�����Ͱ� ���� �� Ʋ��");
		}
		setButton(false);
	}
	
	// ------------ ��Ÿ ���� �� setting �޼ҵ� 
	public void checkcopnum() {
		try {
			String tmp = "����";
			if(radiovendor.isSelected()) { tmp = "����"; }
			Long cuscopnum_int = Long.parseLong(txtcuscopnum.getText());
			
		
			if(txtcuscopnum.getText().length() < 10 || txtcuscopnum.getText().length() > 10) {
				D.dialog("�ŷ�ó���", "����ڵ������ 10�ڸ� �Դϴ�.");
				check.setSelected(false);
				return;
			}
			
			else {
				int result = Cdao.checknumber(tmp, cuscopnum_int);
				if(result == 1) {
					D.dialog("�ŷ�ó���", "��ϵ� ����ڵ�Ϲ�ȣ�Դϴ�.");
				}else {
					D.dialog("�ŷ�ó���", "�����Ϸ�");
				}
			}
		}catch(NumberFormatException e) {
			D.dialog("����ڵ�ϰ���", "���ڷ��Է����ּ���");
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
		if(onoff == true) { // ���
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
		if(onoff == false) { //����
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
