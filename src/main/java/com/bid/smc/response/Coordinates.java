package com.bid.smc.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Coordinates implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Point point;

	private String $type;

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public String get$type() {
		return $type;
	}

	public void set$type(String $type) {
		this.$type = $type;
	}
 

}
