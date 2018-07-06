package com.bid.smc.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.DuplicateExcaption;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.exception.WrongDateException;
import com.bid.smc.model.adminmanager.CompanyEntity;
import com.bid.smc.model.bidsense.BidDocumentEntity;
import com.bid.smc.model.bidsense.BidEntity;
import com.bid.smc.model.bidsense.BidResponseDetailsEntity;
import com.bid.smc.model.bidsense.BidResponseStatusEntity;
import com.bid.smc.model.bidsense.CarrierEntity;
import com.bid.smc.model.bidsense.CustomerEntity;
import com.bid.smc.model.bidsense.InviteCarrierEntity;
import com.bid.smc.model.bidsense.LanesEntity;
import com.bid.smc.repo.adminmanager.CompanyRepository;
import com.bid.smc.repo.adminmanager.UserRepository;
import com.bid.smc.repo.bidsense.BidRepository;
import com.bid.smc.repo.bidsense.BidResponseDetailsRepository;
import com.bid.smc.repo.bidsense.BidResponseStatusRepository;
import com.bid.smc.repo.bidsense.CarrierRepository;
import com.bid.smc.repo.bidsense.CustomerRepository;
import com.bid.smc.repo.bidsense.InviteCarrierRepository;
import com.bid.smc.repo.bidsense.LanesRepository;
import com.bid.smc.repo.bidsense.ShipperRepository;
import com.bid.smc.request.BidRequest;
import com.bid.smc.request.InviteCarrierRequest;
import com.bid.smc.request.MailRequest;
import com.bid.smc.request.ResponseBidRequest;
import com.bid.smc.response.BidDetailsResponse;
import com.bid.smc.response.BidPaginationResponse;
import com.bid.smc.response.BidResponse;
import com.bid.smc.response.CarrierHistPaginationResponse;
import com.bid.smc.response.CarrierLanesResponse;
import com.bid.smc.response.CarrierResponse;
import com.bid.smc.response.CustomerResponse;
import com.bid.smc.response.InviteCarrierResponse;
import com.bid.smc.response.LaneResponse;
import com.bid.smc.response.ResponseBidResponse;
import com.bid.smc.response.ShipperBidHistoryPagResponse;
import com.bid.smc.response.ShipperBidHistoryResponse;
import com.bid.smc.response.ShipperTrackResponse;
import com.bid.smc.service.BidService;
import com.bid.smc.util.DateUtil;
import com.bid.smc.util.EntityToResponse;
import com.bid.smc.util.ModelToEntity;
import com.bid.smc.util.TimeProvider;
import com.bid.smc.validation.RegistrationValidation;

@Service
@Transactional
public class BidServiceImpl implements BidService {

	@Autowired
	private ModelToEntity modelToEntity;

	@Autowired
	private BidRepository bidRepository;

	@Autowired
	private InviteCarrierRepository inviteRepo;

	@Autowired
	private EntityToResponse entityToResponse;

	@Autowired
	private RegistrationValidation validation;

	@Autowired
	private ShipperRepository shipperRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private CompanyRepository companyRepo;

	@Autowired
	private CarrierRepository carrierRepo;

	@Autowired
	private LanesRepository laneRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private BidResponseStatusRepository bidStatusResponseRepo;

	@Autowired
	private BidResponseDetailsRepository bidDetailsResponseRepo;

	@Autowired
	private CompanyRepository companyRepository;

