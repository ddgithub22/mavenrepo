package com.bid.smc.response;

import java.util.List;

public class CarrierHistoryLanePaginationResponse {
	private List<LanesHistoryResponse> list;
	private Integer totalCount;
	private List<LocationResponse> locationList;

	public List<LocationResponse> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<LocationResponse> locationList) {
		this.locationList = locationList;
	}

	public CarrierHistoryLanePaginationResponse(List<LanesHistoryResponse> laneResponse, Integer totalCount,
			List<LocationResponse> locationList) {
		super();
		this.list = laneResponse;
		this.totalCount = totalCount;
		this.locationList = locationList;
	}

	public List<LanesHistoryResponse> getList() {
		return list;
	}

	public void setList(List<LanesHistoryResponse> list) {
		this.list = list;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}
