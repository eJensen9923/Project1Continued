package dao;

import java.util.List;
import java.util.Map;

import models.User;

public interface AccessControlDAO extends PermissionsDAO, DepartmentsDAO, RoleDAO {

	@Override
	Map<Integer, String> getRoles();

	@Override
	Boolean updateDepartment(User u, Integer i);

	@Override
	User getDepartmentHead(Integer i);

	@Override
	List<Integer> getUserPermissions(User u);

	@Override
	Boolean grantPermission(User u, Integer i);

	@Override
	Boolean revokePermission(User u, Integer i);

}
