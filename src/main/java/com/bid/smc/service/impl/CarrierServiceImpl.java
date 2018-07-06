package com.bid.smc.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.encryption.PasswordConverter;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.model.adminmanager.CompanyEntity;
import com.bid.smc.model.adminmanager.LocationEntity;
import com.bid.smc.model.adminmanager.OrgUnitEntity;
import com.bid.smc.model.adminmanager.UserEntity;
import com.bid.smc.model.bidsense.BidEntity;
import com.bid.smc.model.bidsense.CarrierEntity;
import com.bid.smc.model.bidsense.InviteCarrierEntity;
import com.bid.smc.model.bidsense.MessageEntity;
import com.bid.smc.model.bidsense.ShipperEntity;
import com.bid.smc.repo.adminmanager.CompanyRepository;
import com.bid.smc.repo.adminmanager.LocationRepository;
import com.bid.smc.repo.adminmanager.OrgUnitRepository;
import com.bid.smc.repo.adminmanager.UserRepository;
import com.bid.smc.repo.bidsense.BidRepository;
import com.bid.smc.repo.bidsense.CarrierRepository;
import com.bid.smc.repo.bidsense.InviteCarrierRepository;
import com.bid.smc.repo.bidsense.MessageRepository;
import com.bid.smc.repo.bidsense.ShipperRepository;
import com.bid.smc.request.AddCarrierRequest;
import com.bid.smc.request.AddressRequest;
import com.bid.smc.request.CarrierRequest;
import com.bid.smc.request.MessageRequest;
import com.bid.smc.request.StatusRequest;
import com.bid.smc.response.AddCarrierResponse;
import com.bid.smc.response.CarrierPaginationResponse;
import com.bid.smc.response.CarrierResponse;
import com.bid.smc.response.InvitedCarrierResponse;
import com.bid.smc.response.MessageResponse;
import com.bid.smc.response.UserResponse;
import com.bid.smc.service.CarrierService;
import com.bid.smc.util.DateUtil;
import com.bid.smc.util.EntityToResponse;

@Transactional
@Service
public class CarrierServiceImpl implements CarrierService {

	@Autowired
	private OrgUnitRepository orgUnitRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ShipperRepository shipperRepo;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private CarrierRepository carrierRepository;

	@Autowired
	private PasswordConverter password;

	@Autowired
	private EntityToResponse entityToResponse;
	
	@Autowired
	private BidRepository bidrepo;
	
	@Autowired
	private MessageRepository messageRepo;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private InviteCarrierRepository inviteRepo;

	
	
	/***
	 * 
	 * @POST request
	 * @Save the Carrier Company Registration details.
	 */
	public CarrierResponse saveCarrier(CarrierRequest request) {
		
		CompanyEntity companyEntity = new CompanyEntity();
		CarrierEntity carrierEntity = new CarrierEntity();
		LocationEntity locationEntity = new LocationEntity();
		UserEntity userEntity = new UserEntity();
		OrgUnitEntity orgUnitEntity = new OrgUnitEntity();

		companyEntity.setsCompanyName(request.getCompanyName());
		companyEntity.setiCompanyTypeId(2);
		companyEntity.setsCompanyStatus('A');
		CompanyEntity company = companyRepository.save(companyEntity);
		orgUnitEntity.setCompanyId(companyRepository.save(companyEntity));

		carrierEntity.setCompanyId(company.getiCompanyId());
		carrierEntity.setMcNumber(request.getMcNumber());
		carrierEntity.setDotNumber(request.getDotNumber());
		CarrierEntity entity = carrierRepository.save(carrierEntity);

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
		UserEntity user = userRepository.save(userEntity);

		CarrierResponse carrierResponse = new CarrierResponse();
		carrierResponse.setCompanyAddress(request.getCompanyAddress());
		carrierResponse.setCarrierId(entity.getCompanyId());
		AddressRequest address = new AddressRequest();
		address.setCountryCode(location.getCountry());
		address.setAddress1(location.getAddress1());
		address.setAddress2(location.getAddress2());
		address.setCity(location.getCity());
		address.setState(location.getState());
		address.setPostalCode(location.getZipCode());
		carrierResponse.setCompanyAddress(address);
		carrierResponse.setDotNumber(entity.getDotNumber());
		carrierResponse.setMcNumber(entity.getMcNumber());
		UserResponse response = new UserResponse();
		response.setUserId(user.getUserId());
		response.setFirstName(request.getUser().getFirstName());
		response.setLastName(request.getUser().getLastName());
		response.setEmail(request.getUser().getEmail());
		response.setPhoneNumber(request.getUser().getPhoneNumber());

		carrierResponse.setUser(response);
		carrierResponse.setCompanyName(request.getCompanyName());
		return carrierResponse;
	}

