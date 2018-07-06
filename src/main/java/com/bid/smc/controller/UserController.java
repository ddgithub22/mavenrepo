package com.bid.smc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.DuplicateExcaption;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.exception.WrongDateException;
import com.bid.smc.request.UserRequest;
import com.bid.smc.response.UserResponse;
import com.bid.smc.service.UserService;
import com.bid.smc.validation.RegistrationValidation;

/**
 * @author chandan.thakur
 *
 */
@RestController
@CrossOrigin(allowCredentials="false",maxAge=3600)
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private RegistrationValidation validation;
	
	
	/***
	 * @POST request
	 * @Save User
	 */
	@PostMapping(value = "/users/shipper")
	public ResponseEntity<?> saveUser(@RequestParam("Id")Integer id, @RequestBody UserRequest request) {
		UserResponse userResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.userValidation(request);
			if(entity.getStatusCode().equals(HttpStatus.OK)){
				userResponse = userService.saveUser(request,id);
			}
		}catch(DuplicateExcaption duplicat){
			response.setStatus(409);
			response.setErrorMessage("CONFLICT");
			response.setText(duplicat.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}catch(RecordNotFoundException record){
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(record.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}
	
	
	/***
	 * @GET
	 * @param shipperId
	 * @return Users for the Corresponding Shipper.
	 */
	@GetMapping(value = "/users/shipperId")
	public ResponseEntity<?> getAllUserByShipper(@RequestParam("Id")Integer id) {
		List<UserResponse> userResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			userResponse = userService.getUserByShipper(id);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}
	
	
    /***
     * @GET
     * @param id
     * @return users for the given User Id.
     */
    @GetMapping(value = "/users/{id}")
    public UserResponse getOne(@PathVariable(value = "id") Integer id){
        return userService.findById(id);
    }
}
