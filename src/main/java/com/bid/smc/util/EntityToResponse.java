package com.bid.smc.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bid.smc.model.adminmanager.CompanyEntity;
import com.bid.smc.model.adminmanager.CompanyTypeEntity;
import com.bid.smc.model.adminmanager.LocationEntity;
import com.bid.smc.model.adminmanager.UserEntity;
import com.bid.smc.model.bidsense.BidEntity;
import com.bid.smc.model.bidsense.CustomerEntity;
import com.bid.smc.model.bidsense.InviteCarrierEntity;
import com.bid.smc.repo.adminmanager.CompanyTypeRepository;
import com.bid.smc.repo.bidsense.CustomerRepository;
import com.bid.smc.repo.bidsense.InviteCarrierRepository;
import com.bid.smc.repo.bidsense.LanesRepository;
import com.bid.smc.repo.bidsense.ShipperRepository;
import com.bid.smc.response.AddressResponse;
import com.bid.smc.response.BidResponse;
import com.bid.smc.response.CompanyResponse;
import com.bid.smc.response.CustomerResponse;
import com.bid.smc.response.ShipperBidHistoryResponse;
import com.bid.smc.response.UserResponse;

/**
 * @author chandan.thakur
 *
 */
@Component
public class EntityToResponse {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CompanyTypeRepository companyTypeRepo;
	
	@Autowired
	LanesRepository laneRepo;
	
	@Autowired
	InviteCarrierRepository inviteRepo;
	
	@Autowired
	private ShipperRepository shipRepo;
	
	/**
	 * @param response
	 * @param entity
	 * @return
	 */
	public CustomerResponse customeEntityToCustomeResponse(CustomerResponse response, CustomerEntity entity){
		response.setCustomerId(entity.getCustomerId());
		response.setName(entity.getName());
		response.setContactName(entity.getContactName());
		response.setEmail(entity.getEmail());
		response.setExt(entity.getExt());
		response.setPhoneNo(entity.getPhoneNo());
		response.setAddress(entity.getAddress());
		response.setSaleRep(entity.getSaleRep());
		return response;
	}
	
