package com.bid.smc.model.bidsense;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "equipment")
@Transactional("bidsenseTransactionManager")
public class EquipmentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EquipmentId")
	private Integer equipmentId;

	@Column(name = "Equipment")
	private String equipment;

    @JoinColumn(name="parent_id")
	@ManyToOne(cascade = { CascadeType.ALL })
    @JsonIgnore
	private EquipmentEntity parentId;

    @OneToMany(mappedBy="parentId",fetch=FetchType.EAGER)
    private Set<EquipmentEntity> equipmentEntities;
    



	@OneToMany(mappedBy = "equipment",fetch=FetchType.EAGER)
	private List<EquipmentLengthEntity> equipmentLength;

	public List<EquipmentLengthEntity> getEquipmentLength() {
		return equipmentLength;
	}

	public void setEquipmentLength(List<EquipmentLengthEntity> equipmentLength) {
		this.equipmentLength = equipmentLength;
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

	public EquipmentEntity getParentId() {
		return parentId;
	}

	public void setParentId(EquipmentEntity parentId) {
		this.parentId = parentId;
	}

	public Set<EquipmentEntity> getEquipmentEntities() {
		return equipmentEntities;
	}

	public void setEquipmentEntities(Set<EquipmentEntity> equipmentEntities) {
		this.equipmentEntities = equipmentEntities;
	}


}
