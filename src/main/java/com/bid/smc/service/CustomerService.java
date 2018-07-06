package com.bid.smc.service;

import java.util.List;

import com.bid.smc.request.CustomerRequest;
import com.bid.smc.response.CustomerResponse;

public interface CustomerService {

	CustomerResponse saveCustomer(CustomerRequest request);
	
	List<CustomerResponse> getAllCustomer();
}
