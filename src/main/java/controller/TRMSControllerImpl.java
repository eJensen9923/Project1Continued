package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.Doc;
import models.Email;
import models.Ledger;
import models.Login;
import models.Message;
import models.Transaction;
import models.User;
import service.TRMSService;

public class TRMSControllerImpl implements TRMSController {
	public static Gson gson = new Gson();
	public TRMSService ts = null;

	public TRMSControllerImpl(TRMSService ts) {
		super();
		this.ts = ts;
	}

	@Override
	public Boolean login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		Login lgn = gson.fromJson(request.getReader(), Login.class);
		String username = lgn.username, password = lgn.password;
		User u = ts.login(username, password);
		if (u != null) {
			String json = gson.toJson(u);
			String b64json = Base64.getEncoder().encodeToString(json.getBytes());
			Cookie c = new Cookie("user", b64json);
			
			response.addCookie(c);
			success = true;
		}

		return success;
	}
	
	@Override
	public Boolean logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		
		success = true;
		return success;
	}
	
	@Override
	public Boolean generateHomepage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		User u = gson.fromJson(request.getReader(), User.class);
		List<Integer> perms = ts.getUserPermissions(u);
		String buttons = "";
		buttons +="<li class=\"btn\" onclick=\"logout()\">Log Out</li>";
		buttons +="<li class=\"btn\" onclick=\"loadpage('home.do')\">Home</li>";
		for(int i = 0; i < perms.size(); i++) {
			if(perms.get(i).intValue() == 0) {
				buttons +="<li class=\"btn\" onclick=\"loaddiv('request')\">My Requests</li>";
				buttons +="<li class=\"btn\" onclick=\"loaddiv('pending')\">Pending Requests</li>";
			}
			if(perms.get(i).intValue() == 1) {
				buttons +="<li class=\"btn\" onclick=\"loaddiv('dept')\">Department Requests</li>";
			}
			if(perms.get(i).intValue() == 2) {
				buttons +="<li class=\"btn\" onclick=\"loaddiv('benco')\">BenCo Requests</li>";
				buttons +="<li class=\"btn\" onclick=\"loaddiv('newyear')\">Grant New Year Debit</li>";
			}
		}
		response.getWriter().append(buttons);
		
		success = true;
		return success;
	}

//	@Override
//	public Boolean updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Boolean success = false;
//
//		success = true;
//		return success;
//	}
//
	@Override
	public Boolean getAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;

		User u = gson.fromJson(request.getReader(), User.class);
		System.out.println(u);
		List<Ledger> a = ts.getAccount(u);
		response.getWriter().append(gson.toJson(a));
		
		success = true;
		return success;
	}

