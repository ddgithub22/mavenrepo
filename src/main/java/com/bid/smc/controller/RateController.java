package com.bid.smc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.model.bidsense.RateEntity;
import com.bid.smc.service.RateService;

@RestController
@CrossOrigin(allowCredentials="false",maxAge=3600)
public class RateController {
	
	@Autowired
	private RateService service;
    /***
     * @GET request
     * @return all Rates from rate table.
     */
	@GetMapping(value = "/rates")
	public ResponseEntity<?> getAllRates() {
		List<RateEntity> response = null;
		try {
			response = service.findAllRate();
		} catch (Exception e) {
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
