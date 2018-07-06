package com.bid.smc.response;

import java.io.Serializable;

public class PTVAddressResponse implements Serializable{

	 
		private static final long serialVersionUID = 1L;

		private String errorCode;

		private ResultList[] resultList;

		private String errorDescription;

		public String getErrorCode() {
			return errorCode;
		}

		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}

		public ResultList[] getResultList() {
			return resultList;
		}

		public void setResultList(ResultList[] resultList) {
			this.resultList = resultList;
		}

		public String getErrorDescription() {
			return errorDescription;
		}

		public void setErrorDescription(String errorDescription) {
			this.errorDescription = errorDescription;
		}
		
}
