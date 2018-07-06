package com.bid.smc.model.bidsense;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="actions")
public class Action implements Serializable{
	private static final long serialVersionUID = 1L;
	private int actionId;
	private String actionClass;
	private String actionMethod;
	
	public Action(){
	}

	public Action(int actionId, String actionClass, String actionMethod) {
		super();
		this.actionId = actionId;
		this.actionClass = actionClass;
		this.actionMethod = actionMethod;
	}
	@Column(name = "actionClass", nullable = false)
	public String getActionClass() {
		return actionClass;
	}

	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}

	@Column(name = "actionMethod", nullable = false)
	public String getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(String actionMethod) {
		this.actionMethod = actionMethod;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "actionId",nullable=false )
	public long getActionId() {
		return actionId;
	}

	public void setActionId(int actionId) {
		this.actionId = actionId;
	}

	@Override
	public String toString() {
		return "Action [actionId=" + actionId + ", actionClass=" + actionClass + ", actionMethod=" + actionMethod +"]";
	}
	

}