	/**
	 * @param response
	 * @param entity
	 * @return
	 */
	public BidResponse rpfRequestTorpfEntity(BidResponse response,BidEntity entity)  {
		response.setBidName(entity.getBidName());
		if(entity.getBidNickName()!=null){
			response.setBidNickName(entity.getBidNickName());
		}
		if(entity.getRate()!=null){
			response.setRate(entity.getRate().getRateId());
		}
		if(entity.getCustomer()!=null){
			CustomerResponse customerResponse = new CustomerResponse();
			response.setCustomer(customeEntityToCustomeResponse(customerResponse,entity.getCustomer()));
		}
		if(entity.getShareBenchmarkRate()!=null){
			response.setShareBenchmarkRate(entity.getShareBenchmarkRate());
		}	
		Integer noOfLanes = laneRepo.getAllLanesByBidCount(entity.getBidId());
		List<InviteCarrierEntity> noOfcarrier = inviteRepo.findInvitedCarrierByBidId(entity.getBidId());
		response.setShipperId(entity.getCompanyId());
		response.setBidResponseStartDate(DateUtil.formatDate(entity.getBidResponseStartDate()));
		response.setBidResponseEndDate(DateUtil.formatDate(entity.getBidResponseEndDate()));
		response.setBidTermEndDate(DateUtil.formatDate(entity.getBidTermEndDate()));
		response.setBidTermStartDate(DateUtil.formatDate(entity.getBidTermStartDate()));
		response.setNoOfLanes(noOfLanes);
		response.setNoOfCarrier(noOfcarrier.size());
		response.setStatus(entity.getStatus());
		response.setBidId(entity.getBidId());
		response.setCreatedDate(DateUtil.formatDate(entity.getCreatedDate()));
		response.setSubmitDate(DateUtil.formatDate(entity.getSubmitDate()));
		return response;
	}
	/**
	 * 
	 * @param response
	 * @param entity
	 * @return
	 */
	public ShipperBidHistoryResponse rpfReqHisTorpfEntity(ShipperBidHistoryResponse response,BidEntity entity)  {
		response.setBidName(entity.getBidName());
		if(entity.getBidNickName()!=null){
			response.setBidNickName(entity.getBidNickName());
		}
		if(entity.getRate()!=null){
			response.setRate(entity.getRate().getRateId());
		}
		if(entity.getCustomer()!=null){
			CustomerResponse customerResponse = new CustomerResponse();
			response.setCustomer(customeEntityToCustomeResponse(customerResponse,entity.getCustomer()));
		}
		if(entity.getShareBenchmarkRate()!=null){
			response.setShareBenchmarkRate(entity.getShareBenchmarkRate());
		}	
		Integer noOfLanes = laneRepo.getAllLanesByBidCount(entity.getBidId());
		List<InviteCarrierEntity> noOfcarrier = inviteRepo.findInvitedCarrierByBidId(entity.getBidId());
		response.setShipperId(entity.getCompanyId());
		response.setBidResponseStartDate(DateUtil.formatDate(entity.getBidResponseStartDate()));
		response.setBidResponseEndDate(DateUtil.formatDate(entity.getBidResponseEndDate()));
		response.setBidTermEndDate(DateUtil.formatDate(entity.getBidTermEndDate()));
		response.setBidTermStartDate(DateUtil.formatDate(entity.getBidTermStartDate()));
		response.setNoOfLanes(noOfLanes);
		response.setNoOfCarrier(noOfcarrier.size());
		response.setStatus(entity.getStatus());
		response.setBidId(entity.getBidId());
		response.setCreatedDate(DateUtil.formatDate(entity.getCreatedDate()));
		response.setSubmitDate(DateUtil.formatDate(entity.getSubmitDate()));
		return response;
	}
	/**
	 * @param response
	 * @param entity
	 * @return
	 */
	public UserResponse userEntityToResponse(UserResponse response, UserEntity entity){
		AddressResponse addressResponse = new AddressResponse();
		CompanyResponse companyResponse = new CompanyResponse();
		response.setUserId(entity.getUserId());
		response.setEmail(entity.getEmailAddress());
		response.setFirstName(entity.getFirstName());
		response.setLastName(entity.getLastName());
		response.setPhoneNumber(entity.getPhoneNo());
		if (entity.getOrgUnitEntity() != null) {
			AddressResponse address = adddressEntityToResponse(addressResponse,
					entity.getOrgUnitEntity().getLocationId());
			response.setAddress(address);
		}
		response.setStatus(entity.getStatus());
		CompanyResponse company = companyEntityToResponse(companyResponse,entity.getCompanyId());
		response.setCompany(company);
		return response;
	}
	
	/**
	 * @param response
	 * @param entity
	 * @return
	 */
	public AddressResponse adddressEntityToResponse(AddressResponse response, LocationEntity entity){
		response.setAddressLine1(entity.getAddress1());
		response.setAddressLine2(entity.getAddress2());
		response.setCity(entity.getCity());
		response.setCountry(entity.getCountry());
		response.setState(entity.getState());
		response.setZipCode(entity.getZipCode());
		return response;
	}
	
	/**
	 * @param response
	 * @param entity
	 * @return
	 */
	public CompanyResponse companyEntityToResponse(CompanyResponse response, CompanyEntity entity){
		response.setCompanyId(entity.getiCompanyId());
		response.setCompanyName(entity.getsCompanyName());
		response.setCompanyStatus(entity.getsCompanyStatus());
		response.setCompanyTypeId(entity.getiCompanyTypeId());
		CompanyTypeEntity companyTypeEntity = companyTypeRepo.findOne(entity.getiCompanyTypeId());
		response.setCompanyType(companyTypeEntity.getDescription());
		response.setParentCompanyId(entity.getParentCompanyId());
		return response;
	}
}
