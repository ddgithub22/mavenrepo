package com.bid.smc.repo.bidsense;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.bid.smc.model.bidsense.BidEntity;
import com.bid.smc.model.bidsense.LanesEntity;

/**
 * @author chandan.thakur
 *
 */
public interface BidRepository extends JpaRepository<BidEntity, Integer> {
	
	/**
	 * @param companyId
	 * @param bidId
	 * @return
	 */
	@Query("Select r from BidEntity r where r.companyId=:companyId and r.bidId=:bidId")
	List<BidEntity> getBidByShipper(@Param("companyId")Integer companyId,@Param("bidId")Integer bidId);
	
	/**
	 * @param companyId
	 * @param pageable
	 * @return
	 */
	@Transactional("bidsenseTransactionManager")
	@Query("Select r from BidEntity r where r.companyId=:companyId") 
	List<BidEntity> getAllBidByShipper(@Param("companyId")Integer companyId,Pageable pageable);
	
	/**
	 * @param companyId
	 * @return
	 */
	@Transactional("bidsenseTransactionManager")
	@Query("Select count(r) from BidEntity r where r.companyId=:companyId") 
	Integer getAllBidByShipperCount(@Param("companyId")Integer companyId);
	
	/**
	 * @param bidName
	 * @param shipperId
	 * @return
	 */
	@Query("select r from BidEntity r where r.bidName=:bidName and r.companyId=:shipperId")
	BidEntity findBidName(@Param("bidName")String bidName,@Param("shipperId")Integer shipperId);
	
	/**
	 * @param bidId
	 * @return
	 */
	@Query("Select r.bidId from BidEntity r where r.bidId=:bidId ")
	Integer getBid(@Param("bidId")Integer bidId);
	
	/**
	 * @param bidName
	 * @return
	 */
	@Transactional("bidsenseTransactionManager")
	@Query("Select r from BidEntity r where r.bidName=:bidName") 
	List<BidEntity> getAllBidByName(@Param("bidName")String bidName);
	
	/**
	 * @param bidId
	 * @return
	 */
	@Query("Select l from LanesEntity l where l.bid.bidId=:bidId")
	List<LanesEntity> findLaneBybidId(@Param("bidId")Integer bidId);

	/*@Query("Select b from BidEntity b,InviteCarrierEntity i,com.bid.smc.model.adminmanager.CompanyEntity c where b.bidId=i.bidId and b.companyId=c.iCompanyId and i.carrier.companyId=:carrierId") 
	List<BidEntity> getAllBidByCarrier(@Param("carrierId")Integer carrierId,Pageable pageable);*/
	
	/**
	 * @param carrierId
	 * @param pageable
	 * @return
	 */
	@Query("Select b from BidEntity b,InviteCarrierEntity i where b.bidId=i.bidId and i.carrier.companyId=:carrierId and (b.bidResponseEndDate< CURRENT_DATE OR b.status= 'awarded')") 
	List<BidEntity> getAllBidByCarrier(@Param("carrierId")Integer carrierId,Pageable pageable);

	/**
	 * @param carrierId
	 * @return
	 */
	@Query("Select count(b) from BidEntity b,InviteCarrierEntity i where b.bidId=i.bidId and i.carrier.companyId=:carrierId and (b.bidResponseEndDate< CURRENT_DATE OR b.status= 'awarded')") 
	Integer getAllBidByCarrierCount(@Param("carrierId")Integer carrierId);

	/**
	 * @param shipperId
	 * @param pageable
	 * @return
	 */
	@Query("select b from BidEntity b where b.companyId=:shipperId and b.bidTermEndDate< CURRENT_DATE")
	List<BidEntity> getAllBidsByShipperId(@Param("shipperId")Integer shipperId, Pageable pageable);

	/**
	 * @param shipperId
	 * @return
	 */
	@Query("select count(b)from BidEntity b where b.companyId=:shipperId ")
	Integer getAllBidsByshipperCount(@Param("shipperId")Integer shipperId);
	

	
}
