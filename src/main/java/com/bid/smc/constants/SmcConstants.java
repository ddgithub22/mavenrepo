package com.bid.smc.constants;

import java.util.regex.Pattern;

public interface SmcConstants {
	
	public int CARRIER = 2;
	public int SHIPPER = 3;
	public int LOGISTICS = 4;
	public int MOTOR_CARRIER_TARIFF_BUREAU = 6;
	public int SOFTWARE_DEVELOPMENT = 9;
	public int CONSULTANT_SERVICES_PROVIDER = 19;
	public long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public String SIGNING_KEY = "devglan123r";
    public String TOKEN_PREFIX = "Bearer ";
    public String HEADER_STRING = "Authorization";
    String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Integer RANDOM_LENGTH = 20;
    public String SUBJECT = "Bid Invitation";
    public String UNITED_STATES = "United States";
    public String CANADA = "Canada";
	
	public String USER_NAME_REQUIRE="User Name is Required.";
	public String PASSWORD_REQUIRED = "Password is Required";
	public String EIN_REQUIRED = "EIN number is not Valid";
	public String CITY_REQUIRED = "City is Required";
	public String COMPANY_REQUIRED = "Company Name is Required";
	public String ADDRESS_LINE1_REQUIRED = "Address Line1 is Required";
	public String COUNTRY_REQUIRED = "Country is Required";
	public String STATE_REQUIRED = "State is Required";
	public String POSTAL_REQUIRED = "Postal Code is Required";
	public String POSTAL_ISVALID = "Postal Code is not Valid";
	public String FIRSTNAME_REQUIRED = "First Name is Required";
	public String LASTNAME_REQUIRED = "Last Name is Required";
	public String NAME_REQUIRED = "Name is Required";
	public String PHONE_REQUIRED = "Enter a valid Phone Number";
	public String EMAIL_REQUIRED = "Enter a valid Email Address";
	public String BILLTOEMAIL_ISVALID = "Enter valid Email Address";
	public String CARRIER_COMPANY_REQUIRED = "Carrier Company Required";
	public String PASSWORD_NOT_MATCH = "Confirm Password does not Match";
	public String EMAIL_NOT_MATCH = "Confirm Email does not Match";
	public String ZIP_CODE_VALIDATION = "Enter a valid zip code";
	public String PASSWORD_FORMAT = "Minimum Password length of 10 and Password must include 1 uppercase, 1 lowercase, 1 numeric and 1 special character !\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~";

	public String MC_REQUIRED = "MC Number is Required";
	public String MC_ISVALID = "MC Number is not Valid";
	public String DOT_REQUIRED = "DOT Number is Required";
	public String DOT_ISVALID = "DOT Number is not Valid";
	public String SHIPPER_NOT_FOUND ="Shipper Id cannot be found";
	public String CUSTOMER_NOT_FOUND ="Customer Id cannot be found";

	public String BID_START_DATE_REQUIRED = "BID Start Date is Required";
	public String BID_END_DATE_REQUIRED = "BID End Date is Required";
	public String BID_RESPONSE_DATE = "Response end date must be after Response start date";
	public String BID_TERM_DATE = "Response end date  must be before Term start Date ";
	public String RESPONSE_TERM_DATE = "Term start must be after Response End date";
	public String BID_DATE_FORMAT = "Date Format is not correct";
	public String BID_ISVALID = "Please Enter Correct Rate";
	public String RECORD_NOT_FOUND="Record Not Found";
	public String NOT_VALID_FILE_FORMAT="Uploaded file is not in CSV format";

	public String EIN_ISVALID = "Ein Number is already present.";
	public String EMAIL_ISVALID = "Email Address is already present.";
	public String MC_NUMBER_ISVALID = "MC Number is already present.";
	public String DOT_NUMBER_ISVALID = "DOT Number is already present.";
	public String PHONENO_REQUIRED = "Phone no is Required";
	public String PHONE_ISVALID = "Enter a valid Phone Number";
	public String BILLTOEMAIL_REQUIRED = "Bill To Email is Required";
	public String CUSTOMER_NAME_PRESENT = "Customer Name already present.";
	public String BID_NAME_PRESENT = "BID Name already present.";
	public String DUPLICATE_RECORD = "BID Name already present.";
	
