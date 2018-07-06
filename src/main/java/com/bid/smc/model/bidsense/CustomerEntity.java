package com.bid.smc.model.bidsense;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "customer")
public class CustomerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CustomerId")
	private Integer customerId;

	@Column(name = "CustomerName")
	private String name;

	@Column(name = "ContactName")
	private String contactName;

	@Column(name = "EmailId")
	private String email;

	@Column(name = "PhoneNo")
	private String phoneNo;

	@Column(name = "Address")
	private String address;

	@Column(name = "Ext")
	private Integer ext;

	@Column(name = "SaleRep")
	private String saleRep;

	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, orphanRemoval = true)
	@JsonBackReference
	private List<BidEntity> bidEntity;

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

	public List<BidEntity> getBidEntity() {
		return bidEntity;
	}

	public void setBidEntity(List<BidEntity> bidEntity) {
		this.bidEntity = bidEntity;
	}

}
