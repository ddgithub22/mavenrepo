package com.bid.smc.request;

/**
 * @author chandan.thakur
 *
 */
public class ShipperRequest {
	
	private String einNumber;
	private String companyName;
	private AddressRequest companyAddress;
	private UserRequest user;
	
	public String getEinNumber() {
		return einNumber;
	}
	public void setEinNumber(String einNumber) {
		this.einNumber = einNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public AddressRequest getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(AddressRequest companyAddress) {
		this.companyAddress = companyAddress;
	}
	public UserRequest getUser() {
		return user;
	}
	public void setUser(UserRequest user) {
		this.user = user;
	}
	
}
