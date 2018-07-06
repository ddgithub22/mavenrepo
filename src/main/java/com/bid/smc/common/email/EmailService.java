package com.bid.smc.common.email;

import com.bid.smc.exception.BidSenseException;

public interface EmailService {



	/** takes in strings to build an email with and sends it
	 * asynchronously so that callers do not have to wait on 
	 * the mail to finish being sent before moving on.  
	 * 
	 * 
	 * @param subject
	 * @param body
	 * @param to
	 * @param from
	 */
	public void sendEmail(String subject, String body, String to, String from) throws BidSenseException;
	
	
	/** takes in strings to build an email to send to multiple people 
	 * asynchronously so that callers do not have to wait on 
	 * the mail to finish being sent before moving on.  
	 * 
	 * 
	 * @param subject
	 * @param body
	 * @param to
	 * @param from
	 */
	public void sendEmail(String subject, String body, String[] to, String from) throws BidSenseException;
	
	
	
	/** takes in strings to build an HTML email with and sends it
	 * asynchronously so that callers do not have to wait on 
	 * the mail to finish being sent before moving on.  
	 * 
	 * 
	 * @param subject
	 * @param body
	 * @param to
	 * @param from
	 */
	public void sendHtmlEmail(String subject, String html, String to, String from) throws BidSenseException;
	
	
	
	/** takes in strings to build an HTML email to send to
	 * multiple people
	 * asynchronously so that callers do not have to wait on 
	 * the mail to finish being sent before moving on.  
	 * 
	 * 
	 * @param subject
	 * @param body
	 * @param to
	 * @param from
	 */
	public void sendHtmlEmail(String subject, String html, String[] to, String from) throws BidSenseException;
}
