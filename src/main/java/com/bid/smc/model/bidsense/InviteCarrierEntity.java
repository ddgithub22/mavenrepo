package com.bid.smc.model.bidsense;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Invitations")
public class InviteCarrierEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Id")
	private Integer id;

	@Column(name = "Status")
	private String status;

	@Column(name = "InvitedDate")
	private Date invitedDate;

	@Column(name = "Token")
	private String token;

	@ManyToOne(targetEntity = CarrierEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CarrierId")
	@JsonManagedReference
	private CarrierEntity carrier;

	@ManyToOne(targetEntity = BidEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "BidId")
	@JsonManagedReference
	private BidEntity bidId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getInvitedDate() {
		return invitedDate;
	}

	public void setInvitedDate(Date invitedDate) {
		this.invitedDate = invitedDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public BidEntity getBidId() {
		return bidId;
	}

	public void setBidId(BidEntity bidId) {
		this.bidId = bidId;
	}

	public CarrierEntity getCarrier() {
		return carrier;
	}

	public void setCarrier(CarrierEntity carrier) {
		this.carrier = carrier;
	}

}
