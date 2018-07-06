package com.bid.smc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bid.smc.request.ShipperRequest;
import com.bid.smc.response.ShipperResponse;

@Service
public interface ShipperService {
	
	ShipperResponse saveShipper(ShipperRequest request);
	 
	 List<ShipperResponse> getAllShipper();
	 
	 ShipperResponse getShipperById(Integer id);

	

}
