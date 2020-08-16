package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconn.JDBCConnection;
import models.Transaction;
import util.TRMSLogger;

public class DocumentDAOImpl implements DocumentDAO {
	Connection conn = JDBCConnection.getConnection();

	@Override
	public List<String> getDocuments(Transaction t) {
		List<String> urls = new ArrayList<String>();

		try {
			String sql = "SELECT DOC_URL FROM DOCUMENTS WHERE T_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.gettID());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				urls.add(rs.getString("DOC_URL"));
			}

		} catch (SQLException e) {

		}
		return urls;
	}

	@Override
	public Boolean addDocument(Transaction t, String url) {
		Boolean success = false;
		try {
			String sql = "INSERT INTO DOCUMENTS VALUES(?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, url);
			ps.setInt(2, t.gettID());
			ps.execute();
			success = true;

		} catch (SQLException e) {
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " getTransaction");
			System.out.println("FAIL AT SQL");
		}
		return success;
	}

	@Override
	public Boolean deleteDocument(String url) {
		Boolean success = false;
		try {
			String sql = "DELETE DOCUMENTS WHERE DOC_URL = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, url);
			ps.execute();
			
			success = true;			

		} catch (SQLException e) {

		}
		return success;
	}

}
