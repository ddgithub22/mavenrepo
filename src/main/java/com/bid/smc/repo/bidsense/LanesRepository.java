package com.bid.smc.repo.bidsense;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bid.smc.model.bidsense.LanesEntity;

@Repository
public interface LanesRepository extends JpaRepository<LanesEntity, Integer> {

	/*
	 * @Query("Select r from BidEntity r") List<LanesEntity> getAllLanes();
	 */
	@Query("Select b.id from LanesEntity b where b.lane=:lane")
	Integer findLane(@Param("lane") String lane);

	@Transactional("bidsenseTransactionManager")
	@Query("Select r from LanesEntity r where r.bid.bidId=:bidId")
	List<LanesEntity> getAllBidByShipper(@Param("bidId") Integer bidId, Pageable pageable);

	@Transactional("bidsenseTransactionManager")
	@Query("Select count(r) from LanesEntity r where r.bid.bidId=:bidId")
	Integer getAllLanesByBidCount(@Param("bidId") Integer bidId);

	@Modifying
	@Query("Delete from LanesEntity r where r.bid.bidId=:bidId")
	void deleteLaneByBidId(@Param("bidId") Integer bidId);

	@Query("Select r.laneId from LanesEntity r where r.bid.bidId=:bidId")
	List<Integer> findLaneByBidId(@Param("bidId") Integer bidId);

	@Modifying
	@Transactional("bidsenseTransactionManager")
	@Query("Delete from LanesEntity r where r.laneId=:laneId")
	void deleteLaneByLaneId(@Param("laneId") Integer laneId);

	/*
	 * @Query("Select r.laneId from BidEntity r where r.bidId=:laneId") Integer
	 * getLane(@Param("laneId")Integer laneId);
	 */

	@Query("Select l from LanesEntity l where l.bid.bidId=:bidId")
	List<LanesEntity> findLanesByBidId(@Param("bidId") Integer bidId);

	@Query("select l from LanesEntity l where l.bid.bidId=:bidId AND l.laneId=:laneId")
	LanesEntity getLanesByLanesId(@Param("bidId") Integer bidId, @Param("laneId") Integer laneId);

	/*@Query("select DISTINCT l from LanesEntity l,InviteCarrierEntity i,BidEntity b where  b.bidId=l.bid.bidId  and i.carrier.companyId=:carrierId and l.bid.bidId=:bidId")
	List<LanesEntity> getAllLanesByBid(@Param("carrierId")Integer carrierId, @Param("bidId")Integer bidId, Pageable pageable);*/
	
	@Query("select DISTINCT l,bs from LanesEntity l,InviteCarrierEntity i,BidEntity b,BidResponseStatusEntity bs where   bs.bidId=b.bidId  and l.laneId=bs.laneId and b.bidId=l.bid.bidId  and i.carrier.companyId=:carrierId and l.bid.bidId=:bidId")
	List<Object[]> getAllLanesByBid(@Param("carrierId")Integer carrierId, @Param("bidId")Integer bidId, Pageable pageable);
    
	@Query("select count(DISTINCT l) from LanesEntity l,InviteCarrierEntity i,BidEntity b,BidResponseStatusEntity bs where   bs.bidId=b.bidId  and l.laneId=bs.laneId and b.bidId=l.bid.bidId  and i.carrier.companyId=:carrierId and l.bid.bidId=:bidId")
	Integer getAllLanesByBidCount(@Param("carrierId") Integer carrierId, @Param("bidId") Integer bidId);
	
	@Query("select l,bs from LanesEntity l,InviteCarrierEntity i,BidEntity b,BidResponseStatusEntity bs where bs.bidId=b.bidId and l.laneId=bs.laneId and b.bidId=l.bid.bidId and i.carrier.companyId=:carrierId and l.bid.bidId=:bidId")
	List<Object[]> getAllLanesByBidAndCarrierId(@Param("carrierId")Integer carrierId, @Param("bidId")Integer bidId);

}