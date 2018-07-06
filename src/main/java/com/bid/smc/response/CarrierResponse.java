package com.bid.smc.response;

import com.bid.smc.request.AddressRequest;

public class CarrierResponse {
	private String mcNumber;
	private String dotNumber;
	private Integer carrierId;
	private String companyName;
	private String emailId;
	private AddressRequest companyAddress;
	private UserResponse user;
	
	public Integer getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(Integer carrierId) {
		this.carrierId = carrierId;
	}
	public String getMcNumber() {
		return mcNumber;
	}
	public void setMcNumber(String mcNumber) {
		this.mcNumber = mcNumber;
	}
	public String getDotNumber() {
		return dotNumber;
	}
	public void setDotNumber(String dotNumber) {
		this.dotNumber = dotNumber;
	}
	public AddressRequest getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(AddressRequest companyAddress) {
		this.companyAddress = companyAddress;
	}
	public UserResponse getUser() {
		return user;
	}
	public void setUser(UserResponse user) {
		this.user = user;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
}
