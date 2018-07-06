package com.bid.smc.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.common.email.EmailService;
import com.bid.smc.common.email.EmailServiceImpl;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.encryption.PasswordConverter;
import com.bid.smc.exception.InvalidUserException;

import com.bid.smc.model.adminmanager.UserEntity;
import com.bid.smc.model.bidsense.PasswordResetTokenEntity;
import com.bid.smc.request.LoginRequest;
import com.bid.smc.response.UserResponse;
import com.bid.smc.service.UserService;
import com.bid.smc.validation.BidValidation;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@CrossOrigin(allowCredentials = "false", maxAge = 3600)
public class UserLoginController {

	final static long EXPIRATIONTIME = 864_000_000; // 10 days
	final static String SECRET = "ThisIsASecret";
	final static String TOKEN_PREFIX = "Bearer";
	final static String HEADER_STRING = "Authorization";

	@Autowired
	private UserService userService;
	
	@Autowired
	private BidValidation validation;
	
	@Autowired
	private JavaMailSender mailSender;
	
		@PostMapping("/login")
	public ResponseEntity<?> logMeIn(@RequestBody LoginRequest request, HttpServletResponse res) {
		BaseResponse response = new BaseResponse();
		UserResponse userResponse = new UserResponse();
		if (request.getPassword() == null) {
			response.setText(SmcConstants.PASSWORD_REQUIRED);
			response.setErrorMessage("BAD_REQUEST");
			response.setStatus(400);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		if (request.getUsername() == null) {
			response.setText(SmcConstants.USER_NAME_REQUIRE);
			response.setErrorMessage("BAD_REQUEST");
			response.setStatus(400);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			userResponse = userService.userLogin(request.getUsername(), request.getPassword());

		} catch (InvalidUserException invalid) {
			response.setText(invalid.getMessage());
			response.setErrorMessage("INVALID_USER");
			response.setStatus(401);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String JWT = Jwts.builder().setSubject(request.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
		res.addHeader("access-control-expose-headers", "Authorization");
		res.addHeader("Access-Control-Allow-Headers",
				"Content-Type, Authorization, Access-Control-Request-Method, Access-Control-Request-Headers");
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}
	
	
	/**
	 * 
	 * @param request
	 * @param userEmail
	 * @param resetToken
	 * @param password
	 * @param confirmPassword
	 * @return
	 */

	@PostMapping(path = "/users/resetPassword", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> resetPassword(final HttpServletRequest request,
			@RequestParam("email") final String userEmail, @RequestParam("resetToken") final String resetToken,
			@RequestParam("password") final String password,
			@RequestParam("confirmPassword") final String confirmPassword) {
		
		BaseResponse response = new BaseResponse();
		ResponseEntity<?> entity = validation.resetTokenValidation(userEmail, resetToken);
		UserEntity user = null;
		
		if (entity.getStatusCode().equals(HttpStatus.OK)) {
			user = userService.getUserByEmail(userEmail);
			
			if (user != null) {
				PasswordResetTokenEntity resetTokenEntity = userService.getPasswordResetTokenForUser(user.getUserId());
				
				if (validation.validateResetToken(resetToken, resetTokenEntity.getToken())) {
					if (validation.validateTokenExpiry(getCurrentDate(), resetTokenEntity.getExpiryDate()) != 1) {
						ResponseEntity<?> entity1 = validation.validatePassword(password);
						
						if (entity1.getStatusCode().equals(HttpStatus.OK)) {
							if (validation.validateConfirmPassword(password, confirmPassword)) {
								
								boolean updateStatus = userService.updatePassword(user.getUserId(),
										new PasswordConverter().passwordEncoder(password));
								
								if (updateStatus) {
									response.setText(SmcConstants.PASSWORD_UPDATE_SUCCESS);
									response.setErrorMessage("UPDATE_SUCCESS");
									response.setStatus(200);
									return new ResponseEntity<>(response, HttpStatus.OK);
								} else {
									response.setStatus(500);
									response.setText(SmcConstants.UNEXPECTED_ERROR);
									return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
								}
								
							} else {
								response.setText(SmcConstants.PASSWORD_CONFIRM_MISMATCH);
								response.setErrorMessage("NOT_ACCEPTABLE");
								response.setStatus(406);
								return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
							}
						} else {
							return entity1;
						}
					} else {
						response.setStatus(213);
						response.setText(SmcConstants.EMAIL_EXPIRED_TOKEN);
						response.setErrorMessage("RESET_TOKEN_EXPIRED");
						return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
					}
				} else {
					response.setStatus(213);
					response.setText(SmcConstants.EMAIL_INVALID_TOKEN);
					response.setErrorMessage("INVALID_TOKEN");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			} else {
				response.setStatus(213);
				response.setText(SmcConstants.USERNAME_NOT_EXIST_EMAIL);
				response.setErrorMessage("EMAIL_NOT_EXIST");
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} else {
			return entity;
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param userEmail
	 * @return
	 */
	@PostMapping(path = "users/forgetPassword")
	public ResponseEntity<?> forgetPassword(final HttpServletRequest request,
											@RequestParam("email") final String userEmail) {
		BaseResponse response = new BaseResponse();
		UserEntity user = null;
		ResponseEntity<?> entity = validation.forgetPasswordValidation(userEmail);
		try {
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				user = userService.getUserByEmail(userEmail);
				final String token = UUID.randomUUID().toString();
				if (user != null) {
					response = userService.createPasswordResetTokenForUser(response, user, token);
					if (response.getObject() != null && response.getCode() == SmcConstants.SUCCESSCODE) {
					 
						final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
								+ request.getContextPath() + "/users/resetPassword?email=" + user.getEmailAddress()
								+ "&resetToken=" + token;
						boolean mailStatus = sendMailForgetPassword(user, appUrl, SmcConstants.FORGET_PASSWORD_SUBJECT);
						if (mailStatus == true) {
							response.setStatus(200);
							response.setText(SmcConstants.EMAIL_SENT_SUCCESS);
						} else {
							response.setStatus(500);
							response.setText(SmcConstants.EMAIL_NOT_SEND);
						}
						return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				} else {
					response.setStatus(213);
					response.setText(SmcConstants.USERNAME_NOT_EXIST_EMAIL);
					response.setErrorMessage("EMAIL_NOT_EXIST");
					return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
				}
			} else {
				return entity;
			}
		} catch (Exception ex) {
			response.setStatus(417);
			response.setErrorMessage(ex.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(response.getText(), HttpStatus.OK);
	}
	
	
    /**
     * 
     * @param user
     * @param contextpath_token
     * @param subject
     * @return
     */
	private boolean sendMailForgetPassword(UserEntity user,String contextpath_token,String subject) {
		try {
			String htmltext = "Dear "+user.getFirstName()+",<br>" + "We have received your request to reset your password. "
					      + "Please click the link below to complete the reset:<br>" + contextpath_token+"<br> Thank you <br> SMC3 team";
			sendMail(subject,htmltext,user.getEmailAddress(),SmcConstants.FORGET_PASSWORD_EMAIL_FROM);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @param subject
	 * @param text
	 * @param toEmail
	 * @param fromEmail
	 * @return
	 */
	private boolean sendMail(String subject,String text,String toEmail,String fromEmail) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setSubject(subject);
			helper.setTo(toEmail);
			message.addHeader("Content-Transfer-Encoding", "7bit");
			message.setContent(text, "text/html; charset=UTF-8");
			helper.setFrom(fromEmail);
			mailSender.send(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * @return
	 */
	private Date getCurrentDate() {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, Calendar.MINUTE);
		return new Date(cal.getTime().getTime());
	}
	
	
}