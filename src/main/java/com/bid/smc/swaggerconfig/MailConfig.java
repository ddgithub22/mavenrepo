package com.bid.smc.swaggerconfig;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * @author chandan.thakur
 *
 */
@Configuration
public class MailConfig {

	@Value("${spring.mail.host}")
	private String host;
	
	@Value("${spring.mail.port}")
	private int port;
	
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.smtp.ssl.trust}")
	private String ssl;
	
	/**
	 * @return
	 */
	@Bean
	public JavaMailSenderImpl getJavaMailSender() {
	
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(host);
		sender.setPort(port);
	    
		sender.setUsername(username);
	    // set the password 
		sender.setPassword(password);
	    Properties props = sender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");

	    return sender;
	}
}
