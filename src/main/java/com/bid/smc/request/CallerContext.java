package com.bid.smc.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallerContext implements Serializable{
	
 
	private static final long serialVersionUID = 1L;
	private Properties[] properties;

	public Properties[] getProperties() {
		return properties;
	}

	public void setProperties(Properties[] properties) {
		this.properties = properties;
	}

}
