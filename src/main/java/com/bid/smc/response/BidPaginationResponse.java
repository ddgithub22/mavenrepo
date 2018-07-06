package com.bid.smc.response;

import java.util.List;

public class BidPaginationResponse {

	private List<BidResponse> list;
	private Integer totalCount;
	
	
	public List<BidResponse> getList() {
		return list;
	}
	public BidPaginationResponse(List<BidResponse> list, Integer totalCount) {
		super();
		this.list = list;
		this.totalCount = totalCount;
	}
	
	
	public void setList(List<BidResponse> list) {
		this.list = list;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
