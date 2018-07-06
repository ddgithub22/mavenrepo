package com.bid.smc.request;


/**
 * @author chandan.thakur
 *
 */
public class BidRequest {
	private String bidName;
	private String bidNickName;
	private String bidResponseStartDate;
	private String bidResponseEndDate;
	private String bidTermStartDate;
	private String bidTermEndDate;
	private Integer rate;
	private Boolean shareBenchmarkRate;
	private Integer customer;
	private Integer shipperId;
	private Integer bidOwner;
	
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
	public Integer getCustomer() {
		return customer;
	}
	public void setCustomer(Integer customer) {
		this.customer = customer;
	}
	public Integer getShipperId() {
		return shipperId;
	}
	public void setShipperId(Integer shipperId) {
		this.shipperId = shipperId;
	}
	public Integer getBidOwner() {
		return bidOwner;
	}
	public void setBidOwner(Integer bidOwner) {
		this.bidOwner = bidOwner;
	}
}
