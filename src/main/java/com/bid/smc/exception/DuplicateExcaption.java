package com.bid.smc.exception;

/**
 * @author chandan.thakur
 *
 */
public class DuplicateExcaption extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	
	public DuplicateExcaption() {
		super(); 
	}
	
	public DuplicateExcaption(String errorMessage) {
		super( errorMessage);
	}
}