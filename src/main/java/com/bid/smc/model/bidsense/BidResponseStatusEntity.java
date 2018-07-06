package com.bid.smc.model.bidsense;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bid_responses_status")
public class BidResponseStatusEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BidResponseStatusId")
	private Integer bidStatusResponseId;

	@Column(name = "LaneId")
	private Integer laneId;

	@Column(name = "Status")
	private String status;

	@Column(name = "BidId")
	private Integer bidId;

	@Column(name = "ResponseDate")
	private Date responseDate;

	@Column(name = "CompanyId")
	private Integer company;

	@OneToOne(fetch = FetchType.EAGER, mappedBy = "bidResponseStatus", cascade = CascadeType.ALL)
	private BidResponseDetailsEntity bidResponseDetails;
	
	
	

	public Integer getBidStatusResponseId() {
		return bidStatusResponseId;
	}

	public void setBidStatusResponseId(Integer bidStatusResponseId) {
		this.bidStatusResponseId = bidStatusResponseId;
	}

	public Integer getLaneId() {
		return laneId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public BidResponseDetailsEntity getBidResponseDetails() {
		return bidResponseDetails;
	}

	public void setBidResponseDetails(BidResponseDetailsEntity bidResponseDetails) {
		this.bidResponseDetails = bidResponseDetails;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

}
