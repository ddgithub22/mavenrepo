package com.bid.smc.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.repo.adminmanager.CompanyRepository;
import com.bid.smc.repo.adminmanager.UserRepository;
import com.bid.smc.repo.bidsense.BidRepository;
import com.bid.smc.repo.bidsense.BrokerRepository;
import com.bid.smc.repo.bidsense.CarrierRepository;
import com.bid.smc.repo.bidsense.CustomerRepository;
import com.bid.smc.repo.bidsense.ShipperRepository;
import com.bid.smc.request.BidRequest;
import com.bid.smc.request.BrokerRequest;
import com.bid.smc.request.CarrierRequest;
import com.bid.smc.request.CustomerRequest;
import com.bid.smc.request.ShipperRequest;
import com.bid.smc.request.UserRequest;

/**
 * @author chandan.thakur
 *
 */
@Component
public class RegistrationValidation {

	@Autowired
	ShipperRepository shipperRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	BrokerRepository brokerRepo;
	
	@Autowired
	CarrierRepository carrierRepo;
	
	@Autowired
	CustomerRepository customerRepo;
	
	@Autowired
	BidRepository rfpRepo;
	
	@Autowired
	CompanyRepository companyRepo;
	
	/**
	 * @param request
	 * @return
	 */
	public ResponseEntity<BaseResponse> shipperValidation(ShipperRequest request) {

		BaseResponse response = new BaseResponse();
		if (!request.getUser().getPassword().equals(request.getUser().getConfirmPassword())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_NOT_MATCH);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!request.getUser().getEmail().equals(request.getUser().getConfirmEmail())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_NOT_MATCH);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!isValidEin(request.getEinNumber())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EIN_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
  
