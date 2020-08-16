package dao;

import java.util.List;

import models.User;

public interface UserDAO {
	User login(String username, Integer passwordHash); 
	
	User getUser(Integer empID);
	List<User> getAllUsers();
	List<User> getSubordinates(Integer empID);
	List<User> getDepartmentStaff(Integer deptID);
	
	Boolean createUser(User u);
	Boolean updateUser(User u);
	Boolean deleteUser(User u);
}
