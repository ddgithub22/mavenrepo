package com.bid.smc.request;


/**
 * @author chandan.thakur
 *
 */
public class LaneRequest {
	
	private String lane;
	private String volume;
	private String originName;
	private String destinationName;
	private String originAddress;
	private Integer mode;
	private String destinationAddress;
	/*private String origin;
	private String destination;*/
	private Integer equipmentClass;
	private Integer equipmentType;
	private Integer equipmentLength;
	private String commodity;
	private String weight;
	private String haul;
	private String incumbentName;
	private String incumbentRate;
	private String benchmarkRate;
	private String laneDescription;
	
	private String destinationCity;
	private String destinationZipcode;
	private String destinationState;
	
	private String originZipCode;
	private String originCity;
	private String originState;
	private Integer bidId;
	
 
	public String getLane() {
		return lane;
	}

	public void setLane(String lane) {
		this.lane = lane;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getOriginName() {
		return originName;
	}

	public void setOriginName(String originName) {
		this.originName = originName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public String getOriginAddress() {
		return originAddress;
	}

	public void setOriginAddress(String originAddress) {
		this.originAddress = originAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
 

	public Integer getEquipmentClass() {
		return equipmentClass;
	}

	public void setEquipmentClass(Integer equipmentClass) {
		this.equipmentClass = equipmentClass;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Integer getEquipmentLength() {
		return equipmentLength;
	}

	public void setEquipmentLength(Integer equipmentLength) {
		this.equipmentLength = equipmentLength;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getHaul() {
		return haul;
	}

	public void setHaul(String haul) {
		this.haul = haul;
	}

	public String getIncumbentName() {
		return incumbentName;
	}

	public void setIncumbentName(String incumbentName) {
		this.incumbentName = incumbentName;
	}

	public String getIncumbentRate() {
		return incumbentRate;
	}

	public void setIncumbentRate(String incumbentRate) {
		this.incumbentRate = incumbentRate;
	}

	public String getBenchmarkRate() {
		return benchmarkRate;
	}

	public void setBenchmarkRate(String benchmarkRate) {
		this.benchmarkRate = benchmarkRate;
	}

	public String getLaneDescription() {
		return laneDescription;
	}

	public void setLaneDescription(String laneDescription) {
		this.laneDescription = laneDescription;
	}

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
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

	public String getOriginZipCode() {
		return originZipCode;
	}

	public void setOriginZipCode(String originZipCode) {
		this.originZipCode = originZipCode;
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

	public String getDestinationZipcode() {
		return destinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}

}
