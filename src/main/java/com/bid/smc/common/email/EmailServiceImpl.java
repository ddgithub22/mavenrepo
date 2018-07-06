package com.bid.smc.common.email;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bid.smc.exception.BidSenseException;


@Service(value="emailService")
public class EmailServiceImpl implements EmailService{

	
	@Value("${mail.hostserver:ptcsmtp}")
	private String smtpServer;
	
	//TODO: store the emailing thread here, instead of making a new one each time. 
	//should be better to make it on first use, let it sleep between emails, and reuse.
	//better yet, move this thread and other threads to a scheduling service like quartz
	//to properly handle everything in a more well designed fashion. 
	
	
	/* (non-Javadoc)
	 * @see com.smc.bsxl.common.email.EmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendEmail(String subject, String body, String to, String from) throws BidSenseException {

		if (smtpServer == null){
			//if junit is running, or something is wrong with the prop file:
			smtpServer = "ptcsmtp";
		}
		
		//verify input
		if (to == null || from == null){
			throw new BidSenseException("Null to or from.");
		}else if (to.isEmpty()||from.isEmpty()){
			throw new BidSenseException("Empty to or from.");
		}
		
		Thread sendEmailThread = new Thread(){
			public void run(){
				try{
					Email email = new SimpleEmail();
					email.setHostName(smtpServer);
					email.setSmtpPort(25);
					try {
						//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
						//email.setSSLOnConnect(true);
						email.setFrom(from);		
						email.setSubject(subject);
						email.setMsg(body);
						email.addTo(to);
						email.send();
					} catch (EmailException ee) {
					}
					
				
				}catch (RuntimeException re){
					EmailRuntimeException ere = new EmailRuntimeException("Runtime exception thrown while trying to send an emaill.",re);
					ere.setTo(to);
					ere.setFrom(from);
					ere.setEmailBody(body);
					ere.setSubject(subject);
					throw ere;
				}
			}
				
		};//end send email anon Thread class
		//send an email
		sendEmailThread.start();
	}

	/* (non-Javadoc)
	 * @see com.smc.bsxl.common.email.EmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public void sendEmail(String subject, String body, String[] to, String from) throws BidSenseException {
		
		
		throw new BidSenseException("This is not yet implemented. ");
	}

	/* (non-Javadoc)
	 * @see com.smc.bsxl.common.email.EmailService#sendHtmlEmail(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void sendHtmlEmail(String subject, String html, String to, String from) throws BidSenseException {

		
		if (smtpServer == null){
			//if junit is running, or something is wrong with the prop file:
			smtpServer = "ptcsmtp";
		}
		
		//verify input
		if (to == null || from == null){
			throw new BidSenseException("Null to or from.");
		}else if (to.isEmpty()||from.isEmpty()){
			throw new BidSenseException("Empty to or from.");
		}
		Thread sendHtmlEmailThread = new Thread(){
			public void run(){
				try{
					Email email = new HtmlEmail(); 
					email.setHostName(smtpServer);
					email.setSmtpPort(25);
					try {
						//email.setAuthenticator(new DefaultAuthenticator("username", "password"));
						//email.setSSLOnConnect(true);
						email.setFrom(from);
						email.setSubject(subject);
						email.setMsg(html);
						email.addTo(to);
						email.send();
					} catch (EmailException ee) {
					}

				}catch (RuntimeException re){
					EmailRuntimeException ere = new EmailRuntimeException("Runtime exception thrown while trying to send an emaill.",re);
					ere.setTo(to);
					ere.setFrom(from);
					ere.setEmailBody(html);
					ere.setSubject(subject);
					throw ere;
				}
			}	
		};//end send email anon Thread class
		
		
		//send an email
		sendHtmlEmailThread.start();
	}

	/* (non-Javadoc)
	 * @see com.smc.bsxl.common.email.EmailService#sendHtmlEmail(java.lang.String, java.lang.String, java.lang.String[], java.lang.String)
	 */
	@Override
	public void sendHtmlEmail(String subject, String html, String[] to, String from) throws BidSenseException {
		throw new BidSenseException("This is not yet implemented. ");
		
	}
	
}
