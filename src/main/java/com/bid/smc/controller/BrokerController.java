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
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.FormatException;
import com.bid.smc.model.bidsense.BrokerEntity;
import com.bid.smc.request.BrokerRequest;
import com.bid.smc.response.BrokerResponse;
import com.bid.smc.service.BrokerService;
import com.bid.smc.validation.RegistrationValidation;

@RestController
@CrossOrigin(allowCredentials="false",maxAge=3600)
public class BrokerController {
  @Autowired
  private BrokerService brokerService;
  @Autowired
  private RegistrationValidation validation;
  
  /**
   * 
   * @POST request
   * @save Broker Registration.Saving the Broker Registration form.
   */
  @PostMapping(value = "/brokers")
	public ResponseEntity<?> saveBroker(@RequestBody BrokerRequest request) {
		BrokerResponse brokerResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.brokerValidation(request);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				brokerResponse = brokerService.saveBroker(request);
			}else{
				return entity;
			}
		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response,HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(brokerResponse, HttpStatus.CREATED);
	}
  /**
   * @GET request
   * @return all saved Broker data.
   */
  @GetMapping(value = "/brokers")
	public ResponseEntity<?> getBroker() {
		List<BrokerEntity> brokerEntity = null;
		BaseResponse response = new BaseResponse();
		try {
			brokerEntity = brokerService.getAllBroker();

		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(brokerEntity, HttpStatus.CREATED);
	}
  /***
   * 
   * @GET request
   * @return Broker details for the given Broker Id.
   */

	@GetMapping(value = "/brokers/{id}")
	public ResponseEntity<?> getBrokerById(@PathVariable("id") Integer id) {
		BaseResponse response = new BaseResponse();
		BrokerEntity entity = null;
		try {
			entity = brokerService.getBrokerById(id);
			if (entity == null) {
				response.setErrorMessage("NOT_FOUND");
				response.setStatus(404);
				response.setText(SmcConstants.RECORD_NOT_FOUND);
				return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
			}
		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(entity, HttpStatus.FOUND);

	}

  
}
