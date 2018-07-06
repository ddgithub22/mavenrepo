package com.bid.smc.response;

import com.bid.smc.request.AddressRequest;

public class BrokerResponse {
	private String mcNumber;
	private Integer brokerId;
	public Integer getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Integer brokerId) {
		this.brokerId = brokerId;
	}

	private String brokerCompany;
	private String billToEmail;
	private AddressRequest companyAddress;
	private UserResponse user;

	public String getMcNumber() {
		return mcNumber;
	}

	public void setMcNumber(String mcNumber) {
		this.mcNumber = mcNumber;
	}

	public String getBrokerCompany() {
		return brokerCompany;
	}

	public void setBrokerCompany(String brokerCompany) {
		this.brokerCompany = brokerCompany;
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

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

}
