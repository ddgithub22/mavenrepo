package com.bid.smc.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bid.smc.model.adminmanager.UserEntity;
import com.bid.smc.model.bidsense.BidEntity;
import com.bid.smc.model.bidsense.CustomerEntity;
import com.bid.smc.repo.bidsense.CustomerRepository;
import com.bid.smc.repo.bidsense.RateRepository;
import com.bid.smc.request.CustomerRequest;
import com.bid.smc.request.UserRequest;
import com.bid.smc.response.ShipperResponse;
import com.bid.smc.request.BidRequest;
import com.bid.smc.service.ShipperService;

/**
 * @author chandan.thakur
 *
 */
@Component
public class ModelToEntity {
	@Autowired
	RateRepository rateRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ShipperService shipperService;
	
	/**
	 * @param request
	 * @param entity
	 * @return
	 */
	public CustomerEntity customerRequestToCustomerEntity(CustomerRequest request, CustomerEntity entity){
		entity.setCustomerId(request.getCustomerId());
		entity.setName(request.getName());
		entity.setContactName(request.getContactName());
		entity.setEmail(request.getEmail());
		entity.setExt(request.getExt());
		entity.setPhoneNo(request.getPhoneNo());
		entity.setAddress(request.getAddress());
		entity.setSaleRep(request.getSaleRep());
		return entity;
	}

	/**
	 * @param request
	 * @param entity
	 * @return
	 */
	public BidEntity mapToBidRequestToEntity(BidRequest request,BidEntity entity){
		entity.setBidName(request.getBidName());
		if(request.getBidNickName()!=null){
			entity.setBidNickName(request.getBidNickName());
		}
		if(request.getRate()!=null){
			entity.setRate(rateRepository.getOne(request.getRate()));
		}
		if(request.getCustomer()!=null){
			entity.setCustomer(customerRepository.getOne(request.getCustomer()));
		}
		if(request.getShareBenchmarkRate()!=null){
			entity.setShareBenchmarkRate(request.getShareBenchmarkRate());
		}
		entity.setStatus("New");
		ShipperResponse shipperResponse = shipperService.getShipperById(request.getShipperId());
		entity.setCompanyId(shipperResponse.getShipperId());
		TimeProvider time = new TimeProvider();
		entity.setCreatedDate(time.now());
		return entity;
	}
	
	/**
	 * @param request
	 * @param entity
	 * @return
	 */
	public UserEntity userRequestToEntity(UserRequest request, UserEntity entity){
		entity.setEmailAddress(request.getEmail());
		entity.setFirstName(request.getFirstName());
		entity.setLastName(request.getLastName());
		entity.setPassword(request.getPassword());
		entity.setPhoneNo(request.getPhoneNumber());
		return entity;
	}
}
