package com.bid.smc.repo.bidsense;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid.smc.model.bidsense.BidEntity;
import com.bid.smc.model.bidsense.MessageEntity;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer>{

	List<MessageEntity> findByBidEntity(BidEntity bidEntity);

}
