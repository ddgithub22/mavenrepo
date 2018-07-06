package com.bid.smc.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Address implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CallerContext callerContext;

	private String[] sorting;

	private Addr addr;

	private String[] additionalFields;

	private String[] options;

	public CallerContext getCallerContext() {
		return callerContext;
	}

	public void setCallerContext(CallerContext callerContext) {
		this.callerContext = callerContext;
	}

	public String[] getSorting() {
		return sorting;
	}

	public void setSorting(String[] sorting) {
		this.sorting = sorting;
	}

	public Addr getAddr() {
		return addr;
	}

	public void setAddr(Addr addr) {
		this.addr = addr;
	}

	public String[] getAdditionalFields() {
		return additionalFields;
	}

	public void setAdditionalFields(String[] additionalFields) {
		this.additionalFields = additionalFields;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

 
}