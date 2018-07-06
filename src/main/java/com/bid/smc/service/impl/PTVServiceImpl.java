package com.bid.smc.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bid.smc.request.Addr;
import com.bid.smc.request.Address;
import com.bid.smc.request.CalculateDistance;
import com.bid.smc.request.CallerContext;
import com.bid.smc.request.LocationSearchRequest;
import com.bid.smc.request.Properties;
import com.bid.smc.request.Waypoints;
import com.bid.smc.response.AddressResponse;
import com.bid.smc.response.Distance;
import com.bid.smc.response.PTVAddressResponse;
import com.bid.smc.service.PTVService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PTVServiceImpl implements PTVService {
	
	
	@Autowired
	private RestTemplate restTemplate;
	

	@Override
	public AddressResponse findAddressByText(String searchText) {

		LocationSearchRequest locationSearchRequest = new LocationSearchRequest();
		mapToRequest(searchText, locationSearchRequest);
		String url = "https://xlocate-na-n-integration.cloud.ptvgroup.com/xlocate/rs/XLocate/findAddressByText";

		ObjectMapper mapper = new ObjectMapper();
		String serialized = null;

		try {
			serialized = mapper.writeValueAsString(locationSearchRequest);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		HttpEntity<String> request = new HttpEntity<String>(serialized, getHeaders());

		ResponseEntity<AddressResponse> userResponse = null;
		try {
			userResponse = restTemplate.exchange(url, HttpMethod.POST, request, AddressResponse.class);
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		return userResponse.getBody();

	}
	
	@Override
	public PTVAddressResponse getOriginDetails(String searchText) {

		Address address = new Address();

		Addr addr = new Addr();
		
		if (searchText.matches(".*\\d+.*")) {
			addr.setPostCode(searchText);
			addr.setCity(searchText);
		} else {
			addr.setCity2(searchText);
			addr.setCity(searchText);
		}

		address.setAddr(addr);

		CallerContext callerContext = new CallerContext();

		Properties[] properties = new Properties[2];

		Properties property = new Properties();
		property.setKey("CoordFormat");
		property.setValue("PTV_MERCATOR");

		Properties property2 = new Properties();
		property2.setKey("Profile");
		property2.setValue("default");

		properties[0] = property;
		properties[1] = property2;

		callerContext.setProperties(properties);

		address.setCallerContext(callerContext);

		String url = "https://xlocate-na-n-integration.cloud.ptvgroup.com/xlocate/rs/XLocate/findAddress";
		ObjectMapper mapper = new ObjectMapper();
		String serialized = null;

		try {
			serialized = mapper.writeValueAsString(address);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		HttpEntity<String> request = new HttpEntity<String>(serialized, getHeaders());

		ResponseEntity<PTVAddressResponse> userResponse = null;
		try {
			userResponse = restTemplate.exchange(url, HttpMethod.POST, request, PTVAddressResponse.class);
		} catch (RestClientException e) {
			return null;
		}

		if(userResponse !=null){
			return userResponse.getBody();
		}
		return null;
	}
	



	private void mapToRequest(String searchText, LocationSearchRequest locationSearchRequest) {
		locationSearchRequest.setAddress(searchText);

		CallerContext callerContext = new CallerContext();
		Properties[] properties = new Properties[2];

		Properties property = new Properties();
		property.setKey("CoordFormat");
		property.setValue("PTV_MERCATOR");

		Properties property2 = new Properties();
		property2.setKey("Profile");
		property2.setValue("default");

		properties[0] = property;
		properties[1] = property2;

		callerContext.setProperties(properties);

		locationSearchRequest.setCallerContext(callerContext);
	}

	 /**
	 * @return
	 */
	private static HttpHeaders getHeaders() {
		String apitoken = "xtok:" + "85685572-19AF-4981-87F1-8767891F50CC";
		String base64Credentials = new String(Base64.encode(apitoken.getBytes()));
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);

		headers.add("Authorization", "Basic " + base64Credentials);
		return headers;
	}
	   
	
	@Override
	public Object calculateDistance(Waypoints[] waypoints) {

		CalculateDistance calculate = new CalculateDistance();
		calculate.setWaypoints(waypoints);

		String url = "https://xroute-na-n-integration.cloud.ptvgroup.com/xroute/rs/XRoute/calculateRouteInfo";
		ObjectMapper mapper = new ObjectMapper();
		String serialized = null;

		try {
			serialized = mapper.writeValueAsString(calculate);
		} catch (JsonProcessingException e) {

		}

		HttpEntity<String> request = new HttpEntity<String>(serialized, getHeaders());

		ResponseEntity<Distance> userResponse = null;
		try {
			userResponse = restTemplate.exchange(url, HttpMethod.POST, request, Distance.class);
		} catch (RestClientException e) {
			return null;
		}
		if (userResponse != null) {
			return userResponse.getBody();
		}
		return null;

	}
	
}