	/**
	 * @param country
	 * @return
	 */
	private String updateCountryCode(String country) {
		String modCountry = null;
		if (country.equalsIgnoreCase("United States")) {
			modCountry = "USA";
		}
		if (country.equalsIgnoreCase("Canada")) {
			modCountry = "CAN";
		}
		return modCountry;
	}

	/***
	 * @GET request
	 * @return all Carrier Registration input form data.
	 */
	@Override
	public List<CarrierResponse> getAllCarrier() {
		
		List<CarrierResponse> carrierResponses = new ArrayList<>();
		List<CarrierEntity> carrierEntities = carrierRepository.findAll();
		UserResponse userResponse = new UserResponse();

		for (CarrierEntity entity : carrierEntities) {
			CarrierResponse carrierResponse = new CarrierResponse();
			CompanyEntity companyEntity = companyRepository.getOne(entity.getCompanyId());
			
			carrierResponse.setDotNumber(entity.getDotNumber());
			carrierResponse.setMcNumber(entity.getMcNumber());
			carrierResponse.setCompanyName(companyEntity.getsCompanyName());
			carrierResponse.setCarrierId(entity.getCompanyId());
			
			/*UserEntity userEntity = userRepository.getByCompanyId(companyEntity.getiCompanyId());
			UserResponse uResponse = entityToResponse.userEntityToResponse(userResponse, userEntity);
			carrierResponse.setUser(uResponse);*/
			
			carrierResponses.add(carrierResponse);
		}
		return carrierResponses;
	}

	/***
	 * 
	 * @GET request
	 * @return Carrier Registration details for the given Carrier Id.
	 */
	@Override
	public CarrierEntity getCarrierById(Integer id) {
		
		CarrierEntity entity = carrierRepository.findCarrierId(id);
		BaseResponse response = new BaseResponse();
		
		if (entity == null) {
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			response.setErrorMessage("NOT_FOUND");
			response.setStatus(409);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}
		
		return entity;
	}

	/**
	 * @return the Carrier details by Carrier Name for the given Shipper Id.
	 */
	@Override
	@Transactional("bidsenseTransactionManager")
	public List<CarrierResponse> findByName(Integer shipperId, String carrierName) {
		
		List<CompanyEntity> companyEntities = companyRepository.findByCompanyName(carrierName);
		
		if (companyEntities == null) {
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}

		List<CarrierResponse> carrierResponses = new ArrayList<>();
		
		for (CompanyEntity entity : companyEntities) {
			
			CarrierEntity carrierEntity = carrierRepository.getOne(entity.getiCompanyId());
			CarrierResponse carrierResponse = new CarrierResponse();
			carrierResponse.setCarrierId(entity.getiCompanyId());
			carrierResponse.setCompanyName(entity.getsCompanyName());
			carrierResponse.setDotNumber(carrierEntity.getDotNumber());
			carrierResponse.setMcNumber(carrierEntity.getMcNumber());
			carrierResponses.add(carrierResponse);
			
		}
		return carrierResponses;
	}

	/***
	 * 
	 * @POST carrierRequest
	 * @Saving Add Carrier Input form data from the Partner Management Section.
	 */
	public AddCarrierResponse saveCarrierByShipper(Integer shipperId,AddCarrierRequest request) {

		CompanyEntity companyEntity = new CompanyEntity();

		LocationEntity locationEntity = new LocationEntity();
		UserEntity userEntity = new UserEntity();
		OrgUnitEntity orgUnitEntity = new OrgUnitEntity();

		companyEntity.setsCompanyName("XXXXXXXXXXXX");
		companyEntity.setiCompanyTypeId(2);
		companyEntity.setsCompanyStatus('A');
		CompanyEntity company = companyRepository.save(companyEntity);
		orgUnitEntity.setCompanyId(companyRepository.save(companyEntity));
		locationEntity.setAddress1("Address Line 1");
		locationEntity.setAddress2("Address Line 2");
		locationEntity.setCity("City");
		locationEntity.setCountry("USA");
		locationEntity.setState("State");
		locationEntity.setZipCode("12345");
		LocationEntity location = locationRepository.save(locationEntity);
		orgUnitEntity.setLocationId(location);

		userEntity.setFirstName("Demo");
		userEntity.setLastName("User");
		userEntity.setEmailAddress(request.getContactEmail());
		userEntity.setPassword("07c3b8f208c2599e86c7b02ad8f5d8b1");
		userEntity.setPhoneNo("1234567890");
		userEntity.setCompanyId(company);
		userEntity.setStatus('A');
		orgUnitEntity.setOrgStatus('A');
		orgUnitEntity.setOrgUnitname("XXXXXXXXXXXX");
		OrgUnitEntity orgUnit = orgUnitRepository.save(orgUnitEntity);
		userEntity.setOrgUnitEntity(orgUnit);
		List<UserEntity> users = new ArrayList<>();
		users.add(userRepository.save(userEntity));
		orgUnitEntity.setUser(users);

		userRepository.save(userEntity);

		CarrierEntity entity = new CarrierEntity();
		AddCarrierResponse response = new AddCarrierResponse();
		entity.setCompanyId(company.getiCompanyId());
		entity.setContactName(request.getContactName());
		entity.setContactEmail(request.getContactEmail());
		entity.setMcNumber(request.getMcNumber());
		entity.setDotNumber(request.getDotNumber());

		ShipperEntity shipperEntity = shipperRepo.findOne(shipperId);

		List<ShipperEntity> entities = new ArrayList<>();
		entities.add(shipperEntity);
		entity.setShipper(entities);

		CarrierEntity carrierEntity = carrierRepository.save(entity);

		BeanUtils.copyProperties(carrierEntity, response);
		response.setCompanyName(carrierEntity.getContactName());
		response.setCompanyType(carrierEntity.getCompanyType());
		return response;
	}

