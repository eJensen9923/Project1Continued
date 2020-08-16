package servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.TRMSController;
import controller.TRMSControllerImpl;
import dao.AccessControlDAOImpl;
import dao.AccountDAOImpl;
import dao.DocumentDAOImpl;
import dao.UserDAOImpl;
import service.TRMSServiceImpl;

public class Mapper {
	
	
	public static void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TRMSController controller = new TRMSControllerImpl(new TRMSServiceImpl(
				new UserDAOImpl(),
				new AccountDAOImpl(),
				new DocumentDAOImpl(),
				new AccessControlDAOImpl()
					));
		
		String uri = request.getRequestURI();
		
		switch(uri) {
		case "/TRMS/login.do":
			if(controller.login(request, response)) {
				response.setStatus(300);
				response.sendRedirect("http://localhost:8080/TRMS/trms.html");
			} else {
				response.sendError(401, "Login credentials failed");
			}
			break;
			
		case "/TRMS/logout.do":
			controller.logout(request, response);
			response.setStatus(300);
			response.sendRedirect("http://localhost:8080/TRMS/index.html");
			break;
			
		case "/TRMS/home.do":
			controller.generateHomepage(request, response);
			break;
			
		case "/TRMS/loadaccountpage.do":
			System.out.println("account");
			//response.setStatus(300);
			response.sendRedirect("http://localhost:8080/TRMS/request.html");
			break;
			
		case "/TRMS/getaccount.do":
			controller.getAccount(request, response);
			break;
			
//		case "/TRMS/getbyyear.do":
//			controller.getTransactionsByYear(request, response);
//			break;
			
		case "/TRMS/getpendingpage.do":
			response.sendRedirect("http://localhost:8080/TRMS/pending.html");
			break;
			
		case "/TRMS/getsubpending.do":
			controller.getTransactionsFromSubordinatesPending(request, response);
			break;
		
		case "/TRMS/getdeptpending.do":
			controller.getTransactionsFromDepartmentPending(request, response);
			break;
		
		case "/TRMS/getbencopending.do":
			controller.getTransactionsToBenCoPending(request, response);
			break;
		
		case "/TRMS/createt.do":
			controller.createTransaction(request, response);
			break;
		
		case "/TRMS/deletet.do":
			controller.deleteTransaction(request, response);
			break;
		
		case "/TRMS/approve.do":
			controller.approveTransaction(request, response);
			break;
			
		case "/TRMS/reject.do":
			controller.rejectTransaction(request, response);
			break;
			
		case "/TRMS/adjust.do":
			controller.adjustTransaction(request, response);
			break;
			
		case "/TRMS/getdocs.do":
			controller.getDocuments(request, response);
			break;
		
		case "/TRMS/createdoc.do":
			controller.createDocument(request, response);
			break;
		
		case "/TRMS/deletedoc.do":
			controller.deleteDocument(request, response);
			break;
			
		case "/TRMS/getyearpage.do":
			response.sendRedirect("http://localhost:8080/TRMS/fy.html");
			break;
			
		case "/TRMS/addtransactiontoall.do":
			controller.addYear(request, response);
			break;
			
		default:
			response.sendError(451, "File Not Found");
			break;
		}
		
	}
}
/*
public Boolean getTransactionsByYear(HttpServletRequest request, HttpServletResponse response) throws IOException;
public Boolean getTransactionsFromSubordinatesPending(HttpServletRequest request, HttpServletResponse response) throws IOException;
public Boolean getTransactionsFromDepartmentPending(HttpServletRequest request, HttpServletResponse response) throws IOException;
public Boolean getTransactionsToBenCoPending(HttpServletRequest request, HttpServletResponse response) throws IOException;

public Boolean createTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException;
public Boolean deleteTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException;

public Boolean getDocuments(HttpServletRequest request, HttpServletResponse response) throws IOException;
public Boolean createDocument(HttpServletRequest request, HttpServletResponse response) throws IOException;
public Boolean deleteDocument(HttpServletRequest request, HttpServletResponse response) throws IOException;*/