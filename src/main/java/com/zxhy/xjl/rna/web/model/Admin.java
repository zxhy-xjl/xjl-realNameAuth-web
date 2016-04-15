package com.zxhy.xjl.rna.web.model;


public class Admin {
	private String adminId;
	private String accountNumber;
	private String passwd;
	
	public Admin() {
	}
	public Admin(String adminId, String accountNumber, String passwd) {
		super();
		
		this.passwd = passwd;
		this.accountNumber = accountNumber;
		this.passwd = passwd;
	
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
