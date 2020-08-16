package trmstesting;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dao.AccountDAO;
import dao.AccountDAOImpl;
import models.Ledger;
import models.Transaction;
import models.TransactionTime;
import models.User;

public class AccountDAOTest {
	/*public Transaction getTransaction(Integer tID);
	public List<Transaction> getAllTransactions();
	public List<Ledger> getAccount(User u);
	
	public Boolean updateTransaction(Transaction t);
	public Boolean createTransaction(Transaction t);
	public Boolean deleteTransaction(Transaction t);*/
	@Test
	void accountDAO() {
		
		AccountDAO ad = new AccountDAOImpl();
		Transaction t1 = new Transaction(1999, 10, 9999.99, 9, "", "", "");
		Transaction t2 = new Transaction(1998, 10, 999.99, 9, "", "", "");
		User u = new User();
		u.setEmpID(10);
		ad.createTransaction(t1);
		ad.createTransaction(t2);
		List<Ledger> ll = ad.getAccount(u);
		
		for(Ledger l : ll) {
			Assertions.assertTrue(l.getFiscalYear() != null);
			if(l.getFiscalYear() == 1999)
				t1 = l.getTransactions().get(0);
			if(l.getFiscalYear() == 1998)
				t2 = l.getTransactions().get(0);
		}
		
		List<TransactionTime> transactionsTimes = ad.getAllTransactions();
		List<Transaction> transactions = new ArrayList<Transaction>();
		for(TransactionTime tt : transactionsTimes)
			transactions.add(tt.transaction);
		
		for(Transaction transaction : transactions)
			Assertions.assertFalse(transaction == null);
		Transaction t3  = new Transaction();
		t3.setAmount(0.0);
		t3.setEmpID(0);
		t3.setFiscalYear(0);
		t3.setStatus(0);
		t3.settID(0);
		Assertions.assertEquals(0.0, t3.getAmount());
		Assertions.assertEquals(0, t3.getEmpID());
		Assertions.assertEquals(0, t3.getFiscalYear());
		Assertions.assertEquals(0, t3.getStatus());
		Assertions.assertEquals(0, t3.gettID());
		
		Ledger l = new Ledger();
		l.setFiscalYear(0);
		l.setEmpID(0);
		l.setTransactions(transactions);
		Assertions.assertEquals(0, l.getFiscalYear());
		Assertions.assertEquals(0, l.getEmpID());
		Assertions.assertEquals(transactions, l.getTransactions());
		
		t1.setAmount(100.0);
		ad.updateTransaction(t1);
		TransactionTime tt = new TransactionTime();
		tt = ad.getTransaction(t1.gettID());
		Assertions.assertEquals(100.0, t1.getAmount());
		
		ad.deleteTransaction(t1);
		ad.deleteTransaction(t2);
		
		transactionsTimes = ad.getAllTransactions();
		for(TransactionTime ttt : transactionsTimes)
			transactions.add(ttt.transaction);
		
		for(Transaction transaction : transactions)
			Assertions.assertFalse(transaction.gettID() == t1.gettID());
		
		
		
	}

}
