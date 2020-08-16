package trmstesting;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dao.AccessControlDAO;
import dao.AccessControlDAOImpl;
import dao.AccountDAO;
import dao.AccountDAOImpl;
import dao.DocumentDAO;
import dao.DocumentDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import models.Doc;
import models.Ledger;
import models.Transaction;
import models.TransactionTime;
import models.User;
import service.TRMSService;
import service.TRMSServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class ServiceTester {

	@Mock
	UserDAO ud = new UserDAOImpl();
	@Mock
	AccountDAO ad = new AccountDAOImpl();
	@Mock
	DocumentDAO dd = new DocumentDAOImpl();
	@Mock
	AccessControlDAO acd = new AccessControlDAOImpl();
	
	@InjectMocks
	TRMSService ts = new TRMSServiceImpl(ud, ad, dd, acd);
	
	
	@BeforeAll
	void setUp() {
		
		MockitoAnnotations.initMocks(this); 
		
	}
	
	@Test
	void login() {
		User mock = new User();
		mock.setPasswordHash(0);
		mock.setEmpID(0);
		mock.setUsername("TEST");
		
		Mockito.when(ud.login("TEST", 1216985755)).thenReturn(mock);
				
		User u = ts.login("TEST", "password");
		
		Assertions.assertEquals(u.getEmpID(), mock.getEmpID());
	}
	
	@Test
	void updatePassword() {
		User u = new User();
		
		Mockito.when(ud.updateUser(u)).thenReturn(true);
		Assertions.assertTrue(ts.updatePassword(u));
		
	}
	
	@Test
	void getSubordinates() {
		List<User> mockU = new ArrayList<User>();
		User u = new User();
		u.setSuperID(0);
		u.setEmpID(10);
		mockU.add(u);
		Mockito.when(ud.getSubordinates(0)).thenReturn(mockU);
		User superV = new User();
		superV.setEmpID(0);
		List<User> get = ts.getSubordinates(superV);
		Assertions.assertEquals(mockU.get(0).getEmpID(), get.get(0).getEmpID());
		
	}
	
	@Test
	void getDepartmentStaff() {
		List<User> mockU = new ArrayList<User>();
		User u = new User();
		u.setDeptID(1);
		u.setEmpID(10);
		mockU.add(u);
		Mockito.when(ud.getDepartmentStaff(1)).thenReturn(mockU);
		List<User> get = ts.getDepartmentStaff(1);
		Assertions.assertEquals(10, get.get(0).getEmpID());
		
	}
	
	@Test
	void getTransaction() {
		Transaction t = new Transaction();
		t.setEmpID(0);
		t.settID(0);
		TransactionTime tt = new TransactionTime();
		tt.transaction = t;
		Mockito.when(ad.getTransaction(0)).thenReturn(tt);
		
		Transaction x = ts.getTransaction(0);
		Assertions.assertEquals(t.gettID(), x.gettID());
		
	}
	
	@Test
	void getAllTransactions() {
		List<TransactionTime> transactions = new ArrayList<TransactionTime>();
		Transaction t1 = new Transaction();
		t1.settID(0);
		t1.setEmpID(0);
		TransactionTime tt = new TransactionTime();
		tt.transaction = t1;
		transactions.add(tt);
		
		Mockito.when(ad.getAllTransactions()).thenReturn(transactions);
		
		List<Transaction> get = ts.getAllTransactions();
		Assertions.assertEquals(t1.gettID(), get.get(0).gettID());
		
	}
	
	@Test
	void getAccount() {
		User u = new User();
		List<Ledger> ll = new ArrayList<Ledger>();
		u.setEmpID(0);
		Ledger l1 = new Ledger();
		l1.setEmpID(0);
		ll.add(l1);
		
		Mockito.when(ts.getAccount(u)).thenReturn(ll);
		
		List<Ledger> get = ts.getAccount(u);
		
		Assertions.assertEquals(0, get.get(0).getEmpID());
		
	}
	
	@Test
	void createTransaction() {
		Transaction t1 = new Transaction();
		t1.settID(1);
		t1.setEmpID(9);
		t1.setStatus(-1);
		
		User u = new User();
		u.setEmpID(9);
		List<Integer> perms = new ArrayList<Integer>();
		perms.add(0);
		
		Mockito.when(ud.getUser(9)).thenReturn(u);
		Mockito.when(acd.getUserPermissions(u)).thenReturn(perms);
		Mockito.when(ad.createTransaction(t1)).thenReturn(true);
		
		Assertions.assertTrue(ts.createTransaction(t1));
		
		perms.add(1);
		Mockito.when(acd.getUserPermissions(u)).thenReturn(perms);
		Assertions.assertTrue(ts.createTransaction(t1));
		
	}
	
	@Test
	void approveTransaction() {
		Transaction t1 = new Transaction();
		t1.settID(1);
		TransactionTime tt = new TransactionTime();
		tt.transaction = t1;
		User u = new User();
		u.setEmpID(10);
		List<Integer> perms = new ArrayList<Integer>();
		perms.add(1);
		Mockito.when(ad.getTransaction(1)).thenReturn(tt);
		Mockito.when(ad.updateTransaction(t1)).thenReturn(true);
		Mockito.when(acd.getUserPermissions(u)).thenReturn(perms);
		Assertions.assertTrue(ts.approveTransaction(t1, u));
		
	}
	
	@Test
	void deleteTransaction() {
		Transaction t = new Transaction();
		t.settID(0);
		Mockito.when(ad.deleteTransaction(t)).thenReturn(true);
		Assertions.assertTrue(ts.deleteTransaction(t));
		
	}
	
	@Test
	void getDocuments() {
		Transaction t = new Transaction();
		t.settID(0);
		List<String> d = new ArrayList<String>();
		Doc doc = new Doc("test", 0);
		d.add(doc.url);
		Mockito.when(dd.getDocuments(t)).thenReturn(d);
		List<String> get = ts.getDocuments(t);
		Assertions.assertEquals(doc.url, get.get(0));		
		
	}
	
	@Test
	void addDocument() {
		Doc doc = new Doc("test", 0);
		Transaction t = new Transaction();
		t.settID(0);
		TransactionTime tt = new TransactionTime();
		tt.transaction = t;
		Mockito.when(ad.getTransaction(t.gettID())).thenReturn(tt);
		Mockito.when(dd.addDocument(t, "test")).thenReturn(true);
		Assertions.assertTrue(ts.addDocument(doc));
	}
	
	@Test
	void deleteDocument() {
		String url = "test";
		Mockito.when(dd.deleteDocument(url)).thenReturn(true);
		Assertions.assertTrue(ts.deleteDocument(url));
		
	}
	
	@Test
	void getUserPermissions() {
		
		User u = new User();
		u.setEmpID(10);
		List<Integer> perms = new ArrayList<Integer>();
		perms.add(1);
		Mockito.when(acd.getUserPermissions(u)).thenReturn(perms);
		List<Integer> get = ts.getUserPermissions(u);
		Assertions.assertEquals(1, get.get(0).intValue());
		
	}
	
	@Test
	void getDepartmentHead() {
		User u = new User();
		u.setEmpID(1);
		u.setDeptID(1);
		Mockito.when(acd.getDepartmentHead(1)).thenReturn(u);
		Assertions.assertEquals(1, ts.getDepartmentHead(1).getEmpID());
		
	}
	
	@Test
	void getAllUsers() {
		List<User> users = ts.getAllUsers();
		for(User u : users)
			Assertions.assertFalse(u.getEmpID() == null);
	}
	
	@Test
	void getUser() {
		User u = new User();
		u.setEmpID(0);
		Mockito.when(ud.getUser(0)).thenReturn(u);
		Assertions.assertEquals(0, ts.getUser(0).getEmpID());
		
	}
	
	@Test
	void rejectTransaction() {
		Transaction t = new Transaction();
		t.settID(0);
		
		Mockito.when(ad.updateTransaction(t)).thenReturn(true);
		Assertions.assertTrue(ts.rejectTransaction(t));
	}
	
	@Test
	void updateTransaction() {
		Transaction t = new Transaction();
		t.settID(0);
		
		Mockito.when(ad.updateTransaction(t)).thenReturn(true);
		Assertions.assertTrue(ts.updateTransaction(t));
		
	}

}
