package com.bid.smc.response;

/**
 * @author chandan.thakur
 *
 */
public class BidResponse {
	private Integer bidId;
	private String bidName;
	private String bidNickName;
	private Integer rate;
	private Boolean shareBenchmarkRate;
	private String bidResponseStartDate;
	private String bidResponseEndDate;
	private String bidTermStartDate;
	private String bidTermEndDate;
	private Integer noOfLanes;
	private Integer noOfCarrier;
	private String status;
	private String createdDate;
	private String submitDate;
	private Integer shipperId;
	private String shipperName;
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
	public CustomerResponse getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerResponse customer) {
		this.customer = customer;
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
	public Integer getShipperId() {
		return shipperId;
	}
	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	
	
	
}
