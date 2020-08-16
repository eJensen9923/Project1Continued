package models;

public class ReTransaction {
	public Transaction t;
	public User u;
	public Transaction getT() {
		return t;
	}
	public void setT(Transaction t) {
		this.t = t;
	}
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	public ReTransaction(Transaction t, User u) {
		super();
		this.t = t;
		this.u = u;
	}
}
