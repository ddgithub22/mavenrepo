package com.bid.smc.common;

import org.springframework.http.HttpStatus;

public class BaseResponse {
	private static final long serialVersionUID = 1L;
	
	protected int status;
	protected String errorMessage;
	protected String text;

	private long code;
	private Object object;
	private Exception exception;
	private HttpStatus httpStatus;

	/**
	 * @param status
	 * @param errorMessage
	 */
	public BaseResponse(int status, String errorMessage){
		this.status = status;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @param status
	 * @param errorMessage
	 * @param text
	 */
	public BaseResponse(int status, String errorMessage, String text){
		this.status = status;
		this.errorMessage = errorMessage;
		this.text = text;
	}

	
	
	/**
	 * default constructor returns a successful response with status: 0 
	 * and no error message.
	 */
	public BaseResponse(){
		makeSuccessfulResponse();
	}
	
	
	

	public static BaseResponse makeSuccessfulResponse(){
		return new BaseResponse(0,null);
	}
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
}
