package com.bid.smc.response;

public class UserResponse {
	private Integer userId;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private AddressResponse address;
	private CompanyResponse company;
	private char status;
//	private Date lastLogin;
//	private Date lastModify;
//	private String token;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public AddressResponse getAddress() {
		return address;
	}
	public void setAddress(AddressResponse address) {
		this.address = address;
	}
	public CompanyResponse getCompany() {
		return company;
	}
	public void setCompany(CompanyResponse company) {
		this.company = company;
	}
	public char getStatus() {
		return status;
	}
	public void setStatus(char status) {
		this.status = status;
	}
	/*public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Date getLastModify() {
		return lastModify;
	}
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}*/
	
}
