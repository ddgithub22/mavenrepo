package com.bid.smc.response;

public class ResponseBidResponse {

	private Integer bidResponseStatusId;
	private String volume;
	private Integer rate;
	private Integer days;
	private String note;
	private String status;
	private Integer bidId;
	
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getBidResponseStatusId() {
		return bidResponseStatusId;
	}
	public void setBidResponseStatusId(Integer bidResponseStatusId) {
		this.bidResponseStatusId = bidResponseStatusId;
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
	
}
