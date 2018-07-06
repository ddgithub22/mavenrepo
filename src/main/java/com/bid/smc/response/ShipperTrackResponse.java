package com.bid.smc.response;

/**
 * @author chandan.thakur
 *
 */
public class ShipperTrackResponse {
	
	private Integer bidId;
	private String bidName;
	private String bidNickName;
	private Integer rate;
	private Boolean shareBenchmarkRate;
	private String bidResponseStartDate;
	private String bidResponseEndDate;
	private String bidTermStartDate;
	private String bidTermEndDate;
	private String bidCreatedDate;
	private Integer noOfLanes;
	private Integer noOfCarrier;
	private Integer noOfLanesResponded;
	private Integer noOfCarrierResponded;
	private String status;
	private Integer shipperId;
	private CustomerResponse customer;
	
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
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Boolean getShareBenchmarkRate() {
		return shareBenchmarkRate;
	}
	public void setShareBenchmarkRate(Boolean shareBenchmarkRate) {
		this.shareBenchmarkRate = shareBenchmarkRate;
	}
	public String getBidResponseStartDate() {
		return bidResponseStartDate;
	}
	public void setBidResponseStartDate(String bidResponseStartDate) {
		this.bidResponseStartDate = bidResponseStartDate;
	}
	public String getBidResponseEndDate() {
		return bidResponseEndDate;
	}
	public void setBidResponseEndDate(String bidResponseEndDate) {
		this.bidResponseEndDate = bidResponseEndDate;
	}
	public String getBidTermStartDate() {
		return bidTermStartDate;
	}
	public void setBidTermStartDate(String bidTermStartDate) {
		this.bidTermStartDate = bidTermStartDate;
	}
	public String getBidTermEndDate() {
		return bidTermEndDate;
	}
	public void setBidTermEndDate(String bidTermEndDate) {
		this.bidTermEndDate = bidTermEndDate;
	}
	public String getBidCreatedDate() {
		return bidCreatedDate;
	}
	public void setBidCreatedDate(String bidCreatedDate) {
		this.bidCreatedDate = bidCreatedDate;
	}
	public Integer getNoOfLanes() {
		return noOfLanes;
	}
	public void setNoOfLanes(Integer noOfLanes) {
		this.noOfLanes = noOfLanes;
	}
	public Integer getNoOfCarrier() {
		return noOfCarrier;
	}
	public void setNoOfCarrier(Integer noOfCarrier) {
		this.noOfCarrier = noOfCarrier;
	}
	public Integer getNoOfLanesResponded() {
		return noOfLanesResponded;
	}
	public void setNoOfLanesResponded(Integer noOfLanesResponded) {
		this.noOfLanesResponded = noOfLanesResponded;
	}
	public Integer getNoOfCarrierResponded() {
		return noOfCarrierResponded;
	}
	public void setNoOfCarrierResponded(Integer noOfCarrierResponded) {
		this.noOfCarrierResponded = noOfCarrierResponded;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getShipperId() {
		return shipperId;
	}
	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
	}
	public CustomerResponse getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerResponse customer) {
		this.customer = customer;
	}

	
}
