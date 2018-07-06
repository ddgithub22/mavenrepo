package com.bid.smc.repo.bidsense;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.bidsense.ShipperEntity;

/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface ShipperRepository extends JpaRepository<ShipperEntity, Integer> {
	
	/*@Query("Select c.CompanyName, s.EINNumber, l , u from company c inner join shippertl s inner join location l inner join user u group by c.CompanyName")
	List<Object> findAllShipper();*/
	
	/**
	 * @param einNumber
	 * @return
	 */
	@Query("select s.id from ShipperEntity s where s.einNumber=:einNumber")
	Integer findEinNumber(@Param("einNumber")String einNumber);
	
	/**
	 * @param shipperId
	 * @return
	 */
	@Query("Select s from ShipperEntity s where s.id=:shipperId")
	ShipperEntity findShipperId(@Param("shipperId") Integer shipperId);
	
	/**
	 * @param carrierId
	 * @return
	 */
	@Query("Select r from ShipperEntity r join r.carriers s where s.companyId=:carrierId")
	ShipperEntity getShipperByCarrierId(@Param("carrierId") Integer carrierId);

}
