package com.bid.smc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid.smc.encryption.PasswordConverter;
import com.bid.smc.model.adminmanager.CompanyEntity;
import com.bid.smc.model.adminmanager.LocationEntity;
import com.bid.smc.model.adminmanager.OrgUnitEntity;
import com.bid.smc.model.adminmanager.UserEntity;
import com.bid.smc.model.bidsense.BrokerEntity;
import com.bid.smc.repo.adminmanager.CompanyRepository;
import com.bid.smc.repo.adminmanager.LocationRepository;
import com.bid.smc.repo.adminmanager.OrgUnitRepository;
import com.bid.smc.repo.adminmanager.UserRepository;
import com.bid.smc.repo.bidsense.BrokerRepository;
import com.bid.smc.request.AddressRequest;
import com.bid.smc.request.BrokerRequest;
import com.bid.smc.response.BrokerResponse;
import com.bid.smc.response.UserResponse;
import com.bid.smc.service.BrokerService;

@Transactional
@Service
public class BrokerServiceImpl implements BrokerService {
	
	@Autowired
	private OrgUnitRepository orgUnitRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LocationRepository locationRepository;
	
	@Autowired
	private BrokerRepository brokerRepository;
	
	@Autowired
	private PasswordConverter password; 
	
	
	/**
	   * 
	   * @POST request
	   * @save Broker Registration.Saving the Broker Registration input form.
	   */
	@Override
	public BrokerResponse saveBroker(BrokerRequest request) {

		CompanyEntity companyEntity = new CompanyEntity();
		BrokerEntity brokerEntity = new BrokerEntity();
		LocationEntity locationEntity = new LocationEntity();
		UserEntity userEntity = new UserEntity();
		OrgUnitEntity orgUnitEntity = new OrgUnitEntity();

		companyEntity.setsCompanyName(request.getCompanyName());
		companyEntity.setiCompanyTypeId(11);
		companyEntity.setsCompanyStatus('A');
		CompanyEntity company = companyRepository.save(companyEntity);
		orgUnitEntity.setCompanyId(companyRepository.save(companyEntity));
		
		brokerEntity.setCompanyId(company.getiCompanyId());
		brokerEntity.setMcNumber(request.getMcNumber());
		brokerEntity.setBillToEmail(request.getBillToEmail());
		BrokerEntity entity=brokerRepository.save(brokerEntity);

		mapToLocationEntity(request, locationEntity);
		
		LocationEntity location = locationRepository.save(locationEntity);
		orgUnitEntity.setLocationId(location);

		mapToUserEntity(request, userEntity, orgUnitEntity, company);
		
		OrgUnitEntity orgUnit = orgUnitRepository.save(orgUnitEntity);
		userEntity.setOrgUnitEntity(orgUnit);
		
		List<UserEntity> users = new ArrayList<>();
		users.add(userRepository.save(userEntity));
		orgUnitEntity.setUser(users);
		UserEntity user = userRepository.save(userEntity);

		BrokerResponse brokerResponse = new BrokerResponse();
		mapToAddressResponse(request, entity, location, brokerResponse);
		
		mapToUserResponse(request, user, brokerResponse);
		return brokerResponse;
	}

	private void mapToLocationEntity(BrokerRequest request, LocationEntity locationEntity) {
		locationEntity.setAddress1(request.getCompanyAddress().getAddress1());
		locationEntity.setAddress2(request.getCompanyAddress().getAddress2());
		locationEntity.setCity(request.getCompanyAddress().getCity());
		String countCode = updateCountryCode(request.getCompanyAddress().getCountryCode());
		locationEntity.setCountry(countCode);
		locationEntity.setState(request.getCompanyAddress().getState());
		locationEntity.setZipCode(request.getCompanyAddress().getPostalCode());
	}

	/**
	 * @param request
	 * @param userEntity
	 * @param orgUnitEntity
	 * @param company
	 */
	private void mapToUserEntity(BrokerRequest request, UserEntity userEntity, OrgUnitEntity orgUnitEntity,
			CompanyEntity company) {
		userEntity.setFirstName(request.getUser().getFirstName());
		userEntity.setLastName(request.getUser().getLastName());
		userEntity.setEmailAddress(request.getUser().getEmail());
		userEntity.setPassword(password.passwordEncoder(request.getUser().getPassword()));
		userEntity.setPhoneNo(request.getUser().getPhoneNumber());
		userEntity.setCompanyId(company);
		userEntity.setStatus('A');
		orgUnitEntity.setOrgStatus('A');
		orgUnitEntity.setOrgUnitname(request.getCompanyName());
	}

	/**
	 * @param request
	 * @param entity
	 * @param location
	 * @param brokerResponse
	 */
	private void mapToAddressResponse(BrokerRequest request, BrokerEntity entity, LocationEntity location,
									  BrokerResponse brokerResponse) {
		
		brokerResponse.setCompanyAddress(request.getCompanyAddress());
		brokerResponse.setBrokerId(entity.getCompanyId());
		AddressRequest address = new AddressRequest();
		address.setCountryCode(location.getCountry());
		address.setAddress1(location.getAddress1());
		address.setAddress2(location.getAddress2());
		address.setCity(location.getCity());
		address.setState(location.getState());
		address.setPostalCode(location.getZipCode());
		brokerResponse.setCompanyAddress(address);
		brokerResponse.setMcNumber(entity.getMcNumber());
		brokerResponse.setBillToEmail(entity.getBillToEmail());
	}

	/**
	 * @param request
	 * @param user
	 * @param brokerResponse
	 */
	private void mapToUserResponse(BrokerRequest request, UserEntity user, BrokerResponse brokerResponse) {
		UserResponse response = new UserResponse();
		response.setUserId(user.getUserId());
		response.setFirstName(request.getUser().getFirstName());
		response.setLastName(request.getUser().getLastName());
		response.setEmail(request.getUser().getEmail());
		response.setPhoneNumber(request.getUser().getPhoneNumber());
		brokerResponse.setUser(response);
		brokerResponse.setBrokerCompany(request.getCompanyName());
	}

	/**
	 * @param country
	 * @return
	 */
	private String updateCountryCode(String country) {
		String modCountry = null;
		if (country.equals("United States")) {
			modCountry = "USA";
		}
		if (country.equals("Canada")) {
			modCountry = "CAN";
		}
		return modCountry;
	}
	
	 /**
	   * 
	   * @return all saved Broker data.
	   */
	@Override
	public List<BrokerEntity> getAllBroker() {
		List<BrokerEntity> brokerEntities = new ArrayList<>();
		brokerEntities = brokerRepository.findAll();
		return brokerEntities;
	}
	
	/***
	   * 
	   * @return Broker details for the given Broker Id.
	   */
	@Override
	public BrokerEntity getBrokerById(Integer id) {
		BrokerEntity entity = brokerRepository.findBrokerId(id);
		return entity;
	}

}
