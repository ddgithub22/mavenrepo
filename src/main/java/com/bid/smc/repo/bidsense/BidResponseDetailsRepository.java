package com.bid.smc.repo.bidsense;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.BidResponseDetailsEntity;

/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface BidResponseDetailsRepository extends JpaRepository<BidResponseDetailsEntity, Integer> {

}
