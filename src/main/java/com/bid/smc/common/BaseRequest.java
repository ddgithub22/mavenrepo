package com.bid.smc.common;

/**
 * @author chandan.thakur
 *
 */
public class BaseRequest {
	
	private String text;
	private int status;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
