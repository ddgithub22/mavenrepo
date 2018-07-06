package com.bid.smc.service;

import java.util.List;

import com.bid.smc.response.EquipmentLengthResponse;

public interface EquipmentLengthService {
	
	List<EquipmentLengthResponse> findAllEquipmentLength();

	EquipmentLengthResponse getEquipmentById(Integer id);
}
