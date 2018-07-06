package com.bid.smc.common.email;

public enum EmailValidationResult {
	SUCCESS_WITHOUT_ERRORS (1),
	SUCCESS_WITH_ERRORS (2),
	ABORTED (3);
	
	private EmailValidationResult(int value){
		this.emailValidationResult = value;
	}
	
	private final int emailValidationResult;
	
	public int getVal(){
		return emailValidationResult;
	}
	
}
