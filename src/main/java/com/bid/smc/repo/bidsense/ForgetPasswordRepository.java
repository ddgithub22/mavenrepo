package com.bid.smc.repo.bidsense;
import org.springframework.transaction.annotation.Transactional;

import com.bid.smc.model.bidsense.LanesEntity;
import com.bid.smc.model.bidsense.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<PasswordResetTokenEntity, Integer>{
	    @Transactional()
	    @Modifying
		@Query("Delete from PasswordResetTokenEntity r where r.userId=:userId")
	    public void deleteByUser(@Param("userId")Integer userId);
	    
	    @Query("Select u from PasswordResetTokenEntity u where u.userId  =:userId")
		List<PasswordResetTokenEntity> findTokenByuserId(@Param("userId")Integer userId);
	    
	    
}
