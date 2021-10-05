package RAS;


import java.net.*;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;


public class Dialog_userController implements Initializable{
	//--------------ȭ�� ������Ʈ -----------------------
	@FXML private TextField txtuserpw;
	@FXML private TextField txtusername;
	@FXML private CheckBox Mcheck;
	@FXML private Button btnusersearch;
	@FXML private Button btnuseradd;
	@FXML private Button btnuserdelete;
	@FXML private Button btnusermodify;
	@FXML private TableColumn<UserVO, String> Cid;
	@FXML private TableColumn<UserVO, String> Cpw;
	@FXML private TableColumn<UserVO, String> Cname;
	@FXML private TableColumn<UserVO, String> Cstrcheck;
	
	//-----------��Ÿ ������Ʈ -----------------------------
	@FXML private TableView<UserVO> Tableuser;
	private ObservableList<UserVO> mylist = FXCollections.observableArrayList();
	private DialogMessage D = DialogMessage .getInstance();
	private UserDao dao = UserDao.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Cid.setCellValueFactory(new PropertyValueFactory<UserVO, String>("id"));
		Cpw.setCellValueFactory(new PropertyValueFactory<UserVO, String>("pw"));
		Cname.setCellValueFactory(new PropertyValueFactory<UserVO, String>("name"));
		Cstrcheck.setCellValueFactory(new PropertyValueFactory<UserVO, String>("strCheck"));
		Tableuser.setPlaceholder(new Label(" "));
		setButton(true);
	}
	
	//----------------�̺�Ʈ --------------------------------

	@FXML
	public void handleimguserresetAction() {
		setButton(true);
		txtusername.setText("");
		txtuserpw.setText("");
		Mcheck.setSelected(false);
	}
	
	// ����� ��� 
	@FXML
	public void handlebtnuseraddAction () {
		String name = txtusername.getText();
		boolean check = Mcheck.isSelected();
		String strMcheck = "YES";
		if(check == false) {
			strMcheck = "NO";
		}
		int result = dao.add(name, strMcheck);
			if(result == 3) {
				D.dialog("����ڵ��", "������ ������ �����մϴ�.");
			}
		txtusername.setText("");
		Mcheck.setSelected(false);
	}
	
	// ����� ����
	@FXML
	public void handlebtnuserdeleteAction () {
		int tmp = Tableuser.getSelectionModel().getSelectedIndex();
		UserVO data = Tableuser.getItems().get(tmp);
		
		String id = data.getId();
		int result = dao.delete(id);
		
		if(result == 1) { D.dialog("����� ���� ����", "���� �Ϸ��Ͽ����ϴ�.");}
		
		if(result == 0) { D.dialog("����� ���� ����", "���� �����Ͽ����ϴ�.");}
		setButton(true);
		txtusername.setText("");
		txtuserpw.setText("");
		Mcheck.setSelected(false);
	}
	
	// ���̺�� �ڷ� ���
	@FXML
	public void handletVeiwuserclickedAction() {
		try {
			setButton(false);
			int tmp = Tableuser.getSelectionModel().getSelectedIndex();
			UserVO data = Tableuser.getItems().get(tmp);
			
			String name = data.getName(); txtusername.setText(name);
			String pw = data.getPw(); txtuserpw.setText(pw);
			String strcheck = data.getStrCheck(); 
			if(strcheck.equals("YES")) { Mcheck.setSelected(true); }
			else if(strcheck.equals("NO")) { Mcheck.setSelected(false); }
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("�� Ŭ��");
		}
	}
	
	// ����� ���� ���� 
	@FXML
	public void handlebtnusermodifyAction() {
		int result = 0;
		int tmp = Tableuser.getSelectionModel().getSelectedIndex();
		UserVO data = Tableuser.getItems().get(tmp);
		
		String id = data.getId();
		String name = txtusername.getText();
		String pw = txtuserpw.getText();
		String strCheck = null;
		boolean check = Mcheck.isSelected();
			if(check == true) {strCheck = "YES";}
			else if(check == false) {strCheck = "NO";}
		if(pw.length() > 4) {
			result = dao.modify(name, pw, id, strCheck);
			if(result == 1) { D.dialog("����� ���� ����", "���� �Ϸ��Ͽ����ϴ�.");}
			else if(result == 3) {D.dialog("����ڵ��", "������ ������ �����մϴ�.");}
			else {D.dialog("����� ���� ����", "���� �����Ͽ����ϴ�.");}
		} else {  D.dialog("����� ���� ����", "��й�ȣ�� 5�ڸ� �̻�");}
		setButton(true);
		txtusername.setText("");
		txtuserpw.setText("");
		Mcheck.setSelected(false);
	}
	
	// ����� ��ȸ
	@FXML
	public void handlebtnusersearchAction() {
		Tableuser.getItems().clear();
		ResultSet rs = dao.search();
		try {
			while(rs.next()) {
				UserVO tmp = new UserVO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				mylist.add(tmp);
			} 
		} catch (SQLException e) { System.out.println("�ڷ��� ��ȯ ����");}
		Tableuser.setItems(mylist);
	}

	// ��ư ���� 
	public void setButton(boolean isStop) {
		if(isStop) {
			btnuseradd.setDisable(false);
			btnusersearch.setDisable(false);
			btnuserdelete.setDisable(true);
			btnusermodify.setDisable(true);
			txtuserpw.setDisable(true);
		}
		else if(isStop == false) {
			btnuseradd.setDisable(true);
			btnusersearch.setDisable(true);
			btnuserdelete.setDisable(false);
			btnusermodify.setDisable(false);
			txtuserpw.setDisable(false);
		}
	}
}
