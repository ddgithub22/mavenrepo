package com.bid.smc.response;

public class LastLoginDetail {
	private String email;
	private String companyName;
	private String lastLogin;
	private String firstName;
	private String lastName;
	private String companyType;
	
	public LastLoginDetail(String email, String firstName, String lastName, String companyName, 
			String lastLogin, String companyType) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyName = companyName;
		this.lastLogin = lastLogin;
		this.companyType = companyType;
	}
	
	public String getEmail() {
		return email;
	}
	public String getCompanyName() {
		return companyName;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getCompanyType() {
		return companyType;
	}
}
