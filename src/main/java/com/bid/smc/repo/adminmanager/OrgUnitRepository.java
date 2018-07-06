package com.bid.smc.repo.adminmanager;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.adminmanager.OrgUnitEntity;

/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface OrgUnitRepository extends JpaRepository<OrgUnitEntity, Integer> {

	
	/**
	 * @param companyId
	 * @return
	 */
	@Query("Select r from OrgUnitEntity r where r.companyId.iCompanyId=:companyId")
	OrgUnitEntity findByCompanyId(@Param("companyId")Integer companyId);

}
