package com.bid.smc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.FormatException;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.exception.WrongDateException;
import com.bid.smc.request.AddCarrierRequest;
import com.bid.smc.request.ShipperRequest;
import com.bid.smc.response.AddCarrierResponse;
import com.bid.smc.response.BidResponse;
import com.bid.smc.response.CarrierPaginationResponse;
import com.bid.smc.response.ShipperResponse;
import com.bid.smc.service.BidService;
import com.bid.smc.service.CarrierService;
import com.bid.smc.service.ShipperService;
import com.bid.smc.validation.BidValidation;
import com.bid.smc.validation.RegistrationValidation;

@RestController
@CrossOrigin(allowCredentials = "false", maxAge = 3600)
public class ShipperController {

	@Autowired
	private ShipperService shipperService;
	@Autowired
	private BidService bidService;
	@Autowired
	private CarrierService carrierService;

	@Autowired
	private BidValidation bidValidation;
	@Autowired
	private RegistrationValidation validation;

	/***
	 * 
	 * @POST request
	 * @Save all Shipper Registration Input form data.
	 */
	@PostMapping(value = "/shippers")
	public ResponseEntity<?> saveShipper(@RequestBody ShipperRequest request) {
		ShipperResponse shipperResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = validation.shipperValidation(request);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				shipperResponse = shipperService.saveShipper(request);
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
		return new ResponseEntity<>(shipperResponse, HttpStatus.CREATED);
	}

	/***
	 * @GET request
	 * @return all saved Shipper Company details
	 */
	@GetMapping(value = "/shippers")
	public ResponseEntity<?> getShipper() {
		List<ShipperResponse> shipperResponses = null;
		BaseResponse response = new BaseResponse();
		try {
			shipperResponses = shipperService.getAllShipper();

		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		return new ResponseEntity<>(shipperResponses, HttpStatus.CREATED);
	}

	/***
	 * 
	 * @GET request
	 * @return Shipper Company details for the given Shipper Id.
	 */
	@GetMapping(value = "/shippers/{id}")
	public ResponseEntity<?> getShipperById(@PathVariable("id") Integer id) {
		BaseResponse response = new BaseResponse();
		ShipperResponse shipperResponse = null;
		try {
			shipperResponse = shipperService.getShipperById(id);
		} catch (RecordNotFoundException notFound) {
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(notFound.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (FormatException format) {
			response.setErrorMessage(format.getMessage());
			response.setStatus(406);
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

		return new ResponseEntity<>(shipperResponse, HttpStatus.FOUND);

	}

	/***
	 * 
	 * @GET request
	 * @return the corresponding Bid details for the given Shipper.
	 */

	@GetMapping(value = "/shippers/{shipperId}/bids")
	public ResponseEntity<?> getBidByShipper(@PathVariable("shipperId") Integer shipperId,
			@RequestParam("bidId") Integer bidId) {
		List<BidResponse> rfpResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			rfpResponse = bidService.getBidByShipper(shipperId, bidId);

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
		return new ResponseEntity<>(rfpResponse, HttpStatus.CREATED);
	}

	/***
	 * 
	 * @POST carrierRequest
	 * @Saving Add Carrier Input form data from the Partner Management Section.
	 */

	@PostMapping(value = "/shippers/{shipperId}/carriers")
	public ResponseEntity<?> saveCarrier(@PathVariable("shipperId") Integer shipperId,@RequestBody AddCarrierRequest carrierRequest) {
		AddCarrierResponse addCarrierResponse = null;
		BaseResponse response = new BaseResponse();
		try {
			ResponseEntity<?> entity = bidValidation.addCarrierValidation(carrierRequest);
			if (entity.getStatusCode().equals(HttpStatus.OK)) {
				addCarrierResponse = carrierService.saveCarrierByShipper(shipperId,carrierRequest);
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
		return new ResponseEntity<>(addCarrierResponse, HttpStatus.CREATED);
	}

	/***
	 * 
	 * @GET request
	 * @return Carrier details added by the given Shipper Company under Partner
	 *         Management Section.
	 */

	/*@GetMapping(value = "/shippers/{shipperId}/carriers")
	public ResponseEntity<?> getCarrierByShipper(@PathVariable("shipperId") Integer shipperId,
			@RequestParam(name = "pNumber", defaultValue = "1") int pNumber,
			@RequestParam(name = "pSize", defaultValue = "10") int pSize,
			@RequestParam(name = "sortBy", defaultValue = "contactName") String sortBy,
			@RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy) {

		CarrierPaginationResponse carrierResponse = null;
		BaseResponse response = new BaseResponse();
		PageRequest pageReq = null;
		if (orderBy.equals("ASC"))
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.ASC, sortBy);
		else
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.DESC, sortBy);

		try {
			carrierResponse = carrierService.getCarrierByShipper(shipperId, pageReq);

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

		return new ResponseEntity<>(carrierResponse, HttpStatus.OK);
	}
*/
	@GetMapping(value = "/shippers/{shipperId}/carriers/searchC")
	public ResponseEntity<?> getCarrierByShipper(@PathVariable("shipperId") Integer shipperId,
			@RequestParam(value="searchC",defaultValue="")String searchC,
			@RequestParam(name = "pNumber", defaultValue = "1") int pNumber,
			@RequestParam(name = "pSize", defaultValue = "10") int pSize,
			@RequestParam(name = "sortBy", defaultValue = "contactName") String sortBy,
			@RequestParam(name = "orderBy", defaultValue = "ASC") String orderBy) {

		CarrierPaginationResponse carrierResponse = null;
		BaseResponse response = new BaseResponse();
		PageRequest pageReq = null;
		if (orderBy.equals("ASC"))
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.ASC, sortBy);
		else
			pageReq = new PageRequest(pNumber - 1, pSize, Sort.Direction.DESC, sortBy);

		try {
			if (StringUtils.isEmpty(searchC)) {
				carrierResponse = carrierService.getCarrierByShipper(shipperId, pageReq);
			} else {
				carrierResponse = carrierService.searchCarrier(shipperId, searchC, pageReq,sortBy);
			}
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
		}

		return new ResponseEntity<>(carrierResponse, HttpStatus.OK);
	}
}
