package RAS;

import javafx.beans.property.*;

public class CustomerVO {
	
	private SimpleStringProperty vendorPO;
	private SimpleIntegerProperty cuscode;
	private SimpleStringProperty cusname;
	private SimpleLongProperty cuscopnum;
	private SimpleStringProperty cusphone;
	private SimpleStringProperty cusaddress;
	private SimpleStringProperty cusemail;
	private SimpleStringProperty cusbank;
	private SimpleStringProperty cusown;
	private SimpleLongProperty cusaccount;
	private SimpleStringProperty cusforeign;
	
	public CustomerVO() {}
	
	public CustomerVO(String vendorPO, int cuscode, String cusname, long cuscopnum, String cusphone, String cusaddress,
			String cusemail, String cusbank, String cusown, long cusaccount, String cusforeign) {
		this.vendorPO = new SimpleStringProperty(vendorPO);
		this.cuscode = new SimpleIntegerProperty(cuscode);
		this.cusname = new SimpleStringProperty(cusname);
		this.cuscopnum = new SimpleLongProperty (cuscopnum);
		this.cusphone = new SimpleStringProperty(cusphone);
		this.cusaddress = new SimpleStringProperty(cusaddress);
		this.cusemail = new SimpleStringProperty(cusemail);
		this.cusbank = new SimpleStringProperty(cusbank);
		this.cusown = new SimpleStringProperty(cusown);
		this.cusaccount = new SimpleLongProperty (cusaccount);
		this.cusforeign = new SimpleStringProperty(cusforeign);
	}
	
	public CustomerVO(int cuscode, String cusname, long cuscopnum) {
		this.cuscode = new SimpleIntegerProperty(cuscode);
		this.cusname = new SimpleStringProperty(cusname);
		this.cuscopnum = new SimpleLongProperty (cuscopnum);

	}

	public String getVendorPO() {
		return vendorPO.get();
	}
	public void setVendorPO(String vendorPO) {
		this.vendorPO.set(vendorPO);
	}
	
	public int getCuscode() {
		return cuscode.get();
	}
	public void setCuscode(int cuscode) {
		this.cuscode.set(cuscode);
	}
	
	public String getCusname() {
		return cusname.get();
	}
	public void setCusname(String cusname) {
		this.cusname.set(cusname);
	}
	
	public long getCuscopnum() {
		return cuscopnum.get();
	}
	public void setCusCopnum(long cuscopnum) {
		this.cuscopnum.set(cuscopnum);
	}
	
	public String getCusphone() {
		return cusphone.get();
	}
	public void setCusphone(String cusphone) {
		this.cusphone.set(cusphone);
	}
	
	public String getCusaddress() {
		return cusaddress.get();
	}
	public void setCusaddress(String cusaddress) {
		this.cusaddress.set(cusaddress);
	}
	
	public String getCusemail() {
		return cusemail.get();
	}
	public void setCusemail(String cusemail) {
		this.cusemail.set(cusemail);
	}

	public String getCusbank() {
		return cusbank.get();
	}
	public void setCusbank(String cusbank) {
		this.cusbank.set(cusbank);
	}

	public String getCusown() {
		return cusown.get();
	}
	public void setCusown(String cusown) {
		this.cusown.set(cusown);
	}

	public long getCusaccount() {
		return cusaccount.get();
	}
	public void setCusaccount(long cusaccount) {
		this.cusaccount.set(cusaccount);
	}
	
	public String getCusforeign() {
		return cusforeign.get();
	}
	public void setCusforeign(String cusforeign) {
		this.cusforeign.set(cusforeign);
	}
	
}
