package RAS;    
import javafx.beans.property.*;
import javafx.fxml.*;
import javafx.scene.control.*;                      
public class PurchaseVO {                       
	                                                 
	private SimpleIntegerProperty purchaseSnum;       
	private SimpleStringProperty purchaseSdate;           
	private SimpleIntegerProperty purchasetitleC;                
	private SimpleStringProperty purchasetitleN;             
	private SimpleStringProperty purchasecurr;             
	private SimpleStringProperty purchaseSprice;            
	private SimpleStringProperty purchaseSpricewon;              
	private SimpleStringProperty purchasesep;          
	private SimpleIntegerProperty purchasecode;                
	private SimpleStringProperty purchasecodeN;         
	private SimpleIntegerProperty purchaseRoyalty;        
	private SimpleStringProperty purchaseprice; 
	private SimpleStringProperty purchasepricevat; 
	private SimpleStringProperty purchasewriter; 
	private SimpleStringProperty purchasedocdate; 
	private SimpleStringProperty purchasedate; 
	private SimpleIntegerProperty purchasenum; 
	private SimpleStringProperty salescusn;

	private SimpleIntegerProperty purchaseSpricewon_int; 
	private SimpleIntegerProperty purchaseprice_int;
	private SimpleIntegerProperty purchasepricevat_int;
	
	public PurchaseVO(){}
	
	public PurchaseVO(int purchasenum, String purchasedocdate, String purchasedate, int purchasecode, String purchasecodeN, int purchasetitleC,
			String purchasetitleN, String purchaseSprice, String purchaseSpricewon, int purchaseRoyalty, String purchaseprice, String purchasepricevat,
			String purchasewriter) {
		this.purchasenum = new SimpleIntegerProperty(purchasenum);         
		this.purchasedocdate= new SimpleStringProperty(purchasedocdate);        
		this.purchasedate= new SimpleStringProperty(purchasedate);      
		this.purchasecode= new SimpleIntegerProperty(purchasecode);       
		this.purchasecodeN= new SimpleStringProperty(purchasecodeN);         
		this.purchasetitleC= new SimpleIntegerProperty(purchasetitleC);       
		this.purchasetitleN = new SimpleStringProperty(purchasetitleN);    
		this.purchaseSprice = new SimpleStringProperty(purchaseSprice);          
		this.purchaseSpricewon = new SimpleStringProperty(purchaseSpricewon);        
		this.purchaseRoyalty = new SimpleIntegerProperty(purchaseRoyalty);        
		this.purchaseprice = new SimpleStringProperty(purchaseprice);     
	    this.purchasepricevat = new SimpleStringProperty(purchasepricevat);        
	    this.purchasewriter = new SimpleStringProperty(purchasewriter);        

	}

	public PurchaseVO(int purchaseSnum, String purchaseSdate, int purchasetitleC, String purchasetitleN, String purchasecurr, String purchaseSprice,
			String purchaseSpricewon, String purchasesep, int purchasecode, String purchasecodeN, int purchaseRoyalty, String purchaseprice,
			String purchasepricevat, String purchasewriter) {
		this.purchaseSnum = new SimpleIntegerProperty(purchaseSnum);         
		this.purchaseSdate= new SimpleStringProperty(purchaseSdate);        
		this.purchasetitleC= new SimpleIntegerProperty(purchasetitleC);      
		this.purchasetitleN= new SimpleStringProperty(purchasetitleN);       
		this.purchasecurr= new SimpleStringProperty(purchasecurr);         
		this.purchaseSprice= new SimpleStringProperty(purchaseSprice);       
		this.purchaseSpricewon = new SimpleStringProperty(purchaseSpricewon);    
		this.purchasesep = new SimpleStringProperty(purchasesep);          
		this.purchasecode = new SimpleIntegerProperty(purchasecode);        
		this.purchasecodeN = new SimpleStringProperty(purchasecodeN);        
		this.purchaseRoyalty = new SimpleIntegerProperty(purchaseRoyalty);     
	    this.purchaseprice = new SimpleStringProperty(purchaseprice);        
	    this.purchasepricevat = new SimpleStringProperty(purchasepricevat);        
	    this.purchasewriter = new SimpleStringProperty(purchasewriter);  
	}
	
	public PurchaseVO( String purchaseSdate, String salescusn, String purchasetitleN,
			int purchaseSpricewon_int, String purchasecodeN, int purchaseRoyalty, int purchaseprice_int, int purchasepricevat_int) {
		this.purchaseSdate = new SimpleStringProperty(purchaseSdate);         
		this.salescusn= new SimpleStringProperty(salescusn);        
		this.purchasetitleN= new SimpleStringProperty(purchasetitleN);         
		this.purchaseSpricewon_int = new SimpleIntegerProperty(purchaseSpricewon_int);         
		this.purchasecodeN= new SimpleStringProperty(purchasecodeN);       
		this.purchaseRoyalty = new SimpleIntegerProperty(purchaseRoyalty);    
		this.purchaseprice_int = new  SimpleIntegerProperty(purchaseprice_int);    
		this.purchasepricevat_int = new  SimpleIntegerProperty(purchasepricevat_int);          


	}
	
