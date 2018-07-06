package com.bid.smc.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid.smc.model.bidsense.EquipmentEntity;
import com.bid.smc.model.bidsense.EquipmentLengthEntity;
import com.bid.smc.repo.bidsense.EquipmentRepository;
import com.bid.smc.response.EquipmentLengthResponse;
import com.bid.smc.response.EquipmentResponse;
import com.bid.smc.service.EquipmentService;

@Service
@Transactional
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	private EquipmentRepository equiprepo;

	/***
	 * @Get request
	 * @return all Equipment details.
	 */
	@Override
	public List<EquipmentResponse> findAllEquipment() {
		List<EquipmentEntity> entity = equiprepo.getAllEquipment();

		List<EquipmentResponse> equipmentEntities  = new ArrayList<>();
		for (EquipmentEntity entities : entity) {

			EquipmentResponse equipmentResponse = new EquipmentResponse();
			BeanUtils.copyProperties(entities, equipmentResponse);
			
			Set<EquipmentResponse> set = new HashSet<>();
			
			for(EquipmentEntity equipmentEntity: entities.getEquipmentEntities()){
				EquipmentResponse response = new EquipmentResponse();
				BeanUtils.copyProperties(equipmentEntity, response);
				set.add(response);
			}
			
			List<EquipmentLengthResponse> equipmentLengthEntities  = new ArrayList<>();

			for (EquipmentLengthEntity response : entities.getEquipmentLength()) {
				
				EquipmentLengthResponse equipmentLengthResponse = new EquipmentLengthResponse();
				BeanUtils.copyProperties(response, equipmentLengthResponse);
				equipmentLengthEntities.add(equipmentLengthResponse);
			}
			equipmentResponse.setEquipmentLengthResponses(equipmentLengthEntities);

			equipmentEntities.add(equipmentResponse);
		}
		return equipmentEntities;
	}

}
