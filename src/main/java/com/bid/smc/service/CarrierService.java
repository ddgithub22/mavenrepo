package com.bid.smc.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.bid.smc.model.bidsense.CarrierEntity;
import com.bid.smc.request.AddCarrierRequest;
import com.bid.smc.request.CarrierRequest;
import com.bid.smc.request.MessageRequest;
import com.bid.smc.request.StatusRequest;
import com.bid.smc.response.AddCarrierResponse;
import com.bid.smc.response.CarrierPaginationResponse;
import com.bid.smc.response.CarrierResponse;
import com.bid.smc.response.InvitedCarrierResponse;
import com.bid.smc.response.MessageResponse;


public interface CarrierService {
	
	CarrierResponse saveCarrier(CarrierRequest request);
	
	List<CarrierResponse> getAllCarrier();

	AddCarrierResponse saveCarrierByShipper(Integer shipperId, AddCarrierRequest request);
    
	CarrierPaginationResponse getCarrierByShipper(Integer shipperId , Pageable pageable);
	
	CarrierEntity getCarrierById(Integer id);
	
	List<CarrierResponse> findByName(Integer shipperId, String carrierName);

	CarrierPaginationResponse searchCarrier(Integer shipperId,String searchC,Pageable pageable, String sortBy);

	MessageResponse saveMessage(MessageRequest request);

	MessageResponse updateMessage(MessageRequest request,Integer messageId);
	
	List<InvitedCarrierResponse> getBidByCarrierId(Integer id);

	List<MessageResponse> getAllMessages(Integer bidId);
	
	void bidResponse(Integer bidId,StatusRequest request);
}
