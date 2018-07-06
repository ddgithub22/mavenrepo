package com.bid.smc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.response.EquipmentResponse;
import com.bid.smc.service.EquipmentService;

@RestController
@CrossOrigin(allowCredentials="false",maxAge=3600)
public class EquipmentController {
	@Autowired
	private EquipmentService equipService;

	/***
	 * @Get request
	 * @return all Equipment details.
	 */
	@GetMapping(value = "/equipments")
	public ResponseEntity<?> getAllEquipment() {
		List<EquipmentResponse> response = null;
		try {
			response = equipService.findAllEquipment();
		} catch (Exception e) {
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

}
