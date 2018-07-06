package com.bid.smc.response;

import java.util.List;

import com.bid.smc.request.AddressRequest;

public class ShipperResponse {
	
	private String einNumber;
	private Integer shipperId;
	private String companyName;
	private AddressRequest companyAddress;
	private List<UserResponse> users;
	
	public Integer getShipperId() {
		return shipperId;
	}
	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
	}
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
	public List<UserResponse> getUsers() {
		return users;
	}
	public void setUsers(List<UserResponse> users) {
		this.users = users;
	}
	
	
}
