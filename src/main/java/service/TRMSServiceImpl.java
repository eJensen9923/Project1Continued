package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dao.AccessControlDAO;
import dao.AccountDAO;
import dao.DocumentDAO;
import dao.UserDAO;
import models.Doc;
import models.Ledger;
import models.Transaction;
import models.TransactionTime;
import models.User;
import util.TRMSLogger;

public class TRMSServiceImpl implements TRMSService {
	private UserDAO ud;
	private AccountDAO ad;
	private DocumentDAO dd;
	private AccessControlDAO acd;
	
	public TRMSServiceImpl() {
		super();
	}

	public TRMSServiceImpl(UserDAO ud, AccountDAO ad, DocumentDAO dd, AccessControlDAO acd) {
		super();
		this.ud = ud;
		this.ad = ad;
		this.dd = dd;
		this.acd = acd;
	}

	@Override
	public User login(String username, String password) {
		TRMSLogger.logger.info("User: " + username + " is attempting to login");
		return ud.login(username, password.hashCode());
	}

	@Override
	public Boolean updatePassword(User u) {
		TRMSLogger.logger.info("Updating password for: " + u.getEmpID());
		return ud.updateUser(u);
	}
	
	@Override
	public List<User> getAllUsers() {
		TRMSLogger.logger.info("Retrieving all users");
		return ud.getAllUsers();
	}

	@Override
	public List<User> getSubordinates(User u) {
		TRMSLogger.logger.info("Retrieving subordinates of: " + u.getEmpID());
		return ud.getSubordinates(u.getEmpID());
	}

	@Override
	public List<User> getDepartmentStaff(Integer deptID) {
		TRMSLogger.logger.info("Retriving department: " + deptID);
		return ud.getDepartmentStaff(deptID);
	}

	@Override
	public Transaction getTransaction(Integer tID) {
		TRMSLogger.logger.info("Retrieving: " + tID);
		TransactionTime tt = new TransactionTime();
		tt = ad.getTransaction(tID);
		long timeElapsed = System.currentTimeMillis() - tt.time;
		if(timeElapsed > 604800000 && tt.transaction.getStatus() < 2) {
			tt.transaction.setStatus(tt.transaction.getStatus() + 1);
			ad.updateTransaction(tt.transaction);
		}
			
			
		return tt.transaction;
	}
	@Override
	public List<Transaction> getAllTransactions() {
		TRMSLogger.logger.info("Getting all transactions");
		List<TransactionTime> transactions = ad.getAllTransactions();
		List<Transaction> t = new ArrayList<Transaction>();
		Collections.sort(transactions);
		for(TransactionTime tt : transactions) {
			long timeElapsed = System.currentTimeMillis() - tt.time;
			if(timeElapsed > 604800000 && tt.transaction.getStatus() < 2) {
				tt.transaction.setStatus(tt.transaction.getStatus() + 1);
				ad.updateTransaction(tt.transaction);
				tt = ad.getTransaction(tt.transaction.gettID());
			}
			t.add(tt.transaction);
				
		}
			
		return t;
	}

	@Override
	public List<Ledger> getAccount(User u) {
		TRMSLogger.logger.info("Retrieving account of: " + u.getEmpID());
		return ad.getAccount(u);
	}

	@Override
	public Boolean createTransaction(Transaction t) {
		TRMSLogger.logger.info("Creating: " + t.gettID() + " By: " + t.getEmpID());
		User u = ud.getUser(t.getEmpID());
		List<Integer> perms = acd.getUserPermissions(u);
		for(Integer i : perms) {
			if((i == 1) && (t.getStatus() < 1))
					t.setStatus(1);
		}
		return ad.createTransaction(t);
	}
	
	@Override
	public Boolean approveTransaction(Transaction t, User u) {
		TRMSLogger.logger.info("Approving: " + t.gettID() + " By: " + u.getEmpID());
		t = ad.getTransaction(t.gettID()).transaction;
		if(t.getEmpID() != u.getEmpID()) {
			if(t.getStatus() == 3) {
				t.setStatus(4);
			} else {
				List<Integer> perms = this.getUserPermissions(u);
				int status = -1;
				for(int i : perms)
					if(i > status)
						status = i;
				t.setStatus(status);
			}
			
		} else {
			t.setStatus(3);
		}
		
		
		return ad.updateTransaction(t);
	}
	
	@Override
	public Boolean updateTransaction(Transaction t) {
		TRMSLogger.logger.info("Updating: " + t.gettID());
		return ad.updateTransaction(t);
	}


	@Override
	public Boolean deleteTransaction(Transaction t) {
		TRMSLogger.logger.info("Deleting: " + t.gettID());
		return ad.deleteTransaction(t);
	}

	@Override
	public List<String> getDocuments(Transaction t) {
		TRMSLogger.logger.info("Retrieving docs for: " + t.gettID());
		return dd.getDocuments(t);
	}

	@Override
	public Boolean addDocument(Doc doc) {
		TRMSLogger.logger.info("Adding url: " + doc.url);
		Transaction t = ad.getTransaction(doc.tID).transaction;
		String url = doc.url;
		return dd.addDocument(t, url);
	}

	@Override
	public Boolean deleteDocument(String url) {
		TRMSLogger.logger.info("Deleting url: " + url);
		return dd.deleteDocument(url);
	}

	@Override
	public List<Integer> getUserPermissions(User u) {
		TRMSLogger.logger.info("Getting permissions of: " + u.getEmpID());
		return acd.getUserPermissions(u);
	}

	@Override
	public User getDepartmentHead(Integer i) {
		TRMSLogger.logger.info("Accessing Department Head of: " + i);
		return acd.getDepartmentHead(i);
	}

	@Override
	public User getUser(Integer id) {
		TRMSLogger.logger.info("Retrieving: " + id);
		return ud.getUser(id);
	}

	@Override
	public Boolean rejectTransaction(Transaction t) {
		TRMSLogger.logger.info("Rejected: " + t.gettID());
		Boolean success = false;
		t.setAmount(0.0);
		t.setStatus(9);
		ad.updateTransaction(t);
		success = true;
		return success;
	}

	

	

	
	

}
