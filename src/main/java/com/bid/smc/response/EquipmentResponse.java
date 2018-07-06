package com.bid.smc.response;

import java.util.List;
import java.util.Set;

public class EquipmentResponse {
	
	private Integer equipmentId;
	private String equipment;
    private Set<EquipmentResponse> equipmentEntities;
    private List<EquipmentLengthResponse> equipmentLengthResponses;
	
	
	public List<EquipmentLengthResponse> getEquipmentLengthResponses() {
		return equipmentLengthResponses;
	}
	public void setEquipmentLengthResponses(List<EquipmentLengthResponse> equipmentLengthResponses) {
		this.equipmentLengthResponses = equipmentLengthResponses;
	}
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public Set<EquipmentResponse> getEquipmentEntities() {
		return equipmentEntities;
	}
	public void setEquipmentEntities(Set<EquipmentResponse> equipmentEntities) {
		this.equipmentEntities = equipmentEntities;
	}

	
	
}