	/***
	 * @POST
	 * @Save all Input form data in Define Bid Rules Page.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public BidResponse saveBid(BidRequest bidRequest) throws ParseException {

		BidEntity entity = new BidEntity();
		BaseResponse response = new BaseResponse();

		if (!validation.isValidFormat(SmcConstants.DATE_FORMAT, bidRequest.getBidResponseStartDate())
				&& !validation.isValidFormat(SmcConstants.DATE_FORMAT, bidRequest.getBidResponseEndDate())
				&& !validation.isValidFormat(SmcConstants.DATE_FORMAT, bidRequest.getBidTermEndDate())
				&& !validation.isValidFormat(SmcConstants.DATE_FORMAT, bidRequest.getBidTermStartDate())) {
			response.setText(SmcConstants.BID_DATE_FORMAT);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			throw new WrongDateException(SmcConstants.BID_DATE_FORMAT);
		}

		BidResponse bidResponse = new BidResponse();

		entity.setBidResponseStartDate(DateUtil.parseDate(bidRequest.getBidResponseStartDate()));
		entity.setBidResponseEndDate(DateUtil.parseDate(bidRequest.getBidResponseEndDate()));
		entity.setBidTermStartDate(DateUtil.parseDate(bidRequest.getBidTermStartDate()));
		entity.setBidTermEndDate(DateUtil.parseDate(bidRequest.getBidTermEndDate()));

		bidValidation(bidRequest, entity, response);
		BidEntity bidEntity = modelToEntity.mapToBidRequestToEntity(bidRequest, entity);
		return entityToResponse.rpfRequestTorpfEntity(bidResponse, bidRepository.save(bidEntity));
	}

	/**
	 * @param bidRequest
	 * @param entity
	 * @param response
	 */
	private void bidValidation(BidRequest bidRequest, BidEntity entity, BaseResponse response) {
		if (comparsionDate(entity)) {
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			throw new WrongDateException(SmcConstants.BID_RESPONSE_DATE);
		}
		if (entity.getBidTermStartDate().after(entity.getBidTermEndDate())) {
			response.setText(SmcConstants.BID_TERM_DATE);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			throw new WrongDateException(SmcConstants.BID_TERM_DATE);
		}
		if (entity.getBidResponseEndDate().after(entity.getBidTermStartDate())) {
			response.setText(SmcConstants.RESPONSE_TERM_DATE);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			throw new WrongDateException(SmcConstants.RESPONSE_TERM_DATE);
		}
		if (shipperRepo.findShipperId(bidRequest.getShipperId()) == null) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(SmcConstants.SHIPPER_NOT_FOUND);
			throw new RecordNotFoundException(SmcConstants.SHIPPER_NOT_FOUND);
		}
		if (customerRepo.findCustomerById(bidRequest.getCustomer()) == null) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(SmcConstants.CUSTOMER_NOT_FOUND);
			throw new RecordNotFoundException(SmcConstants.CUSTOMER_NOT_FOUND);
		}
		if (validation.isBidNamePresent(bidRequest.getBidName(), bidRequest.getShipperId())) {
			response.setStatus(409);
			response.setErrorMessage(SmcConstants.CONFLICT);
			response.setText(SmcConstants.BID_NAME_PRESENT);
			throw new DuplicateExcaption(SmcConstants.BID_NAME_PRESENT);
		}
	}

	private boolean comparsionDate(BidEntity entity) {
		return entity.getBidResponseStartDate().after(entity.getBidResponseEndDate());
	}

	/***
	 * @PUT
	 * @param bidId
	 * @Update Bid for the corresponding Bid Id.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public BidResponse updateBid(BidRequest request, Integer bidId) throws ParseException {
		BaseResponse response = new BaseResponse();
		BidResponse bidResponse = new BidResponse();

		if (!validation.isValidFormat(SmcConstants.DATE_FORMAT, request.getBidResponseStartDate())
				&& !validation.isValidFormat(SmcConstants.DATE_FORMAT, request.getBidResponseEndDate())
				&& !validation.isValidFormat(SmcConstants.DATE_FORMAT, request.getBidTermEndDate())
				&& !validation.isValidFormat(SmcConstants.DATE_FORMAT, request.getBidTermStartDate())) {
			response.setText(SmcConstants.BID_DATE_FORMAT);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			throw new WrongDateException(SmcConstants.BID_DATE_FORMAT);
		}

		BidEntity entity = bidRepository.getOne(bidId);

		entity.setBidResponseStartDate(DateUtil.parseDate(request.getBidResponseStartDate()));
		entity.setBidResponseEndDate(DateUtil.parseDate(request.getBidResponseEndDate()));
		entity.setBidTermStartDate(DateUtil.parseDate(request.getBidTermStartDate()));
		entity.setBidTermEndDate(DateUtil.parseDate(request.getBidTermEndDate()));

		updateBidValidation(request, response, entity);
		BidEntity bidEntity = modelToEntity.mapToBidRequestToEntity(request, entity);
		return entityToResponse.rpfRequestTorpfEntity(bidResponse, bidRepository.saveAndFlush(bidEntity));
	}

	/**
	 * @param request
	 * @param response
	 * @param entity
	 */
	private void updateBidValidation(BidRequest request, BaseResponse response, BidEntity entity) {

		if (entity == null) {
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setStatus(409);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}

		if (!request.getBidName().equals(entity.getBidName())
				&& validation.isBidNamePresent(request.getBidName(), request.getShipperId())) {
			response.setText(SmcConstants.BID_NAME_PRESENT);
			response.setErrorMessage(SmcConstants.CONFLICT);
			response.setStatus(409);
			throw new DuplicateExcaption(SmcConstants.DUPLICATE_RECORD);
		}
		if (DateUtil.compareDate(entity.getBidResponseStartDate(), entity.getBidResponseEndDate())) {
			response.setText(SmcConstants.BID_RESPONSE_DATE);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			throw new WrongDateException(SmcConstants.BID_RESPONSE_DATE);
		}
		if (DateUtil.compareDate(entity.getBidTermStartDate(), entity.getBidTermEndDate())) {
			response.setText(SmcConstants.BID_TERM_DATE);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			throw new WrongDateException(SmcConstants.BID_TERM_DATE);
		}
		if (entity.getBidResponseEndDate().after(entity.getBidTermStartDate())) {
			response.setText(SmcConstants.RESPONSE_TERM_DATE);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setStatus(406);
			throw new WrongDateException(SmcConstants.RESPONSE_TERM_DATE);
		}
	}

	/***
	 * 
	 * @GET request
	 * @return the corresponding Bid details for the given Shipper.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public List<BidResponse> getBidByShipper(Integer companyId, Integer bidId) {
		BaseResponse response = new BaseResponse();
		List<BidEntity> frBidEntity = bidRepository.getBidByShipper(companyId, bidId);
		if (frBidEntity.isEmpty()) {
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setStatus(409);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}
		List<BidResponse> bidResponses = new ArrayList<>();
		for (BidEntity entity : frBidEntity) {
			BidResponse bidResponse = new BidResponse();
			bidResponses.add(entityToResponse.rpfRequestTorpfEntity(bidResponse, entity));
		}
		return bidResponses;
	}

	/***
	 * @GET
	 * @param shipperId
	 * @return Bid for the respective Shipper.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public BidPaginationResponse getAllBidByShipper(Integer companyId, Pageable pageable) {

		List<BidEntity> frBidEntity = bidRepository.getAllBidByShipper(companyId, pageable);
 

		Integer count = bidRepository.getAllBidByShipperCount(companyId);

		List<BidResponse> bidResponses = new ArrayList<>();
		for (BidEntity entity : frBidEntity) {
			BidResponse bidResponse = new BidResponse();

			bidResponses.add(entityToResponse.rpfRequestTorpfEntity(bidResponse, entity));
		}

		return new BidPaginationResponse(bidResponses, count);
 
	}

	/***
	 * @DELETE
	 * @param bidId
	 * @Delete Bid for the corresponding Bid id.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public ResponseEntity<?> deleteBid(Integer bidId) {

		BaseResponse response = new BaseResponse();

		if (bidRepository.getBid(bidId) == null) {
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setStatus(409);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}
		bidRepository.delete(bidId);

		response.setStatus(200);
		response.setText("Bid successfully Deleted");
		response.setErrorMessage("Success");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/***
	 * @GET
	 * @param bidName
	 * @Search Bid by Bid Name.
	 */
	@Override
	public List<BidResponse> findByName(String bidName) {
		List<BidEntity> frBidEntity = bidRepository.getAllBidByName(bidName);
		List<BidResponse> bidResponses = new ArrayList<>();
		for (BidEntity entity : frBidEntity) {
			BidResponse bidResponse = new BidResponse();
			bidResponses.add(entityToResponse.rpfRequestTorpfEntity(bidResponse, entity));
		}
		return bidResponses;
	}

	/***
	 * @POST
	 * @param request
	 * @Save all Invited Carriers.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public InviteCarrierResponse inviteCarrier(Integer bidId, InviteCarrierRequest request) {
		BaseResponse response = new BaseResponse();
		List<InviteCarrierEntity> inviteCarrierEntities = new ArrayList<>();
		InviteCarrierResponse inviteCarrierResponse = new InviteCarrierResponse();
		for (Integer carrierId : request.getCarrierIds()) {
			CarrierEntity carrierEntity = carrierRepo.findOne(carrierId);
			if (carrierEntity == null) {
				response.setText(SmcConstants.RECORD_NOT_FOUND);
				response.setErrorMessage(SmcConstants.NOT_FOUND);
				response.setStatus(404);
				throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
			}
		}
		for (Integer carrierId : request.getCarrierIds()) {
			InviteCarrierEntity inviteCarrierEntity = inviteRepo.findInvitation(bidId, carrierId);
			if (inviteCarrierEntity != null) {
				inviteRepo.delete(inviteCarrierEntity.getId());
			}
		}
		for (Integer carrierId : request.getCarrierIds()) {
			InviteCarrierEntity inviteCarrierEntity = inviteRepo.findInvitation(bidId, carrierId);
			if (inviteCarrierEntity == null) {
				InviteCarrierEntity inviteCarrier = new InviteCarrierEntity();
				CarrierEntity carrierEntity = carrierRepo.findOne(carrierId);
				BidEntity bidEntity = bidRepository.findOne(bidId);
				inviteCarrier.setCarrier(carrierEntity);
				inviteCarrier.setBidId(bidEntity);
				inviteCarrier.setToken(randomAlphaNumeric(SmcConstants.RANDOM_LENGTH));
				inviteCarrier.setInvitedDate(new TimeProvider().now());
				inviteCarrier.setStatus("New");
				inviteCarrier = inviteRepo.save(inviteCarrier);
				inviteCarrierEntities.add(inviteCarrier);
			}
		}

		List<CarrierResponse> responses = new ArrayList<>();
		for (InviteCarrierEntity entity : inviteCarrierEntities) {
			CompanyEntity companyEntity = companyRepo.findOne(entity.getCarrier().getCompanyId());
			CarrierResponse carrierResponse = new CarrierResponse();
			carrierResponse.setCarrierId(entity.getCarrier().getCompanyId());
			carrierResponse.setDotNumber(entity.getCarrier().getDotNumber());
			carrierResponse.setMcNumber(entity.getCarrier().getMcNumber());
			carrierResponse.setCompanyName(companyEntity.getsCompanyName());
			responses.add(carrierResponse);
			inviteCarrierResponse.setInvitedCarriers(responses);
		}
		return inviteCarrierResponse;
	}

	/***
	 * GET
	 * 
	 * @param bidId
	 * @return REview for the respective Bid id.
	 */

	@Override
	public void saveUploadedDetails(Integer bidId, MultipartFile[] uploadfiles) {
		for (MultipartFile multipartFile : uploadfiles) {
			BidDocumentEntity entity = new BidDocumentEntity();

			entity.setDocumentName(multipartFile.getOriginalFilename());
		}
	}

	/**
	 * @param count
	 * @return
	 */
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count != 0) {
			int character = (int) (Math.random() * SmcConstants.ALPHA_NUMERIC_STRING.length());
			builder.append(SmcConstants.ALPHA_NUMERIC_STRING.charAt(character));
			count = count - 1;
		}
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bid.smc.service.BidService#invitedCarrier(java.lang.Integer)
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public InviteCarrierResponse invitedCarrier(Integer bidId) {
		
		List<InviteCarrierEntity> invitedCarriers = inviteRepo.findInvitedCarrierByBidId(bidId);
		InviteCarrierResponse inviteCarrierResponse = new InviteCarrierResponse();
		
		List<CarrierResponse> responses = new ArrayList<>();
		
		for (InviteCarrierEntity entity : invitedCarriers) {
			CompanyEntity companyEntity = companyRepo.findOne(entity.getCarrier().getCompanyId());
			CarrierResponse carrierResponse = new CarrierResponse();
			carrierResponse.setCarrierId(entity.getCarrier().getCompanyId());
			carrierResponse.setDotNumber(entity.getCarrier().getDotNumber());
			carrierResponse.setMcNumber(entity.getCarrier().getMcNumber());
			carrierResponse.setCompanyName(companyEntity.getsCompanyName());
		 
			responses.add(carrierResponse);
			inviteCarrierResponse.setInvitedCarriers(responses);
		}
		return inviteCarrierResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bid.smc.service.BidService#sendMail(java.lang.Integer,
	 * com.bid.smc.request.MailRequest)
	 */
	@SuppressWarnings("unused")
	@Override
	@Transactional("bidsenseTransactionManager")
	public void sendMail(Integer bidId, MailRequest message) throws MessagingException, ParseException {

		BaseResponse response = new BaseResponse();
		BidEntity bidEntity = bidRepository.findOne(bidId);

		if (bidEntity == null) {
			response.setStatus(404);
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}

		List<String> emailIds = inviteRepo.findEmailIdOfInvitedCarrierByBidId(bidId);

		String body = null;

		bidEntity.setSubmitDate(new TimeProvider().now());
		bidEntity.setStatus("Submitted / Awaiting responses");

		bidRepository.saveAndFlush(bidEntity);

		String reportDate = DateUtil.formatDate(bidEntity.getBidResponseEndDate());

		CompanyEntity entity = companyRepo.findOne(bidEntity.getCompanyId());

		if (!message.getMessage().isEmpty()) {
			body = "<Html><body>Dear carrier,<br><br>" + entity.getsCompanyName()
					+ " has invited you to smc3.com to participate in an " + bidEntity.getBidName()
					+ ".<br> In order to participate please login using the link below and submit pricing by "
					+ reportDate + "."
					+ "<br> <br> Login Link : <a href='http://13.71.117.64/SMC3#/login'>http://13.71.117.64/SMC3#/login</a><br><br>"
					+ message.getMessage() + "</body></html>";
		} else {
			body = "<Html><body>Dear Carrier,<br><br>" + entity.getsCompanyName()
					+ " has invited you to smc3.com to participate in an " + bidEntity.getBidName()
					+ ". In order to participate please login using the link below and submit pricing by " + reportDate
					+ ".<br><br>"
					+ "<br> <br> Login Link : <a href='http://13.71.117.64/SMC3#/login'>http://13.71.117.64/SMC3#/login</a><br><br>"
					+ message.getMessage() + "</body></html>";
		}
		/*
		 * for (InviteCarrierEntity entity : invitedCarriers) { String email =
		 * entity.getCarrier().getContactEmail(); sendMail(email,body); }
		 */
		sendMail("raghvendra.rathore@dreamorbit.com", body);
	}

	/**
	 * @param email
	 * @param text
	 * @return
	 */
	private boolean sendMail(String email, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			String subject = SmcConstants.SUBJECT;
			helper.setSubject(subject);
			helper.setTo(email);
			message.addHeader("Content-Transfer-Encoding", "7bit");
			message.setContent(text, "text/html; charset=UTF-8");
			helper.setFrom("testemail1@dreamorbit.com");
			mailSender.send(message);
			return true;
		} catch (MessagingException e) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bid.smc.service.BidService#carrierLanes(java.lang.Integer)
	 */
	@Override
	public CarrierLanesResponse carrierLanes(Integer bidId) {
		CarrierLanesResponse carrierLanesResponse = new CarrierLanesResponse();
		BidResponse bidResponse = new BidResponse();

		BidEntity bidEntity = bidRepository.findOne(bidId);

		bidResponse.setBidId(bidEntity.getBidId());
		bidResponse.setBidResponseEndDate(DateUtil.formatDate(bidEntity.getBidResponseEndDate()));
		bidResponse.setBidResponseStartDate(DateUtil.formatDate(bidEntity.getBidResponseStartDate()));
		bidResponse.setBidTermEndDate(DateUtil.formatDate(bidEntity.getBidTermEndDate()));
		bidResponse.setBidTermStartDate(DateUtil.formatDate(bidEntity.getBidTermStartDate()));

		List<LanesEntity> lanesEntities = laneRepo.findLanesByBidId(bidId);

		List<LaneResponse> laneResponses = new ArrayList<>();

		for (LanesEntity lanesEntity : lanesEntities) {
			LaneResponse laneResponse = new LaneResponse();
			BeanUtils.copyProperties(lanesEntity, laneResponse);
			BidResponseStatusEntity entity = bidStatusResponseRepo.findLanesDatils(bidId, lanesEntity.getLaneId());

			if (entity != null && entity.getBidResponseDetails() != null) {
				
				BidResponseStatusEntity bidResponseStatusEntity = bidStatusResponseRepo.findLanesDatils(bidId, lanesEntity.getLaneId());
				laneResponse.setFlatRate(bidResponseStatusEntity.getBidResponseDetails().getRate());
				laneResponse.setTransitDays(bidResponseStatusEntity.getBidResponseDetails().getDays());
				laneResponse.setVolume(bidResponseStatusEntity.getBidResponseDetails().getVolume());
			}
			
			laneResponses.add(laneResponse);
		}

		carrierLanesResponse.setBid(bidResponse);
		carrierLanesResponse.setLanes(laneResponses);
		return carrierLanesResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bid.smc.service.BidService#bidResponse(java.lang.Integer,
	 * java.lang.Integer, com.bid.smc.request.ResponseBidRequest)
	 */
	@Override
	public ResponseBidResponse bidResponse(Integer bidId, Integer laneId, ResponseBidRequest request) {
		BidResponseStatusEntity bidResponseStatusEntity = new BidResponseStatusEntity();

		BaseResponse baseResponse = new BaseResponse();

		LanesEntity lanesEntity = laneRepo.findOne(laneId);

		BidEntity bidEntity = lanesEntity.getBid();

		bidResponseStatusEntity.setLaneId(lanesEntity.getLaneId());
		bidResponseStatusEntity.setBidId(bidEntity.getBidId());
		bidResponseStatusEntity.setStatus("Approve");
		bidResponseStatusEntity.setCompany(bidEntity.getCompanyId());
		bidResponseStatusEntity.setResponseDate(new TimeProvider().now());

		BidResponseStatusEntity saveBidResponseStatus = bidStatusResponseRepo.save(bidResponseStatusEntity);

		BidResponseDetailsEntity bidResponseDetailsEntity = new BidResponseDetailsEntity();
		bidResponseDetailsEntity.setBidResponseStatus(saveBidResponseStatus);
		bidResponseDetailsEntity.setDays(request.getDays());
		bidResponseDetailsEntity.setNotes(request.getNote());
		bidResponseDetailsEntity.setRate(request.getRate());

		LanesEntity lane = laneRepo.findOne(laneId);
		if (Integer.parseInt(lane.getVolume()) >= Integer.parseInt(request.getVolume())) {
			bidResponseDetailsEntity.setVolume(request.getVolume());
		} else {
			baseResponse.setText(SmcConstants.VOLUME_SIZE);
			baseResponse.setErrorMessage(SmcConstants.NOT_FOUND);
			baseResponse.setStatus(404);
			throw new RecordNotFoundException(SmcConstants.VOLUME_SIZE);
		}

		bidDetailsResponseRepo.save(bidResponseDetailsEntity);
		return mapToBidResponse(bidId, request, saveBidResponseStatus);
	}

	/**
	 * @param bidId
	 * @param request
	 * @param saveBidResponseStatus
	 * @return
	 */
	private ResponseBidResponse mapToBidResponse(Integer bidId, ResponseBidRequest request,
			BidResponseStatusEntity saveBidResponseStatus) {
		ResponseBidResponse response = new ResponseBidResponse();
		response.setBidId(bidId);
		response.setBidResponseStatusId(saveBidResponseStatus.getBidStatusResponseId());
		response.setDays(request.getDays());
		response.setNote(request.getNote());
		response.setRate(request.getRate());
		response.setStatus(saveBidResponseStatus.getStatus());
		response.setVolume(request.getVolume());
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bid.smc.service.BidService#bidReject(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@Override
	public ResponseBidResponse bidReject(Integer bidId, Integer laneId) {

		BidResponseStatusEntity bidResponseStatusEntity = new BidResponseStatusEntity();

		LanesEntity lanesEntity = laneRepo.findOne(laneId);

		BidEntity bidEntity = lanesEntity.getBid();

		bidResponseStatusEntity.setLaneId(lanesEntity.getLaneId());
		bidResponseStatusEntity.setBidId(bidEntity.getBidId());
		bidResponseStatusEntity.setStatus("No Bid");
		bidResponseStatusEntity.setCompany(bidEntity.getCompanyId());
		bidResponseStatusEntity.setResponseDate(new TimeProvider().now());

		BidResponseStatusEntity saveBidResponseStatus = bidStatusResponseRepo.save(bidResponseStatusEntity);

		ResponseBidResponse response = new ResponseBidResponse();
		response.setBidId(bidId);
		response.setBidResponseStatusId(saveBidResponseStatus.getBidStatusResponseId());
		response.setStatus(saveBidResponseStatus.getStatus());

		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bid.smc.service.BidService#bidDetaileResponse(java.lang.Integer)
	 */
	@Override
	public BidDetailsResponse bidDetaileResponse(Integer bidId) {
		
		BidDetailsResponse bidResponse = new BidDetailsResponse();
		List<LaneResponse> laneResponses = new ArrayList<>();
		
		BidEntity bidEntity = bidRepository.findOne(bidId);
		
		bidResponse.setBidId(bidEntity.getBidId());
		BeanUtils.copyProperties(bidEntity, bidResponse);
		
		bidResponse.setBidResponseEndDate(DateUtil.formatDate(bidEntity.getBidResponseEndDate()));
		bidResponse.setBidResponseStartDate(DateUtil.formatDate(bidEntity.getBidResponseStartDate()));
		bidResponse.setBidTermEndDate(DateUtil.formatDate(bidEntity.getBidTermEndDate()));
		bidResponse.setBidTermStartDate(DateUtil.formatDate(bidEntity.getBidTermStartDate()));
		CustomerEntity entity = customerRepo.findOne(bidEntity.getCustomer().getCustomerId());
		CustomerResponse customer = new CustomerResponse();
		BeanUtils.copyProperties(entity, customer);
		bidResponse.setCustomer(customer);
		List<LanesEntity> lanesEntities = laneRepo.findLanesByBidId(bidId);
		
		/*for (LanesEntity laneEntity : lanesEntities) {
			LaneResponse response = new LaneResponse();
			BeanUtils.copyProperties(laneEntity, response);
			response.setEquipmentTypeId(laneEntity.getEquipmentType());
			response.setEquipmentClassId(laneEntity.getEquipment());
			response.setEquipmentLengthId(laneEntity.getEquipmentLength());
			response.setModeId(laneEntity.getMode());
			laneResponses.add(response);
		}*/
		bidResponse.setNoOfLanes(lanesEntities.size());
		bidResponse.setShipperId(bidEntity.getCompanyId());
		//bidResponse.setLanesResponses(laneResponses);
		return bidResponse;
	}

	/*
	 * (non-Javadoc)
	 * @see com.bid.smc.service.BidService#getAllBidsByCarrier(java.lang.Integer,
	 * org.springframework.data.domain.Pageable)
	 */
	@Override
	public CarrierHistPaginationResponse getAllBidsByCarrier(Integer carrierId, Pageable pageable) {
		List<BidEntity> bidEntity = bidRepository.getAllBidByCarrier(carrierId, pageable);
		Integer count = bidRepository.getAllBidByCarrierCount(carrierId);

		List<BidResponse> carResponses = new ArrayList<>();

		for (BidEntity entity : bidEntity) {
			BidResponse bidResponse = new BidResponse();
			bidResponse.setShipperName(getShipperNameByCompanyId(entity));
			carResponses.add(entityToResponse.rpfRequestTorpfEntity(bidResponse, entity));
		}

		return new CarrierHistPaginationResponse(carResponses, count);
	}

	private String getShipperNameByCompanyId(BidEntity entity) {
		return companyRepository.getShipperNameByCompanyId(entity.getCompanyId());
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.BidService#getBidsByShipper(java.lang.Integer, org.springframework.data.domain.Pageable)
	 */
	@Override
	public ShipperBidHistoryPagResponse getBidsByShipper(Integer shipperId, Pageable pageable) {
		List<BidEntity> bidEntity = bidRepository.getAllBidsByShipperId(shipperId, pageable);
		Integer count = bidRepository.getAllBidsByshipperCount(shipperId);

		List<ShipperBidHistoryResponse> shipperResponses = new ArrayList<>();

		for (BidEntity entity : bidEntity) {
			ShipperBidHistoryResponse shipResponse = new ShipperBidHistoryResponse();
			shipResponse.setShipperName(getShipperNameByCompanyId(entity));
			shipperResponses.add(entityToResponse.rpfReqHisTorpfEntity(shipResponse, entity));
		}

		return new ShipperBidHistoryPagResponse(shipperResponses, count);
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.BidService#getShipperTrack(java.lang.Integer, org.springframework.data.domain.Pageable)
	 */
	@Override
	public ShipperTrackResponse getShipperTrack(Integer shipperId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
