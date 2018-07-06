package com.bid.smc.repo.bidsense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.BidDocumentEntity;


/**
 * @author chandan.thakur
 *
 */
@Repository
@Transactional
public interface BidDocumentRepository extends JpaRepository<BidDocumentEntity, Integer>{

	/**
	 * @param bidId
	 * @return
	 */
	@Query("Select r  from BidDocumentEntity r where r.bidEntity.bidId=:bidId ")
	List<BidDocumentEntity> findByBidId(@Param("bidId")Integer bidId);
}
