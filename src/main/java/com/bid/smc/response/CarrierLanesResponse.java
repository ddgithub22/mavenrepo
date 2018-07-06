package com.bid.smc.response;

import java.util.List;

public class CarrierLanesResponse {

	private Integer carrierLaneResponseId;
	private BidResponse bid;
	private List<LaneResponse> lanes;
	
	public BidResponse getBid() {
		return bid;
	}
	public void setBid(BidResponse bid) {
		this.bid = bid;
	}
	public List<LaneResponse> getLanes() {
		return lanes;
	}
	public void setLanes(List<LaneResponse> lanes) {
		this.lanes = lanes;
	}
	public Integer getCarrierLaneResponseId() {
		return carrierLaneResponseId;
	}
	public void setCarrierLaneResponseId(Integer carrierLaneResponseId) {
		this.carrierLaneResponseId = carrierLaneResponseId;
	}
	
	
}
