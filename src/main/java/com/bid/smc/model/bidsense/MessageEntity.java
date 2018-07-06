package com.bid.smc.model.bidsense;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "message")
public class MessageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "messageId")
	private Integer messageId;

	@Column(name = "Questions")
	private String questions;

	@Column(name = "Answers")
	private String answers;

	@CreationTimestamp
	@Column(name = "AskedDate")
	private Date askedDate;

	@Column(name = "ReplyDate")
	private Date replyDate;

	@OneToOne
	@JoinColumn(name = "bidId")
	private BidEntity bidEntity;

	@OneToOne
	@JoinColumn(name = "carrierId")
	private CarrierEntity carEntity;

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getQuestions() {
		return questions;
	}

	public void setQuestions(String questions) {
		this.questions = questions;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public BidEntity getBidEntity() {
		return bidEntity;
	}

	public void setBidEntity(BidEntity bidEntity) {
		this.bidEntity = bidEntity;
	}

	public CarrierEntity getCarEntity() {
		return carEntity;
	}

	public void setCarEntity(CarrierEntity carEntity) {
		this.carEntity = carEntity;
	}

	public Date getAskedDate() {
		return askedDate;
	}

	public void setAskedDate(Date askedDate) {
		this.askedDate = askedDate;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

}
