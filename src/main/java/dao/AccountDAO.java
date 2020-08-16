package dao;

import java.util.List;

import models.Ledger;
import models.Transaction;
import models.TransactionTime;
import models.User;

public interface AccountDAO {
	
	public TransactionTime getTransaction(Integer tID);
	public List<TransactionTime> getAllTransactions();
	public List<Ledger> getAccount(User u);
	
	public Boolean updateTransaction(Transaction t);
	public Boolean createTransaction(Transaction t);
	public Boolean deleteTransaction(Transaction t);
	
	
	

}
