package com.bid.smc.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordConverter {

	@Autowired
	PasswordEncoder encoder;
	
	/**
	 * @param password
	 * @return
	 */
	public String encordingPassword(String password) {
		String hashedPassword = encoder.encode(password);
		return hashedPassword;
	}

	/**
	 * @param password
	 * @return
	 */
	public String passwordEncoder(String password)  {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

		}
		md.update(password.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format 
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
				hexString.append(hex);
		}
		return hexString.toString();
	}
}
