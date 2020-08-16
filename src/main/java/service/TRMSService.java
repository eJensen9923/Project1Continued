package service;

import java.util.List;

import models.Doc;
import models.Ledger;
import models.Transaction;
import models.User;

public interface TRMSService {
	public User login(String username, String password);
	public Boolean updatePassword(User u);
	public List<User> getAllUsers();
	public List<User> getSubordinates(User u);
	public List<User> getDepartmentStaff(Integer deptID);
	
	public Transaction getTransaction(Integer tID);
	public List<Transaction> getAllTransactions();
	public List<Ledger> getAccount(User u);
	public Boolean createTransaction(Transaction t);
	public Boolean approveTransaction(Transaction t, User u);
	public Boolean updateTransaction(Transaction t);
	public Boolean rejectTransaction(Transaction t);
	public Boolean deleteTransaction(Transaction t);
	
	public List<String> getDocuments(Transaction t);
	public Boolean addDocument(Doc doc);
	public Boolean deleteDocument(String url);
	
	public List<Integer>getUserPermissions(User u);
	public User getDepartmentHead(Integer i);
	public User getUser(Integer id);
	
	
	
}
/*
performs validation, decisions on request, calls DAO 
User login(String username, String password);
Boolean updatePassword(User u, String password);

List<User> getOrgChart(Integer empID);
Boolean createEmployee(User u);
Boolean updateEmployee(User u);
Boolean assignRole(User u, Integer rID);
Boolean removeEmployee(User u);

Date getFY();
Boolean setFY(Date date);
Double getLimit();
Boolean setLimit(Double limit);
Map<String, Integer> getReimbursementDefines();
Boolean setReimbursementDefine(Integer typeID, Integer percentage);

List<Request> getRequests();
List<Request> getMyRequests(User u);
List<Request> getSubRequests(User u);
List<String> getDocuments(Integer tID);
Boolean addDocument(Integer tID, String url);
Boolean deleteDocument(String url);

Boolean approveRequest(Integer tID, Integer empID);
Boolean approveReimbursement(Integer tID);
*/