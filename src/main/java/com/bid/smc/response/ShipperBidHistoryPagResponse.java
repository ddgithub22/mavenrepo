package com.bid.smc.response;

import java.util.List;

public class ShipperBidHistoryPagResponse {
     List<ShipperBidHistoryResponse> list;
     private Integer totalCount;
     
     
	public ShipperBidHistoryPagResponse(List<ShipperBidHistoryResponse> list, Integer totalCount) {
		super();
		this.list = list;
		this.totalCount = totalCount;
	}
	public List<ShipperBidHistoryResponse> getList() {
		return list;
	}
	public void setList(List<ShipperBidHistoryResponse> list) {
		this.list = list;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
     
     
}
