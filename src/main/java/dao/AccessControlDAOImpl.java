package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbconn.JDBCConnection;
import models.User;
import util.TRMSLogger;

public class AccessControlDAOImpl implements AccessControlDAO {
	Connection conn = JDBCConnection.getConnection();

	@Override
	public List<Integer> getUserPermissions(User u) {
		List<Integer> perms = new ArrayList<Integer>();
		try {
			String sql = "SELECT * FROM PERMISSIONS WHERE EMP_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getEmpID());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				perms.add(rs.getInt("ROLE_ID"));

		} catch (SQLException e) {

		}
		return perms;
	}

	@Override
	public Boolean grantPermission(User u, Integer i) {
		Boolean success = false;
		try {
			String sql = "INSERT INTO PERMISSIONS VALUES (?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getEmpID());
			ps.setInt(2, i);
			ps.execute();
			
			success = true;

		} catch (SQLException e) {

		}
		return success;
	}

	@Override
	public Boolean revokePermission(User u, Integer i) {
		Boolean success = false;
		try {
			String sql = "DELETE PERMISSIONS WHERE EMP_ID = ? AND ROLE_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getEmpID());
			ps.setInt(2, i);
			ps.execute();
			
			success = true;

		} catch (SQLException e) {

		}
		return success;
	}

	@Override
	public Boolean updateDepartment(User u, Integer i) {
		Boolean success = false;
		try {
			String sql = "UPDATE DEPARTMENTS SET EMP_ID = ? WHERE DEPT_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getEmpID());
			ps.setInt(2, i);
			ps.execute();
			
			success = true;

		} catch (SQLException e) {

		}
		return success;
	}

	@Override
	public User getDepartmentHead(Integer i) {
		User u = null;
		try {
			String sql = "SELECT EMP_ID FROM DEPARTMENTS WHERE DEPT_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, i);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				UserDAO ud = new UserDAOImpl();
				u = ud.getUser(rs.getInt("EMP_ID"));
			}

		} catch (SQLException e) {

		}
		return u;
	}

	@Override
	public Map<Integer, String> getRoles() {
		Map<Integer, String> rm = new HashMap<Integer, String>();
		try {
			String sql = "SELECT * FROM ROLE_LIST";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				rm.put(rs.getInt("ROLE_ID"), rs.getString("TITLE"));

		} catch (SQLException e) {

		}
		return rm;
	}

}
