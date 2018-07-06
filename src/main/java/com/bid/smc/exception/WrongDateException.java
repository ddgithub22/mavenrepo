package com.bid.smc.exception;

import com.bid.smc.common.BaseResponse;

public class WrongDateException extends RuntimeException {

private static final long serialVersionUID = 1L;
	
	
	public WrongDateException(BaseResponse response) {
		super(); 
	}
	
	public WrongDateException(String errorMessage) {
		super( errorMessage);
	}
}
