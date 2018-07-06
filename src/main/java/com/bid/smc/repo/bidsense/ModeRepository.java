package com.bid.smc.repo.bidsense;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.ModeEntity;

@Repository
@Transactional
public interface ModeRepository extends JpaRepository<ModeEntity, Integer>{
 
	
	@Query("select m from ModeEntity m where m.modes=:modeName")
	ModeEntity getModeByName(@Param("modeName") String modeName); 
}