package com.bid.smc.response;

public class CompanyResponse {

	private int companyId;
	private String companyName;
	private Integer customerId;
	private Integer companyTypeId;
	private String companyType;
	private Integer parentCompanyId;
	private char companyStatus;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getCompanyTypeId() {
		return companyTypeId;
	}
	public void setCompanyTypeId(Integer companyTypeId) {
		this.companyTypeId = companyTypeId;
	}
	public Integer getParentCompanyId() {
		return parentCompanyId;
	}
	public void setParentCompanyId(Integer parentCompanyId) {
		this.parentCompanyId = parentCompanyId;
	}
	public char getCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(char companyStatus) {
		this.companyStatus = companyStatus;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	
}
