package com.bid.smc.response;

import java.util.Date;

public class FileUploadResponse {

	private Integer documentId;
	private String documentName;
	private Date createdDate;                                                                                                                                                                                                       
	private String documentPath;
	private Integer bidEntity;
	
	
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
	public Integer getBidEntity() {
		return bidEntity;
	}
	public void setBidEntity(Integer bidEntity) {
		this.bidEntity = bidEntity;
	}
	
	
	
}
