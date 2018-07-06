package com.bid.smc.model.bidsense;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="role_action")
public class RoleAction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "roleactionId",nullable=false )
	public int roleActionId;
	
	@Column(name = "roleId",nullable=false )
	public int roleId;
	
	@Column(name = "actionId",nullable=false )
	public int actionId;

	public int getRoleActionId() {
		return roleActionId;
	}

	public void setRoleActionId(int roleActionId) {
		this.roleActionId = roleActionId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	
	
	
	
}
