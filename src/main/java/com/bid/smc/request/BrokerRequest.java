package com.bid.smc.request;

/**
 * @author chandan.thakur
 *
 */
public class BrokerRequest {
	private String mcNumber;
	private String companyName;
	private String billToEmail;
	private AddressRequest companyAddress;
	private UserRequest user;
	
	public String getMcNumber() {
		return mcNumber;
	}
	public void setMcNumber(String mcNumber) {
		this.mcNumber = mcNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBillToEmail() {
		return billToEmail;
	}
	public void setBillToEmail(String billToEmail) {
		this.billToEmail = billToEmail;
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
