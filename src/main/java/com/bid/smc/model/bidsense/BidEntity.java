package com.bid.smc.model.bidsense;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "bid")
public class BidEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BidId")
	private Integer bidId;

	@Column(name = "BidName")
	private String bidName;

	@Column(name = "BidNickName")
	private String bidNickName;

	@Column(name = "BidResponseStartDate")
	private Date bidResponseStartDate;

	@Column(name = "BidResponseEndDate")
	private Date bidResponseEndDate;

	@Column(name = "BidTermStartDate")
	private Date bidTermStartDate;

	@Column(name = "BidTermEndDate")
	private Date bidTermEndDate;

	@Column(name = "ShareBenchmarkRate", nullable = false, columnDefinition = "BIT DEFAULT 1")
	private Boolean shareBenchmarkRate;

	@Column(name = "CompanyId", nullable = false)
	private Integer companyId;

	@Column(name = "Status")
	private String status;

	@Column(name = "BidOwner")
	private Integer bidOwnerId;

	@Column(name = "CreatedDate")
	private Date createdDate;

	@Column(name = "SubmitDate")
	private Date submitDate;

	@ManyToOne(targetEntity = CustomerEntity.class)
	@JoinColumn(name = "CustomerId", referencedColumnName = "CustomerId")
	@JsonManagedReference
	private CustomerEntity customer;

	@OneToOne(targetEntity = RateEntity.class, orphanRemoval = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "Rate", referencedColumnName = "RateId")
	private RateEntity rate;

	@OneToMany(mappedBy = "bidEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonBackReference
	private List<BidDocumentEntity> bidDocument;

	@OneToMany(mappedBy = "bidId", cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonBackReference
	private List<InviteCarrierEntity> inviteCarrierId;
	
	@OneToMany(mappedBy = "bid", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JsonBackReference
	private List<LanesEntity> lanesEntity;

	public BidEntity() {
		super();
	}

	public BidEntity(Integer bidId) {
		super();
		this.bidId = bidId;
	}

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

	public String getBidName() {
		return bidName;
	}

	public void setBidName(String bidName) {
		this.bidName = bidName;
	}

	public String getBidNickName() {
		return bidNickName;
	}

	public void setBidNickName(String bidNickName) {
		this.bidNickName = bidNickName;
	}

	public Date getBidResponseStartDate() {
		return bidResponseStartDate;
	}

	public void setBidResponseStartDate(Date bidResponseStartDate) {
		this.bidResponseStartDate = bidResponseStartDate;
	}

	public Date getBidResponseEndDate() {
		return bidResponseEndDate;
	}

	public void setBidResponseEndDate(Date bidResponseEndDate) {
		this.bidResponseEndDate = bidResponseEndDate;
	}

	public Date getBidTermStartDate() {
		return bidTermStartDate;
	}

	public void setBidTermStartDate(Date bidTermStartDate) {
		this.bidTermStartDate = bidTermStartDate;
	}

	public Date getBidTermEndDate() {
		return bidTermEndDate;
	}

	public void setBidTermEndDate(Date bidTermEndDate) {
		this.bidTermEndDate = bidTermEndDate;
	}

	public Boolean getShareBenchmarkRate() {
		return shareBenchmarkRate;
	}

	public void setShareBenchmarkRate(Boolean shareBenchmarkRate) {
		this.shareBenchmarkRate = shareBenchmarkRate;
	}

	public CustomerEntity getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}

	public RateEntity getRate() {
		return rate;
	}

	public void setRate(RateEntity rate) {
		this.rate = rate;
	}

	public List<BidDocumentEntity> getBidDocument() {
		return bidDocument;
	}

	public void setBidDocument(List<BidDocumentEntity> bidDocument) {
		this.bidDocument = bidDocument;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getBidOwnerId() {
		return bidOwnerId;
	}

	public void setBidOwnerId(Integer bidOwnerId) {
		this.bidOwnerId = bidOwnerId;
	}

	public List<InviteCarrierEntity> getInviteCarrierId() {
		return inviteCarrierId;
	}

	public void setInviteCarrierId(List<InviteCarrierEntity> inviteCarrierId) {
		this.inviteCarrierId = inviteCarrierId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public List<LanesEntity> getLanesEntity() {
		return lanesEntity;
	}

	public void setLanesEntity(List<LanesEntity> lanesEntity) {
		this.lanesEntity = lanesEntity;
	}
	

}
