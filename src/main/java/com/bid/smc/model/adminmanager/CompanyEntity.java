package com.bid.smc.model.adminmanager;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "company", schema = "adminmanager")
public class CompanyEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CompanyId")
	private int iCompanyId;
	
	@Column(name = "CompanyName")
	private String sCompanyName;
	
	@Column(name = "SMCCustomer")
	private int iSMCCustomer;
	
	@Column(name = "CompanyTypeId")
	private int iCompanyTypeId;
	
	@Column(name = "Theme")
	private String sTheme;
	
	@Column(name = "CompanyStatus")
	private char sCompanyStatus;
	
	@Column(name = "ParentCompanyId")
	private Integer parentCompanyId;
	
	@Column(name = "LastModified")
	private Date lastModified;
	
	@OneToMany(mappedBy = "companyId", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<UserEntity> userEntity;
	
	
	@OneToMany(mappedBy = "companyId", fetch = FetchType.LAZY)
	private List<OrgUnitEntity> orgUnitEntity;

	public Integer getParentCompanyId() {
		return parentCompanyId;
	}

	public void setParentCompanyId(Integer parentCompanyId) {
		this.parentCompanyId = parentCompanyId;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public int getiCompanyId() {
		return iCompanyId;
	}

	public void setiCompanyId(int iCompanyId) {
		this.iCompanyId = iCompanyId;
	}

	public String getsCompanyName() {
		return sCompanyName;
	}

	public void setsCompanyName(String sCompanyName) {
		this.sCompanyName = sCompanyName;
	}

	public int getiSMCCustomer() {
		return iSMCCustomer;
	}

	public void setiSMCCustomer(int iSMCCustomer) {
		this.iSMCCustomer = iSMCCustomer;
	}

	public int getiCompanyTypeId() {
		return iCompanyTypeId;
	}

	public void setiCompanyTypeId(int iCompanyTypeId) {
		this.iCompanyTypeId = iCompanyTypeId;
	}

	public String getsTheme() {
		return sTheme;
	}

	public void setsTheme(String sTheme) {
		this.sTheme = sTheme;
	}

	public char getsCompanyStatus() {
		return sCompanyStatus;
	}

	public void setsCompanyStatus(char sCompanyStatus) {
		this.sCompanyStatus = sCompanyStatus;
	}

	public List<UserEntity> getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(List<UserEntity> userEntity) {
		this.userEntity = userEntity;
	}

	public List<OrgUnitEntity> getOrgUnitEntity() {
		return orgUnitEntity;
	}

	public void setOrgUnitEntity(List<OrgUnitEntity> orgUnitEntity) {
		this.orgUnitEntity = orgUnitEntity;
	}
}