package dao;

import models.User;

public interface DepartmentsDAO {
	public Boolean updateDepartment(User u, Integer i);
	public User getDepartmentHead(Integer i);

}
