package com.bid.smc.service;

import com.bid.smc.request.Waypoints;
import com.bid.smc.response.AddressResponse;
import com.bid.smc.response.PTVAddressResponse;

public interface PTVService {

	PTVAddressResponse getOriginDetails(String searchText);

	AddressResponse findAddressByText(String searchText);

	Object calculateDistance(Waypoints[] waypoints);
	
	
	
}
