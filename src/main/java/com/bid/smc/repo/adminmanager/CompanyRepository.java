package com.bid.smc.repo.adminmanager;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.adminmanager.CompanyEntity;

/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Integer> {

	/**
	 * @param companyName
	 * @return
	 */
	@Query("SELECT c From CompanyEntity c WHERE c.sCompanyName=:companyName")
	List<CompanyEntity> findByCompanyName(@Param("companyName")String companyName);

	/**
	 * @param iCompanyId
	 * @return
	 */
	@Query("SELECT c.sCompanyName From CompanyEntity c WHERE c.iCompanyId=:iCompanyId")
	String getShipperNameByCompanyId(@Param("iCompanyId")Integer iCompanyId);
}
