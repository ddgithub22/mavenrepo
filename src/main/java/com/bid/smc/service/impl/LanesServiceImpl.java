package com.bid.smc.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.model.bidsense.BidEntity;
import com.bid.smc.model.bidsense.BidResponseStatusEntity;
import com.bid.smc.model.bidsense.EquipmentEntity;
import com.bid.smc.model.bidsense.EquipmentLengthEntity;
import com.bid.smc.model.bidsense.LanesEntity;
import com.bid.smc.model.bidsense.ModeEntity;
import com.bid.smc.repo.bidsense.BidRepository;
import com.bid.smc.repo.bidsense.BidResponseStatusRepository;
import com.bid.smc.repo.bidsense.EquipmentLengthRepository;
import com.bid.smc.repo.bidsense.EquipmentRepository;
import com.bid.smc.repo.bidsense.InviteCarrierRepository;
import com.bid.smc.repo.bidsense.LanesRepository;
import com.bid.smc.repo.bidsense.ModeRepository;
import com.bid.smc.request.LaneRequest;
import com.bid.smc.response.CarrierHistoryLanePaginationResponse;
import com.bid.smc.response.LanePaginationResponse;
import com.bid.smc.response.LaneResponse;
import com.bid.smc.response.LanesHistoryResponse;
import com.bid.smc.response.LocationResponse;
import com.bid.smc.response.ReviewBidResponse;
import com.bid.smc.service.LanesService;

@Service
@Transactional
public class LanesServiceImpl implements LanesService {

	@Autowired
	private LanesRepository lanesRepo;
	@Autowired
	private ModeRepository modeRepo;
	@Autowired
	private EquipmentRepository equipmentRepo;
	@Autowired
	private EquipmentLengthRepository equipmentLengthRepo;
	@Autowired
	private BidRepository bidRepo;
	@Autowired
	private InviteCarrierRepository inviteRepo;
	
	@Autowired
	private BidResponseStatusRepository statusRepo;

	/*
	 * @Autowired private LaneResponse response;
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	/***
	 * 
	 * @POST request
	 * @Save Add Lanes Input form page.
	 */
	public LaneResponse saveLane(LaneRequest request) {
		LanesEntity lane = new LanesEntity();
		LaneResponse response = new LaneResponse();

		lane.setLane(request.getLane());
		lane.setVolume(request.getVolume());
		lane.setOriginName(request.getOriginName());
		lane.setOriginAddress(request.getOriginAddress());
		lane.setDestinationAddress(request.getDestinationAddress());
		lane.setCommodity(request.getCommodity());
		lane.setWeight(request.getWeight());
		lane.setHaul(request.getHaul());
		lane.setIncumbentName(request.getIncumbentName());
		lane.setIncumbentRate(request.getIncumbentRate());
		lane.setBenchmarkRate(request.getBenchmarkRate());
		lane.setLaneDescription(request.getLaneDescription());
		
		lane.setDestinationName(request.getDestinationName());
		lane.setDestinationCity(request.getDestinationCity());
		lane.setDestinationState(request.getDestinationState());
		lane.setOriginZipCode(request.getOriginZipCode());
		lane.setDestinationZipcode(request.getDestinationZipcode());
        lane.setOriginCity(request.getOriginCity());
        lane.setOriginState(request.getOriginState());
        
		if (request.getMode() != null) {
			lane.setMode(request.getMode());
		}
		if (request.getEquipmentClass() != null) {
			lane.setEquipment(request.getEquipmentClass());
		}
		if (request.getEquipmentType() != null) {
			lane.setEquipmentType(request.getEquipmentType());
		}
		if (request.getEquipmentLength() != null) {
			lane.setEquipmentLength(request.getEquipmentLength());
		}
		if (request.getBidId() != null) {
			lane.setBid(bidRepo.findOne(request.getBidId()));
		}
		LanesEntity lanesEntity = lanesRepo.save(lane);
		BeanUtils.copyProperties(lanesEntity, response);

		response.setBidId(lanesEntity.getBid().getBidId());
		if (lanesEntity.getEquipmentType() != null) {
			EquipmentLengthEntity entity = equipmentLengthRepo.findOne(request.getEquipmentLength());
			response.setEquipmentLength(entity.getEquipmentLength());
		}
		ModeEntity modeEntity = modeRepo.findOne(request.getMode());
		response.setMode(modeEntity.getModes());
		EquipmentEntity equipEntity = equipmentRepo.findOne(request.getEquipmentClass());
		response.setEquipmentClass(equipEntity.getEquipment());
		response.setEquipmentClassId(equipEntity.getEquipmentId());
		if (lanesEntity.getEquipmentType() != null) {
			EquipmentEntity equipType = equipmentRepo.findOne(request.getEquipmentType());
			response.setEquipmentType(equipType.getEquipment());
		}
		return response;
	}

