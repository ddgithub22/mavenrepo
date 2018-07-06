package com.bid.smc.model.bidsense;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "BidDocument")
public class BidDocumentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RfpDocumentId")
	private Integer documentId;
	
	@Column(name = "DocumentName")
	private String documentName;
	
	@Column(name = "CreatedDate")
	private Date createdDate;
	
	@Column(name = "DocumentPath")
	private String documentPath;
	
	@ManyToOne(targetEntity = BidEntity.class)
	@JoinColumn(name = "BidId", referencedColumnName = "BidId")
	@JsonManagedReference
	private BidEntity bidEntity;

	
	@PrePersist
	protected void onCreate() {
		createdDate = new Date();
	}

	
	
	
	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public BidEntity getBidEntity() {
		return bidEntity;
	}

	public void setBidEntity(BidEntity bidEntity) {
		this.bidEntity = bidEntity;
	}

}
