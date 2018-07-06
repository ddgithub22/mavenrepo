package com.bid.smc.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.encryption.PasswordConverter;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.model.adminmanager.CompanyEntity;
import com.bid.smc.model.adminmanager.LocationEntity;
import com.bid.smc.model.adminmanager.OrgUnitEntity;
import com.bid.smc.model.adminmanager.UserEntity;
import com.bid.smc.model.bidsense.ShipperEntity;
import com.bid.smc.repo.adminmanager.CompanyRepository;
import com.bid.smc.repo.adminmanager.LocationRepository;
import com.bid.smc.repo.adminmanager.OrgUnitRepository;
import com.bid.smc.repo.adminmanager.UserRepository;
import com.bid.smc.repo.bidsense.ShipperRepository;
import com.bid.smc.request.AddressRequest;
import com.bid.smc.request.ShipperRequest;
import com.bid.smc.response.ShipperResponse;
import com.bid.smc.response.UserResponse;
import com.bid.smc.service.ShipperService;
import com.bid.smc.util.EntityToResponse;

@Transactional
@Service
public class ShipperServiceImpl implements ShipperService {
	@Autowired
	private OrgUnitRepository orgUnitRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private ShipperRepository shipperRepository;
	@Autowired
	private PasswordConverter password;
	@Autowired
	private EntityToResponse entityToResponse;

	@PersistenceContext
	private EntityManager entityManager;
	/*** 
	 * 
	 * @POST request
	 * @Save all Shipper Registration Input form data.
	 */
	public ShipperResponse saveShipper(ShipperRequest request) {
		CompanyEntity companyEntity = new CompanyEntity();
		ShipperEntity shipperEntity = new ShipperEntity();
		LocationEntity locationEntity = new LocationEntity();
		UserEntity userEntity = new UserEntity();
		OrgUnitEntity orgUnitEntity = new OrgUnitEntity();

		companyEntity.setsCompanyName(request.getCompanyName());
		companyEntity.setiCompanyTypeId(3);
		companyEntity.setsCompanyStatus('A');
		CompanyEntity company = companyRepository.save(companyEntity);
		orgUnitEntity.setCompanyId(companyRepository.save(companyEntity));
		
		shipperEntity.setEinNumber(request.getEinNumber());
		shipperEntity.setCompanyId(company.getiCompanyId());
		ShipperEntity entity = shipperRepository.save(shipperEntity);

		locationEntity.setAddress1(request.getCompanyAddress().getAddress1());
		locationEntity.setAddress2(request.getCompanyAddress().getAddress2());
		locationEntity.setCity(request.getCompanyAddress().getCity());
		String countCode = updateCountryCode(request.getCompanyAddress().getCountryCode());
		locationEntity.setCountry(countCode);
		locationEntity.setState(request.getCompanyAddress().getState());
		locationEntity.setZipCode(request.getCompanyAddress().getPostalCode());
		LocationEntity location = locationRepository.save(locationEntity);
		orgUnitEntity.setLocationId(location);

		userEntity.setFirstName(request.getUser().getFirstName());
		userEntity.setLastName(request.getUser().getLastName());
		userEntity.setEmailAddress(request.getUser().getEmail());
		userEntity.setPassword(password.passwordEncoder(request.getUser().getPassword()));
		userEntity.setPhoneNo(request.getUser().getPhoneNumber());
		userEntity.setCompanyId(company);
		userEntity.setStatus('A');
		orgUnitEntity.setOrgStatus('A');
		orgUnitEntity.setOrgUnitname(request.getCompanyName());
		OrgUnitEntity orgUnit = orgUnitRepository.save(orgUnitEntity);
		userEntity.setOrgUnitEntity(orgUnit);
		List<UserEntity> users = new ArrayList<>();
		users.add(userRepository.save(userEntity));
		orgUnitEntity.setUser(users);
		UserEntity user=userRepository.save(userEntity);

		ShipperResponse shipperResponse = new ShipperResponse();
		shipperResponse.setCompanyAddress(request.getCompanyAddress());
		shipperResponse.setShipperId(company.getiCompanyId());
		AddressRequest address = new AddressRequest();
		address.setCountryCode(location.getCountry());
		address.setAddress1(location.getAddress1());
		address.setAddress2(location.getAddress2());
		address.setCity(location.getCity());
		address.setState(location.getState());
		address.setPostalCode(location.getZipCode());
		shipperResponse.setCompanyAddress(address);
		shipperResponse.setEinNumber(request.getEinNumber());
		UserResponse response = new UserResponse();
		List<UserResponse> responses = new ArrayList<>();
		response.setUserId(user.getUserId());
		response.setFirstName(request.getUser().getFirstName());
		response.setLastName(request.getUser().getLastName());
		response.setEmail(request.getUser().getEmail());
		response.setPhoneNumber(request.getUser().getPhoneNumber());
		responses.add(response);
		shipperResponse.setUsers(responses);
		shipperResponse.setCompanyName(request.getCompanyName());
		return shipperResponse;

	}

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
    /***
     * @Return all Shipper Company details.
     */
	@Override
	public List<ShipperResponse> getAllShipper() {
		List<ShipperEntity> shipperEntities=shipperRepository.findAll();
		List<ShipperResponse> shipperResponses = new ArrayList<>();
		for(ShipperEntity entity : shipperEntities){
			ShipperResponse response = getShipperById(entity.getCompanyId());
			shipperResponses.add(response);
		}
		return shipperResponses;
	}
	/***
	 * 
	 * @GET request
	 * @return Shipper Company details for the given Shipper Id.
	 */
	@Override
	public ShipperResponse getShipperById(Integer id) {
		ShipperResponse response = new ShipperResponse();
		BaseResponse responses = new BaseResponse();
		ShipperEntity entity = shipperRepository.findShipperId(id);
		if(entity == null){
			responses.setText(SmcConstants.RECORD_NOT_FOUND);
			responses.setErrorMessage("NOT_FOUND");
			responses.setStatus(409);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}
		response.setEinNumber(entity.getEinNumber());
		CompanyEntity company = companyRepository.findOne(entity.getCompanyId());
		response.setShipperId(entity.getCompanyId());
		response.setCompanyName(company.getsCompanyName());
		List<UserEntity> users = company.getUserEntity();
		List<UserResponse> userResponses = new ArrayList<>();
		for(UserEntity userEntity : users){
			UserResponse userResponse= new UserResponse();
			UserResponse user =entityToResponse.userEntityToResponse(userResponse, userEntity); 
			userResponses.add(user);
		}
		response.setUsers(userResponses);
		return response;
	}

	}
	
	
	
