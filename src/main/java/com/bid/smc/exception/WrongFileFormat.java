package com.bid.smc.exception;

public class WrongFileFormat extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongFileFormat() {
		super();
	}

	public WrongFileFormat(String errorMessage) {
		super(errorMessage);
	}
}
