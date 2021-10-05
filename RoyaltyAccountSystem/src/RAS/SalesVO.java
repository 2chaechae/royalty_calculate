package RAS;

import javafx.beans.property.*;

public class SalesVO {
	private SimpleIntegerProperty salesnum;
	private SimpleStringProperty salesCD;
	private SimpleStringProperty salesdocdate;
	private SimpleStringProperty salesdate;
	private SimpleStringProperty salesaccountN;
	private SimpleIntegerProperty salescuscode;
	private SimpleStringProperty salescusN;
	private SimpleIntegerProperty salestitleC;
	private SimpleStringProperty salestitleN;
	private SimpleStringProperty salesprice;
	private SimpleStringProperty salescurrency;
	private SimpleIntegerProperty salesrate;
	private SimpleStringProperty saleswon;
	private SimpleStringProperty salesVatC;
	private SimpleStringProperty salesVat;
	private SimpleStringProperty salesWriter;
	private SimpleStringProperty saleswriteP;
	
	public SalesVO() {}
	
	public SalesVO(String salesCD, String salesdocdate, String salesdate, String salesaccountN, int salescuscode, 
			String salescusN, int salestitleC, String salestitleN, String salesprice, String salescurrency, int salesrate, 
			String saleswon, String salesVatC, String salesVat, String salesWriter, String saleswriteP) {
	
			this.salesCD =  new SimpleStringProperty(salesCD);
			this.salesdocdate = new SimpleStringProperty(salesdocdate);
			this.salesdate = new SimpleStringProperty(salesdate);
			this.salesaccountN = new SimpleStringProperty(salesaccountN);
			this.salescuscode = new SimpleIntegerProperty(salescuscode);
			this.salescusN = new SimpleStringProperty(salescusN);
			this.salestitleC = new SimpleIntegerProperty(salestitleC);
			this.salestitleN = new SimpleStringProperty(salestitleN);
			this.salesprice = new SimpleStringProperty(salesprice);
			this.salescurrency = new SimpleStringProperty(salescurrency);
			this.salesrate = new SimpleIntegerProperty(salesrate);
			this.saleswon = new SimpleStringProperty(saleswon);
			this.salesVatC = new SimpleStringProperty(salesVatC);
			this.salesVat = new SimpleStringProperty(salesVat);
			this.salesWriter = new SimpleStringProperty(salesWriter);
			this.saleswriteP = new SimpleStringProperty(saleswriteP);
		}
	
	public SalesVO(int salesnum, String salesdocdate, String salesdate, int salescuscode, 
			String salescusN, int salestitleC, String salestitleN, String salesprice, String salescurrency, int salesrate, 
			String saleswon, String salesVatC, String salesVat, String salesWriter, String saleswriteP) {
	
		    this.salesnum = new SimpleIntegerProperty(salesnum);
			this.salesdocdate = new SimpleStringProperty(salesdocdate);
			this.salesdate = new SimpleStringProperty(salesdate);
			this.salescuscode = new SimpleIntegerProperty(salescuscode);
			this.salescusN = new SimpleStringProperty(salescusN);
			this.salestitleC = new SimpleIntegerProperty(salestitleC);
			this.salestitleN = new SimpleStringProperty(salestitleN);
			this.salesprice = new SimpleStringProperty(salesprice);
			this.salescurrency = new SimpleStringProperty(salescurrency);
			this.salesrate = new SimpleIntegerProperty(salesrate);
			this.saleswon = new SimpleStringProperty(saleswon);
			this.salesVatC = new SimpleStringProperty(salesVatC);
			this.salesVat = new SimpleStringProperty(salesVat);
			this.salesWriter = new SimpleStringProperty(salesWriter);
			this.saleswriteP = new SimpleStringProperty(saleswriteP);
		}

	public int getSalesnum() {
		return salesnum.get();
	}
	public void setSalesnum(int salesnum) {
		this.salesnum.set(salesnum);
	}
	
	public String getSalesCD() {
		return salesCD.get();
	}
	public void setgetSalesCD(String salesCD) {
		this.salesCD.set(salesCD);
	}

	public String getSalesdocdate() {
		return salesdocdate.get();
	}
	public void setSalesdocdate(String salesdocdate) {
		this.salesdocdate.set(salesdocdate);
	}

	public String getSalesdate() {
		return salesdate.get();
	}
	public void setSalesdate(String salesdate) {
		this.salesdate.set(salesdate);
	}

	public String getSalesaccountN() {
		return salesaccountN.get();
	}
	public void setSalesaccountN(String salesaccountN) {
		this.salesaccountN.set(salesaccountN);
	}
	
	public int getSalescuscode() {
		return salescuscode.get();
	}
	public void setSalescuscode(int salescuscode) {
		this.salescuscode.set(salescuscode);
	}
	
	
	public String getSalescusN() {
		return salescusN.get();
	}
	public void setSalescusN(String salescusN) {
		this.salescusN.set(salescusN);
	}

	public int getSalestitleC() {
		return salestitleC.get();
	}
	public void setSalestitleC(int salestitleC) {
		this.salestitleC.set(salestitleC);
	}
	
	public String getSalestitleN() {
		return salestitleN.get();
	}
	public void setSalestitleN(String salestitleN) {
		this.salestitleN.set(salestitleN);
	}

	public String getSalesprice() {
		return salesprice.get();
	}
	public void setSalesprice(String salesprice) {
		this.salesprice.set(salesprice);
	}

	public String getSalescurrency() {
		return salescurrency.get();
	}
	public void setSalescurrency(String salescurrency) {
		this.salescurrency.set(salescurrency);
	}

	public int getSalesrate() {
		return salesrate.get();
	}
	public void setSalesrate(int salesrate) {
		this.salesrate.set(salesrate);
	}

	public String getSaleswon() {
		return saleswon.get();
	}
	public void setSaleswon(String saleswon) {
		this.saleswon.set(saleswon);
	}

	public String getSalesVatC() {
		return salesVatC.get();
	}
	public void setSalesVatC(String salesVatC) {
		this.salesVatC.set(salesVatC);
	}

	public String getSalesVat() {
		return salesVat.get();
	}
	public void setSalesVat(String salesVat) {
		this.salesVat.set(salesVat);
	}

	public String getSalesWriter() {
		return salesWriter.get();
	}
	public void setSalesWriter(String salesWriter) {
		this.salesWriter.set(salesWriter);
	}

	public String getSaleswriteP() {
		return saleswriteP.get();
	}
	public void setSaleswriteP(String saleswriteP) {
		this.saleswriteP.set(saleswriteP);
	}
}

