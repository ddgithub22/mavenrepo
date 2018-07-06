package com.bid.smc.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
/*import javax.transaction.Transactional;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.encryption.PasswordConverter;
import com.bid.smc.exception.BidSenseException;
import com.bid.smc.exception.InvalidUserException;
import com.bid.smc.model.adminmanager.CompanyEntity;
import com.bid.smc.model.adminmanager.OrgUnitEntity;
import com.bid.smc.model.adminmanager.UserEntity;
import com.bid.smc.model.bidsense.PasswordResetTokenEntity;
import com.bid.smc.repo.adminmanager.CompanyRepository;

/*import com.bid.smc.repo.adminmanager.ForgetPasswordRepository;*/
import com.bid.smc.repo.adminmanager.OrgUnitRepository;
import com.bid.smc.repo.adminmanager.UserRepository;
import com.bid.smc.repo.bidsense.ForgetPasswordRepository;
import com.bid.smc.request.UserRequest;
import com.bid.smc.response.UserResponse;
import com.bid.smc.service.UserService;
import com.bid.smc.util.EntityToResponse;
import com.bid.smc.util.ModelToEntity;
import com.bid.smc.util.TimeProvider;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	ForgetPasswordRepository forgetPasswordRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	@Autowired
	PasswordConverter password;
	 
	@Autowired
	EntityToResponse entityToResponse;

	@Autowired
	ModelToEntity modelToEntity;

	@Autowired
	OrgUnitRepository OrgUnitRepository;
	
	@Override
	public UserEntity getUserByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	/***
	 * GET
	 * @param bidId
	 * @return REview for the respective Bid id.
	 */
	@Override
	public UserResponse userLogin(String email, String password) {
		UserEntity userEntity = userRepo.findByEmail(email);
		UserResponse response = new UserResponse();
		UserResponse userResponse = new UserResponse();
		if (isEmail(email)) {
			// Check for User entered passsword 
			if (matching(password, userEntity.getPassword())
					&& email.equalsIgnoreCase(userEntity.getEmailAddress()) && isEmail(email)) {
				response = entityToResponse.userEntityToResponse(userResponse, userEntity);
				TimeProvider timeProvider = new TimeProvider();
				userRepo.updateLastLogin(timeProvider.now(), response.getUserId());
			} else {
				throw new InvalidUserException(SmcConstants.LOGIN_ERROR_MESSAGE);
			}
		} else {
			throw new InvalidUserException(SmcConstants.LOGIN_ERROR_MESSAGE);
		}
		return response;
	}

	/**
	 * @param entered
	 * @param saved
	 * @return
	 */
	public boolean matching(String entered, String saved){
	    //String md5 = null;
	    String requestPassword = password.passwordEncoder(entered);
	    try{
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(entered.getBytes());
	        //byte[] digest = md.digest();
	        //md5 = new BigInteger(1, digest).toString(16);

	        return requestPassword.equals(saved);

	    } catch (NoSuchAlgorithmException e) {
	        return false;
	    }
	}
	
	/**
	 * @param emailId
	 * @return
	 */
	public boolean isEmail(String emailId) {
		boolean value = false;
		if (userRepo.findByEmail(emailId) != null) {
			value = true;
		}
		return value;
	}

	/***
	 * @GET
	 * @param shipperId
	 * @return Users for the Corresponding Shipper.
	 */
	@Override
	public List<UserResponse> getUserByShipper(Integer shipperId) {
		List<UserResponse> responses = new ArrayList<>();
		UserResponse userResponse = new UserResponse();
		List<UserEntity> userEntities = userRepo.findAll();
		for (UserEntity entity : userEntities) {
			if (entity.getCompanyId().getiCompanyId() == shipperId) {
				UserResponse response = entityToResponse.userEntityToResponse(userResponse, entity);
				responses.add(response);
			}
		}
		return responses;
	}

	@Override
	public UserDetails loadUserByUsername(String arg0) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	 }

	@Override
	public boolean changePassword(String password, String changePassword, String email, int userId)
			throws BidSenseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateUserSettings(String email, String userSettingsJson) throws BidSenseException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLastLogin(int userId) throws BidSenseException {
		// TODO Auto-generated method stub

	}
	/***
	 * @POST request
	 * @Save User
	 */
	@Override
	@Transactional
	public UserResponse saveUser(UserRequest userRequest,Integer shipperId) {
		UserEntity userEntity = new UserEntity();
		UserResponse response = new UserResponse();
		UserEntity entity = modelToEntity.userRequestToEntity(userRequest,userEntity);
		CompanyEntity companyEntity = companyRepo.findOne(shipperId);
		OrgUnitEntity orgUnit = OrgUnitRepository.findByCompanyId(shipperId);
		
		entity.setCompanyId(companyEntity);
		entity.setOrgUnitEntity(orgUnit);
		entity.setStatus('A');
		
		try {
			StringBuffer hexString = passwordEncoder(userRequest);
			entity.setPassword(hexString.toString());
		} catch (Exception e) {
			
		}
		UserEntity user = userRepo.save(entity);
		return entityToResponse.userEntityToResponse(response, user);
	}
	
	
	
	/**
	 * @param userRequest
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public StringBuffer passwordEncoder(UserRequest userRequest) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(userRequest.getPassword().getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString;
	}

	/***
	 * @GET
	 * @return all users.
	 */
	@Override
	public List<UserResponse> findAll() {
		return null;
	}
	
    /**
     * @return users for the given User Id.s
     */
	@Override
	public UserResponse findById(Integer id) {
		return null;
	}
	
	@Override
	public BaseResponse createPasswordResetTokenForUser(BaseResponse response,UserEntity user, String token) {
		  forgetPasswordRepo.deleteByUser(user.getUserId());
		  final PasswordResetTokenEntity myToken = new PasswordResetTokenEntity(token,user.getUserId());
		  forgetPasswordRepo.save(myToken);
		  response.setObject(myToken);
		  response.setCode(SmcConstants.SUCCESSCODE);
		  return response;
	}
	@Override
	public PasswordResetTokenEntity getPasswordResetTokenForUser(Integer userId) {
		PasswordResetTokenEntity myToken = forgetPasswordRepo.findTokenByuserId(userId).get(0);
		return myToken;
	}
	@Override
	public boolean updatePassword(Integer userId, String newPassword) {
		Integer row =  userRepo.updatePasswordById(newPassword, userId);
	    if(row>0){
	       return true;
	    }
		return false;
	}
}
