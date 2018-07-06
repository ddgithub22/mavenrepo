package com.bid.smc.response;


public class CustomerResponse {
	
	private Integer customerId;
	private String name;
	private String contactName;
	private String email;
	private String phoneNo;
	private String address;
	private Integer ext;
	private String saleRep;
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getExt() {
		return ext;
	}
	public void setExt(Integer ext) {
		this.ext = ext;
	}
	public String getSaleRep() {
		return saleRep;
	}
	public void setSaleRep(String saleRep) {
		this.saleRep = saleRep;
	} 
	
	
}
