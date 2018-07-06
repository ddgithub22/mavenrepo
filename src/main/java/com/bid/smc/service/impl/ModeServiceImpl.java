package com.bid.smc.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid.smc.model.bidsense.ModeEntity;
import com.bid.smc.repo.bidsense.ModeRepository;
import com.bid.smc.service.ModeService;

@Service
@Transactional
public class ModeServiceImpl implements ModeService {
	
	@Autowired
	private ModeRepository modeRepo;

	/***
	 * @GET request
	 * @return all Mode Names from mode table.
	 */
	@Override
	public List<ModeEntity> findAllModes() {
		List<ModeEntity> entity = modeRepo.findAll();
		return entity;
	}

}