	/***
	 * 
	 * @GET request
	 * @return Carrier details added by the given Shipper Company under Partner
	 *         Management Section.
	 */
	@Override
	public CarrierPaginationResponse getCarrierByShipper(Integer shipperId, Pageable pageable) {

		BaseResponse response = new BaseResponse();

		List<CarrierEntity> carrierEntity = carrierRepository.findCarrier(shipperId, pageable);

		Integer count = carrierRepository.getAllCarrierByShipperCount(shipperId);

		if (carrierEntity.isEmpty()) {
			response.setErrorMessage("NOT_FOUND");
			response.setStatus(404);
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}

		List<AddCarrierResponse> addCarrierResponse = new ArrayList<>();
		
		for (CarrierEntity entity : carrierEntity) {
			AddCarrierResponse addCarResponse = new AddCarrierResponse();
			BeanUtils.copyProperties(entity, addCarResponse);
			addCarrierResponse.add(addCarResponse);
		}
		return new CarrierPaginationResponse(addCarrierResponse, count);

	}



	@Override
	@SuppressWarnings("unchecked")
	public CarrierPaginationResponse searchCarrier(Integer shipperId,String searchC,Pageable pageable, String sortBy) {
		List<AddCarrierResponse> response = new ArrayList<>();
		String searchCriteria = " and LOWER(a.ContactName) LIKE('%" + searchC + "%') OR a.ContactEmail LIKE('%"
				+ searchC + "%') OR a.MCNumber LIKE('%" + searchC + "%') " + "OR a.DOTNumber LIKE('%" + searchC + "%')";

		if(sortBy.equals("contactName")) {
			searchCriteria += " order by  ContactName";
		}else if(sortBy.equals("dotNumber")) {
			searchCriteria += " order by  DOTNumber";
		}else if(sortBy.equals("mcNumber")) {
			searchCriteria += " order by  MCNumber";
		}else if(sortBy.equals("contactEmail")) {
			searchCriteria += " order by  ContactEmail";
		}else if(sortBy.equals("companyType")) {
			searchCriteria += " order by  CompanyType";
		}
		
		String sql = "select a.* from bidsense.carrier_details a where a.CompanyId in(select carriers_CompanyId from bidsense.shipper_details_carrier_details where ShipperEntity_CompanyId="+shipperId + " )" + searchCriteria;
		
		Query nativeQuery = entityManager.createNativeQuery(sql);
		
			
			
		nativeQuery.setFirstResult(pageable.getPageSize() * (pageable.getPageNumber()));
        nativeQuery.setMaxResults(pageable.getPageSize());

        
		List<Object[]> list = nativeQuery.getResultList();
		
		String countSql = "select count(a.CompanyId) from bidsense.carrier_details a where a.CompanyId in(select carriers_CompanyId from bidsense.shipper_details_carrier_details where ShipperEntity_CompanyId="+shipperId + " )" + searchCriteria;
		
		BigInteger count =   (BigInteger) entityManager.createNativeQuery(countSql).getSingleResult();
		
		for (Object[] carrierEntity : list) {
			AddCarrierResponse carResponse = new AddCarrierResponse();
			carResponse.setCompanyId((Integer) carrierEntity[0]);
			carResponse.setDotNumber((String) carrierEntity[1]);
			carResponse.setMcNumber((String) carrierEntity[2]);
			if (carrierEntity[3] != null)
				carResponse.setCompanyType((String) carrierEntity[3]);
			if (carrierEntity[4] != null)
				carResponse.setContactEmail((String) carrierEntity[4]);
			carResponse.setContactName((String) carrierEntity[5]);
			response.add(carResponse);
		}

		return new  CarrierPaginationResponse(response,  count);
	}
	@Override
	public MessageResponse saveMessage(MessageRequest request) {
		
		MessageEntity messageEntity = new MessageEntity();
		BeanUtils.copyProperties(request, messageEntity);
		
		BidEntity bidEntity = bidrepo.findOne(request.getBidId());
		messageEntity.setBidEntity(bidEntity);
		
		CarrierEntity carrierEntity = carrierRepository.findOne(request.getCarrierId());
		messageEntity.setCarEntity(carrierEntity);
		
		MessageEntity messageEntity_saved = messageRepo.save(messageEntity);
		
		MessageResponse response = new MessageResponse();
		BeanUtils.copyProperties(messageEntity_saved, response);

		response.setCarrierId(carrierEntity.getCompanyId());
		response.setBidId(bidEntity.getBidId());
		return response;
}

