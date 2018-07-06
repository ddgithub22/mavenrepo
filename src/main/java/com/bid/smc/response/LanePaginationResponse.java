package com.bid.smc.response;

import java.util.List;

public class LanePaginationResponse {
   private List<LaneResponse> list;
   private Integer totalCount;
   private List<LocationResponse> locationList;
   
public List<LocationResponse> getLocationList() {
	return locationList;
}
public void setLocationList(List<LocationResponse> locationList) {
	this.locationList = locationList;
}
public LanePaginationResponse(List<LaneResponse> list, Integer totalCount,List<LocationResponse> locationList) {
	super();
	this.list = list;
	this.totalCount = totalCount;
	this.locationList = locationList;
}
public List<LaneResponse> getList() {
	return list;
}
public void setList(List<LaneResponse> list) {
	this.list = list;
}
public Integer getTotalCount() {
	return totalCount;
}
public void setTotalCount(Integer totalCount) {
	this.totalCount = totalCount;
}
   
}
