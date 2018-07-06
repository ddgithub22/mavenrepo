package com.bid.smc.response;

public class CsvUploadErrorResponse {
	
	private CsvErrorResponse laneNumber;
	private String laneDescription;
	private String originFacilityName;
	private String originFacilityAddress;
	private String originCity;
	private String originState;
	private String originPostalCode;
	private String destinationFacilityName;
	private String destinationFacilityAddress;
	private String destinationCity;
	private String destinationState;
	private String destinationPostalCode;
	private CsvErrorResponse equipmentType;
	private CsvErrorResponse equipmentSize;
	private CsvErrorResponse volume;
	private CsvErrorResponse commodityName;
	private CsvErrorResponse weightinPounds;
	private CsvErrorResponse modeType;
	private String haulLength;
	private String incumbentName;
	private float incumbentRate;
	private float benchmarkRate;
	private Integer equipmentClass;
	private Integer equipmentTypeId;
	private Integer equipmentLengthId;
	private Integer bidId;
	private String weight;
	
	public CsvErrorResponse getLaneNumber() {
		return laneNumber;
	}
	public void setLaneNumber(CsvErrorResponse laneNumber) {
		this.laneNumber = laneNumber;
	}
	public String getLaneDescription() {
		return laneDescription;
	}
	public void setLaneDescription(String laneDescription) {
		this.laneDescription = laneDescription;
	}
	public String getOriginFacilityName() {
		return originFacilityName;
	}
	public void setOriginFacilityName(String originFacilityName) {
		this.originFacilityName = originFacilityName;
	}
	public String getOriginFacilityAddress() {
		return originFacilityAddress;
	}
	public void setOriginFacilityAddress(String originFacilityAddress) {
		this.originFacilityAddress = originFacilityAddress;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getOriginState() {
		return originState;
	}
	public void setOriginState(String originState) {
		this.originState = originState;
	}
	public String getOriginPostalCode() {
		return originPostalCode;
	}
	public void setOriginPostalCode(String originPostalCode) {
		this.originPostalCode = originPostalCode;
	}
	public String getDestinationFacilityName() {
		return destinationFacilityName;
	}
	public void setDestinationFacilityName(String destinationFacilityName) {
		this.destinationFacilityName = destinationFacilityName;
	}
	public String getDestinationFacilityAddress() {
		return destinationFacilityAddress;
	}
	public void setDestinationFacilityAddress(String destinationFacilityAddress) {
		this.destinationFacilityAddress = destinationFacilityAddress;
	}
	public String getDestinationCity() {
		return destinationCity;
	}
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	public String getDestinationState() {
		return destinationState;
	}
	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}
	public String getDestinationPostalCode() {
		return destinationPostalCode;
	}
	public void setDestinationPostalCode(String destinationPostalCode) {
		this.destinationPostalCode = destinationPostalCode;
	}
	public void setIncumbentName(String incumbentName) {
		this.incumbentName = incumbentName;
	}
	public CsvErrorResponse getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(CsvErrorResponse equipmentType) {
		this.equipmentType = equipmentType;
	}
	public CsvErrorResponse getEquipmentSize() {
		return equipmentSize;
	}
	public void setEquipmentSize(CsvErrorResponse equipmentSize) {
		this.equipmentSize = equipmentSize;
	}
	public CsvErrorResponse getVolume() {
		return volume;
	}
	public void setVolume(CsvErrorResponse volume) {
		this.volume = volume;
	}
	public CsvErrorResponse getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(CsvErrorResponse commodityName) {
		this.commodityName = commodityName;
	}
	public CsvErrorResponse getWeightinPounds() {
		return weightinPounds;
	}
	public void setWeightinPounds(CsvErrorResponse weightinPounds) {
		this.weightinPounds = weightinPounds;
	}
	public CsvErrorResponse getModeType() {
		return modeType;
	}
	public void setModeType(CsvErrorResponse modeType) {
		this.modeType = modeType;
	}
	public String getIncumbentName() {
		return incumbentName;
	}
	public String getHaulLength() {
		return haulLength;
	}
	public void setHaulLength(String haulLength) {
		this.haulLength = haulLength;
	}
	public Integer getBidId() {
		return bidId;
	}
	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}
	public Integer getEquipmentClass() {
		return equipmentClass;
	}
	public void setEquipmentClass(Integer equipmentClass) {
		this.equipmentClass = equipmentClass;
	}
	public Integer getEquipmentTypeId() {
		return equipmentTypeId;
	}
	public void setEquipmentTypeId(Integer equipmentTypeId) {
		this.equipmentTypeId = equipmentTypeId;
	}
	public Integer getEquipmentLengthId() {
		return equipmentLengthId;
	}
	public void setEquipmentLengthId(Integer equipmentLengthId) {
		this.equipmentLengthId = equipmentLengthId;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public float getIncumbentRate() {
		return incumbentRate;
	}
	public void setIncumbentRate(float incumbentRate) {
		this.incumbentRate = incumbentRate;
	}
	public float getBenchmarkRate() {
		return benchmarkRate;
	}
	public void setBenchmarkRate(float benchmarkRate) {
		this.benchmarkRate = benchmarkRate;
	}
	
}
