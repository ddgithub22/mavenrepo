package com.bid.smc.model.bidsense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name="equipmentlength")
@Transactional("bidsenseTransactionManager")
public class EquipmentLengthEntity {
	
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="EquipmentLengthId")
	private Integer equipmentLengthId;
    
    @Column(name="equipmentLength")
	private String equipmentLength;
    
    @ManyToOne 
    @JoinColumn(name="EquipmentId")
    private EquipmentEntity equipment;
    
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
	
	public EquipmentEntity getEquipment() {
		return equipment;
	}
	public void setEquipment(EquipmentEntity equipment) {
		this.equipment = equipment;
	}
   
}
