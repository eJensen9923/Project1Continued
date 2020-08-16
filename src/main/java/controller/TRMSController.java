package controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface TRMSController {
	public Boolean login(HttpServletRequest request, HttpServletResponse response) throws IOException;//Should also get permissions here
	public Boolean logout(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean generateHomepage(HttpServletRequest request, HttpServletResponse response) throws IOException;
//	public Boolean updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException;
//	
	public Boolean getAccount(HttpServletRequest request, HttpServletResponse response) throws IOException;
//	public Boolean getTransactionsByYear(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean getTransactionsFromSubordinatesPending(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean getTransactionsFromDepartmentPending(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean getTransactionsToBenCoPending(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	public Boolean addYear(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	public Boolean approveTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean rejectTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean adjustTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	public Boolean createTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean deleteTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	public Boolean getDocuments(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean createDocument(HttpServletRequest request, HttpServletResponse response) throws IOException;
	public Boolean deleteDocument(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	
	
}

/*
public User login(String username, String password);
public Boolean updatePassword(User u);
public List<User> getSubordinates(User u);
public List<User> getDepartmentStaff(Integer deptID);

public Transaction getTransaction(Integer tID);
public List<Ledger> getAccount(User u);
public Boolean createTransaction(Transaction t);
public Boolean deleteTransaction(Transaction t);

public List<String> getDocuments(Transaction t);
public Boolean addDocument(Transaction t, String url);
public Boolean deleteDocument(String url);

public List<Integer>getUserPermissions(User u);
public User getDepartmentHead(Integer i);
*/