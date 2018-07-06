package com.bid.smc.service;

import java.util.List;

import com.bid.smc.model.bidsense.RateEntity;

public interface RateService {
	List<RateEntity> findAllRate();
	
	RateEntity getRateById(Integer rateId);
}
