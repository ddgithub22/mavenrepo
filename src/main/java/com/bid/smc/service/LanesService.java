package com.bid.smc.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.bid.smc.request.LaneRequest;
import com.bid.smc.response.CarrierHistoryLanePaginationResponse;
import com.bid.smc.response.LanePaginationResponse;
import com.bid.smc.response.LaneResponse;
import com.bid.smc.response.ReviewBidResponse;

public interface LanesService {
	
	LaneResponse saveLane(LaneRequest request);
	
	List<LaneResponse> getAllLanes(PageRequest page_req);
	
	LanePaginationResponse getAllLanesByBid(Integer bidId ,Pageable pageable );
	
	ResponseEntity<?> deleteLanes(Integer bidId);
	
	LaneResponse updateLane(LaneRequest request, Integer bidId,Integer laneId) throws ParseException;
	
	ResponseEntity<?> deleteLane(Integer laneId);
	
	LaneResponse getLanesByLaneId(Integer bidId, Integer laneId);
	
	ReviewBidResponse reviewBid(Integer bidId);
	
	


	CarrierHistoryLanePaginationResponse getAllLanesByBid(Integer carrierId, Integer bidId, Pageable pageable);
	
	
}