//	@Override
//	public Boolean getTransactionsByYear(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Boolean success = false;
//		User u = gson.fromJson(request.getReader(), User.class);
//		Integer year = gson.fromJson(request.getReader(), Integer.class);
//		
//		List<Ledger> ll = ts.getAccount(u);
//		Ledger ledgeryear = null;
//		for(Ledger l : ll)
//			if(l.getFiscalYear() == year)
//				ledgeryear = l;
//		response.getWriter().append(gson.toJson(ledgeryear));
//
//		success = true;
//		return success;
//	}

	@Override
	public Boolean getTransactionsFromSubordinatesPending(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Boolean success = false;
		User supervisor = gson.fromJson(request.getReader(), User.class);
		List<User> subordinates = ts.getSubordinates(supervisor);
		List<Transaction> pending = new ArrayList<Transaction>();
		for (User u : subordinates) {
			List<Ledger> a = ts.getAccount(u);
			for (Ledger l : a) {
				for (Transaction t : l.getTransactions())
					if (t.getStatus() == -1 || t.getStatus() == 3)
						pending.add(t);
			}
		}
		response.getWriter().append(gson.toJson(pending));
		success = true;

		return success;
	}

	@Override
	public Boolean getTransactionsFromDepartmentPending(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Boolean success = false;
		User deptHead = gson.fromJson(request.getReader(), User.class);
		int deptID = deptHead.getDeptID();
		List<User> staff = ts.getDepartmentStaff(deptID);
		List<Transaction> pending = new ArrayList<Transaction>();
		for (User u : staff) {
			List<Ledger> a = ts.getAccount(u);
			for (Ledger l : a) {
				for (Transaction t : l.getTransactions())
					if (t.getStatus() == 0)
						pending.add(t);
			}
		}
		response.getWriter().append(gson.toJson(pending));
		success = true;

		return success;
	}

	@Override
	public Boolean getTransactionsToBenCoPending(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Boolean success = false;
		User benco = gson.fromJson(request.getReader(), User.class);
		List<Transaction> pending = new ArrayList<Transaction>();
		List<Transaction> all = ts.getAllTransactions();
		for (Transaction t : all) {
			if (t.getStatus() == 1 && t.getEmpID() != benco.getEmpID())
				pending.add(t);
			if (t.getStatus() == 3 && t.getEmpID() != benco.getEmpID())
				pending.add(t);
		}
			

		response.getWriter().append(gson.toJson(pending));
		success = true;

		return success;
	}
	
	@Override
	public Boolean addYear(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		Transaction t = gson.fromJson(request.getReader(), Transaction.class);
		List<User> all = ts.getAllUsers();
		for(User u : all)
			ts.createTransaction(new Transaction(t.getFiscalYear(), u.getEmpID(), t.getAmount(), 5, t.getDate(), t.getLocation(), t.getGradetype()));
		
		success = true;
		return success;
	}
	
	@Override
	public Boolean approveTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		Transaction t = gson.fromJson(request.getReader(), Transaction.class);
		User u = new User();
		u.setEmpID(t.getEmpID());
		ts.approveTransaction(t, u);
		
		success = true;
		return success;
	}
	
	@Override
	public Boolean rejectTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		String header = "Your request for reimbursement has been rejected";
		
		Message m = gson.fromJson(request.getReader(), Message.class);
		Transaction t = m.transaction;
		System.out.println(t);
		System.out.println(m);
		User sender = ts.getUser(t.getEmpID());
		t = ts.getTransaction(t.gettID());
		User reciever = ts.getUser(t.getEmpID());
		
		Email email = new Email(sender.getEmail(), reciever.getEmail(), header, m.message);
		
		//This is where the email can be sent from, NOT implemented
		System.out.println(email);
		
		ts.rejectTransaction(t);
		
		success = true;
		return success;
	}
	
	@Override
	public Boolean adjustTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		
		Transaction t = gson.fromJson(request.getReader(), Transaction.class);		
		System.out.println(t);
		Double amount = t.getAmount();
		Integer status = 2;
		t = ts.getTransaction(t.gettID());
		t.setAmount(amount);
		t.setStatus(status);
		ts.updateTransaction(t);
		
		success = true;
		return success;
	}

	@Override
	public Boolean createTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		Transaction t = gson.fromJson(request.getReader(), Transaction.class);
		System.out.println(t);
		ts.createTransaction(t);
		response.setStatus(200);
		success = true;
		return success;
	}

	@Override
	public Boolean deleteTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		Transaction t = gson.fromJson(request.getReader(), Transaction.class);
		ts.deleteTransaction(t);
		response.setStatus(200);
		success = true;
		return success;
	}

	@Override
	public Boolean getDocuments(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		Transaction t = gson.fromJson(request.getReader(), Transaction.class);
		List<String> urls = ts.getDocuments(t);
		response.getWriter().append(gson.toJson(urls));

		success = true;
		return success;
	}

	@Override
	public Boolean createDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		Doc doc = gson.fromJson(request.getReader(), Doc.class);
		ts.addDocument(doc);

		success = true;
		return success;
	}

	@Override
	public Boolean deleteDocument(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Boolean success = false;
		String url = gson.fromJson(request.getReader(), String.class);
		ts.deleteDocument(url);

		success = true;
		return success;
	}

	

	

	

	


	

	

}
