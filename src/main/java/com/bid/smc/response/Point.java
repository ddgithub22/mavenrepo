package com.bid.smc.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Point implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String $type;

	private String y;

	private String x;

	public String get$type() {
		return $type;
	}

	public void set$type(String $type) {
		this.$type = $type;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

 
}
