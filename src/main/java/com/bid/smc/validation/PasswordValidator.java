package com.bid.smc.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

	  private Pattern pattern;
	  private Matcher matcher;

	  private static final String PASSWORD_PATTERN = "(?=.*[!@#$%&*'\"()_+=|<>?{}\\[\\]~-[\\`][\"][\\.]])(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*";
	  //Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
	  // (?=.*[@#$%])
	  public PasswordValidator(){
		  pattern = Pattern.compile(PASSWORD_PATTERN);
	  }
	  
	  /**
	   * Validate password with regular expression
	   * @param password password for validation
	   * @return true valid password, false invalid password
	   */
	  public boolean validate(final String password){
		  matcher = pattern.matcher(password);
		  return matcher.matches();
	  }
	  
	 /*public static void main(String[] args){
		  String password = "H3Fs.";
		  System.out.println(new PasswordValidator().validate(password));
	  }*/
	  
	  
	  
	  
	  
}
