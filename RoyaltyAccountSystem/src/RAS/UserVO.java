package RAS;

import javafx.beans.property.*;

public class UserVO {

	private SimpleStringProperty id;
	private SimpleStringProperty pw;
	private SimpleStringProperty name;
	private SimpleStringProperty strCheck;
	
	public UserVO() {}
	
	public UserVO(String id, String pw, String name, String strCheck) {
		this.id = new SimpleStringProperty(id);
		this.pw = new SimpleStringProperty(pw);
		this.name = new SimpleStringProperty(name);
		this.strCheck = new SimpleStringProperty(strCheck);
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public String getPw() {
		return pw.get();
	}

	public void setPw(String pw) {
		this.pw.set(pw);
	}


	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getStrCheck() {
		return strCheck.get();
	}

	public void getStrCheck(String strCheck) {
		this.strCheck.set(strCheck);
	}

	
}