	public int getPurchasepricevat_int() {
		return purchasepricevat_int.get();
	}
	public void setPurchasepricevat_int(int purchasepricevat_int) {
		this.purchasepricevat_int.set(purchasepricevat_int);
	}
	
	public int getPurchaseSpricewon_int() {
		return purchaseSpricewon_int.get();
	}
	public void setPurchaseSpricewon_int(int purchaseSpricewon_int) {
		this.purchaseSpricewon_int.set(purchaseSpricewon_int);
	}
	
	public int getPurchaseprice_int() {
		return purchaseprice_int.get();
	}
	public void setPurchaseprice_int(int purchaseprice_int) {
		this.purchaseprice_int.set(purchaseprice_int);
	}
	
	public String getSalescusn() {
		return salescusn.get();
	}
	public void setSalescusn(String salescusn) {
		this.salescusn.set(salescusn);
	}


	public int getPurchasenum() {
		return purchasenum.get();
	}
	public void setPurchasenum(int purchasenum) {
		this.purchasenum.set(purchasenum);
	}

	
	public String getPurchasedate() {
		return purchasedate.get();
	}
	public void setPurchasedate(String purchasedate) {
		this.purchasedate.set(purchasedate);
	}


	public String getPurchasedocdate() {
		return purchasedocdate.get();
	}
	public void setPurchasedocdate(String purchasedocdate) {
		this.purchasedocdate.set(purchasedocdate);
	}

	
	public int getPurchaseSnum() {
		return purchaseSnum.get();
	}
	public void setPurchaseSnum(int purchaseSnum) {
		this.purchaseSnum.set(purchaseSnum);
	}

	public String getPurchaseSdate() {
		return purchaseSdate.get();
	}
	public void setPurchaseSdate(String purchaseSdate) {
		this.purchaseSdate.set(purchaseSdate);
	}

	public int getPurchasetitleC() {
		return purchasetitleC.get();
	}
	public void setPurchasetitleC(int purchasetitleC) {
		this.purchasetitleC.set(purchasetitleC);
	}

	public String getPurchasetitleN() {
		return purchasetitleN.get();
	}
	public void setPurchasetitleN(String purchasetitleN) {
		this.purchasetitleN.set(purchasetitleN);
	}
	
	
	public String getPurchasecurr() {
		return purchasecurr.get();
	}
	public void setPurchasecurr(String purchasecurr) {
		this.purchasecurr.set(purchasecurr);
	}
	
	public String getPurchaseSprice() {
		return purchaseSprice.get();
	}
	public void setPurchaseSprice(String purchaseSprice) {
		this.purchaseSprice.set(purchaseSprice);
	}
	
	public String getPurchaseSpricewon() {
		return purchaseSpricewon.get();
	}
	public void setPurchaseSpricewon(String purchaseSpricewon) {
		this.purchaseSpricewon.set(purchaseSpricewon);
	}
	
	
	public String getPurchasesep() {
		return purchasesep.get();
	}
	public void setPurchasesep(String purchasesep) {
		this.purchasesep.set(purchasesep);
	}
	
	public int getPurchasecode() {
		return purchasecode.get();
	}
	public void setPurchasecode(int purchasecode) {
		this.purchasecode.set(purchasecode);
	}

	
	public String getPurchasecodeN() {
		return purchasecodeN.get();
	}
	public void setPurchasecodeN(String purchasecodeN) {
		this.purchasecodeN.set(purchasecodeN);
	}

	
	
	public int getPurchaseRoyalty() {
		return purchaseRoyalty.get();
	}
	public void setPurchaseRoyalty(int purchaseRoyalty) {
		this.purchaseRoyalty.set(purchaseRoyalty);
	}

	public String getPurchaseprice() {
		return purchaseprice.get();
	}
	public void setPurchaseprice(String purchaseprice) {
		this.purchaseprice.set(purchaseprice);
	}

	public String getPurchasepricevat() {
		return purchasepricevat.get();
	}
	public void setPurchasepricevat(String purchasepricevat) {
		this.purchasepricevat.set(purchasepricevat);
	}


	public String getPurchasewriter() {
		return purchasewriter.get();
	}
	public void setPurchasewriter(String purchasewriter) {
		this.purchasewriter.set(purchasewriter);
	}


	
	
}