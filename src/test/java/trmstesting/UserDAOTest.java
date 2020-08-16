package trmstesting;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dao.UserDAO;
import dao.UserDAOImpl;
import models.User;

public class UserDAOTest {
/*
 * User login(String username, Integer passwordHash); 
	
	User getUser(Integer empID);
	List<User> getAllUsers();
	List<User> getSubordinates(Integer empID);
	List<User> getDepartmentStaff(Integer deptID);
	
	Boolean createUser(User u);
	Boolean updateUser(User u);
	Boolean deleteUser(User u);
	
 * */
	
	@Test
	void userDAO() {
		UserDAO ud = new UserDAOImpl();
		User u = new User("TESTU", 0, "TEST", "UNIT", "J", "X",	"x", 11, 2);
		ud.createUser(u);
		u = ud.login("TESTU", 0);
		u.setDeptID(1);
		u.setUsername("TESTA");
		int empid = u.getEmpID();
		u.setEmpID(empid);
		u.setSuperID(10);
		u.setEmail("E");
		u.setPhone("P");
		u.setfName("FNAME");
		u.setlName("LNAME");
		u.setmName("MNAME");
		u.setPasswordHash(1);
		Assertions.assertEquals(1, u.getDeptID());
		Assertions.assertEquals("TESTA", u.getUsername());
		Assertions.assertEquals(empid, u.getEmpID());
		Assertions.assertEquals(10, u.getSuperID());
		Assertions.assertEquals("E", u.getEmail());
		Assertions.assertEquals("P", u.getPhone());
		Assertions.assertEquals("FNAME", u.getfName());
		Assertions.assertEquals("LNAME", u.getlName());
		Assertions.assertEquals("MNAME", u.getmName());
		Assertions.assertEquals(1, u.getPasswordHash());
		ud.updateUser(u);
		List<User> users = ud.getAllUsers();
		for(User user : users)
			Assertions.assertTrue(user != null);
		users = ud.getDepartmentStaff(1);
		for(User user : users)
			Assertions.assertTrue(user.getDeptID() == 1);
		users = ud.getSubordinates(10);
		for(User user : users)
			Assertions.assertTrue(user.getSuperID() == 10);
		u = ud.getUser(empid);
		Assertions.assertTrue(u.getEmpID() == empid);
		ud.deleteUser(u);
		users = ud.getAllUsers();
		for(User user : users)
			Assertions.assertFalse(user.getEmpID() == -1);
			
	}
}
