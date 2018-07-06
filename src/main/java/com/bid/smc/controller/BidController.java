package com.bid.smc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.DuplicateExcaption;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.exception.WrongDateException;
import com.bid.smc.exception.WrongFileFormat;
import com.bid.smc.request.BidRequest;
import com.bid.smc.request.InviteCarrierRequest;
import com.bid.smc.request.MailRequest;
import com.bid.smc.request.ResponseBidRequest;
import com.bid.smc.response.BidDetailsResponse;
import com.bid.smc.response.BidPaginationResponse;
import com.bid.smc.response.BidResponse;
import com.bid.smc.response.CarrierHistPaginationResponse;
import com.bid.smc.response.CarrierLanesResponse;
import com.bid.smc.response.CsvUploadErrorResponse;
import com.bid.smc.response.InviteCarrierResponse;
import com.bid.smc.response.ResponseBidResponse;
import com.bid.smc.response.ReviewBidResponse;
import com.bid.smc.response.ShipperBidHistoryPagResponse;
import com.bid.smc.response.ShipperTrackResponse;
import com.bid.smc.security.JwtTokenUtil;
import com.bid.smc.service.BidService;
import com.bid.smc.service.LanesService;
import com.bid.smc.service.SmcService;
import com.bid.smc.validation.RegistrationValidation;

@RestController
@CrossOrigin(allowCredentials = "false", maxAge = 3600)
@RequestMapping("/bids")
public class BidController {
	
	final static String HEADER_STRING = "Authorization";
	
	@Autowired
	private RegistrationValidation validation;
	
	@Autowired
	private BidService bidService;
	
	@Autowired
	private LanesService service;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private SmcService smcService;
	
	/***
	 * @POST
	 * @Save all Input form data in Define Bid Rules Page.
	 */

