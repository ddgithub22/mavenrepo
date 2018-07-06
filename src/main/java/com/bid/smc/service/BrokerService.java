package com.bid.smc.service;

import java.util.List;

import com.bid.smc.model.bidsense.BrokerEntity;
import com.bid.smc.request.BrokerRequest;
import com.bid.smc.response.BrokerResponse;

public interface BrokerService {
    BrokerResponse saveBroker(BrokerRequest request);
    List<BrokerEntity> getAllBroker();
	 
    BrokerEntity getBrokerById(Integer Id);
}
