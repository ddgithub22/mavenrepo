package com.bid.smc.model.bidsense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "broker_details")
public class BrokerEntity {
	
	
	@Id
	@Column(name = "CompanyId")
	private Integer companyId;
	
	@Column(name = "MCNumber")
	private String mcNumber;
	
	@Column(name = "billToEmail")
	private String billToEmail;
	


	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getMcNumber() {
		return mcNumber;
	}

	public void setMcNumber(String mcNumber) {
		this.mcNumber = mcNumber;
	}

	public String getBillToEmail() {
		return billToEmail;
	}

	public void setBillToEmail(String billToEmail) {
		this.billToEmail = billToEmail;
	}
 

}
