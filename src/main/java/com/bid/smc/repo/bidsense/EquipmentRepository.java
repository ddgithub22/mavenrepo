package com.bid.smc.repo.bidsense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.EquipmentEntity;


@Repository
@Transactional
public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Integer> {

	@Query("select e from EquipmentEntity e where e.parentId is null")
	List<EquipmentEntity> getAllEquipment();
	
	@Query("select e from EquipmentEntity e where e.equipment=:equipmentName and parentId is not null")
	EquipmentEntity getEquipmentByName(@Param("equipmentName")String equipmentName);
}
