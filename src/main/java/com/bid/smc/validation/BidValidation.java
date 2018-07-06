package com.bid.smc.validation;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.repo.bidsense.BrokerRepository;
import com.bid.smc.repo.bidsense.CarrierRepository;
import com.bid.smc.repo.bidsense.LanesRepository;
import com.bid.smc.request.AddCarrierRequest;
import com.bid.smc.request.LaneRequest;

@Component
public class BidValidation {

	@Autowired
	LanesRepository repository;
	
	@Autowired
	BrokerRepository brokerRepo;
	
	@Autowired
	CarrierRepository carrierRepo;

	/**
	 * @param weight
	 * @return
	 */
	public boolean isValidWeight(String weight) {
		String ePattern = "\\d+";
		java.util.regex.Matcher match = patternMatcher(weight, ePattern);
		return match.matches();
	}

	/**
	 * @param incumbent
	 * @return
	 */
	public boolean isValidIncumbent(String incumbent) {
		String ePattern = "^\\d*\\.\\d+|\\d+\\.\\d*$";
		java.util.regex.Matcher match = patternMatcher(incumbent, ePattern);
		return match.matches();
	}

	/**
	 * @param d
	 * @return
	 */
	public boolean isValidBenchmark(String d) {
		String ePattern = "^\\d*\\.\\d+|\\d+\\.\\d*$";
		java.util.regex.Matcher match = patternMatcher(d, ePattern);
		return match.matches();
	}

	/**
	 * @param volume
	 * @return
	 */
	public boolean isValidVolume(String volume) {
		String ePattern = "\\d+";
		java.util.regex.Matcher match = patternMatcher(volume, ePattern);
		return match.matches();
	}

	/**
	 * @param haul
	 * @return
	 */
	public boolean isHaul(String haul) {
		String ePattern = "\\d+";
		java.util.regex.Matcher match = patternMatcher(haul, ePattern);
		return match.matches();
	}

