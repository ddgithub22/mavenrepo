package com.bid.smc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.exception.FormatException;
import com.bid.smc.response.EquipmentLengthResponse;
import com.bid.smc.service.EquipmentLengthService;

@RestController
@CrossOrigin(allowCredentials="false",maxAge=3600)
public class EquipmentLengthController {
	@Autowired
	private EquipmentLengthService service;
	/***
	 * @GET request
	 * @return all Equipment length values from add Lanes Input form page.
	 */
	
    @GetMapping(value = "/equipmentlength")
	public ResponseEntity<?> getAllEquipmentLength() {
		List<EquipmentLengthResponse> response = null;
		try {
			response = service.findAllEquipmentLength();
		} catch (Exception e) {
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
    
    /***
     * 
     * @GET request
     * @return Equipment Length for the corresponding Id. 
     */
	@GetMapping(value="/equipments/{id}")
	public ResponseEntity<?> getEquipmentById(@PathVariable("id") Integer id){
		BaseResponse response=new BaseResponse();
		EquipmentLengthResponse entity=null;
		try{
			entity=service.getEquipmentById(id);
		}catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		
		return new ResponseEntity<>(entity, HttpStatus.OK);
		
	}
}
