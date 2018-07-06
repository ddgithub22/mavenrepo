package com.bid.smc.repo.bidsense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.EquipmentLengthEntity;

@Repository
@Transactional
public interface EquipmentLengthRepository extends JpaRepository<EquipmentLengthEntity, Integer>{

	@Query("Select e from EquipmentLengthEntity e where e.equipmentLengthId=:equipmentLengthId")
	EquipmentLengthEntity findEquipmentLength(@Param("equipmentLengthId") Integer equipmentLengthId);
	
	@Query("select e from EquipmentLengthEntity e")
	List<EquipmentLengthEntity> findAllEquipmentLength();
	
	@Query("select e from EquipmentLengthEntity e where e.equipmentLength=:equipmentLength and e.equipment.equipmentId=:equipment")
	EquipmentLengthEntity getEquipmentByLength(@Param("equipmentLength")String equipmentLength,@Param("equipment")Integer equipment);
	
}
