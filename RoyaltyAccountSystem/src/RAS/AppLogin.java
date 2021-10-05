package RAS;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class AppLogin extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception { // 시작, 로그인 화면 출력
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		primaryStage.setTitle("RoyaltyAccountSystem Login");
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);

	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
