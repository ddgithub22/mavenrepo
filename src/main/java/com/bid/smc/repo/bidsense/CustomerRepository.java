package com.bid.smc.repo.bidsense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bid.smc.model.bidsense.CustomerEntity;

/**
 * @author chandan.thakur
 *
 */
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
	
	/**
	 * @param name
	 * @return
	 */
	@Query("select c.customerId from CustomerEntity c where c.name=:name")
	Integer findCustomerName(@Param("name")String name);

	/**
	 * @param id
	 * @return
	 */
	@Query("select c from CustomerEntity c where c.customerId=:id")
	CustomerEntity findCustomerById(@Param("id")Integer id);
}
