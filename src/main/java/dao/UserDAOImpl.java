package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconn.JDBCConnection;
import models.User;
import util.TRMSLogger;

public class UserDAOImpl implements UserDAO {
	Connection conn = JDBCConnection.getConnection();

	@Override
	public User login(String username, Integer passwordHash) {
		User u = null;

		try {

			String sql = "SELECT * FROM USER_LIST WHERE USER_NAME = ? AND PASSWORD_HASH = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setDouble(2, passwordHash);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				u = new User(rs.getInt("EMP_ID"), rs.getString("USER_NAME"), rs.getInt("PASSWORD_HASH"),
						rs.getString("LNAME"), rs.getString("FNAME"), rs.getString("MNAME"), rs.getString("EMAIL"),
						rs.getString("PHONE"), rs.getInt("SUPER_ID"), rs.getInt("DEPT_ID"));
			
			System.out.println(u);

		} catch (SQLException e) {
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " login");
			System.out.println("FAIL AT SQL");
		}

		return u;
	}

	@Override
	public User getUser(Integer empID) {
		User u = null;

		try {

			String sql = "SELECT * FROM USER_LIST WHERE EMP_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, empID);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				u = new User(rs.getInt("EMP_ID"), rs.getString("USER_NAME"), rs.getInt("PASSWORD_HASH"),
						rs.getString("LNAME"), rs.getString("FNAME"), rs.getString("MNAME"), rs.getString("EMAIL"),
						rs.getString("PHONE"), rs.getInt("SUPER_ID"), rs.getInt("DEPT_ID"));

		} catch (SQLException e) {
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " getUser");
		}

		return u;
	}
	@Override
	public List<User> getSubordinates(Integer empID) {
		List<User> subordinates = new ArrayList<User>();
		try {

			String sql = "SELECT * FROM USER_LIST WHERE SUPER_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, empID);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
				subordinates.add(new User(rs.getInt("EMP_ID"), rs.getString("USER_NAME"), rs.getInt("PASSWORD_HASH"),
						rs.getString("LNAME"), rs.getString("FNAME"), rs.getString("MNAME"), rs.getString("EMAIL"),
						rs.getString("PHONE"), rs.getInt("SUPER_ID"), rs.getInt("DEPT_ID")));

		} catch (SQLException e) {
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " getUser");
		}
		
		return subordinates;
	}
	
	@Override
	public List<User> getDepartmentStaff(Integer deptID) {
		List<User> department = new ArrayList<User>();
		try {

			String sql = "SELECT * FROM USER_LIST WHERE DEPT_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, deptID);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
				department.add(new User(rs.getInt("EMP_ID"), rs.getString("USER_NAME"), rs.getInt("PASSWORD_HASH"),
						rs.getString("LNAME"), rs.getString("FNAME"), rs.getString("MNAME"), rs.getString("EMAIL"),
						rs.getString("PHONE"), rs.getInt("SUPER_ID"), rs.getInt("DEPT_ID")));

		} catch (SQLException e) {
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " getUser");
		}
		return department;
	}

	@Override
	public Boolean createUser(User u) {
		Boolean success = false;
		try {
			//CALL CREATE_USER('TESTA', 1, 'L', 'F','M','E','P',10,1);
//USERNAME VARCHAR2, PASSWORDHASH NUMBER, LNAME VARCHAR2, FNAME VARCHAR2, 
			//MNAME VARCHAR2, EMAIL VARCHAR2, PHONE VARCHAR2, SUPER_ID NUMBER, DEPT_ID NUMBER
			String sql = "CALL CREATE_USER(?,?,?,?,?,?,?,?,?)";
			CallableStatement cs = conn.prepareCall(sql);
			cs.setString(1, u.getUsername());
			cs.setInt(2, u.getPasswordHash());
			cs.setString(3, u.getlName());
			cs.setString(4, u.getfName());
			cs.setString(5, u.getmName());
			cs.setString(6, u.getEmail());
			cs.setString(7, u.getPhone());
			cs.setInt(8, u.getSuperID());
			cs.setInt(9, u.getDeptID());

			cs.execute();
			success = true;

		} catch (SQLException e) {
			System.out.println("SQL Error at createuser");
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " createUser");
		}
		return success;
	}
	//USERNAME VARCHAR2, PASSWORDHASH NUMBER, LNAME VARCHAR2, FNAME VARCHAR2, 
	//MNAME VARCHAR2, EMAIL VARCHAR2, PHONE VARCHAR2, SUPER_ID NUMBER, DEPT_ID NUMBER
	@Override
	public Boolean updateUser(User u) {
		boolean success = false;
		try {
			String sql = "UPDATE USER_LIST SET PASSWORD_HASH = ?, LNAME = ?, FNAME = ?, MNAME = ?, EMAIL = ?, PHONE = ?, SUPER_ID = ?, DEPT_ID = ?"
					+ "WHERE EMP_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getPasswordHash());
			ps.setString(2, u.getlName());
			ps.setString(3, u.getfName());
			ps.setString(4, u.getmName());
			ps.setString(5, u.getEmail());
			ps.setString(6, u.getPhone());
			ps.setInt(7, u.getSuperID());
			ps.setInt(8, u.getDeptID());
			ps.setInt(9, u.getEmpID());			
			ps.execute();
			
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " createUser");
		}
		
		return success;
	}

	@Override
	public Boolean deleteUser(User u) {
		boolean success = false;
		try {
			String sql = "DELETE USER_LIST WHERE EMP_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getEmpID());
			ps.execute();
			
			success = true;
		} catch (SQLException e) {
			e.printStackTrace();
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " createUser");
		}
		
		return success;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> company = new ArrayList<User>();
		try {

			String sql = "SELECT * FROM USER_LIST";
			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while(rs.next())
				company.add(new User(rs.getInt("EMP_ID"), rs.getString("USER_NAME"), rs.getInt("PASSWORD_HASH"),
						rs.getString("LNAME"), rs.getString("FNAME"), rs.getString("MNAME"), rs.getString("EMAIL"),
						rs.getString("PHONE"), rs.getInt("SUPER_ID"), rs.getInt("DEPT_ID")));

		} catch (SQLException e) {
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " getUser");
		}
		return company;
	}

}
