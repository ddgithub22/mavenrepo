package com.bid.smc.model.bidsense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bid_response_details")
public class BidResponseDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BidResponseDetailsId")
	private Integer bidResponseDetailsId;

	@Column(name = "Rate")
	private Integer rate;

	@Column(name = "Volume")
	private String volume;

	@Column(name = "Days")
	private Integer days;

	@Column(name = "Notes")
	private String notes;

	@OneToOne(targetEntity = BidResponseStatusEntity.class, orphanRemoval = true)
	@JoinColumn(name = "BidResponseStatusId", referencedColumnName = "BidResponseStatusId")
	private BidResponseStatusEntity bidResponseStatus;

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public BidResponseStatusEntity getBidResponseStatus() {
		return bidResponseStatus;
	}

	public void setBidResponseStatus(BidResponseStatusEntity bidResponseStatus) {
		this.bidResponseStatus = bidResponseStatus;
	}

	public Integer getBidResponseDetailsId() {
		return bidResponseDetailsId;
	}

	public void setBidResponseDetailsId(Integer bidResponseDetailsId) {
		this.bidResponseDetailsId = bidResponseDetailsId;
	}

}
