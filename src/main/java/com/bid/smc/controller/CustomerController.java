package com.bid.smc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.request.CustomerRequest;
import com.bid.smc.response.CustomerResponse;
import com.bid.smc.service.CustomerService;
import com.bid.smc.validation.RegistrationValidation;

@RestController
@CrossOrigin(allowCredentials="false",maxAge=3600)
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	@Autowired
	  private RegistrationValidation validation;
	
	/***
	 * @POST
	 * @Save Customers
	 */
	@PostMapping(value = "/customers")
	public ResponseEntity<?> saveCustomer(@RequestBody CustomerRequest request) {
		CustomerResponse customerResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.customerValidation(request);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				customerResponse = customerService.saveCustomer(request);
			}else{
				return entity;
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
	}
	
	/***
	 * @GET
	 * @return all customers.
	 */
	@GetMapping(value = "/customers")
	public ResponseEntity<?> getAllCustomer() {
		List<CustomerResponse> customerResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			customerResponse = customerService.getAllCustomer();
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
	}
	
}
