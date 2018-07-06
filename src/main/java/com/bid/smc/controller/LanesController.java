package com.bid.smc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.DuplicateExcaption;
import com.bid.smc.exception.FormatException;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.exception.WrongDateException;
import com.bid.smc.request.LaneRequest;
import com.bid.smc.response.CarrierHistoryLanePaginationResponse;
import com.bid.smc.response.LanePaginationResponse;
import com.bid.smc.response.LaneResponse;
import com.bid.smc.service.LanesService;
import com.bid.smc.validation.BidValidation;

@RestController
@CrossOrigin(allowCredentials = "false", maxAge = 3600)
public class LanesController {

	@Autowired
	private BidValidation validation;
	@Autowired
	private LanesService service;

	/***
	 * 
	 * @POST request
	 * @Save Add Lanes Input form page.
	 */
	@PostMapping(value = "/lanes")
	public ResponseEntity<?> saveLane(@RequestBody LaneRequest request) {
		LaneResponse laneResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.laneValidation(request);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				laneResponse = service.saveLane(request);
			} else {
				return entity;
			}
		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(laneResponse, HttpStatus.CREATED);
	}


	/**
	 * 
	 * @param bidId
	 * @param pNumber
	 * @param pSize
	 * @param sortBy
	 * @return
	 */
	@GetMapping(value = "/bids/{Id}/lanes")
	public ResponseEntity<?> getAllLanesByBid(@PathVariable("Id") Integer bidId,
			@RequestParam(name = "pNumber", defaultValue = "1") int pNumber,
			@RequestParam(name = "pSize", defaultValue = "10") int pSize,
			@RequestParam(name = "sortBy", defaultValue = "lane") String sortBy,
			@RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy) {

		LanePaginationResponse laneResponse = null;
		BaseResponse response = new BaseResponse();
		PageRequest pageReq = null;
		if (orderBy.equals("ASC"))
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.ASC, sortBy);
		else
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.DESC, sortBy);

		try {
			laneResponse = service.getAllLanesByBid(bidId, pageReq);

		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(laneResponse, HttpStatus.CREATED);
	}

	/**
	 * @Delete all lanes for the corresponding given Bid Id.
	 * @param bidId
	 * @return
	 */
	@DeleteMapping(value = "/bids/{Id}/lanes/")
	public ResponseEntity<?> deleteLanesByBid(@PathVariable("Id") Integer bidId) {
		ResponseEntity<?> laneResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			laneResponse = service.deleteLanes(bidId);
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(laneResponse, HttpStatus.CREATED);
	}
    /***
     * @Update the Lanes for the corresponding bidId and  LaneId.
     * @param request
     * @param bidId
     * @param laneId
     * @return
     */
	@PutMapping(value = "/lanes/{laneId}")
	public ResponseEntity<?> updateBid(	@RequestBody LaneRequest request,
										@RequestParam("bidId") Integer bidId,
										@PathVariable("laneId") Integer laneId) {
		LaneResponse laneResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.laneValidation(request);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				laneResponse = service.updateLane(request, bidId, laneId);
			} else {
				return entity;
			}
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (DuplicateExcaption duplicate) {
			response.setStatus(409);
			response.setErrorMessage("CONFLICT");
			response.setText(SmcConstants.DUPLICATE_RECORD);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setText(wrongDateException.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(laneResponse, HttpStatus.CREATED);
	}
	/***
	 * @Delete Lanes for the given Lane Id.
	 * @param laneId
	 * @return
	 */
	
	@DeleteMapping(value="/lanes/{laneId}")
	public ResponseEntity<?> deleteLanesByLaneId(@PathVariable("laneId") Integer laneId){
		ResponseEntity<?> laneResponse = null;
		BaseResponse response=new BaseResponse();
		try {
			laneResponse = service.deleteLane(laneId);
		}catch(RecordNotFoundException notFound){
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(laneResponse, HttpStatus.CREATED);
	}
	
	@GetMapping(value="/lanes/{laneId}/bids")
	public ResponseEntity<?> getLanesByLaneId(@RequestParam("Id") Integer bidId,@PathVariable("laneId")Integer laneId){
		LaneResponse laneResponse = null;
		BaseResponse response = new BaseResponse();
		try {
				laneResponse = service.getLanesByLaneId(bidId, laneId);
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (DuplicateExcaption duplicate) {
			response.setStatus(409);
			response.setErrorMessage("CONFLICT");
			response.setText(SmcConstants.DUPLICATE_RECORD);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setText(wrongDateException.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(laneResponse, HttpStatus.OK);
	}
 
	/**
	 * @param carrierId
	 * @param bidId
	 * @param pNumber
	 * @param pSize
	 * @param sortBy
	 * @param orderBy
	 * @return carrier history Lanes tab.
	 */
	@GetMapping(value="/lanes/bids")
	public ResponseEntity<?> getCarrierLanesHistory(@RequestParam("carrierId")Integer carrierId,
													@RequestParam("bidId")Integer bidId,
	                                                @RequestParam(name = "pNumber", defaultValue = "1") int pNumber,
	                                                @RequestParam(name = "pSize", defaultValue = "10") int pSize,
	                                                @RequestParam(name = "sortBy", defaultValue = "lane") String sortBy,
	                                                @RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy){
		BaseResponse response=new BaseResponse();
		CarrierHistoryLanePaginationResponse history=null;
		PageRequest pageReq = null;
		if (orderBy.equals("ASC"))
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.ASC, sortBy);
		else
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.DESC, sortBy);
		
		try {
			history = service.getAllLanesByBid(carrierId, bidId,pageReq);

		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage("NOT_ACCEPTABLE");
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(history, HttpStatus.OK);
}
	
}