		if (request.getCompanyName() == null || request.getCompanyName().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.COMPANY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getAddress1() == null
				|| request.getCompanyAddress().getAddress1().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.ADDRESS_LINE1_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCity() == null || request.getCompanyAddress().getCity().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.CITY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCountryCode() == null || request.getCompanyAddress().getCountryCode().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.COUNTRY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getState() == null || request.getCompanyAddress().getState().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EIN_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getPostalCode() == null || request.getCompanyAddress().getPostalCode().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getUser().getFirstName() == null || request.getUser().getFirstName().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.FIRSTNAME_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getUser().getLastName() == null || request.getUser().getLastName().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.LASTNAME_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!validatePhoneNumber(request.getUser().getPhoneNumber())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PHONE_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		if (!isValidEmailAdress(request.getUser().getEmail())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(isEmail(request.getUser().getEmail())){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getUser().getPassword() == null || request.getUser().getPassword().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!validate(request.getUser().getPassword())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_FORMAT);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCountryCode().equals(SmcConstants.UNITED_STATES)) {
			if (request.getCompanyAddress().getPostalCode().equals("")
					|| request.getCompanyAddress().getPostalCode() == null) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_REQUIRED);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			if (!isValidUSPostal(request.getCompanyAddress().getPostalCode())) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_ISVALID);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		if (request.getCompanyAddress().getCountryCode().equals(SmcConstants.CANADA)) {
			if (request.getCompanyAddress().getPostalCode().equals("")
					|| request.getCompanyAddress().getPostalCode() == null) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_REQUIRED);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			if (!isValidCANPostal(request.getCompanyAddress().getPostalCode()) || request.getCompanyAddress().getPostalCode().length()!=6) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_ISVALID);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	} // call by creating ValidationObject

	/**
	 * @param email
	 * @return
	 */
	public boolean isValidEmailAdress(String email) {
		String ePattern = SmcConstants.EMAIL_PATTERN;
		java.util.regex.Matcher m = patterMatcher(email, ePattern);
		return m.matches();
	}

	/**
	 * @param phone
	 * @return
	 */
	public boolean validatePhoneNumber(String phone) {
		String ePattern = SmcConstants.PHONE_NUMBER_PATTERN;
		java.util.regex.Matcher r = patterMatcher(phone, ePattern);
		return r.matches();
	}

	/**
	 * @param phone
	 * @param ePattern
	 * @return
	 */
	private java.util.regex.Matcher patterMatcher(String phone, String ePattern) {
		java.util.regex.Pattern o = java.util.regex.Pattern.compile(ePattern);
		return o.matcher(phone);
	}

	/**
	 * @param ein
	 * @return
	 */
	public boolean isValidEin(String ein) {
		String epattern = SmcConstants.EIN_NUMBER_PATTERN;
		java.util.regex.Matcher match = patterMatcher(ein, epattern);
		return match.matches();
	}

	/**
	 * @param zipCode
	 * @return
	 */
	public boolean isValidUSPostal(String zipCode) {
		String ePattern = SmcConstants.US_ZIP;
		java.util.regex.Matcher match = patterMatcher(zipCode, ePattern);
		return match.matches();
	}

	/**
	 * @param zipCode
	 * @return
	 */
	public boolean isValidCANPostal(String zipCode) {
		String ePattern = SmcConstants.CAN_ZIP;
		java.util.regex.Matcher match = patterMatcher(zipCode, ePattern);
		return match.matches();
	}

	/**
	 * @param request
	 * @return
	 */
	public ResponseEntity<BaseResponse> carrierValidation(CarrierRequest request) {

		BaseResponse response = null;
		if (!request.getUser().getPassword().equals(request.getUser().getConfirmPassword())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_NOT_MATCH);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!request.getUser().getEmail().equals(request.getUser().getConfirmEmail())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_NOT_MATCH);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(isEmail(request.getUser().getEmail())){
			response = new BaseResponse(409,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(request.getCompanyName()==null || request.getCompanyName().equals("")){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.COMPANY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getMcNumber()== "" || request.getMcNumber() == null) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.MC_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(!isValidMC(request.getMcNumber())){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.MC_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		if(isMcNo(request.getMcNumber())){
			response = new BaseResponse(409,SmcConstants.NOT_ACCEPTABLE,SmcConstants.MC_NUMBER_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		if (request.getDotNumber() == "" || request.getDotNumber() == null) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.DOT_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(!isValidDOT(request.getDotNumber())){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.DOT_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(isDotNo(request.getDotNumber())){
			response = new BaseResponse(409,SmcConstants.CONFLICT,SmcConstants.DOT_NUMBER_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		if (request.getCompanyAddress().getAddress1() == null
				|| request.getCompanyAddress().getAddress1().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.ADDRESS_LINE1_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCity() == null || request.getCompanyAddress().getCity().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.CITY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCountryCode() == null || request.getCompanyAddress().getCountryCode().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.COUNTRY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getState() == null || request.getCompanyAddress().getState().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.STATE_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCountryCode().equals(SmcConstants.UNITED_STATES)) {
			if (request.getCompanyAddress().getPostalCode().equals("")
					|| request.getCompanyAddress().getPostalCode() == null) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_REQUIRED);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			if (!isValidUSPostal(request.getCompanyAddress().getPostalCode())) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_ISVALID);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		if (request.getCompanyAddress().getCountryCode().equals(SmcConstants.CANADA)) {
			if (request.getCompanyAddress().getPostalCode().equals("")
					|| request.getCompanyAddress().getPostalCode() == null) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_REQUIRED);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			if (!isValidCANPostal(request.getCompanyAddress().getPostalCode()) || request.getCompanyAddress().getPostalCode().length()!=6) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_ISVALID);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		if (request.getUser().getFirstName() == null || request.getUser().getFirstName().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.FIRSTNAME_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getUser().getLastName() == null || request.getUser().getLastName().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.LASTNAME_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!validatePhoneNumber(request.getUser().getPhoneNumber())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PHONE_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}

		if (!isValidEmailAdress(request.getUser().getEmail())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getUser().getPassword() == null || request.getUser().getPassword().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!validate(request.getUser().getPassword())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_FORMAT);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @param newPassword
	 * @return
	 */
	public boolean validate(String newPassword) {
		boolean matcher = false;
		
		Matcher mtch = SmcConstants.PASSWORD_PATTERN.matcher(newPassword);
		if (mtch.matches()) {
			matcher = true ;
		}
		
		return matcher;
	}

	/**
	 * @param request
	 * @return
	 */
	public ResponseEntity<BaseResponse> brokerValidation(BrokerRequest request) {

		BaseResponse response = new BaseResponse();
		if (!request.getUser().getPassword().equals(request.getUser().getConfirmPassword())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_NOT_MATCH);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!request.getUser().getEmail().equals(request.getUser().getConfirmEmail())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_NOT_MATCH);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(isEmail(request.getUser().getEmail())){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getMcNumber() == "" || request.getMcNumber() == null) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.MC_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(!isValidMC(request.getMcNumber())){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.MC_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(isMcNo(request.getMcNumber())){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.MC_NUMBER_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyName() == null || request.getCompanyName().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.COMPANY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getAddress1() == null
				|| request.getCompanyAddress().getAddress1().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.ADDRESS_LINE1_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCity() == null || request.getCompanyAddress().getCity().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.CITY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCountryCode() == null || request.getCompanyAddress().getCountryCode().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.COUNTRY_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getCompanyAddress().getCountryCode().equals(SmcConstants.UNITED_STATES)) {
			if (request.getCompanyAddress().getPostalCode().equals("")
					|| request.getCompanyAddress().getPostalCode() == null) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_REQUIRED);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			if (!isValidUSPostal(request.getCompanyAddress().getPostalCode())) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_ISVALID);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		if (request.getCompanyAddress().getCountryCode().equals(SmcConstants.CANADA)) {
			if (request.getCompanyAddress().getPostalCode().equals("")
					|| request.getCompanyAddress().getPostalCode() == null) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_REQUIRED);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			if (!isValidCANPostal(request.getCompanyAddress().getPostalCode()) || request.getCompanyAddress().getPostalCode().length()!=6) {
				response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.POSTAL_ISVALID);
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		if (request.getCompanyAddress().getState() == null || request.getCompanyAddress().getState().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EIN_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getUser().getFirstName() == null || request.getUser().getFirstName().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.FIRSTNAME_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getUser().getLastName() == null || request.getUser().getLastName().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.LASTNAME_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(request.getUser().getPhoneNumber()== null || request.getUser().getPhoneNumber().equals("")){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PHONENO_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!validatePhoneNumber(request.getUser().getPhoneNumber())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PHONE_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!isValidEmailAdress(request.getUser().getEmail())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.EMAIL_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(request.getBillToEmail()==null || request.getBillToEmail().equals("")){
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.BILLTOEMAIL_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!isValidEmailAdress(request.getBillToEmail())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.BILLTOEMAIL_ISVALID);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getUser().getPassword() == null || request.getUser().getPassword().equals("")) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_REQUIRED);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (!validate(request.getUser().getPassword())) {
			response = new BaseResponse(406,SmcConstants.NOT_ACCEPTABLE,SmcConstants.PASSWORD_FORMAT);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}  // call by creating ValidationObject
	
	
	/**
	 * @param request
	 * @return
	 */
	public ResponseEntity<BaseResponse> rfpValidation(BidRequest request){
		BaseResponse response = new BaseResponse();
		if(request.getBidName() == null || request.getBidName()==""){
			response.setText(SmcConstants.NAME_REQUIRED);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(request.getBidResponseStartDate() == null || request.getBidResponseStartDate()==""){
			response.setText(SmcConstants.BID_START_DATE_REQUIRED);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(request.getBidResponseEndDate() == null || request.getBidResponseEndDate()==""){
			response.setText(SmcConstants.BID_END_DATE_REQUIRED);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		Integer rate = request.getRate();
		
		if (rate != null && (rate != 1 && rate != 2 && rate != 3 && rate != 4)) {
			response.setText(SmcConstants.BID_ISVALID);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		if(validateBidOwner(request.getBidOwner(),request.getShipperId())){
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(SmcConstants.BID_OWNER);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	/**
	 * @param request
	 * @return
	 */
	public ResponseEntity<BaseResponse> customerValidation(CustomerRequest request){
		BaseResponse response = new BaseResponse();
		
		if(request.getName() == null || request.getName()== ""){
			response.setText(SmcConstants.NAME_REQUIRED);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if(isCustomerNamePresent(request.getName())){
			response.setText(SmcConstants.CUSTOMER_NAME_PRESENT);
			response.setErrorMessage(SmcConstants.CONFLICT);
			response.setStatus(409);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (request.getEmail()!= null && !isValidEmailAdress(request.getEmail())) {
			response.setText(SmcConstants.EMAIL_REQUIRED);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if(isEmail(request.getName())){
			response.setText(SmcConstants.CUSTOMER_NAME_PRESENT);
			response.setErrorMessage(SmcConstants.CONFLICT);
			response.setStatus(409);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * @param format
	 * @param value
	 * @return
	 */
	public boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
             
        }
        return date != null;
    }
	
	
	/**
	 * @param emailId
	 * @return
	 */
	public boolean isEmail(String emailId){
		boolean value=false;
		if(userRepo.findByEmail(emailId) != null){
			value=true;
		}
		return value;
	}

	/**
	 * @param mcNumber
	 * @return
	 */
	public boolean isMcNo(String mcNumber) {
		boolean value = false;
		if (brokerRepo.findMcNumber(mcNumber) != null || carrierRepo.findMcNumber(mcNumber)!=null) {
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
	 * @param customerName
	 * @return
	 */
	public boolean isCustomerNamePresent(String customerName) {
		boolean value = false;
		if (customerRepo.findCustomerName(customerName) != null) {
			value = true;
		}
		return value;
	}
	
	/**
	 * @param rfpName
	 * @param shipperId
	 * @return
	 */
	public boolean isBidNamePresent(String rfpName, Integer shipperId) {
		boolean value = false;
		if (rfpRepo.findBidName(rfpName,shipperId) != null) {
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
		java.util.regex.Matcher match = patterMatcher(mcNumber, ePattern);
		return match.matches();
	}
	
	/**
	 * @param dotNumber
	 * @return
	 */
	public boolean isValidDOT(String dotNumber) {
		String ePattern = "^\\d{7}$";
		java.util.regex.Matcher match = patterMatcher(dotNumber, ePattern);
		return match.matches();
	}

	/**
	 * @param bidOwnerId
	 * @param companyId
	 * @return
	 */
	public boolean validateBidOwner(Integer bidOwnerId,Integer companyId){
		boolean value = false;
		if (userRepo.getBidOwner(bidOwnerId,companyId) == null){
			value=true;
		}
		return value;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public ResponseEntity<BaseResponse> userValidation(UserRequest request){
		BaseResponse response = new BaseResponse();
		if (!isValidEmailAdress(request.getEmail())) {
			response.setText(SmcConstants.EMAIL_REQUIRED);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
