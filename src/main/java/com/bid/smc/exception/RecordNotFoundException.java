package com.bid.smc.exception;

public class RecordNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordNotFoundException() {
		super();
	}

	public RecordNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}