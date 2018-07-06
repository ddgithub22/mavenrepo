package com.bid.smc.repo.bidsense;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.BidResponseStatusEntity;

/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface BidResponseStatusRepository extends JpaRepository<BidResponseStatusEntity, Integer> {
	
	/**
	 * @param bidId
	 * @param laneId
	 * @return
	 */
	@Query("select brs from BidResponseStatusEntity brs where brs.bidId=:bidId and brs.laneId=:laneId")
	BidResponseStatusEntity findLanesDatils(@Param("bidId")Integer bidId,@Param("laneId")Integer laneId);

	@Query("select brs from BidResponseStatusEntity brs where brs.bidId=:bidId and brs.laneId=:laneId and brs.company=:companyId")
	BidResponseStatusEntity findByBidIdAndLaneId(@Param("bidId")Integer bidId,@Param("laneId")Integer laneId,@Param("companyId")Integer companyId);
	
}
