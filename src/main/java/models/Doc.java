package models;

public class Doc {
	public String url;
	public int tID;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int gettID() {
		return tID;
	}
	public void settID(int tID) {
		this.tID = tID;
	}
	public Doc(String url, int tID) {
		super();
		this.url = url;
		this.tID = tID;
	}

}
