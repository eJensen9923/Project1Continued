package models;

public class Transaction {
	private Integer tID = 0;
	private Integer fiscalYear = 0;
	private Integer empID = 0;
	private Double amount = 0.0;
	private Integer Status = 0;
	String startdate, location, gradetype;
	
	public Transaction() {
		super();
	}

	public Transaction(Integer fiscalYear, Integer empID, Double amount, Integer status, String startdate, String location, String gradetype) {
		super();
		this.fiscalYear = fiscalYear;
		this.empID = empID;
		this.amount = amount;
		this.Status = status;
		this.startdate = startdate;
		this.location = location;
		this.gradetype = gradetype;
	}

	public Transaction(Integer tID, Integer fiscalYear, Integer empID, Double amount, Integer status, String startdate, String location, String gradetype) {
		super();
		this.tID = tID;
		this.fiscalYear = fiscalYear;
		this.empID = empID;
		this.amount = amount;
		Status = status;
		this.startdate = startdate;
		this.location = location;
		this.gradetype = gradetype;
	}

	public Integer gettID() {
		return tID;
	}

	public void settID(Integer tID) {
		this.tID = tID;
	}

	public Integer getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
	}

	public Integer getEmpID() {
		return empID;
	}

	public void setEmpID(Integer empID) {
		this.empID = empID;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}

	public String getDate() {
		return startdate;
	}

	public void setDate(String startdate) {
		this.startdate = startdate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getGradetype() {
		return gradetype;
	}

	public void setGradetype(String gradetype) {
		this.gradetype = gradetype;
	}
	
	
	
	
}
