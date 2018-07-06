package com.bid.smc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid.smc.model.bidsense.CustomerEntity;
import com.bid.smc.repo.bidsense.CustomerRepository;
import com.bid.smc.request.CustomerRequest;
import com.bid.smc.response.CustomerResponse;
import com.bid.smc.service.CustomerService;
import com.bid.smc.util.EntityToResponse;
import com.bid.smc.util.ModelToEntity;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	ModelToEntity modelToEntity;
	@Autowired
	EntityToResponse entityToResponse;
	/***
	 * @POST
	 * @Save Customers
	 */
	@Override
	public CustomerResponse saveCustomer(CustomerRequest request) {
		CustomerEntity customer = new CustomerEntity();
		CustomerEntity entity = customerRepository.save(modelToEntity.customerRequestToCustomerEntity(request, customer));
		CustomerResponse response = new CustomerResponse();
		return entityToResponse.customeEntityToCustomeResponse(response, entity);
	}
	/***
	 * @GET
	 * @return all customers.
	 */
	@Override
	public List<CustomerResponse> getAllCustomer() {
		List<CustomerResponse> responses = new ArrayList<>();
		List<CustomerEntity> customers = customerRepository.findAll();
		for(CustomerEntity entity:customers){
			CustomerResponse response = new CustomerResponse();
			responses.add(entityToResponse.customeEntityToCustomeResponse(response, entity));
		}
		return responses;
	}

	
	
}
