package com.bid.smc.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid.smc.model.bidsense.RateEntity;
import com.bid.smc.repo.bidsense.RateRepository;
import com.bid.smc.service.RateService;

@Service
@Transactional
public class RateServiceImpl implements RateService {
	@Autowired
	private RateRepository rateRepo;
	 /***
     * @GET request
     * @return all Rates from rate table.
     */
	@Override
	public List<RateEntity> findAllRate() {
		List<RateEntity> response = rateRepo.findAll();
		return response;
	}

	@Override
	public RateEntity getRateById(Integer rateId) {
		RateEntity entity = rateRepo.getOne(rateId);
		return entity;
	}
	
	

}
