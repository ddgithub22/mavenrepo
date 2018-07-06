package com.bid.smc.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid.smc.model.adminmanager.CompanyEntity;
import com.bid.smc.repo.adminmanager.CompanyRepository;
import com.bid.smc.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;

	@Transactional
	public List<CompanyEntity> findByCompanyName(String name) {
		System.out.println(name);
		//companyRepository.findByCompanyName(name);
		return null;
	}

	@Transactional
	public CompanyEntity saveCompany(CompanyEntity companyEntity) {
		CompanyEntity c=null;
		try {
			c = companyRepository.save(companyEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	@Transactional
	public CompanyEntity updateCompany(CompanyEntity entity) {
		CompanyEntity c=null;
		c = companyRepository.save(entity);
		return c;
	}

	@Override
	public void deleteCompany(String companyName) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void deleteCompany(String companyName) {
		companyRepository.
	}*/

	/*@Override
	public CompanyEntity deleteCompany(String sCompanyName) {
		return null;
	}*/

	
}
