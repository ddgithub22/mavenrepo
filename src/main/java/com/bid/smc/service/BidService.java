package com.bid.smc.service;

import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.bid.smc.request.BidRequest;
import com.bid.smc.request.InviteCarrierRequest;
import com.bid.smc.request.MailRequest;
import com.bid.smc.request.ResponseBidRequest;
import com.bid.smc.response.BidDetailsResponse;
import com.bid.smc.response.BidPaginationResponse;
import com.bid.smc.response.BidResponse;
import com.bid.smc.response.CarrierHistPaginationResponse;
import com.bid.smc.response.CarrierLanesResponse;
import com.bid.smc.response.InviteCarrierResponse;
import com.bid.smc.response.ResponseBidResponse;
import com.bid.smc.response.ShipperBidHistoryPagResponse;
import com.bid.smc.response.ShipperTrackResponse;

public interface BidService {

	BidResponse saveBid(BidRequest request) throws ParseException;
	
	BidResponse updateBid(BidRequest request, Integer bidId) throws ParseException;
	
	List<BidResponse> getBidByShipper(Integer shipperId,Integer bidId);
	
	BidPaginationResponse getAllBidByShipper(Integer shipperId ,Pageable pageable );
	
	ResponseEntity<?> deleteBid(Integer bidId);
	
	List<BidResponse> findByName(String bidName);
	
	InviteCarrierResponse inviteCarrier(Integer bidId, InviteCarrierRequest request);
	
	void saveUploadedDetails(Integer bidId, MultipartFile[] uploadfiles);
	
	InviteCarrierResponse invitedCarrier(Integer bidId);
	
	void sendMail(Integer bidId, MailRequest message) throws MessagingException, ParseException;
	
	CarrierLanesResponse carrierLanes(Integer bidId);
	
	ResponseBidResponse bidResponse(Integer bidId, Integer laneId, ResponseBidRequest request);

	CarrierHistPaginationResponse getAllBidsByCarrier(Integer carrierId, Pageable pageable);
	
	ResponseBidResponse bidReject(Integer bidId, Integer laneId);
	
	BidDetailsResponse bidDetaileResponse(Integer bidId);
	
	ShipperBidHistoryPagResponse getBidsByShipper(Integer shipperId, Pageable pageable);
	
	ShipperTrackResponse getShipperTrack(Integer shipperId, Pageable pageable);
	
}
