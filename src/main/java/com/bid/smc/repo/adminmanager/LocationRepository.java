package com.bid.smc.repo.adminmanager;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.adminmanager.LocationEntity;
/**
 * @author chandan.thakur
 *
 */
@Transactional
@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Integer>{

}
