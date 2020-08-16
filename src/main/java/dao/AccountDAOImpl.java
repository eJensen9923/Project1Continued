package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbconn.JDBCConnection;
import models.Ledger;
import models.Transaction;
import models.TransactionTime;
import models.User;
import util.TRMSLogger;

public class AccountDAOImpl implements AccountDAO {
	Connection conn = JDBCConnection.getConnection();

	@Override
	public TransactionTime getTransaction(Integer tID) {
		TransactionTime tt = new TransactionTime();
		Transaction t = null;
		try {
			String sql = "SELECT * FROM ACCOUNTS WHERE T_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, tID);
			
			ResultSet rs = ps.executeQuery();
			
			
			if(rs.next()){
				t = new Transaction(rs.getInt("T_ID"), rs.getInt("FY"), rs.getInt("EMP_ID"), rs.getDouble("TRANSACTION_AMOUNT"),
						rs.getInt("STATUS"), rs.getString("START_DATE"), rs.getString("ADDRESS"), rs.getString("GRADE"));
				tt.transaction = t;
				tt.time = rs.getLong("TIMEVAL");
			}//Integer tID, Integer fiscalYear, Integer empID, Double amount, Integer status
				
			
			
		} catch (SQLException e) {

		}
		
		return tt;
	}

	@Override
	public List<Ledger> getAccount(User u) {
		List<Ledger> a = new ArrayList<Ledger>();
		List<Integer> years = new ArrayList<Integer>();
		try {
			String sql = "SELECT UNIQUE(FY) FROM ACCOUNTS WHERE EMP_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, u.getEmpID());
			ResultSet rs = ps.executeQuery();
			while(rs.next())
				years.add(rs.getInt("FY"));
			
		} catch (SQLException e) {
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " getAccount part1");
			System.out.println("FAIL AT SQL");
		}
		for(Integer year : years) {
			try {
				List<Transaction> t = new ArrayList<Transaction>();
				String sql = "SELECT * FROM ACCOUNTS WHERE EMP_ID = ? AND FY = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, u.getEmpID());
				ps.setInt(2, year);
				ResultSet rs = ps.executeQuery();
				while(rs.next())
					t.add(new Transaction(rs.getInt("T_ID"), rs.getInt("FY"), rs.getInt("EMP_ID"), rs.getDouble("TRANSACTION_AMOUNT"),
							rs.getInt("STATUS"), rs.getString("START_DATE"), rs.getString("ADDRESS"), rs.getString("GRADE")));
				a.add(new Ledger(u.getEmpID(), year, t));
				
			} catch (SQLException e) {

			}
		}
		return a;
	}

	@Override
	public Boolean createTransaction(Transaction t) {
		Boolean success = false;
		try {
			String sql = "CALL CREATE_TRANSACTION(?,?,?,?,?,?,?,?)";
			CallableStatement cs = conn.prepareCall(sql);
			cs.setInt(1, t.getFiscalYear());
			cs.setInt(2, t.getEmpID());
			cs.setDouble(3, t.getAmount());
			cs.setInt(4, t.getStatus());
			cs.setLong(5, System.currentTimeMillis());
			cs.setString(6, t.getLocation());
			cs.setString(7, t.getDate());
			cs.setString(8, t.getGradetype());
			
			cs.execute();
			success = true;
			
		} catch (SQLException e) {

		}
		
		
		return success;
	}
	
	@Override
	public Boolean updateTransaction(Transaction t) {
		Boolean success = false;
		try {
			String sql = "UPDATE ACCOUNTS SET FY= ?, EMP_ID = ?, TRANSACTION_AMOUNT = ?, STATUS = ?, TIMEVAL=? WHERE T_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, t.getFiscalYear());
			ps.setInt(2, t.getEmpID());
			ps.setDouble(3, t.getAmount());
			ps.setInt(4, t.getStatus());
			ps.setLong(5, System.currentTimeMillis());
			ps.setInt(6, t.gettID());
			
			ps.execute();
			success = true;
			
		} catch (SQLException e) {

		}
		
		
		return success;
	}

	@Override
	public Boolean deleteTransaction(Transaction t) {
		Boolean success = false;
		try {
			String sql = "DELETE ACCOUNTS WHERE T_ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, t.gettID());
			ps.execute();
			success = true;
		} catch (SQLException e) {
			TRMSLogger.logger.error("SQL Error: " + this.toString() + " deleteTransaction");
			System.out.println("FAIL AT SQL");
		}
		
		return success;
	}

	@Override
	public List<TransactionTime> getAllTransactions() {
		List<TransactionTime> ttl = new ArrayList<TransactionTime>();
		try {
			String sql = "SELECT * FROM ACCOUNTS";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){//Integer tID, Integer fiscalYear, Integer empID, Double amount, Integer status
				TransactionTime tt = new TransactionTime();
				tt.transaction = new Transaction(rs.getInt("T_ID"), rs.getInt("FY"), rs.getInt("EMP_ID"), rs.getDouble("TRANSACTION_AMOUNT"),
						rs.getInt("STATUS"), rs.getString("START_DATE"), rs.getString("ADDRESS"), rs.getString("GRADE"));
				tt.time = rs.getLong("TIMEVAL");
				ttl.add(tt);
			}
				
			
			
		} catch (SQLException e) {

		}
		
		return ttl;
	}

	

}