	@PostMapping("/{bidId}/csv")
	public ResponseEntity<?> uploadCsv(@PathVariable("bidId") Integer bidId,
			@RequestParam("files") MultipartFile csvFile) {
		BaseResponse response = new BaseResponse();
		List<CsvUploadErrorResponse> laneResponse = null;
		try {
			laneResponse = smcService.processCsv(bidId, csvFile);
			if (!laneResponse.isEmpty()) {
				return new ResponseEntity<>(laneResponse, HttpStatus.EXPECTATION_FAILED);
			}

		} catch (RecordNotFoundException record) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(record.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongFileFormat | WrongDateException error) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(error.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(laneResponse, HttpStatus.OK);
	}

	/**
	 * @param request
	 * @return
	 */
	@PostMapping(value = "")
	public ResponseEntity<?> saveBid(@RequestBody BidRequest request) {
		BidResponse rfpResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.rfpValidation(request);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				rfpResponse = bidService.saveBid(request);
			} else {
				return entity;
			}
		} catch (DuplicateExcaption duplicat) {
			response.setStatus(409);
			response.setErrorMessage(SmcConstants.CONFLICT);
			response.setText(duplicat.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (RecordNotFoundException record) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(record.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(wrongDateException.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(rfpResponse, HttpStatus.CREATED);
	}

	/***
	 * @PUT
	 * @param bidId
	 * @Update Bid for the corresponding Bid Id.
	 */
	@PutMapping(value = "/{bidId}")
	public ResponseEntity<?> updateBid(@RequestBody BidRequest request, @PathVariable("bidId") Integer bidId) {
		BidResponse rfpResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.rfpValidation(request);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				rfpResponse = bidService.updateBid(request, bidId);
			} else {
				return entity;
			}
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (DuplicateExcaption duplicate) {
			response.setStatus(409);
			response.setErrorMessage(SmcConstants.CONFLICT);
			response.setText(SmcConstants.DUPLICATE_RECORD);
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(wrongDateException.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(rfpResponse, HttpStatus.CREATED);
	}

	/***
	 * @DELETE
	 * @param bidId
	 * @Delete Bid for the corresponding Bid id.
	 */
	@DeleteMapping(value = "/{bidId}")
	public ResponseEntity<?> deleteBid(@PathVariable("bidId") Integer bidId) {
		ResponseEntity<?> rfpResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			rfpResponse = bidService.deleteBid(bidId);
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(rfpResponse, HttpStatus.CREATED);
	}

	/***
	 * @GET
	 * @param shipperId
	 * @return Bid for the respective Shipper.
	 */
	@GetMapping(value = "/shippers")
	public ResponseEntity<?> getAllBidByShipper(@RequestParam("shipperId") Integer shipperId,
			@RequestParam(name = "pNumber", defaultValue = "1") int pNumber,
			@RequestParam(name = "pSize", defaultValue = "10") int pSize,
			@RequestParam(name = "sortBy", defaultValue = "bidName") String sortBy,
			@RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy) {
		BidPaginationResponse rfpResponse = null;
		BaseResponse response = new BaseResponse();
		PageRequest pageReq = null;

		if (orderBy.equals("ASC"))
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.ASC, sortBy);
		else
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.DESC, sortBy);

		try {
			rfpResponse = bidService.getAllBidByShipper(shipperId, pageReq);

		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(rfpResponse, HttpStatus.CREATED);
	}

	/***
	 * @GET
	 * @param bidName
	 * @Search Bid by Bid Name.
	 */
	@GetMapping(value = "/bidName")
	public ResponseEntity<?> bidName(@RequestParam("name") String bidName) {
		List<BidResponse> rfpResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			rfpResponse = bidService.findByName(bidName);
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(rfpResponse, HttpStatus.CREATED);
	}

	/***
	 * @POST
	 * @Save all Invited Carriers.
	 */
	@PostMapping(value = "/{bidId}/inviteCarriers")
	public ResponseEntity<?> inviteCarrier(@PathVariable Integer bidId, @RequestBody InviteCarrierRequest request) {
		BaseResponse response = new BaseResponse();
		InviteCarrierResponse invitedCarrierResponse = null;
		try {
			invitedCarrierResponse = bidService.inviteCarrier(bidId, request);
		} catch (DuplicateExcaption duplicat) {
			response.setStatus(409);
			response.setErrorMessage(SmcConstants.CONFLICT);
			response.setText(duplicat.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (RecordNotFoundException record) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(record.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(wrongDateException.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(invitedCarrierResponse, HttpStatus.CREATED);
	}

	/**
	 * @param bidId
	 * @return
	 */
	@GetMapping(value = "/{bidId}/invitedCarriers")
	public ResponseEntity<?> invitedCarrier(@PathVariable Integer bidId) {
		BaseResponse response = new BaseResponse();
		InviteCarrierResponse invitedCarrierResponse = null;
		try {
			invitedCarrierResponse = bidService.invitedCarrier(bidId);
		} catch (DuplicateExcaption duplicat) {
			response.setStatus(409);
			response.setErrorMessage(SmcConstants.CONFLICT);
			response.setText(duplicat.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch (RecordNotFoundException record) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(record.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(wrongDateException.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(invitedCarrierResponse, HttpStatus.CREATED);
	}

	/***
	 * GET
	 * 
	 * @param bidId
	 * @return REview for the respective Bid id.
	 */
	@GetMapping(value = "/{bidId}/reviewBids")
	public ResponseEntity<?> reviewBid(@PathVariable("bidId") Integer bidId) {
		BaseResponse response = new BaseResponse();
		ReviewBidResponse bidResponse = new ReviewBidResponse();
		try {
			bidResponse = service.reviewBid(bidId);

		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(bidResponse, HttpStatus.CREATED);
	}

	/**
	 * @param bidId
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/{bidId}/submitBids")
	public ResponseEntity<?> submitBid(@PathVariable Integer bidId, @RequestBody MailRequest request) {
		BaseResponse response = new BaseResponse();
		try {

			if (bidId != null && request != null) {
				bidService.sendMail(bidId, request);
				response.setStatus(200);
				response.setErrorMessage("SUCCESS");
				response.setText("Success");
			}
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	/**
	 * @param bidId
	 * @return
	 */
	@GetMapping(value = "/{bidId}/invitations")
	public ResponseEntity<?> carrireLanes(@PathVariable("bidId") Integer bidId) {
		BaseResponse response = new BaseResponse();
		CarrierLanesResponse bidLanesResponse = new CarrierLanesResponse();
		try {
			bidLanesResponse = bidService.carrierLanes(bidId);

		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(bidLanesResponse, HttpStatus.CREATED);
	}

	/**
	 * @param bidId
	 * @param laneId
	 * @param request
	 * @param httpRequest
	 * @return
	 */
	@PostMapping(value = "/{bidId}/responseLanes")
	public ResponseEntity<?> responseBid(@PathVariable Integer bidId, @RequestParam("laneId") Integer laneId,
			@RequestBody ResponseBidRequest request,HttpServletRequest httpRequest) {
		BaseResponse response = new BaseResponse();
		ResponseBidResponse responseBid = new ResponseBidResponse();
		//final String requestHeader = httpRequest.getHeader(this.HEADER_STRING);
		try {
			//String username = jwtTokenUtil.getUsernameFromToken(requestHeader.substring(7));
			if (bidId != null && request.getStatus().equalsIgnoreCase("Approved")) {
				if (request.getDays() == null) {
					response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
					response.setText("Days Require");
					response.setStatus(406);
					return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
				}
				if (request.getRate() == null) {
					response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
					response.setText("Rate Require");
					response.setStatus(406);
					return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
				}
				if(request.getVolume()==null){
					response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
					response.setText("Volume Require");
					response.setStatus(406);
					return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
				}
				responseBid = bidService.bidResponse(bidId, laneId, request);
			} else if(request.getStatus().equalsIgnoreCase("Rejected")){
				responseBid = bidService.bidReject(bidId,laneId);
			}
		}catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(responseBid, HttpStatus.OK);
	}
 
	
	
	/**
	 * @param bidId
	 * @return
	 */
	@GetMapping(value="/{bidId}")
	public ResponseEntity<?> getBidDetails(@PathVariable("bidId") Integer bidId) {
		BaseResponse response = new BaseResponse();
		BidDetailsResponse bidDetaileResponse =new BidDetailsResponse();
		try {
			bidDetaileResponse = bidService.bidDetaileResponse(bidId);

		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(bidDetaileResponse, HttpStatus.CREATED);
	}

	/**
	 * @param carrierId
	 * @param pNumber
	 * @param pSize
	 * @param sortBy
	 * @param orderBy
	 * @return
	 */
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
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongDateException wrongDateException) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(history, HttpStatus.OK);
		
	}
	
	/**
	 * @param shipperId
	 * @param pNumber
	 * @param pSize
	 * @param sortBy
	 * @param orderBy
	 * @return
	 */
	@GetMapping("shippers/{shipperId}/history")
	public ResponseEntity<?> getBidsByShipper(@PathVariable("shipperId") Integer shipperId,
			@RequestParam(name = "pNumber", defaultValue = "1") int pNumber,
			@RequestParam(name = "pSize", defaultValue = "10") int pSize,
			@RequestParam(name = "sortBy", defaultValue = "bidName") String sortBy,
			@RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy) {
		ShipperBidHistoryPagResponse shipperResponse = null;
		BaseResponse response = new BaseResponse();
		PageRequest pageReq = null;
		if (orderBy.equals("ASC"))
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.ASC, sortBy);
		else
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.DESC, sortBy);

		try {
			shipperResponse = bidService.getBidsByShipper(shipperId, pageReq);

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

		return new ResponseEntity<>(shipperResponse, HttpStatus.OK);

	}

	
	/**
	 * @param shipperId
	 * @param pNumber
	 * @param pSize
	 * @param sortBy
	 * @param orderBy
	 * @return
	 */
	@GetMapping("shippers/{shipperId}/track")
	public ResponseEntity<?> getShipperTrack(@PathVariable("shipperId") Integer shipperId,
			@RequestParam(name = "pNumber", defaultValue = "1") int pNumber,
			@RequestParam(name = "pSize", defaultValue = "10") int pSize,
			@RequestParam(name = "sortBy", defaultValue = "bidName") String sortBy,
			@RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy) {
		ShipperTrackResponse shipperResponse = null;
		BaseResponse response = new BaseResponse();
		PageRequest pageReq = null;
		if (orderBy.equals("ASC"))
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.ASC, sortBy);
		else
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.DESC, sortBy);

		try {
			shipperResponse = bidService.getShipperTrack(shipperId, pageReq);

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

		return new ResponseEntity<>(shipperResponse, HttpStatus.OK);

	}

}