package models;

import java.util.ArrayList;
import java.util.List;

public class Ledger {
	private Integer empID = 0;
	private Integer fiscalYear = 0;
	private List<Transaction> transactions = new ArrayList<Transaction>();
	public Ledger() {
		super();
	}
	public Ledger(Integer empID, Integer fiscalYear, List<Transaction> transactions) {
		super();
		this.empID = empID;
		this.fiscalYear = fiscalYear;
		this.transactions = transactions;
	}
	public Integer getEmpID() {
		return empID;
	}
	public void setEmpID(Integer empID) {
		this.empID = empID;
	}
	public Integer getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public List<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}
	public Double getBalance() {
		Double balance = 0.0;
		for(Transaction t : transactions)
			balance += t.getAmount();
		return balance;
	}
	
	

}
