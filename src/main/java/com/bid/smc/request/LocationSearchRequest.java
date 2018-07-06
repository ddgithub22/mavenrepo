package com.bid.smc.request;

public class LocationSearchRequest {

	private CallerContext callerContext;

	private String address;

	private String[] sorting;

	private String[] additionalFields;

	private String[] options;

	private String country;

	public CallerContext getCallerContext() {
		return callerContext;
	}

	public void setCallerContext(CallerContext callerContext) {
		this.callerContext = callerContext;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String[] getSorting() {
		return sorting;
	}

	public void setSorting(String[] sorting) {
		this.sorting = sorting;
	}

	public String[] getAdditionalFields() {
		return additionalFields;
	}

	public void setAdditionalFields(String[] additionalFields) {
		this.additionalFields = additionalFields;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
