import java.util.List;

import com.google.gson.Gson;

import dao.AccessControlDAO;
import dao.AccessControlDAOImpl;
import dao.AccountDAO;
import dao.AccountDAOImpl;
import dao.DocumentDAO;
import dao.DocumentDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import models.Ledger;
import models.User;
import service.TRMSService;
import service.TRMSServiceImpl;

public class tester {

	public static void main(String[] args) {
		Gson gson = new Gson();
		UserDAO ud = new UserDAOImpl();
		AccountDAO ad = new AccountDAOImpl();
		DocumentDAO dd = new DocumentDAOImpl();
		AccessControlDAO acd = new AccessControlDAOImpl();
		TRMSService ts = new TRMSServiceImpl(ud,ad,dd,acd);
		User u = ud.getUser(14);
		List<Ledger> ll = ts.getAccount(u);
		for(Ledger l : ll)
			System.out.println(gson.toJson(l));

	}

}
