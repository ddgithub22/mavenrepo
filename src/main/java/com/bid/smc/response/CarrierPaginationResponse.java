package com.bid.smc.response;

import java.util.List;

public class CarrierPaginationResponse {
	
    private List<AddCarrierResponse> list;
    private Object totalCount;
	public CarrierPaginationResponse(List<AddCarrierResponse> list, Object totalCount) {
		super();
		this.list = list;
		this.totalCount = totalCount;
	}
	public List<AddCarrierResponse> getList() {
		return list;
	}
	public void setList(List<AddCarrierResponse> list) {
		this.list = list;
	}
	public Object getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Object totalCount) {
		this.totalCount = totalCount;
	}
	
	
    
    
	
    
    
}