	/**
	 * @param haul
	 * @param ePattern
	 * @return
	 */
	private java.util.regex.Matcher patternMatcher(String haul, String ePattern) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher match = pattern.matcher(haul);
		return match;
	}

	/**
	 * @param request
	 * @return
	 */
	public ResponseEntity<?> laneValidation(LaneRequest request) {

		BaseResponse response = new BaseResponse();

		if (StringUtils.isEmpty(request.getLane())) {
			response.setText(SmcConstants.LANES_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (isLane(request.getLane())) {
			response.setText(SmcConstants.LANE_PRESENT);
			response.setErrorMessage("CONFLICT");
			response.setStatus(409);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (StringUtils.isEmpty(request.getVolume())) {
			response.setText(SmcConstants.VOLUME_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (!isValidVolume(request.getVolume())) {
			response.setText(SmcConstants.VOLUME_NOTVALID);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (request.getVolume().length() > 9) {
			response.setText(SmcConstants.VOLUME_LENGTH);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (StringUtils.isEmpty(request.getOriginCity())) {
			response.setText(SmcConstants.ORIGIN_CITY_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (StringUtils.isEmpty(request.getOriginState())) {
			response.setText(SmcConstants.ORIGIN_STATE_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (StringUtils.isEmpty(request.getOriginZipCode())) {
			response.setText(SmcConstants.ORIGIN_ZIP_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		
		/*if (request.getDestination() == null || request.getDestination().equals("")) {
			response.setText(SmcConstants.DESTINATION_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}*/
		
		/*if (request.getOrigin() == null || request.getOrigin().equals("")) {
			response.setText(SmcConstants.ORIGIN_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (request.getDestination() == null || request.getDestination().equals("")) {
			response.setText(SmcConstants.DESTINATION_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}*/
		if (StringUtils.isEmpty(request.getMode())) {
			response.setText(SmcConstants.MODE_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (StringUtils.isEmpty(request.getEquipmentClass())) {
			response.setText(SmcConstants.EQUIPMENT_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (StringUtils.isEmpty(request.getCommodity())) {
			response.setText(SmcConstants.COMMODITY_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (request.getIncumbentRate()!=null && !isValidIncumbent(request.getIncumbentRate())) {
			response.setText(SmcConstants.INCUMBENT_NOTVALID);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (request.getIncumbentRate()!=null && !isValidBenchmark(request.getBenchmarkRate())) {
			response.setText(SmcConstants.BENCHMARK_NOTVALID);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (StringUtils.isEmpty(request.getWeight())) {
			response.setText(SmcConstants.WEIGHT_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (!isValidWeight(request.getWeight())) {
			response.setText(SmcConstants.WEIGHT_NOTVALID);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (request.getWeight().length() > 8) {
			response.setText(SmcConstants.WEIGHT_LENGTH);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (StringUtils.isEmpty(request.getHaul())) {
			response.setText(SmcConstants.HAUL_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (!isHaul(request.getHaul())) {
			response.setText(SmcConstants.HAUL_NOTVALID);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (request.getHaul().length() > 6) {
			response.setText(SmcConstants.HAUL_LENGTH);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @param lane
	 * @return
	 */
	public boolean isLane(String lane) {
		boolean value = false;
		if (repository.findLane(lane) != null || repository.findLane(lane) != null) {
			value = true;
		}
		return value;
	}

	/**
	 * @param mcNumber
	 * @return
	 */
	public boolean isMcNo(String mcNumber) {
		boolean value = false;
		if (brokerRepo.findMcNumber(mcNumber) != null || carrierRepo.findMcNumber(mcNumber) != null) {
			value = true;
		}
		return value;
	}

	/**
	 * @param dotNumber
	 * @return
	 */
	public boolean isDotNo(String dotNumber) {
		boolean value = false;
		if (carrierRepo.findDotNumber(dotNumber) != null) {
			value = true;
		}
		return value;
	}

	/**
	 * @param mcNumber
	 * @return
	 */
	public boolean isValidMC(String mcNumber) {
		String ePattern = "^\\d{6}$";
		java.util.regex.Matcher match = patternMatcher(mcNumber, ePattern);
		return match.matches();
	}

	/**
	 * @param dotNumber
	 * @return
	 */
	public boolean isValidDOT(String dotNumber) {
		String ePattern = "^\\d{7}$";
		java.util.regex.Matcher match = patternMatcher(dotNumber, ePattern);
		return match.matches();
	}

	/**
	 * @param email
	 * @return
	 */
	public boolean isValidEmailAdress(String email) {
		// String ePattern =
		// "^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-zA-Z-0-9)]+\\.[(a-zA-z)]{2,3}$";
		String ePattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z0-9]{2,})$";
		java.util.regex.Matcher m = patternMatcher(email, ePattern);
		return m.matches();
	}

 
	/**
	 * @param contactEmail
	 * @return
	 */
	public boolean isEmail(String contactEmail) {
		boolean value = false;
		if (carrierRepo.findByEmail(contactEmail) != null) {
			value = true;
		}
		return value;
	}

	/**
	 * @param request
	 * @return
	 */
	public ResponseEntity<?> addCarrierValidation(AddCarrierRequest request) {
		BaseResponse response = new BaseResponse();

		if (StringUtils.isEmpty(request.getMcNumber())) {
			response.setText(SmcConstants.MC_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (!isValidMC(request.getMcNumber())) {
			response.setText(SmcConstants.MC_ISVALID);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (isMcNo(request.getMcNumber())) {
			response.setText(SmcConstants.MC_NUMBER_ISVALID);
			response.setErrorMessage("CONFLICT");
			response.setStatus(409);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (StringUtils.isEmpty(request.getDotNumber())) {
			response.setText(SmcConstants.DOT_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (!isValidDOT(request.getDotNumber())) {
			response.setText(SmcConstants.DOT_ISVALID);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (isDotNo(request.getDotNumber())) {
			response.setText(SmcConstants.DOT_NUMBER_ISVALID);
			response.setErrorMessage("CONFLICT");
			response.setStatus(409);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (StringUtils.isEmpty(request.getContactEmail())) {
			response.setText(SmcConstants.EMAIL_MANDATORY);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (!isValidEmailAdress(request.getContactEmail())) {
			response.setText(SmcConstants.EMAIL_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (isEmail(request.getContactEmail())) {
			response.setText(SmcConstants.EMAIL_ISVALID);
			response.setErrorMessage("CONFLICT");
			response.setStatus(409);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * Forget Password Email Validation
	 * @param userEmail
	 * @return
	 */
	public ResponseEntity<?> forgetPasswordValidation(String userEmail) {
		BaseResponse response = new BaseResponse();
		if (StringUtils.isEmpty(userEmail)) {
			response.setText(SmcConstants.EMAIL_MANDATORY);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} else {
			if (!isValidEmailAdress(userEmail)) {
				response.setText(SmcConstants.BILLTOEMAIL_ISVALID);
				response.setErrorMessage("NOT_ACCEPTABLE");
				response.setStatus(406);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param userEmail
	 * @return
	 */
	public ResponseEntity<?> resetTokenValidation(String userEmail, String token) {
		BaseResponse response = new BaseResponse();
		if (StringUtils.isEmpty(userEmail)) {
			response.setText(SmcConstants.EMAIL_MANDATORY);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} else {
			if (!isValidEmailAdress(userEmail)) {
				response.setText(SmcConstants.BILLTOEMAIL_ISVALID);
				response.setErrorMessage("NOT_ACCEPTABLE");
				response.setStatus(406);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		if (StringUtils.isEmpty(token)) {
			response.setText(SmcConstants.EMAIL_MANDATORY);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * @param currentDate
	 * @param expiryDate
	 * @return
	 */
	public int validateTokenExpiry(Date currentDate,Date expiryDate){
		return currentDate.compareTo(expiryDate);
	}
	
	/**
	 * @param enteredToken
	 * @param dbToken
	 * @return
	 */
	public boolean validateResetToken(String enteredToken,String dbToken){
		return enteredToken.equals(dbToken);
	}
	
	
	/**
	 * @param password
	 * @param confirmPassword
	 * @return
	 */
	public boolean validateConfirmPassword(String password,String confirmPassword){
		return password.equals(confirmPassword);
	}
	
    /**
     * @param password
     * @return
     */
	public ResponseEntity<?> validatePassword(String password) {
		BaseResponse response = new BaseResponse();
		if (StringUtils.isEmpty(password)) {
			response.setText(SmcConstants.PASSWORD_REQUIRED);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} else {
			if (password.length() >= 10) {
				boolean validation = new PasswordValidator().validate(password);
				if (!validation) {
					response.setText(SmcConstants.PASSWORD_PATTERN_MISMATCH);
					response.setErrorMessage("NOT_ACCEPTABLE");
					response.setStatus(406);
					return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				response.setText(SmcConstants.PASSWORD_REQUIRED);
				response.setErrorMessage("NOT_ACCEPTABLE");
				response.setStatus(406);
				return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
