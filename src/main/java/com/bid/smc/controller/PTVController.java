package com.bid.smc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.request.Waypoints;
import com.bid.smc.response.PTVAddressResponse;
import com.bid.smc.service.PTVService;

@RestController
@CrossOrigin(allowCredentials = "false", maxAge = 3600)
@RequestMapping("/ptv")
public class PTVController {

	@Autowired
	private PTVService ptvService;
	
	/**
	 * @param searchText
	 * @return
	 */
	@GetMapping(value = "/origin")
	public ResponseEntity<?> getOriginDetails(@RequestParam String searchText) {
		PTVAddressResponse  address = ptvService.getOriginDetails(searchText);
		
		if(address == null){
			return new ResponseEntity<>(address, HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(address, HttpStatus.OK);
	}
	
	/**
	 * @param requests
	 * @return
	 */
	@PostMapping(value = "/distance")
	public ResponseEntity<?> calculateDistance(@RequestBody Waypoints[] waypoints) {
		Object obj = ptvService.calculateDistance(waypoints);
		if(obj == null){
			return new ResponseEntity<>(obj, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}
	
}
