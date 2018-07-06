package com.bid.smc.model.bidsense;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "lanes")
public class LanesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LaneId")
	private Integer laneId;

	@Column(name = "Lane")
	private String lane;

	@Column(name = "Volume")
	private String volume;

	@Column(name = "OriginFacilityName")
	private String originName;

	@Column(name = "DestinationFacilityName")
	private String destinationName;

	@Column(name = "OriginAddress")
	private String originAddress;

	@Column(name = "DestinationAddress")
	private String destinationAddress;

	@Column(name = "origincity")
	private String originCity;

	@Column(name = "originstate")
	private String originState;

	@Column(name = "originZip")
	private String originZipCode;

	@Column(name = "destinationCity")
	private String DestinationCity;

	@Column(name = "destinationState")
	private String destinationState;

	@Column(name = "destinationZip")
	private String DestinationZipcode;

	@Column(name = "Commodity")
	private String commodity;

	@Column(name = "Weight")
	private String weight;

	@Column(name = "HaulLength")
	private String haul;

	@Column(name = "IncumbentName")
	private String incumbentName;

	@Column(name = "IncumbentRate")
	private String incumbentRate;

	@Column(name = "BenchMarkRate")
	private String benchmarkRate;

	@Column(name = "LaneDescription")
	private String laneDescription;

	@Column(name = "Mode")
	private Integer mode;

	@Column(name = "Equipment")
	private Integer equipment;

	@Column(name = "EquipmentLength")
	private Integer equipmentLength;

	@Column(name = "EquipmentType")
	private Integer equipmentType;

	@ManyToOne(targetEntity = BidEntity.class)
	@JoinColumn(name = "BidId", referencedColumnName = "BidId")
	@JsonManagedReference
	private BidEntity bid;

	public BidEntity getBid() {
		return bid;
	}

	public void setBid(BidEntity bid) {
		this.bid = bid;
	}

	public Integer getLaneId() {
		return laneId;
	}

	public void setLaneId(Integer laneId) {
		this.laneId = laneId;
	}

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
		return DestinationCity;
	}

	public void setDestinationCity(String destinationCity) {
		DestinationCity = destinationCity;
	}

	public String getDestinationState() {
		return destinationState;
	}

	public void setDestinationState(String destinationState) {
		this.destinationState = destinationState;
	}

	public String getDestinationZipcode() {
		return DestinationZipcode;
	}

	public void setDestinationZipcode(String destinationZipcode) {
		DestinationZipcode = destinationZipcode;
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

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getEquipment() {
		return equipment;
	}

	public void setEquipment(Integer equipment) {
		this.equipment = equipment;
	}

	public Integer getEquipmentLength() {
		return equipmentLength;
	}

	public void setEquipmentLength(Integer equipmentLength) {
		this.equipmentLength = equipmentLength;
	}

	public Integer getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}

	/*
	 * public ModeEntity getModeEntity() { return modeEntity; }
	 * 
	 * public void setModeEntity(ModeEntity modeEntity) { this.modeEntity =
	 * modeEntity; }
	 */

}
