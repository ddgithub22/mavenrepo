package com.bid.smc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.exception.WrongDateException;
import com.bid.smc.exception.WrongFileFormat;
import com.bid.smc.response.CsvUploadErrorResponse;
import com.bid.smc.response.FileUploadResponse;
import com.bid.smc.response.UploadDocumentResponse;
import com.bid.smc.service.SmcService;

@RestController
@CrossOrigin(allowCredentials = "false", maxAge = 3600)
public class FileController {

	@Autowired
	private SmcService smcService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/bids/{bidId}/files/")
	public ResponseEntity<?> uploadFileMulti(@PathVariable("bidId") Integer bidId,
			@RequestParam("files") MultipartFile[] uploadfiles) {
		String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename())
				.filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(" , "));
		if (StringUtils.isEmpty(uploadedFileName)) {
			return new ResponseEntity("please select a file!", HttpStatus.OK);
		}
		List<UploadDocumentResponse> responses = new ArrayList<>();
		try {
			if (uploadfiles.length < 10) {
				responses = smcService.saveUploadedFiles(Arrays.asList(uploadfiles), bidId);
			} else {
				return new ResponseEntity("Maxmimum Ten file can be uploaded", HttpStatus.OK);
			}
		} catch (IOException e) {
		}
		return new ResponseEntity(responses, HttpStatus.OK);
	}

	@RequestMapping(value = "/bids/{bidId}/files", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<FileUploadResponse>> getFileById(@PathVariable("bidId") int bidId) {
		List<FileUploadResponse> f = smcService.getFileById(bidId);
		return new ResponseEntity<List<FileUploadResponse>>(f, HttpStatus.OK);
	}

	@GetMapping(value = "/files/{fileId}")
	public void downloadFile(@PathVariable("fileId") int fileId, HttpServletResponse response) {
		try {
			smcService.downloadFile(response, fileId);
		} catch (IOException e) {
			
		}
	}

	@DeleteMapping(value = "/files")
	public void deleteFile(@RequestParam("fileId") int id) {
		smcService.deleteFile(id);
	}
	
	
	@GetMapping("/bids/{bidId}/exportLanes/carrier")
	public ResponseEntity<?> exportCsv(@RequestParam("Id")Integer carrierId, @PathVariable("bidId") Integer bidId) {
		BaseResponse response = new BaseResponse();
		try {
			smcService.exportCsv(carrierId,bidId);
		} catch (RecordNotFoundException record) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(record.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/bids/{bidId}/importLanes/carrier")
	public ResponseEntity<?> uploadCsv(@PathVariable("bidId") Integer bidId,@RequestParam("Id")Integer carrierId,
			@RequestParam("files") MultipartFile csvFile) {
		BaseResponse response = new BaseResponse();
		List<CsvUploadErrorResponse> laneResponse = null;
		try {
			smcService.importCsv(bidId, carrierId, csvFile);

		} catch (RecordNotFoundException record) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(record.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (WrongFileFormat | WrongDateException error) {
			response.setStatus(406);
			response.setErrorMessage(SmcConstants.NOT_ACCEPTABLE);
			response.setText(error.getMessage());
			return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(laneResponse, HttpStatus.OK);
	}
}
