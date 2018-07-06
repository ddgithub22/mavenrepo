package com.bid.smc.repo.bidsense;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.CarrierEntity;

/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface CarrierRepository extends JpaRepository<CarrierEntity, Integer>{
	
	/**
	 * @param mcNo
	 * @return
	 */
	@Query("Select b.id from CarrierEntity b where b.mcNumber=:mcNo")
	Integer findMcNumber(@Param("mcNo")String mcNo);
	
	/**
	 * @param dotNo
	 * @return
	 */
	@Query("Select b.id from CarrierEntity b where b.dotNumber=:dotNo")
	Integer findDotNumber(@Param("dotNo")String dotNo);
	
	/**
	 * @param contactEmail
	 * @return
	 */
	@Query("select u from CarrierEntity u where u.contactEmail=:contactEmail")
	CarrierEntity findByEmail(@Param("contactEmail")String contactEmail);
	
	/**
	 * @param carrierId
	 * @return
	 */
	@Query("Select c from CarrierEntity c where c.id=:carrierId")
	CarrierEntity findCarrierId(@Param("carrierId") Integer carrierId);
	
	/**
	 * @param shipperId
	 * @param pageable
	 * @return
	 */
	@Query("select t from CarrierEntity t join t.shipper s where s.companyId = :shipperId") 
	List<CarrierEntity> findCarrier(@Param("shipperId") Integer shipperId,  Pageable pageable);
	
	 
	/**
	 * @param shipperId
	 * @return
	 */
	@Query("Select count(r) from CarrierEntity r join r.shipper s where s.companyId=:shipperId")
	Integer getAllCarrierByShipperCount(@Param("shipperId") Integer shipperId);

	 
	
}