	@Override
	public MessageResponse updateMessage(MessageRequest request,Integer messageId) {
		
		MessageEntity messageEntity  = messageRepo.findOne(messageId);
		
		BidEntity bidEntity = bidrepo.findOne(request.getBidId());
		messageEntity.setBidEntity(bidEntity);
		
		CarrierEntity carrierEntity = carrierRepository.findOne(request.getCarrierId());
		messageEntity.setCarEntity(carrierEntity);
		
		messageEntity.setAnswers(request.getAnswer());
		messageEntity.setReplyDate(new Date());
		
		MessageEntity messageEntity_saved = messageRepo.save(messageEntity);
		
		MessageResponse response = new MessageResponse();
		BeanUtils.copyProperties(messageEntity_saved, response);

		response.setCarrierId(carrierEntity.getCompanyId());
		response.setBidId(bidEntity.getBidId());
		return response;
	}

	@Override
	@Transactional("bidsenseTransactionManager")
	public List<InvitedCarrierResponse> getBidByCarrierId(Integer id) {
		List<InviteCarrierEntity> inviteCarrierEntities = inviteRepo.getEntityByCarrierId(id);
		List<InvitedCarrierResponse> invitedCarrierResponses = new ArrayList<>();
		for(InviteCarrierEntity entity : inviteCarrierEntities){
			InvitedCarrierResponse bidResponse = new InvitedCarrierResponse();
			bidResponse.setBidName(entity.getBidId().getBidName());
			bidResponse.setResponseDueDate(DateUtil.formatDate(entity.getBidId().getBidResponseEndDate()));
			bidResponse.setNoOfLanes(bidrepo.findLaneBybidId(entity.getBidId().getBidId()).size());
			ShipperEntity shipperName = shipperRepo.getShipperByCarrierId(entity.getCarrier().getCompanyId());
			CompanyEntity companyEntity=companyRepository.findOne(shipperName.getCompanyId());
			bidResponse.setShipperName(companyEntity.getsCompanyName());
			bidResponse.setBidId(entity.getBidId().getBidId());
			invitedCarrierResponses.add(bidResponse);
		}
		return invitedCarrierResponses;
	}

	@Override
	public void bidResponse(Integer bidId,StatusRequest request) {
		List<InviteCarrierEntity> inviteCarrierEntities = inviteRepo.findInvitedCarrierByBidId(bidId);
		for(InviteCarrierEntity entity : inviteCarrierEntities){
			if (request.getStatus().equalsIgnoreCase("Accepted")) {
				entity.setStatus("Accepted");
				inviteRepo.saveAndFlush(entity);
			}else{
				entity.setStatus("Rejected");
				inviteRepo.saveAndFlush(entity);
			}
		}
	}

	@Override
	public List<MessageResponse> getAllMessages(Integer bidId) {
		List<MessageResponse> messageResponses = new ArrayList<>();
		
		List<MessageEntity> messageEntities = messageRepo.findByBidEntity(new BidEntity(bidId));
		
		for (MessageEntity messageEntity : messageEntities) {
			MessageResponse messageResponse = new MessageResponse();
		 
			messageEntity.setAskedDate(DateUtil.formatDateTime(messageEntity.getAskedDate()));
			messageEntity.setReplyDate(DateUtil.formatDateTime(messageEntity.getReplyDate()));
			BeanUtils.copyProperties(messageEntity, messageResponse);
			messageResponse.setBidId(messageEntity.getBidEntity().getBidId());
			messageResponse.setCarrierId(messageEntity.getCarEntity().getCompanyId());
			
			messageResponses.add(messageResponse);
		}
		return messageResponses;
 
}
}
