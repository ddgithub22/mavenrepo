package com.bid.smc.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String totalScore;

	private String countryCapital;

	private String street;

	private String detailLevelDescription;

	private String state;

	private String postCode;

	private String houseNumber;

	private String city2;

	private String classificationDescription;

	private String country;

	private String city;

	private String $type;

	private String adminRegion;

	private String appendix;

	private String[] additionalFields;

	private Coordinates coordinates;

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getCountryCapital() {
		return countryCapital;
	}

	public void setCountryCapital(String countryCapital) {
		this.countryCapital = countryCapital;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDetailLevelDescription() {
		return detailLevelDescription;
	}

	public void setDetailLevelDescription(String detailLevelDescription) {
		this.detailLevelDescription = detailLevelDescription;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getCity2() {
		return city2;
	}

	public void setCity2(String city2) {
		this.city2 = city2;
	}

	public String getClassificationDescription() {
		return classificationDescription;
	}

	public void setClassificationDescription(String classificationDescription) {
		this.classificationDescription = classificationDescription;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String get$type() {
		return $type;
	}

	public void set$type(String $type) {
		this.$type = $type;
	}

	public String getAdminRegion() {
		return adminRegion;
	}

	public void setAdminRegion(String adminRegion) {
		this.adminRegion = adminRegion;
	}

	public String getAppendix() {
		return appendix;
	}

	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}

	public String[] getAdditionalFields() {
		return additionalFields;
	}

	public void setAdditionalFields(String[] additionalFields) {
		this.additionalFields = additionalFields;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

 

}
