package com.bid.smc.exception;

/**
 * @author chandan.thakur
 *
 */
public class BidSenseRuntimeException extends RuntimeException{

	
	private static final long serialVersionUID = 4063077027103406483L;
	
	private int userId;
	private int companyId;
	private String userEmailAddress;
	private String usersFileName;
	
	

	/**
	 * 
	 * only use this to create a new exception. if 
	 * wrapping an old exception, drop the old exception 
	 * in as well to preserve the stack trace.
	 * 
	 * @param message
	 */
	public BidSenseRuntimeException(String message) {
        super(message);

    }
	
	public BidSenseRuntimeException(String message, Throwable t) {
        super(message,t);

    } 
	
	public BidSenseRuntimeException(Throwable t){
		super(t);
	}

	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getUserEmailAddress() {
		return userEmailAddress;
	}

	public void setUserEmailAddress(String userEmailAddress) {
		this.userEmailAddress = userEmailAddress;
	}
	
	public String getUsersFileName() {
		return usersFileName;
	}

	public void setUsersFileName(String usersFileName) {
		this.usersFileName = usersFileName;
	}	
	

}
