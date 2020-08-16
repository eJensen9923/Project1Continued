package trmstesting;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dao.AccessControlDAO;
import dao.AccessControlDAOImpl;
import models.User;

public class AccessControlDAOTest {
	
	@Test
	void accessControlDAO() {
		AccessControlDAO acd = new AccessControlDAOImpl();
		
		Map<Integer, String> roles = acd.getRoles();
		Assertions.assertTrue(roles.get(0).contentEquals("Associate"));
		User u = new User();
		u.setEmpID(10);
		Assertions.assertTrue(acd.updateDepartment(u, 1));
		u = acd.getDepartmentHead(1);
		Assertions.assertEquals(10, u.getEmpID());
		
		u.setEmpID(14);
		List<Integer> perms = acd.getUserPermissions(u);
		for(Integer i : perms)
			Assertions.assertTrue(i<3);
		acd.grantPermission(u, 1);
		perms = acd.getUserPermissions(u);
		boolean hasPerm = false;
		for(Integer i: perms)
			if(i == 1)
				hasPerm = true;
		Assertions.assertTrue(hasPerm);
		
		acd.revokePermission(u, 1);
		perms = acd.getUserPermissions(u);
		
		hasPerm = false;
		for(Integer i: perms)
			if(i == 1)
				hasPerm = true;
		Assertions.assertFalse(hasPerm);
		
		
		
		
	}
	
	

}
