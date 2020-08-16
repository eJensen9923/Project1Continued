package trmstesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUpgradeHandler;
import javax.servlet.http.Part;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;

import controller.TRMSController;
import controller.TRMSControllerImpl;
import models.Doc;
import models.Ledger;
import models.Login;
import models.Message;
import models.Transaction;
import models.User;
import service.TRMSService;
import service.TRMSServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class ControllerTest {
	Gson gson = new Gson();
	
	@Mock
	TRMSService ts = new TRMSServiceImpl();
	
	@Mock
	HttpServletRequest request = new HttpServletRequest() {

		@Override
		public Object getAttribute(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getCharacterEncoding() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getContentLength() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getContentLengthLong() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getParameter(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<String> getParameterNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] getParameterValues(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getProtocol() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getScheme() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getServerName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getServerPort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public BufferedReader getReader() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRemoteAddr() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRemoteHost() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setAttribute(String name, Object o) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeAttribute(String name) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Locale getLocale() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<Locale> getLocales() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isSecure() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRealPath(String path) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getRemotePort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getLocalName() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLocalAddr() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getLocalPort() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public ServletContext getServletContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AsyncContext startAsync() throws IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
				throws IllegalStateException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isAsyncStarted() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isAsyncSupported() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public AsyncContext getAsyncContext() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public DispatcherType getDispatcherType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getAuthType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Cookie[] getCookies() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getDateHeader(String name) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getHeader(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getIntHeader(String name) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getMethod() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPathInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getPathTranslated() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContextPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getQueryString() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRemoteUser() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isUserInRole(String role) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Principal getUserPrincipal() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRequestedSessionId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getRequestURI() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public StringBuffer getRequestURL() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getServletPath() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpSession getSession(boolean create) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public HttpSession getSession() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String changeSessionId() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isRequestedSessionIdValid() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromCookie() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromURL() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isRequestedSessionIdFromUrl() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void login(String username, String password) throws ServletException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void logout() throws ServletException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Collection<Part> getParts() throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Part getPart(String name) throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
	@Mock
	HttpServletResponse response = new HttpServletResponse() {

		@Override
		public String getCharacterEncoding() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getContentType() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PrintWriter getWriter() throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setCharacterEncoding(String charset) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setContentLength(int len) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setContentLengthLong(long len) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setContentType(String type) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setBufferSize(int size) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getBufferSize() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void flushBuffer() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resetBuffer() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isCommitted() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void reset() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setLocale(Locale loc) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Locale getLocale() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void addCookie(Cookie cookie) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean containsHeader(String name) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public String encodeURL(String url) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String encodeRedirectURL(String url) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String encodeUrl(String url) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String encodeRedirectUrl(String url) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void sendError(int sc, String msg) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void sendError(int sc) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void sendRedirect(String location) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setDateHeader(String name, long date) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addDateHeader(String name, long date) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setHeader(String name, String value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addHeader(String name, String value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setIntHeader(String name, int value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addIntHeader(String name, int value) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setStatus(int sc) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setStatus(int sc, String sm) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getStatus() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public String getHeader(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<String> getHeaders(String name) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Collection<String> getHeaderNames() {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
	@InjectMocks
	TRMSController con = new TRMSControllerImpl(ts);
	

	
	@BeforeAll
	void setUp() {
		
		MockitoAnnotations.initMocks(this); 
		
	}
	
	private BufferedReader getJSON(Object o) {
		Reader r = new StringReader(gson.toJson(o));
		BufferedReader br = new BufferedReader(r);
		return br;
	}
	
	@Test
	public void login() throws IOException {
		Login login = new Login();
		login.username = "test";
		login.password = "ptest";

		Mockito.when(request.getReader()).thenReturn(getJSON(login));
		User u = new User();
		u.setUsername(login.username);
		u.setPasswordHash(login.password.hashCode());
		u.setEmpID(9);
		Mockito.when(ts.login(login.username, login.password)).thenReturn(u);
		
		Assertions.assertTrue(con.login(request, response));
		
		
	}

	@Test
	public void logout() throws IOException {
		Assertions.assertTrue(con.logout(request, response));
	}
	
	@Test
	public void generateHomepage() throws IOException {
		User u = new User();
		u.setEmpID(9);
		List<Integer> perms = new ArrayList<Integer>();
		perms.add(0);
		perms.add(1);
		perms.add(2);
		PrintWriter pw = new PrintWriter("pwTest");
		Mockito.when(request.getReader()).thenReturn(getJSON(u));
		Mockito.when(ts.getUserPermissions(u)).thenReturn(perms);
		Mockito.when(response.getWriter()).thenReturn(pw);
		
		Assertions.assertTrue(con.generateHomepage(request, response));
		
		
	}
	
	@Test
	public void getAccount() throws IOException {
		List<Ledger> ll = new ArrayList<Ledger>();
		Ledger l = new Ledger();
		l.setEmpID(9);
		l.setFiscalYear(2020);
		ll.add(l);
		
		User u = new User();
		u.setEmpID(9);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(u));
		Mockito.when(response.getWriter()).thenReturn(new PrintWriter("pwTest"));
		
		Assertions.assertTrue(con.getAccount(request, response));
		
	}
	
//	@Test
//	public void getTransactionsByYear() throws IOException {
//		
//	}
	
	@Test
	public void getTransactionsFromSubordinatesPending() throws IOException {
		User sub = new User();
		sub.setEmpID(5);
		sub.setSuperID(9);
		List<User> subs = new ArrayList<User>();
		subs.add(sub);
		List<Transaction> transactions = new ArrayList<Transaction>();
		Transaction t = new Transaction();
		t.setStatus(-1);
		t.setEmpID(5);
		Transaction t2 = new Transaction();
		t2.setStatus(0);
		t2.setEmpID(5);
		transactions.add(t);
		transactions.add(t2);
		Ledger l = new Ledger(5,2020, transactions);
		List<Ledger> a = new ArrayList<Ledger>();
		a.add(l);
		User u = new User();
		u.setEmpID(9);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(u));
		Mockito.when(ts.getSubordinates(u)).thenReturn(subs);
		Mockito.when(ts.getAccount(sub)).thenReturn(a);
		Mockito.when(response.getWriter()).thenReturn(new PrintWriter("pwTest"));
		
		Assertions.assertTrue(con.getTransactionsFromSubordinatesPending(request, response));
		
	}
	
	@Test
	public void getTransactionsFromDepartmentPending() throws IOException {
		User sub = new User();
		sub.setEmpID(5);
		sub.setSuperID(9);
		sub.setDeptID(1);
		List<User> subs = new ArrayList<User>();
		subs.add(sub);
		List<Transaction> transactions = new ArrayList<Transaction>();
		Transaction t = new Transaction();
		t.setStatus(-1);
		t.setEmpID(5);
		Transaction t2 = new Transaction();
		t2.setStatus(0);
		t2.setEmpID(5);
		transactions.add(t);
		transactions.add(t2);
		Ledger l = new Ledger(5,2020, transactions);
		List<Ledger> a = new ArrayList<Ledger>();
		a.add(l);
		User u = new User();
		u.setEmpID(9);
		u.setDeptID(1);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(u));
		Mockito.when(ts.getDepartmentStaff(u.getDeptID())).thenReturn(subs);
		Mockito.when(ts.getAccount(sub)).thenReturn(a);
		Mockito.when(response.getWriter()).thenReturn(new PrintWriter("pwTest"));
		
		Assertions.assertTrue(con.getTransactionsFromDepartmentPending(request, response));
	}
	
	@Test
	public void getTransactionsToBenCoPending() throws IOException {
		User sub = new User();
		sub.setEmpID(5);
		sub.setSuperID(9);
		List<User> subs = new ArrayList<User>();
		subs.add(sub);
		List<Transaction> transactions = new ArrayList<Transaction>();
		Transaction t = new Transaction();
		t.setStatus(-1);
		t.setEmpID(5);
		Transaction t2 = new Transaction();
		t2.setStatus(1);
		t2.setEmpID(5);
		transactions.add(t);
		transactions.add(t2);
		User u = new User();
		u.setEmpID(9);
		u.setDeptID(1);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(u));
		Mockito.when(ts.getAllTransactions()).thenReturn(transactions);
		Mockito.when(response.getWriter()).thenReturn(new PrintWriter("pwTest"));
		
		Assertions.assertTrue(con.getTransactionsToBenCoPending(request, response));
	}
	
	@Test
	public void addYear() throws IOException {
		User sub = new User();
		sub.setEmpID(5);
		sub.setSuperID(9);
		List<User> subs = new ArrayList<User>();
		subs.add(sub);
		Transaction t = new Transaction();
		t.setFiscalYear(2020);
		t.setStatus(5);
		t.setAmount(1000.0);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(t));
		Mockito.when(ts.getAllUsers()).thenReturn(subs);
		Mockito.when(ts.createTransaction(t)).thenReturn(true);
		
		Assertions.assertTrue(con.addYear(request, response));
		
		
	}
	
	@Test
	public void approveTransaction() throws IOException {
		Transaction t = new Transaction();
		t.setEmpID(9);
		t.setFiscalYear(2020);
		t.setAmount(500.0);
		t.setStatus(-1);
		t.settID(999);
		User u = new User();
		u.setEmpID(9);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(t));
		Mockito.when(ts.approveTransaction(t, u)).thenReturn(true);
		Assertions.assertTrue(con.approveTransaction(request, response));
	}
	
	@Test
	public void rejectTransaction() throws IOException{
		Transaction t1 = new Transaction();
		String body = "test";
		t1.setEmpID(8);
		t1.settID(5);
		Message m = new Message();
		m.transaction = t1;
		m.message = body;
		
		Transaction t2 = new Transaction();
		t2.setEmpID(9);
		t2.settID(5);		
		
		User send = new User();
		User recieve = new User();
		
		send.setEmpID(8);
		send.setEmail("TEST@TESTMAIL");
		
		recieve.setEmpID(9);
		recieve.setEmail("RT@TESTMAIL");
		
		Mockito.when(request.getReader()).thenReturn(getJSON(m));
		Mockito.when(ts.getUser(8)).thenReturn(send);
		Mockito.when(ts.getUser(9)).thenReturn(recieve);
		Mockito.when(ts.getTransaction(5)).thenReturn(t2);
		Mockito.when(ts.rejectTransaction(t2)).thenReturn(true);
		
		Assertions.assertTrue(con.rejectTransaction(request, response));
	}
	
	@Test
	public void adjustTransaction() throws IOException{
		Transaction t1 = new Transaction();
		Transaction t2 = new Transaction();
		
		t1.settID(5);
		t1.setAmount(10.0);
		t2.settID(5);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(t1));
		Mockito.when(ts.getTransaction(5)).thenReturn(t2);
		Mockito.when(ts.updateTransaction(t2)).thenReturn(true);
		
		Assertions.assertTrue(con.adjustTransaction(request, response));
		
	}
	
	@Test
	public void createTransaction() throws IOException {
		Transaction t = new Transaction();
		t.setEmpID(9);
		t.setFiscalYear(2020);
		t.setAmount(500.0);
		t.setStatus(-1);
		t.settID(999);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(t));
		Mockito.when(ts.createTransaction(t)).thenReturn(true);
		
		Assertions.assertTrue(con.createTransaction(request, response));
	}
	
	@Test
	public void deleteTransaction() throws IOException {
		Transaction t = new Transaction();
		t.setEmpID(9);
		t.setFiscalYear(2020);
		t.setAmount(500.0);
		t.setStatus(-1);
		t.settID(999);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(t));
		Mockito.when(ts.deleteTransaction(t)).thenReturn(true);
		
		Assertions.assertTrue(con.deleteTransaction(request, response));
	}
	
	@Test
	public void getDocuments() throws IOException {
		Transaction t = new Transaction();
		t.setEmpID(9);
		t.setFiscalYear(2020);
		t.setAmount(500.0);
		t.setStatus(-1);
		t.settID(999);
		List<String> urls = new ArrayList<String>();
		urls.add("TEST");
		
		Mockito.when(request.getReader()).thenReturn(getJSON(t));
		Mockito.when(ts.getDocuments(t)).thenReturn(urls);
		
		Assertions.assertTrue(con.getDocuments(request, response));
	}
	
	@Test
	public void createDocument() throws IOException {
		Doc doc = new Doc("TEST", 999);
		
		Mockito.when(request.getReader()).thenReturn(getJSON(doc));
		Mockito.when(ts.addDocument(doc)).thenReturn(true);
		
		Assertions.assertTrue(con.createDocument(request, response));
	}
	
	@Test
	public void deleteDocument() throws IOException {
		String doc = "TEST";
		
		Mockito.when(request.getReader()).thenReturn(getJSON(doc));
		Mockito.when(ts.deleteDocument(doc)).thenReturn(true);
		
		Assertions.assertTrue(con.deleteDocument(request, response));
	}

}
