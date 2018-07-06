package com.bid.smc.model.bidsense;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "carrier_details")
@Transactional("bidsenseTransactionManager")
public class CarrierEntity {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CompanyId")
	private Integer companyId;

	@Column(name = "MCNumber")
	private String mcNumber;

	@Column(name = "DOTNumber")
	private String dotNumber;

	@Column(name = "CompanyType")
	private String companyType;

	@Column(name = "ContactName")
	private String contactName;

	@Column(name = "ContactEmail")
	private String contactEmail;

	@Column(name = "Registered")
	private Boolean registered;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.ALL })
	@JoinTable(name = "shipper_details_carrier_details", joinColumns = {
			@JoinColumn(name = "carriers_CompanyId") }, inverseJoinColumns = {
					@JoinColumn(name = "ShipperEntity_CompanyId") })
	private List<ShipperEntity> shipper;

	@OneToMany(mappedBy = "carrier", orphanRemoval = true)
	@JsonBackReference
	private List<InviteCarrierEntity> inviteCarrier;

	public Integer getCompanyId() {
		return companyId;
	}

	public List<ShipperEntity> getShipper() {
		return shipper;
	}

	public void setShipper(List<ShipperEntity> shipper) {
		this.shipper = shipper;
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

	public String getDotNumber() {
		return dotNumber;
	}

	public void setDotNumber(String dotNumber) {
		this.dotNumber = dotNumber;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
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

	public Boolean getRegistered() {
		return registered;
	}

	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}

	public List<InviteCarrierEntity> getInviteCarrier() {
		return inviteCarrier;
	}

	public void setInviteCarrier(List<InviteCarrierEntity> inviteCarrier) {
		this.inviteCarrier = inviteCarrier;
	}

}
