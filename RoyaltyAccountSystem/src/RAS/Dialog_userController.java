package RAS;


import java.net.*;
import java.sql.*;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;


public class Dialog_userController implements Initializable{
	//--------------화면 컴포넌트 -----------------------
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
	
	//-----------기타 검포넌트 -----------------------------
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
	
	//----------------이벤트 --------------------------------

	@FXML
	public void handleimguserresetAction() {
		setButton(true);
		txtusername.setText("");
		txtuserpw.setText("");
		Mcheck.setSelected(false);
	}
	
	// 사용자 등록 
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
				D.dialog("사용자등록", "관리자 계정이 존재합니다.");
			}
		txtusername.setText("");
		Mcheck.setSelected(false);
	}
	
	// 사용자 삭제
	@FXML
	public void handlebtnuserdeleteAction () {
		int tmp = Tableuser.getSelectionModel().getSelectedIndex();
		UserVO data = Tableuser.getItems().get(tmp);
		
		String id = data.getId();
		int result = dao.delete(id);
		
		if(result == 1) { D.dialog("사용자 정보 삭제", "삭제 완료하였습니다.");}
		
		if(result == 0) { D.dialog("사용자 정보 삭제", "삭제 실패하였습니다.");}
		setButton(true);
		txtusername.setText("");
		txtuserpw.setText("");
		Mcheck.setSelected(false);
	}
	
	// 테이블뷰 자료 얻기
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
			System.out.println("빈열 클릭");
		}
	}
	
	// 사용자 정보 수정 
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
			if(result == 1) { D.dialog("사용자 정보 수정", "수정 완료하였습니다.");}
			else if(result == 3) {D.dialog("사용자등록", "관리자 계정이 존재합니다.");}
			else {D.dialog("사용자 정보 수정", "수정 실패하였습니다.");}
		} else {  D.dialog("사용자 정보 수정", "비밀번호는 5자리 이상");}
		setButton(true);
		txtusername.setText("");
		txtuserpw.setText("");
		Mcheck.setSelected(false);
	}
	
	// 사용자 조회
	@FXML
	public void handlebtnusersearchAction() {
		Tableuser.getItems().clear();
		ResultSet rs = dao.search();
		try {
			while(rs.next()) {
				UserVO tmp = new UserVO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				mylist.add(tmp);
			} 
		} catch (SQLException e) { System.out.println("자료형 변환 오류");}
		Tableuser.setItems(mylist);
	}

	// 버튼 셋팅 
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
