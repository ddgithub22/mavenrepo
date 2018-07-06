package com.bid.smc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.request.MailRequest;
import com.bid.smc.service.BidService;

@RestController
@CrossOrigin(allowCredentials="false",maxAge=3600)
public class SendMailController {
	
	@Autowired
	private BidService bidService;
	
	@PostMapping("bids/{bidId}/mails")
	public ResponseEntity<?> uploadCsv(@PathVariable("bidId")Integer bidId,@RequestBody MailRequest request){
		BaseResponse response = new BaseResponse();
		try{
			bidService.sendMail(bidId,request);
		}catch(RecordNotFoundException record){
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(record.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}catch(Exception e){
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
