package com.bid.smc.exception;

/**
 * @author chandan.thakur
 *
 */
public class FormatException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatException() {
		super();
	}

	public FormatException(String errorMessage) {
		super(errorMessage);
	}
}
