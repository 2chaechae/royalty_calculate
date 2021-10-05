package RAS;

import java.io.*;

import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class DialogMessage {
		
	public static DialogMessage instance = null;
	
	private DialogMessage() {}
	
	public static DialogMessage getInstance() {
		if(instance == null) {
			instance = new DialogMessage();
		}
		return instance;
	}
	
	// 다이얼로그 메세지창 생성 
	public void dialog(String message1, String message2) {
			Stage dialog = new Stage(StageStyle.UTILITY);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.setTitle(message1);
				try {
					Parent parent = FXMLLoader.load(getClass().getResource("dialog_message.fxml"));
					Label txtTitle = (Label) parent.lookup("#txtTitle");
					txtTitle.setText(message2);
					Button btnOk = (Button) parent.lookup("#btnOk");
					btnOk.setOnAction(evnet->dialog.close());
					Scene scene = new Scene(parent);
					dialog.setScene(scene);
					dialog.setResizable(false);
					dialog.show();
					
				} catch (IOException e) {
					e.printStackTrace();
			}
		}

	// 문자열 -> int 로 변환
	public int Stringintchang(String price) {
		String tmp = "";
		price = price.trim();
		String[] plist = price.split(",");
		for(int i=0 ; i <plist.length ; i++) {
			 tmp += plist[i];
		}
		int result = Integer.parseInt(tmp.toString());
		return result;
	}

}
