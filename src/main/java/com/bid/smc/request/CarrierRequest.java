package com.bid.smc.request;

/**
 * @author chandan.thakur
 *
 */
public class CarrierRequest {
	private String mcNumber;
	private String dotNumber;
	private String companyName;
	private AddressRequest companyAddress;
	private UserRequest user;
	
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
	public UserRequest getUser() {
		return user;
	}
	public void setUser(UserRequest user) {
		this.user = user;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
