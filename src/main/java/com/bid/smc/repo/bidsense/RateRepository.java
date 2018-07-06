package com.bid.smc.repo.bidsense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.RateEntity;


@Repository
@Transactional
public interface RateRepository extends JpaRepository<RateEntity, Integer>{
	List<RateEntity> findAll();
}
