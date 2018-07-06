package com.bid.smc.response;

public class EquipmentLengthResponse {
	
	private Integer equipmentLengthId;
	private String equipmentLength;
	private EquipmentResponse equipment;
	
	public EquipmentResponse getEquipment() {
		return equipment;
	}
	public void setEquipment(EquipmentResponse equipment) {
		this.equipment = equipment;
	}
	public Integer getEquipmentLengthId() {
		return equipmentLengthId;
	}
	public void setEquipmentLengthId(Integer equipmentLengthId) {
		this.equipmentLengthId = equipmentLengthId;
	}
	public String getEquipmentLength() {
		return equipmentLength;
	}
	public void setEquipmentLength(String equipmentLength) {
		this.equipmentLength = equipmentLength;
	}
    
	
}
