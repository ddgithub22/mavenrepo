package com.bid.smc.model.bidsense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rate")
public class RateEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RateId")
	private Integer rateId;

	@Column(name = "Rate")
	private String rate;

	public Integer getRateId() {
		return rateId;
	}

	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

}
