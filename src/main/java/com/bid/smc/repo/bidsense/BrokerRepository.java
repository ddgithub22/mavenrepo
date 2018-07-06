package com.bid.smc.repo.bidsense;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.BrokerEntity;

/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface BrokerRepository extends JpaRepository<BrokerEntity, Integer>{
	
	/**
	 * @param mcNo
	 * @return
	 */
	@Query("Select b.id from BrokerEntity b where b.mcNumber=:mcNo")
	Integer findMcNumber(@Param("mcNo")String mcNo);

	/**
	 * @param brokerId
	 * @return
	 */
	@Query("Select b from BrokerEntity b where b.id=:brokerId")
	BrokerEntity findBrokerId(@Param("brokerId")Integer brokerId);
	
	
}
