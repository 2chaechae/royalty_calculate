package RAS;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class LoginController implements Initializable{
	
	// ----------------- 로그인 컨트롤 --------------------------
	@FXML private AnchorPane loginback;
	@FXML private Button btnlogin;
	@FXML private TextField txtid;
	@FXML private TextField tfid;
	@FXML private PasswordField passwd;
	@FXML private PasswordField txtnowpw;
	@FXML private PasswordField txtnewpw;
	@FXML private PasswordField txtnewpwcheck;
	
	public static String login_id = null;

	private DialogMessage D = DialogMessage.getInstance();
	private UserDao dao = UserDao.getInstance();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	// -------------- 로그인 화면 이벤트 -------------------------
	
	@FXML
	public void handleBtnloginAction() {
		String id = tfid.getText();
		String pw = passwd.getText();
		boolean login_check = dao.login(id, pw);
		if(login_check == true) {
			try {
				login_id = id;
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main.fxml"));
				Parent main = (Parent)fxmlLoader.load();
				Stage stage = new Stage();
				stage.setScene(new Scene(main));
				stage.setTitle("Royalty Account System");
				stage.show();
				stage.setResizable(false);
				closeStage();
				
			} catch (IOException e) { System.out.println("화면 로드 실패");}
		}	
		else if(login_check == false) {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("로그인 실패");
			try {
				Parent parent = FXMLLoader.load(getClass().getResource("dialog_message.fxml"));
				Label txtTitle = (Label) parent.lookup("#txtTitle");
				txtTitle.setText("로그인 정보가 불일치 합니다.");
				Button btnOk = (Button) parent.lookup("#btnOk");
				btnOk.setOnAction(evnet->dialog.close());
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();
				
			} catch (IOException e) { System.out.println("화면 로드 실패");}
		}
	}
	
	@FXML
	public void handleBtnchangpwAction() {	// 비밀번호 변경 다이얼로그 버튼
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setTitle("비밀번호 변경");
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("dialog_changepw.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
			
		} catch (IOException e) { System.out.println("화면 로드 실패");}
	}
	
	@FXML
	public void handlebtchangeAction() {	// 비밀변호 변경
		String id = txtid.getText();
		String nowpw = txtnowpw.getText();
		String newpw = txtnewpw.getText();
		String newpwcheck = txtnewpwcheck.getText();
		
		if(newpw.length() < 5) { D.dialog("비밀번호 변경", "비밀번호는 5자리 이상만 가능합니다.");}
		else { dao.usermodify(id, nowpw, newpw, newpwcheck);}
	}
	
	@FXML
	public void handlebtcancelAction() {	// 비밀번호 변경 취소
		txtnowpw.setText("");
		txtnewpw.setText("");
		txtnewpwcheck.setText("");
		Stage passwd = (Stage) txtnowpw.getScene().getWindow();
		Platform.runLater(() -> {
			passwd.close();
		});
	}
	
	public void closeStage() {	// 로그인 성공 시 로그인창 닫기
		Stage login = (Stage) btnlogin.getScene().getWindow();
		Platform.runLater(() -> {
			login.close();
		});
	}
}
