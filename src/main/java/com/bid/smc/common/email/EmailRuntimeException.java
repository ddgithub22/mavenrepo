package com.bid.smc.common.email;

import com.bid.smc.exception.BidSenseRuntimeException;

public class EmailRuntimeException extends BidSenseRuntimeException {

	
	private static final long serialVersionUID = -8033170872486836277L;
	
	private String from;
	private String to;
	private String emailBody;
	private String subject;
	
	/**
	 * 
	 * only use this to create a new exception from scratch. if 
	 * wrapping an old exception, drop the old exception 
	 * in as well to preserve the stack trace.
	 * 
	 * @param message
	 */
	public EmailRuntimeException(String message) {
        super(message);

    }
	
	public EmailRuntimeException(String message, Throwable t) {
        super(message,t);

    } 
	
	public EmailRuntimeException(Throwable t){
		super(t);
	}

	public String getEmailBody() {
		return emailBody;
	}

	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}


}
