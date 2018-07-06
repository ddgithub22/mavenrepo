package com.bid.smc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bid.smc.model.adminmanager.CompanyEntity;

@Service
public interface CompanyService {
	
	List<CompanyEntity> findByCompanyName(String companyName);
	CompanyEntity saveCompany(CompanyEntity companyEntity);
	CompanyEntity updateCompany(CompanyEntity entity);
	void deleteCompany(String companyName);
}
