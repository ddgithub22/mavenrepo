package com.bid.smc.exception;

import java.io.Serializable;

/**
 * @author chandan.thakur
 *
 */
public class BidSenseException extends Exception implements Serializable {
	
	private static final long serialVersionUID = -3866476754440476573L;


	public BidSenseException() {
		super("BidSense General Exception");
	}

	/**
	 * 
	 * only use this to create a new exception. if 
	 * wrapping an old exception, drop the old exception 
	 * in as well to preserve the stack trace.
	 * 
	 * @param s
	 */
	public BidSenseException(String s) {
        super(s);

    }
	
	public BidSenseException(String s, Throwable t) {
        super(s,t);

    } 
	
	public BidSenseException(Throwable t){
		super(t);
	}


}
