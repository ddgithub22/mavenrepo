package com.bid.smc.model.bidsense;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "shipper_details")
public class ShipperEntity {

	@Id
	@Column(name = "CompanyId")
	private Integer companyId;

	@Column(name = "EINNumber")
	private String einNumber;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "shipper")
	private List<CarrierEntity> carriers;

	public List<CarrierEntity> getCarriers() {
		return carriers;
	}

	public void setCarriers(List<CarrierEntity> carriers) {
		this.carriers = carriers;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getEinNumber() {
		return einNumber;
	}

	public void setEinNumber(String einNumber) {
		this.einNumber = einNumber;
	}

}
