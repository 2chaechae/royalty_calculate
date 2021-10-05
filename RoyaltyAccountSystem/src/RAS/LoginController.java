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
	
	// ----------------- �α��� ��Ʈ�� --------------------------
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

	// -------------- �α��� ȭ�� �̺�Ʈ -------------------------
	
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
				
			} catch (IOException e) { System.out.println("ȭ�� �ε� ����");}
		}	
		else if(login_check == false) {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle("�α��� ����");
			try {
				Parent parent = FXMLLoader.load(getClass().getResource("dialog_message.fxml"));
				Label txtTitle = (Label) parent.lookup("#txtTitle");
				txtTitle.setText("�α��� ������ ����ġ �մϴ�.");
				Button btnOk = (Button) parent.lookup("#btnOk");
				btnOk.setOnAction(evnet->dialog.close());
				Scene scene = new Scene(parent);
				dialog.setScene(scene);
				dialog.setResizable(false);
				dialog.show();
				
			} catch (IOException e) { System.out.println("ȭ�� �ε� ����");}
		}
	}
	
	@FXML
	public void handleBtnchangpwAction() {	// ��й�ȣ ���� ���̾�α� ��ư
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setTitle("��й�ȣ ����");
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("dialog_changepw.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
			
		} catch (IOException e) { System.out.println("ȭ�� �ε� ����");}
	}
	
	@FXML
	public void handlebtchangeAction() {	// ��к�ȣ ����
		String id = txtid.getText();
		String nowpw = txtnowpw.getText();
		String newpw = txtnewpw.getText();
		String newpwcheck = txtnewpwcheck.getText();
		
		if(newpw.length() < 5) { D.dialog("��й�ȣ ����", "��й�ȣ�� 5�ڸ� �̻� �����մϴ�.");}
		else { dao.usermodify(id, nowpw, newpw, newpwcheck);}
	}
	
	@FXML
	public void handlebtcancelAction() {	// ��й�ȣ ���� ���
		txtnowpw.setText("");
		txtnewpw.setText("");
		txtnewpwcheck.setText("");
		Stage passwd = (Stage) txtnowpw.getScene().getWindow();
		Platform.runLater(() -> {
			passwd.close();
		});
	}
	
	public void closeStage() {	// �α��� ���� �� �α���â �ݱ�
		Stage login = (Stage) btnlogin.getScene().getWindow();
		Platform.runLater(() -> {
			login.close();
		});
	}
}
