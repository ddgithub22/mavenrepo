package com.bid.smc.request;

/**
 * @author chandan.thakur
 *
 */
public class AddCarrierRequest {
	private String mcNumber;
	private String dotNumber;
	private String contactName;
	private String contactEmail;
	
	public String getMcNumber() {
		return mcNumber;
	}
	public void setMcNumber(String mcNumber) {
		this.mcNumber = mcNumber;
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
	public String getDotNumber() {
		return dotNumber;
	}
	public void setDotNumber(String dotNumber) {
		this.dotNumber = dotNumber;
	}
	
}
