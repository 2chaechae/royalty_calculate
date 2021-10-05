package RAS;


import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.time.*;
import java.util.*;
import java.util.Date;

import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class MainController implements Initializable{	
	// -------------- 홈화면 컴포넌트-----------------
	@FXML public Button btnuserM;
	@FXML public BorderPane test;
	@FXML public BorderPane customer;
	@FXML public BorderPane contract;
	@FXML public BorderPane sales;
	@FXML public BorderPane purchase;
	@FXML public BorderPane profit;
	@FXML public BorderPane salesSearch;
	@FXML public BorderPane purchasesearch;

	//-------------------기타 컴포넌트---------------

	UserDao dao = UserDao.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// 관리자여부
		boolean tmp = UserDao.Manager_check;
		if(tmp == true) { btnuserM.setDisable(false);}
		
		//각 화면로드
		FXMLLoader customerfile = new FXMLLoader(getClass().getResource("customer.fxml"));
		Parent customerP = null;
		try {customerP = (Parent)customerfile.load();} catch (IOException e) {e.printStackTrace();}
		customer.setCenter(customerP);
		
		FXMLLoader contractfile = new FXMLLoader(getClass().getResource("contract.fxml"));
		Parent contractP = null;
		try {contractP = (Parent)contractfile.load();} catch (IOException e) {e.printStackTrace();}
		contract.setCenter(contractP);
		
		FXMLLoader salesfile = new FXMLLoader(getClass().getResource("sales.fxml"));
		Parent salesP = null;
		try {salesP = (Parent)salesfile.load();} catch (IOException e) {e.printStackTrace();}
		sales.setCenter(salesP);
		
		FXMLLoader salesSearchfile = new FXMLLoader(getClass().getResource("salesSearch.fxml"));
		Parent salesSearchP = null;
		try {salesSearchP = (Parent)salesSearchfile.load();} catch (IOException e) {e.printStackTrace();}
		salesSearch.setCenter(salesSearchP);
		
		FXMLLoader purchasefile = new FXMLLoader(getClass().getResource("purchase.fxml"));
		Parent purchaseP = null;
		try {purchaseP = (Parent)purchasefile.load();} catch (IOException e) {e.printStackTrace();}
		purchase.setCenter(purchaseP);
		
		FXMLLoader purchaseSearchfile = new FXMLLoader(getClass().getResource("purchaseSearch.fxml"));
		Parent purchaseSearchP = null;
		try {purchaseSearchP = (Parent)purchaseSearchfile.load();} catch (IOException e) {e.printStackTrace();}
		purchasesearch.setCenter(purchaseSearchP);
		
		FXMLLoader profitfile = new FXMLLoader(getClass().getResource("profit.fxml"));
		Parent profitP = null;
		try {profitP = (Parent)profitfile.load();} catch (IOException e) {e.printStackTrace();}
		profit.setCenter(profitP);
	

	}

	// -------------- 홈화면 이벤트 ------------------
	
	@FXML
	public void handleBtnuserMAction() {
		Stage dialog = new Stage(StageStyle.UTILITY);
		dialog.initModality(Modality.WINDOW_MODAL);
		dialog.setTitle("사용자 관리");
		
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("dialog_user.fxml"));
			Scene scene = new Scene(parent);
			dialog.setScene(scene);
			dialog.setResizable(false);
			dialog.show();
			
		} catch (IOException e) { System.out.println("화면 로드 실패");}
	}
}
