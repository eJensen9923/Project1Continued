package models;

public class Email {
	private String sender;
	private String reciever;
	private String header;
	private String message;
	public Email(String sender, String reciever, String header, String message) {
		super();
		this.sender = sender;
		this.reciever = reciever;
		this.header = header;
		this.message = message;
	}
	public Email() {
		super();
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReciever() {
		return reciever;
	}
	public void setReciever(String reciever) {
		this.reciever = reciever;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "Email [sender=" + sender + ", reciever=" + reciever + ", header=" + header + ", message=" + message
				+ "]";
	}
	

}
