package dao;

import java.util.List;

import models.User;

public interface PermissionsDAO {
	public List<Integer> getUserPermissions(User u);
	public Boolean grantPermission(User u, Integer i);
	public Boolean revokePermission(User u, Integer i);

}
