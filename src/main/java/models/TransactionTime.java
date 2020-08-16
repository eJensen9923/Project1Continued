package models;

public class TransactionTime implements Comparable<Object> {
	public Transaction transaction;
	public long time;
	@Override
	public int compareTo(Object arg0) {
		TransactionTime tt = ((TransactionTime) arg0);
		
		return (int)(this.time - tt.time);
	}
	
	
}
