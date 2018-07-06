package com.bid.smc.model.adminmanager;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orgunit")
public class OrgUnitEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "OrgUnitId")
	private Integer orgUnitId;

	@Column(name = "OrgUnitname")
	private String orgUnitname;

	@OneToMany(mappedBy = "orgUnitEntity", fetch = FetchType.LAZY)
	private List<UserEntity> user;

	@ManyToOne(targetEntity = CompanyEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "CompanyId", referencedColumnName = "CompanyId")
	private CompanyEntity companyId;

	@ManyToOne(targetEntity = LocationEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "LocationId", referencedColumnName = "LocationId")
	private LocationEntity locationId;

	@Column(name = "OrgStatus")
	private char orgStatus;

	@Column(name = "LastModified")
	private Date lastModified;

	public Integer getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(Integer orgUnitId) {
		this.orgUnitId = orgUnitId;
	}

	public String getOrgUnitname() {
		return orgUnitname;
	}

	public void setOrgUnitname(String orgUnitname) {
		this.orgUnitname = orgUnitname;
	}

	public char getOrgStatus() {
		return orgStatus;
	}

	public void setOrgStatus(char orgStatus) {
		this.orgStatus = orgStatus;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public LocationEntity getLocationId() {
		return locationId;
	}

	public void setLocationId(LocationEntity locationId) {
		this.locationId = locationId;
	}

	public CompanyEntity getCompanyId() {
		return companyId;
	}

	public void setCompanyId(CompanyEntity companyId) {
		this.companyId = companyId;
	}

	public List<UserEntity> getUser() {
		return user;
	}

	public void setUser(List<UserEntity> user) {
		this.user = user;
	}

}