	/**
	 * @return all Lanes Details for the given Bid Id with Pagination and
	 *         Sorting.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public List<LaneResponse> getAllLanes(PageRequest page_req) {

		Page<LanesEntity> laneEntity = lanesRepo.findAll(page_req);
		List<LaneResponse> laneResponse = new ArrayList<>();

		for (LanesEntity entity : laneEntity) {
			LaneResponse response = new LaneResponse();
			BeanUtils.copyProperties(entity, response);
			mapToLaneResponse(entity, response);
			response.setVolume(entity.getVolume());
			laneResponse.add(response);
		}
		return laneResponse;

	}

	
	/* (non-Javadoc)
	 * @see com.bid.smc.service.LanesService#getAllLanesByBid(java.lang.Integer, org.springframework.data.domain.Pageable)
	 * @Get all lanes with pagination and sorting by using Bid Id.
	 * 
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public LanePaginationResponse getAllLanesByBid(Integer bidId, Pageable pageable) {
		List<LanesEntity> laneEntity = lanesRepo.getAllBidByShipper(bidId, pageable);
		Integer count = lanesRepo.getAllLanesByBidCount(bidId);
		
		List<LaneResponse> laneResponses = new ArrayList<>();
		for (LanesEntity entity : laneEntity) {
			
			LaneResponse response = new LaneResponse();
			BeanUtils.copyProperties(entity, response);
			mapToLaneResponse(entity, response);
			laneResponses.add(response);
			
		/*	LocationResponse locationOriginResponse = getOriginDetailsByLocationId(locationId) ;
			LocationResponse locationDestResponse = getOriginDetailsByLocationId(locationId) ;*/

		}
		
		return new LanePaginationResponse(laneResponses,count,null);
	}
	
	
	/**
	 * Get location list based on locationId
	 * @param locationId
	 * @return
	 */
	public LocationResponse getOriginDetailsByLocationId(int locationId) {
		 LocationResponse locationResponse = new LocationResponse();
		 locationResponse.setState("New York");
		if (locationId != 0 && locationId == 1) {
			locationResponse.setCityCode(34);
			locationResponse.setCityName("Florence");
			locationResponse.setCountryCode("US");
			locationResponse.setPostalCode("35633");
			locationResponse.setLocationId(1);
		} else if (locationId != 0 && locationId == 2) {
			locationResponse.setCityCode(34);
			locationResponse.setCityName("Florence");
			locationResponse.setCountryCode("US");
			locationResponse.setPostalCode("35633");
			locationResponse.setLocationId(2);
		} else if (locationId != 0 && locationId == 3) {
			locationResponse.setCityCode(34);
			locationResponse.setCityName("Tampa");
			locationResponse.setCountryCode("US");
			locationResponse.setPostalCode("33615");
			locationResponse.setLocationId(3);
		} else if (locationId != 0 && locationId == 4) {
			locationResponse.setCityCode(34);
			locationResponse.setCityName("Tampa");
			locationResponse.setCountryCode("US");
			locationResponse.setPostalCode("33612");
			locationResponse.setLocationId(4);
		}
		return locationResponse;
	}
	
	

	/**
	 * @param entity
	 * @param response
	 */
	private void mapToLaneResponse(LanesEntity entity, LaneResponse response) {
		ModeEntity modeEntity = modeRepo.findOne(entity.getMode());
		
		if (modeEntity != null)
		response.setMode(modeEntity.getModes());
		
		EquipmentEntity equipEntity = equipmentRepo.findOne(entity.getEquipment());
		response.setEquipmentClass(equipEntity.getEquipment());
		
		EquipmentEntity equipType = equipmentRepo.findOne(entity.getEquipmentType());
		response.setEquipmentType(equipType.getEquipment());
		
		EquipmentLengthEntity equipmentLengthEntity = equipmentLengthRepo.findOne(entity.getEquipmentLength());
		response.setEquipmentLength(equipmentLengthEntity.getEquipmentLength());
		
		response.setBidId(entity.getBid().getBidId());
		response.setModeId(entity.getMode());
		if (equipEntity.getParentId() != null) {
			response.setEquipmentClassId(equipEntity.getParentId().getEquipmentId());
		}
		response.setEquipmentLengthId(equipmentLengthEntity.getEquipmentLengthId());
		response.setEquipmentTypeId(equipType.getEquipmentId());
	}
	
	
	
	private void mapLaneResponse(LanesEntity entity, LanesHistoryResponse response) {
		ModeEntity modeEntity = modeRepo.findOne(entity.getMode());
		
		if (modeEntity != null)
		response.setMode(modeEntity.getModes());
		
		EquipmentEntity equipEntity = equipmentRepo.findOne(entity.getEquipment());
		response.setEquipmentClass(equipEntity.getEquipment());
		
		EquipmentEntity equipType = equipmentRepo.findOne(entity.getEquipmentType());
		response.setEquipmentType(equipType.getEquipment());
		
		EquipmentLengthEntity equipmentLengthEntity = equipmentLengthRepo.findOne(entity.getEquipmentLength());
		response.setEquipmentLength(equipmentLengthEntity.getEquipmentLength());
		
		response.setBidId(entity.getBid().getBidId());
		response.setModeId(entity.getMode());
		if (equipEntity.getParentId() != null) {
			response.setEquipmentClassId(equipEntity.getParentId().getEquipmentId());
		}
		response.setEquipmentLengthId(equipmentLengthEntity.getEquipmentLengthId());
		response.setEquipmentTypeId(equipType.getEquipmentId());
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.LanesService#deleteLanes(java.lang.Integer)
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public ResponseEntity<?> deleteLanes(Integer bidId) {

		BaseResponse response = new BaseResponse();
		List<Integer> listOfLaneIds = lanesRepo.findLaneByBidId(bidId);

		if (CollectionUtils.isEmpty(listOfLaneIds)) {
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			response.setErrorMessage("NOT_FOUND");
			response.setStatus(409);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		} else {
			lanesRepo.deleteLaneByBidId(bidId);
			response.setStatus(200);
			response.setText("Bid successfully Deleted");
			response.setErrorMessage("Success");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.LanesService#updateLane(com.bid.smc.request.LaneRequest, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public LaneResponse updateLane(LaneRequest request, Integer bidId, Integer laneId) throws ParseException {

		LanesEntity lane = lanesRepo.findOne(laneId);
		BeanUtils.copyProperties(request, lane);

		LanesEntity lanesEntity = lanesRepo.save(lane);
		LaneResponse response = new LaneResponse();
		BeanUtils.copyProperties(lanesEntity, response);

		response.setBidId(lane.getBid().getBidId());
		EquipmentLengthEntity entity = equipmentLengthRepo.findOne(request.getEquipmentLength());
		response.setEquipmentLength(entity.getEquipmentLength());
		ModeEntity modeEntity = modeRepo.findOne(request.getMode());
		response.setMode(modeEntity.getModes());
		EquipmentEntity equipEntity = equipmentRepo.findOne(request.getEquipmentClass());
		response.setEquipmentClass(equipEntity.getEquipment());
		EquipmentEntity equipType = equipmentRepo.findOne(request.getEquipmentType());
		response.setEquipmentType(equipType.getEquipment());
		return response;
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.LanesService#deleteLane(java.lang.Integer)
	 * @DELETE lanes using the lane Id.
	 */
	@Override
	public ResponseEntity<?> deleteLane(Integer laneId) {

		BaseResponse response = new BaseResponse();
		
		LanesEntity lanesEntity = lanesRepo.findOne(laneId);
		if (lanesEntity==null) {
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			response.setErrorMessage("NOT_FOUND");
			response.setStatus(409);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		} else {
			lanesRepo.deleteLaneByLaneId(laneId);
			response.setStatus(200);
			response.setText("Bid successfully Deleted");
			response.setErrorMessage("Success");
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.LanesService#submitBid(java.lang.Integer)
	 * submitting the bid.
	 */
	@Override
	public ReviewBidResponse reviewBid(Integer bidId) {
		BaseResponse response = new BaseResponse();
		ReviewBidResponse review = new ReviewBidResponse();
		List<LanesEntity> lanesEntities = lanesRepo.findLanesByBidId(bidId);
		List<Integer> carrierEntities = inviteRepo.carrierCompanyId(bidId);
		BidEntity bidEntity = bidRepo.findOne(bidId);
		if(bidEntity==null && lanesEntities.isEmpty() && carrierEntities.isEmpty()){
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			response.setErrorMessage("NOT_FOUND");
			response.setStatus(404);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}
		review.setBidName(bidEntity.getBidName());
		review.setNickName(bidEntity.getBidNickName());
		review.setBidResponseStartTime(bidEntity.getBidResponseStartDate());
		review.setBidResponseEndTime(bidEntity.getBidResponseEndDate());
		review.setBidTermStartTime(bidEntity.getBidTermStartDate());
		review.setBidTermEndTime(bidEntity.getBidTermEndDate());
		review.setNoOfLanes(lanesEntities.size());
		review.setNoOfCarrier(carrierEntities.size());
		
		if (!CollectionUtils.isEmpty(lanesEntities) && !CollectionUtils.isEmpty(carrierEntities)) {
			bidEntity.setStatus("Ready to submit");
			bidRepo.saveAndFlush(bidEntity);
		}
		return review;
	}

	@Override
	public LaneResponse getLanesByLaneId(Integer bidId, Integer laneId) {

		LanesEntity laneEntity = lanesRepo.getLanesByLanesId(bidId, laneId);        
		LaneResponse response=new LaneResponse();
		BeanUtils.copyProperties(laneEntity, response);
		mapToLane(laneEntity, response);
		return response;
	}

	private void mapToLane(LanesEntity entity, LaneResponse response) {
		ModeEntity modeEntity = modeRepo.findOne(entity.getMode());
		response.setMode(Integer.toString(modeEntity.getModeId()));
		EquipmentEntity equipEntity = equipmentRepo.findOne(entity.getEquipment());
		response.setEquipmentClass(Integer.toString(equipEntity.getEquipmentId()));
		EquipmentEntity equipType = equipmentRepo.findOne(entity.getEquipmentType());
		response.setEquipmentType(Integer.toString(equipType.getEquipmentId()));
		EquipmentLengthEntity equipmentLengthEntity = equipmentLengthRepo.findOne(entity.getEquipmentLength());
		response.setEquipmentLength(Integer.toString(equipmentLengthEntity.getEquipmentLengthId()));
		response.setBidId(entity.getBid().getBidId());
	}

	

	@Override
	public CarrierHistoryLanePaginationResponse getAllLanesByBid(Integer carrierId, Integer bidId, Pageable pageable) {
		List<Object[]> laneEntity = lanesRepo.getAllLanesByBid(carrierId, bidId, pageable);
		Integer count = lanesRepo.getAllLanesByBidCount(carrierId, bidId);
		List<LanesHistoryResponse> laneResponse = new ArrayList<>();
	
		for (Object[] entitities : laneEntity) {
			LanesHistoryResponse response = new LanesHistoryResponse();
			BeanUtils.copyProperties(entitities[0], response);
			mapLaneResponse((LanesEntity) entitities[0], response);

			BidResponseStatusEntity bidStatus = (BidResponseStatusEntity) entitities[1];
			response.setStatus(bidStatus.getStatus());
			response.setDays(String.valueOf(bidStatus.getBidResponseDetails().getDays()));
			response.setRate(String.valueOf(bidStatus.getBidResponseDetails().getRate()));
			response.setNotes(bidStatus.getBidResponseDetails().getNotes());
			laneResponse.add(response);
		}

		return new CarrierHistoryLanePaginationResponse(laneResponse, count, null);
	}

	}