	public String ORIGIN_CITY_REQUIRED = "Origin City is Required";
	public String ORIGIN_ZIP_REQUIRED = "Origin Postal is Required";
	public String ORIGIN_STATE_REQUIRED = "Origin State is Required";
	
	public String LANES_REQUIRED = "Lane is Required";
	public String LANE_PRESENT= "Lane is already present";
	public String VOLUME_REQUIRED = "Volume is Required";
	public String VOLUME_NOTVALID = "Volume accepts only numbers";
	public String VOLUME_LENGTH="Volume length exceeds the limit";
	public String ORIGIN_REQUIRED = "Origin is Required";
	public String DESTINATION_REQUIRED = "Destination is Required";
	public String COMMODITY_REQUIRED = "Commodity is Required";
	public String INCUMBENT_NOTVALID = "Incumbent is Invalid";
	public String BENCHMARK_NOTVALID = "BenchMark is Invalid";
	public String WEIGHT_REQUIRED = "Weight is Required";
	public String WEIGHT_NOTVALID = "Weight accepts only numbers";
	public String WEIGHT_LENGTH="Weight exceeds the limit";
	public String HAUL_REQUIRED="Haul Length is Required";
	public String HAUL_NOTVALID="Haul accepts only numbers";
	public String HAUL_LENGTH="Haul exceeds the limits";
	public String MODE_REQUIRED="Mode is Required";
	public String EQUIPMENT_REQUIRED="Equipment is required";
	
	public String LOGIN_ERROR_MESSAGE = "Invalid User Name Or Password";
	public String USE_NOT_FOUND = "User Not Registered";
	public String BID_OWNER = "Bid Owner Id not found";
	public String BAD_FORMAT = "File is not in CSV format.";

	public String EMAIL_MANDATORY ="Email Address is Required";

	public String EQUIPMENTTYPE_NOT_FOUND="Equipment Type Not Found";
	public String EQUIPMENTLENGTH_NOT_FOUND="Equipment Length Not Found";
	public String MODE_NOT_FOUND="Mode Id Not Found";
	public String EMPTY_CSV="No data in csv file";
	
	public  String USERNAME_NOT_EXIST_EMAIL="A user with given email does not exist in the system";
	public  String EMAIL_SENT_SUCCESS = "Email successfully sent with reset password link";
	public  String EMAIL_NOT_SEND = "Unexpected Error Occured while sending Email";
	public String EMAIL_EMPTY_TOKEN = "Token is empty";
	public String EMAIL_EXPIRED_TOKEN = "Token Expired";
	public String EMAIL_INVALID_TOKEN = "Invalid Token";
	
	public long ERRORCODE=403L;
	public long SUCCESSCODE=400L;
	
    public String FORGET_PASSWORD_EMAIL_FROM="testemail1@dreamorbit.com";
    public String FORGET_PASSWORD_SUBJECT = "Forget Password";
    public String PASSWORD_LENGTH_MISMATCH = "Password lenth should be minimum 10";
    public String PASSWORD_PATTERN_MISMATCH = "Password should match the pattern";
    public String PASSWORD_CONFIRM_MISMATCH = "Confirm Password not matches with password";
    public String PASSWORD_UPDATE_SUCCESS = "Password updated successfully";
    public  String UNEXPECTED_ERROR = "Unexpected Error";
    
    //public String PASSWORD_REQUIRED = "Password is required";

    public String VOLUME_SIZE="Volume most be equal or less";
	
	public  final String CONFLICT = "CONFLICT";
	public String NOT_ACCEPTABLE = "NOT_ACCEPTABLE";
	
	public Pattern PASSWORD_PATTERN = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%*!]).{10,16})");
	public String CAN_ZIP = "^[a-zA-Z0-9]*$";
	public String US_ZIP = "^\\d{5}$";
	public String EIN_NUMBER_PATTERN = "^\\d{2}-\\d{7}$";
	public String PHONE_NUMBER_PATTERN = "^\\(?(\\d{3})\\)?(\\d{3})[- ]?(\\d{4})$";
	public String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[_A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z0-9]{2,})$";
	public String DATE_FORMAT = "MM/dd/yyyy";
	public String NOT_FOUND="NOT_FOUND";
}
