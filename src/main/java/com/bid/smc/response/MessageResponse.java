package com.bid.smc.response;

import java.util.Date;

public class MessageResponse {
	    private Integer bidId;
	    private Integer carrierId;
	    private Integer messageId;
	    private String questions;
	    private String answers;
	    private Date askedDate;
	    private Date replyDate;
	    public String getQuestions() {
			return questions;
		}
		public void setQuestions(String questions) {
			this.questions = questions;
		}
		public Date getAskedDate() {
			return askedDate;
		}
		public void setAskedDate(Date askedDate) {
			this.askedDate = askedDate;
		}
		
		public Integer getBidId() {
			return bidId;
		}
		public void setBidId(Integer bidId) {
			this.bidId = bidId;
		}
		public Integer getCarrierId() {
			return carrierId;
		}
		public void setCarrierId(Integer carrierId) {
			this.carrierId = carrierId;
		}
		public Integer getMessageId() {
			return messageId;
		}
		public void setMessageId(Integer messageId) {
			this.messageId = messageId;
		}
		public String getAnswers() {
			return answers;
		}
		public void setAnswers(String answers) {
			this.answers = answers;
		}
		public Date getReplyDate() {
			return replyDate;
		}
		public void setReplyDate(Date replyDate) {
			this.replyDate = replyDate;
		}
	
	
}
