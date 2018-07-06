package com.bid.smc.response;

import java.util.Date;

public class ReviewBidResponse {

	private String bidName;
	private String nickName;
	private Integer noOfLanes;
	private Date bidResponseStartTime;
	private Date bidResponseEndTime;
	private Date bidTermStartTime;
	private Date bidTermEndTime;
	private Integer noOfCarrier;
	
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getNoOfLanes() {
		return noOfLanes;
	}
	public void setNoOfLanes(Integer noOfLanes) {
		this.noOfLanes = noOfLanes;
	}
	public Date getBidResponseStartTime() {
		return bidResponseStartTime;
	}
	public void setBidResponseStartTime(Date bidResponseStartTime) {
		this.bidResponseStartTime = bidResponseStartTime;
	}
	public Date getBidResponseEndTime() {
		return bidResponseEndTime;
	}
	public void setBidResponseEndTime(Date bidResponseEndTime) {
		this.bidResponseEndTime = bidResponseEndTime;
	}
	public Date getBidTermStartTime() {
		return bidTermStartTime;
	}
	public void setBidTermStartTime(Date bidTermStartTime) {
		this.bidTermStartTime = bidTermStartTime;
	}
	public Date getBidTermEndTime() {
		return bidTermEndTime;
	}
	public void setBidTermEndTime(Date bidTermEndTime) {
		this.bidTermEndTime = bidTermEndTime;
	}
	public Integer getNoOfCarrier() {
		return noOfCarrier;
	}
	public void setNoOfCarrier(Integer noOfCarrier) {
		this.noOfCarrier = noOfCarrier;
	}
	
}
