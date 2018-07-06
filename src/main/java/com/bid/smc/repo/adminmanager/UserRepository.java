package com.bid.smc.repo.adminmanager;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bid.smc.model.adminmanager.UserEntity;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	
	@Query("select u from UserEntity u where u.emailAddress=:emailId")
	UserEntity findByEmail(@Param("emailId")String emailId);

	@Modifying
	@Query("update UserEntity set lastLogin=:date where userId=:userId")
	Integer updateLastLogin(@Param("date")Date date,@Param("userId")Integer userId);
	
	@Transactional
	@Query("select u.userId from UserEntity u where u.userId=:userId and u.companyId.iCompanyId=:companyId")
	Integer getBidOwner(@Param("userId")Integer userId, @Param("companyId")Integer companyId);
	
	@Transactional
	@Query("select u from UserEntity u where u.emailAddress=:userName")
	UserEntity loadUserByUsername(@Param("userName")String userName);
	

	@Transactional
	@Query("select u from UserEntity u where u.companyId.iCompanyId=:companyId")
	List<UserEntity> getByCompanyId(@Param("companyId")Integer companyId);

	@Modifying
	@Query("update UserEntity set password=:password where userId=:userId")
	Integer updatePasswordById(@Param("password")String password,@Param("userId")Integer userId);
	
}
