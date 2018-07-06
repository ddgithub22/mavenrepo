package com.bid.smc.service;

import java.util.List;

import org.springframework.http.RequestEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.UserStatus;
import com.bid.smc.exception.BidSenseException;
import com.bid.smc.model.adminmanager.UserEntity;
import com.bid.smc.model.bidsense.PasswordResetTokenEntity;
import com.bid.smc.request.UserRequest;
import com.bid.smc.response.UserResponse;

public interface UserService extends UserDetailsService{

	
	/**
	 * 
	 * attempts to change a users password.
	 * before making the change, this method checks to ensure the user does not have
	 * roles other than the ones required for bidsense access assigned to them
	 * returns true if a password has been changed, otherwise returns false.
	 * 
	 * @param password
	 * @param changePassword
	 * @param email
	 * @param userId
	 * @return
	 * @throws BidSenseException
	 */
	public boolean changePassword(String password, String changePassword, String email, int userId) throws BidSenseException;
	
	/**
	 * attempts to load a user's settings object
	 * @param email
	 */
	UserResponse saveUser(UserRequest user,Integer shipperId);
	
	void updateUserSettings(String email, String userSettingsJson) throws BidSenseException;
	
	void updateLastLogin(int userId) throws BidSenseException;

	public UserEntity getUserByEmail(String email);
	
	public UserResponse userLogin(String email, String password);
	
	List<UserResponse> getUserByShipper(Integer shipperId);
	
	List<UserResponse> findAll();
	
	UserResponse findById(Integer id);
	
	public BaseResponse createPasswordResetTokenForUser(BaseResponse response,UserEntity user, String token);
	
	public PasswordResetTokenEntity getPasswordResetTokenForUser(Integer userId);
	
	public boolean updatePassword(Integer userId,String newPassword); 
	
}
