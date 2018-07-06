package com.bid.smc.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.smc.model.bidsense.EquipmentLengthEntity;
import com.bid.smc.repo.bidsense.EquipmentLengthRepository;
import com.bid.smc.response.EquipmentLengthResponse;
import com.bid.smc.response.EquipmentResponse;
import com.bid.smc.service.EquipmentLengthService;

@Service
@Transactional
public class EquipmentLengthServiceImpl implements EquipmentLengthService {
	
	@Autowired
	private EquipmentLengthRepository equipment;

	/***
	 * @GET request
	 * @return all Equipment length values from add Lanes Input form page.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public List<EquipmentLengthResponse> findAllEquipmentLength() {
		List<EquipmentLengthEntity> entity = equipment.findAllEquipmentLength();
		// List<EquipmentLengthEntity> entity = equipment.findAll();

		List<EquipmentLengthResponse> equipResponse = new ArrayList<>();
		EquipmentLengthResponse response = null;
		for (EquipmentLengthEntity entities : entity) {

			response = new EquipmentLengthResponse();
			BeanUtils.copyProperties(entities, response);

			EquipmentResponse equipmentResponse = new EquipmentResponse();
			BeanUtils.copyProperties(entities.getEquipment(), equipmentResponse);

			response.setEquipment(equipmentResponse);

			equipResponse.add(response);
		}
		return equipResponse;
	}

	/***
	 * 
	 * @GET request
	 * @return Equipment Length for the corresponding Id.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public EquipmentLengthResponse getEquipmentById(Integer id) {
		EquipmentLengthEntity entity = equipment.findEquipmentLength(id);
		
		EquipmentLengthResponse response = new EquipmentLengthResponse();
		BeanUtils.copyProperties(entity, response);
		
		EquipmentResponse equipmentResponse = new EquipmentResponse();
		BeanUtils.copyProperties(entity.getEquipment(), equipmentResponse);

		response.setEquipment(equipmentResponse);
		return response;
	}

}
