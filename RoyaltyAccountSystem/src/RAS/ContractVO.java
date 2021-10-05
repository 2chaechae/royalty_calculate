package RAS;

import javafx.beans.property.*;

public class ContractVO {
	private SimpleStringProperty vendorPO;
	private SimpleStringProperty contnum;
	private SimpleIntegerProperty contcuscode;
	private SimpleStringProperty conttitle;
	private SimpleIntegerProperty contipcode;
	private SimpleStringProperty contprice;
	private SimpleIntegerProperty controyalty;
	private SimpleStringProperty contcusname;
	private SimpleStringProperty contstart;
	private SimpleStringProperty contend;
	private SimpleStringProperty currency;

	
	public ContractVO() {
		
	}
	public ContractVO(String vendorPO, String contnum, int contcuscode, String contcusname, int contipcode, String conttitle,
			String contstart, String contend, String currency, String contprice, int controyalty) {
		this.vendorPO = new SimpleStringProperty(vendorPO);
		this.contnum = new SimpleStringProperty(contnum);
		this.contcuscode = new SimpleIntegerProperty(contcuscode);
		this.contcusname = new SimpleStringProperty(contcusname);
		this.contipcode = new SimpleIntegerProperty(contipcode);
		this.conttitle = new SimpleStringProperty(conttitle);
		this.contstart = new SimpleStringProperty(contstart);
		this.contend = new SimpleStringProperty(contend);
		this.currency = new SimpleStringProperty(currency);
		this.contprice = new SimpleStringProperty(contprice);
		this.controyalty = new SimpleIntegerProperty(controyalty);
	}
	
	public ContractVO(int contipcode, String conttitle, String contstart, String contend) {
		this.contipcode = new SimpleIntegerProperty(contipcode);
		this.conttitle = new SimpleStringProperty(conttitle);
		this.contstart = new SimpleStringProperty(contstart);
		this.contend = new SimpleStringProperty(contend);
	}
	
	public String getVendorPO() {
		return vendorPO.get();
	}
	public void setVendorPO(String vendorPO) {
		this.vendorPO.set(vendorPO);
	}
	
	public String getContnum() {
		return contnum.get();
	}
	public void setContnum(String contnum) {
		this.contnum.set(contnum);
	}
	
	public int getContcuscode() {
		return contcuscode.get();
	}
	public void setContcuscode(int contcuscode) {
		this.contcuscode.set(contcuscode);
	}

	public String getConttitle() {
		return conttitle.get();
	}
	public void setConttitle(String conttitle) {
		this.conttitle.set(conttitle);
	}
	
	public int getContipcode() {
		return contipcode.get();
	}
	public void setContipcode(int contipcode) {
		this.contipcode.set(contipcode);
	}

	public String getContprice() {
		return contprice.get();
	}
	public void setContprice(String contprice) {
		this.contprice.set(contprice);
	}
	
	public int getControyalty() {
		return controyalty.get();
	}
	public void setControyalty(int controyalty) {
		this.controyalty.set(controyalty);
	}
	
	public String getContcusname() {
		return contcusname.get();
	}
	public void setContcusname(String contcusname) {
		this.contcusname.set(contcusname);
	}
	
	public String getContstart() {
		return contstart.get();
	}
	public void setContstart(String contstart) {
		this.contstart.set(contstart);
	}
	
	public String getContend() {
		return contend.get();
	}
	public void setContend(String contend) {
		this.contend.set(contend);
	}
	
	public String getCurrency() {
		return currency.get();
	}
	public void setCurrency(String currency) {
		this.currency.set(currency);
	}
	


}
