package com.bid.smc.model.adminmanager;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "user")
@Proxy(lazy = false)
public class UserEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "UserId")
	private int userId;

	@Column(name = "FirstName")
	private String firstName;

	@Column(name = "LastName")
	private String lastName;

	@Column(name = "LoginPassword")
	private String password;

	@Column(name = "Phone")
	private String phoneNo;

	@Column(name = "Email")
	private String emailAddress;

	@Column(name = "Ext")
	private Integer ext;

	@Column(name = "Title")
	private String title;

	@Column(name = "LastLogin")
	private Date lastLogin;

	@Column(name = "UserStatus")
	private char status;

	@Column(name = "LastModified")
	private Date lastModified;

	@Column(name = "IsPasswordChangeRequired")
	private boolean isPasswordChangeRequired;

	@Column(name = "ServicePassword")
	private String servicePassword;

	@Column(name = "UserInterfacePassword")
	private String userInterfacePassword;

	@ManyToOne(targetEntity = CompanyEntity.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "CompanyId", referencedColumnName = "CompanyId")
	private CompanyEntity companyId;

	@ManyToOne(targetEntity = OrgUnitEntity.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "OrgUnitId", referencedColumnName = "OrgUnitId")
	private OrgUnitEntity orgUnitEntity;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Integer getExt() {
		return ext;
	}

	public void setExt(Integer ext) {
		this.ext = ext;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public boolean isPasswordChangeRequired() {
		return isPasswordChangeRequired;
	}

	public void setPasswordChangeRequired(boolean isPasswordChangeRequired) {
		this.isPasswordChangeRequired = isPasswordChangeRequired;
	}

	public String getServicePassword() {
		return servicePassword;
	}

	public void setServicePassword(String servicePassword) {
		this.servicePassword = servicePassword;
	}

	public String getUserInterfacePassword() {
		return userInterfacePassword;
	}

	public void setUserInterfacePassword(String userInterfacePassword) {
		this.userInterfacePassword = userInterfacePassword;
	}

	public CompanyEntity getCompanyId() {
		return companyId;
	}

	public void setCompanyId(CompanyEntity companyId) {
		this.companyId = companyId;
	}

	public OrgUnitEntity getOrgUnitEntity() {
		return orgUnitEntity;
	}

	public void setOrgUnitEntity(OrgUnitEntity orgUnitEntity) {
		this.orgUnitEntity = orgUnitEntity;
	}

}
