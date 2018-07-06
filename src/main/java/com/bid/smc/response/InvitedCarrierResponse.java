package com.bid.smc.response;

public class InvitedCarrierResponse {

	private String bidName;
	private String shipperName;
	private String responseDueDate;
	private Integer noOfLanes;
	private Integer bidId;
	
	public String getBidName() {
		return bidName;
	}
	public void setBidName(String bidName) {
		this.bidName = bidName;
	}
	public String getShipperName() {
		return shipperName;
	}
	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}
	public String getResponseDueDate() {
		return responseDueDate;
	}
	public void setResponseDueDate(String responseDueDate) {
		this.responseDueDate = responseDueDate;
	}
	public Integer getNoOfLanes() {
		return noOfLanes;
	}
	public void setNoOfLanes(Integer noOfLanes) {
		this.noOfLanes = noOfLanes;
	}
	public Integer getBidId() {
		return bidId;
	}
	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}
	
}
