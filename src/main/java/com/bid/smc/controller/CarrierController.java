package com.bid.smc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.FormatException;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.exception.WrongDateException;
import com.bid.smc.model.bidsense.CarrierEntity;
import com.bid.smc.request.CarrierRequest;
import com.bid.smc.request.MessageRequest;
import com.bid.smc.request.StatusRequest;
import com.bid.smc.response.AddCarrierResponse;
import com.bid.smc.response.CarrierHistPaginationResponse;
import com.bid.smc.response.CarrierResponse;
import com.bid.smc.response.InvitedCarrierResponse;
import com.bid.smc.response.MessageResponse;
import com.bid.smc.service.BidService;
import com.bid.smc.service.CarrierService;
import com.bid.smc.validation.RegistrationValidation;

@RestController
@CrossOrigin(allowCredentials = "false", maxAge = 3600)
public class CarrierController {
	
	@Autowired
	private CarrierService carrierService;
	
	@Autowired
	private RegistrationValidation validation;
	
	@Autowired
	private BidService bidService;

	/***
	 * 
	 * @POST request
	 * @Save the Carrier Company Registration details.
	 */
	@PostMapping(value = "/carriers")
	public ResponseEntity<?> saveCarrier(@RequestBody CarrierRequest request) {
		CarrierResponse carrierResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.carrierValidation(request);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				carrierResponse = carrierService.saveCarrier(request);
			} else {
				return entity;
			}
		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(carrierResponse, HttpStatus.CREATED);
	}

	/***
	 * @GET request
	 * @return all Carrier Registration input form data.
	 */
	@GetMapping(value = "/carriers")
	public ResponseEntity<?> getCarrier() {
		BaseResponse response = new BaseResponse();
		List<CarrierResponse> carrierResponse = new ArrayList<>();
		try {
			carrierResponse = carrierService.getAllCarrier();

		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(carrierResponse, HttpStatus.CREATED);
	}

	/***
	 * 
	 * @GET request
	 * @return Carrier Registration details for the given Carrier Id.
	 */
	@GetMapping(value = "/carriers/{id}")
	public ResponseEntity<?> getCarrierById(@PathVariable("id") Integer id) {
		BaseResponse response = new BaseResponse();
		CarrierEntity entity = null;
		try {
			entity = carrierService.getCarrierById(id);
		} catch (RecordNotFoundException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(409);
			response.setText(format.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(entity, HttpStatus.FOUND);

	}

	/***
	 * 
	 * @param shipperId
	 * @RequestParam carrierName
	 * @return the Carrier details by Carrier Name for the given Shipper Id.
	 */

	@GetMapping(value = "/carriers/carrierName")
	public ResponseEntity<?> getCarrier(@RequestParam("Name") String carrierName,
			@RequestParam("Id") Integer shipperId) {
		List<CarrierResponse> carrierResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			carrierResponse = carrierService.findByName(shipperId, carrierName);
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(carrierResponse, HttpStatus.CREATED);
	}
 
	
	/**
	 * @param searchC
	 * @return
	 */
/*	@GetMapping(value="/carriers/search")
	public ResponseEntity<?> searchCarrier(@RequestParam("searchC") String searchC){
		 List<AddCarrierResponse> carrierResponse=null;
		 BaseResponse response=new BaseResponse();
		 try {
				carrierResponse = carrierService.searchCarrier(searchC);
			} catch (RecordNotFoundException notFound) {
				response.setStatus(404);
				response.setErrorMessage("NOT_FOUND");
				response.setText(notFound.getMessage());
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
			}
			return new ResponseEntity<>(carrierResponse, HttpStatus.CREATED);
	}*/

	/***
	 * @POST Questions.
	 * @param request
	 * @return
	 */
	@PostMapping(value="/carriers/messages")
	public ResponseEntity<?> saveMessage(@RequestBody MessageRequest request) {
		MessageResponse questionResponse = carrierService.saveMessage(request);
		return new ResponseEntity<>(questionResponse, HttpStatus.CREATED);
	}
	
	
	/***
	 * @Update the answer for the question.
	 * @param request
	 * @param messageId
	 * @return
	 */
	@PutMapping(value="/carriers/messages")
	public ResponseEntity<?> updateMessage(@RequestBody MessageRequest request,@RequestParam("messageId")Integer messageId) {
		MessageResponse questionResponse = carrierService.updateMessage(request, messageId);
		return new ResponseEntity<>(questionResponse, HttpStatus.OK);
	}
	
	/***
	 * @Getting all messages for the respective bidId.
	 * @param bidId
	 * @return
	 */
	@GetMapping(value="/messages/{bidId}")
	public ResponseEntity<?> getAllMessages(@PathVariable("bidId")Integer bidId){
		BaseResponse response = new BaseResponse();
		List<MessageResponse> messageResponse = new ArrayList<>();
		try {
			messageResponse = carrierService.getAllMessages(bidId);

		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(messageResponse, HttpStatus.CREATED);
	}
    
	
	@GetMapping(value = "/carriers/{carrierId}/bids")
	public ResponseEntity<?> getBidByCarrierId(@PathVariable("carrierId") Integer id) {
		BaseResponse response = new BaseResponse();
		List<InvitedCarrierResponse> bidResponse = null;
		try {
			bidResponse = carrierService.getBidByCarrierId(id);
		} catch (RecordNotFoundException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(409);
			response.setText(format.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(bidResponse, HttpStatus.OK);
	}
	
	@PutMapping(value = "/carriers/responses/bidId")
	public ResponseEntity<?> response(@RequestParam("id") Integer bidId,@RequestBody StatusRequest request) {
		BaseResponse response = new BaseResponse();
		try {
			if (bidId != null) {
				carrierService.bidResponse(bidId,request);
				response.setStatus(200);
				response.setErrorMessage("SUCCESS");
				response.setText("Success");
			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	@GetMapping(value="/carriers/{carrierId}/history")
	public ResponseEntity<?> getCarrierHistory(@PathVariable("carrierId") Integer carrierId,
			@RequestParam(name = "pNumber", defaultValue = "1") int pNumber,
			@RequestParam(name = "pSize", defaultValue = "10") int pSize,
			@RequestParam(name = "sortBy", defaultValue = "bidName") String sortBy,
			@RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy){
		BaseResponse response=new BaseResponse();
		CarrierHistPaginationResponse history=null;
		PageRequest pageReq = null;
		if (orderBy.equals("ASC"))
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.ASC, sortBy);
		else
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.DESC, sortBy);
		
		try {
			history = bidService.getAllBidsByCarrier(carrierId, pageReq);

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
		return new ResponseEntity<>(history, HttpStatus.OK);
		
	}
}
