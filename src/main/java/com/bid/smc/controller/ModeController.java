package com.bid.smc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.model.bidsense.ModeEntity;
import com.bid.smc.service.ModeService;

@RestController
@CrossOrigin(allowCredentials = "false", maxAge = 3600)
public class ModeController {
	@Autowired
	private ModeService modeService;

	/***
	 * @GET request
	 * @return all Mode Names from mode table.
	 */
	@GetMapping(value = "/modes")
	public ResponseEntity<?> getAllModes() {
		List<ModeEntity> response = null;
		try {
			response = modeService.findAllModes();
		} catch (Exception e) {
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
