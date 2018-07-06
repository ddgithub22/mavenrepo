package com.bid.smc.response;

public class AddCarrierResponse {
	private Integer companyId;
	private String dotNumber;
	private String mcNumber;
	private String companyName;
	private String contactEmail;
	private String companyType;
	private String contactName;
	
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	

}
