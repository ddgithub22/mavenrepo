package com.bid.smc.repo.bidsense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.InviteCarrierEntity;

/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface InviteCarrierRepository extends JpaRepository<InviteCarrierEntity, Integer>{

	/**
	 * @param bidId
	 * @return
	 */
	@Query("select ice.carrier.companyId from InviteCarrierEntity ice where ice.bidId.bidId=:bidId")
	List<Integer> carrierCompanyId(@Param("bidId")Integer bidId);
		
	/**
	 * @param bidId
	 * @return
	 */
	@Query("select ie from InviteCarrierEntity ie where ie.carrier.companyId in (select ice.carrier.companyId from InviteCarrierEntity ice where ice.bidId.bidId=:bidId)")
	List<InviteCarrierEntity> findCarrierByBidId(@Param("bidId") Integer bidId);
	
	/**
	 * @param bidId
	 * @return
	 */
	@Query("select ice from InviteCarrierEntity ice where ice.bidId.bidId=:bidId")
	List<InviteCarrierEntity> findInvitedCarrierByBidId(@Param("bidId") Integer bidId);
	
	/**
	 * @param carrierId
	 * @return
	 */
	@Query("select ice from InviteCarrierEntity ice where ice.carrier.companyId=:carrierId")
	List<InviteCarrierEntity> getEntityByCarrierId(@Param("carrierId")Integer carrierId);
	
	/**
	 * @param bidId
	 * @param carrierId
	 * @return
	 */
	@Query("select ice from InviteCarrierEntity ice where ice.bidId.bidId=:bidId and ice.carrier.companyId=:carrierId")
	InviteCarrierEntity findInvitation(@Param("bidId")Integer bidId, @Param("carrierId")Integer carrierId);

	
	@Query("select ie.carrier.contactEmail from InviteCarrierEntity ie where ie.carrier.companyId in (select ice.carrier.companyId from InviteCarrierEntity ice where ice.bidId.bidId=:bidId)")
	List<String> findEmailIdOfInvitedCarrierByBidId(@Param("bidId")Integer bidId);
	
}
