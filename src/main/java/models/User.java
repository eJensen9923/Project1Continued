package models;

public class User {
	private Integer empID;
	private String username;
	private Integer passwordHash;
	private String lName;
	private String fName;
	private String mName;
	private String phone;
	private String email;
	private Integer superID;
	private Integer deptID;
	public User(Integer empID, String username, Integer passwordHash, String lName, String fName, String mName, String email,
			String phone, Integer superID, Integer deptID) {
		super();
		this.empID = empID;
		this.username = username;
		this.passwordHash = passwordHash;
		this.lName = lName;
		this.fName = fName;
		this.mName = mName;
		this.phone = phone;
		this.email = email;
		this.superID = superID;
		this.deptID = deptID;
	}
	public User(String username, Integer passwordHash, String lName, String fName, String mName, String phone,
			String email, Integer superID, Integer deptID) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.lName = lName;
		this.fName = fName;
		this.mName = mName;
		this.phone = phone;
		this.email = email;
		this.superID = superID;
		this.deptID = deptID;
	}
	public User() {
		super();
	}
	public Integer getEmpID() {
		return empID;
	}
	public void setEmpID(Integer empID) {
		this.empID = empID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(Integer passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getSuperID() {
		return superID;
	}
	public void setSuperID(Integer superID) {
		this.superID = superID;
	}
	public Integer getDeptID() {
		return deptID;
	}
	public void setDeptID(Integer deptID) {
		this.deptID = deptID;
	}
	@Override
	public String toString() {
		return "User [empID=" + empID + ", username=" + username + ", passwordHash=" + passwordHash + ", lName=" + lName
				+ ", fName=" + fName + ", mName=" + mName + ", phone=" + phone + ", email=" + email + ", superID="
				+ superID + ", deptID=" + deptID + "]";
	}
	
}
