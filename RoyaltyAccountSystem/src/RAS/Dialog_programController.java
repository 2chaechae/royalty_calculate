package RAS;

import java.net.*;
import java.sql.*;
import java.util.*;

import javafx.application.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.stage.*;

public class Dialog_programController implements Initializable{

	@FXML private Button btmprogramok;
	@FXML private Button btmprogramadd;
	@FXML private Button programsearch;
	@FXML private TextField txtprogramn;
	@FXML private TextField txtipcode;
	@FXML private TableView<ContractVO> tbprogram;
	@FXML private TableColumn <ContractVO, Integer> Cipcode;
	@FXML private TableColumn <ContractVO, String> Cprogram;
	@FXML private TableColumn <ContractVO, String> Cstar;
	@FXML private TableColumn <ContractVO, String> Cend;
	ContractDao Contdao = ContractDao.getInstance();
	private ObservableList<ContractVO> ContractList = FXCollections.observableArrayList();
	public static String vpo = "매입";
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(vpo.equals("매출")) {btmprogramadd.setDisable(true);}
		Cipcode.setCellValueFactory(new PropertyValueFactory<ContractVO, Integer>("contipcode"));
		Cprogram.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("conttitle"));
		Cstar.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("contstart"));
		Cend.setCellValueFactory(new PropertyValueFactory<ContractVO, String>("contend"));
	}
	
	// 프로그램 등록
	public void handelbtmprogramadd() {
		String newprogram = txtprogramn.getText();
		System.out.println(newprogram);
		int result = Contdao.programadd(newprogram);
		txtipcode.setText(String.valueOf(result));
		txtipcode.setDisable(false);
		btmprogramadd.setDisable(true);
		
	}
	
	public void handleprogramsearch() {
		ContractList.remove(0, ContractList.size());
		ContractList = Contdao.programsearch();
		tbprogram.setItems(ContractList);
	}
	
	public void handelbtmprogramokAction() {
		Stage program = (Stage) btmprogramok.getScene().getWindow();
		Platform.runLater(() -> {
			program.close();
		});
	}
}
