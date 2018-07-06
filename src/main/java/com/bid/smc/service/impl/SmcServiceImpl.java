package com.bid.smc.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bid.smc.common.BaseResponse;
import com.bid.smc.constants.SmcConstants;
import com.bid.smc.exception.RecordNotFoundException;
import com.bid.smc.exception.WrongFileFormat;
import com.bid.smc.model.bidsense.BidDocumentEntity;
import com.bid.smc.model.bidsense.BidEntity;
import com.bid.smc.model.bidsense.BidResponseDetailsEntity;
import com.bid.smc.model.bidsense.BidResponseStatusEntity;
import com.bid.smc.model.bidsense.EquipmentEntity;
import com.bid.smc.model.bidsense.EquipmentLengthEntity;
import com.bid.smc.model.bidsense.LanesEntity;
import com.bid.smc.model.bidsense.ModeEntity;
import com.bid.smc.repo.adminmanager.UserRepository;
import com.bid.smc.repo.bidsense.BidDocumentRepository;
import com.bid.smc.repo.bidsense.BidRepository;
import com.bid.smc.repo.bidsense.BidResponseDetailsRepository;
import com.bid.smc.repo.bidsense.BidResponseStatusRepository;
import com.bid.smc.repo.bidsense.EquipmentLengthRepository;
import com.bid.smc.repo.bidsense.EquipmentRepository;
import com.bid.smc.repo.bidsense.LanesRepository;
import com.bid.smc.repo.bidsense.ModeRepository;
import com.bid.smc.request.CsvRequest;
import com.bid.smc.request.LanesHistoryRequest;
import com.bid.smc.response.CsvErrorResponse;
import com.bid.smc.response.CsvUploadErrorResponse;
import com.bid.smc.response.FileUploadResponse;
import com.bid.smc.response.LanesHistoryResponse;
import com.bid.smc.response.UploadDocumentResponse;
import com.bid.smc.service.SmcService;

@Service
public class SmcServiceImpl implements SmcService {

	@Value("${imagesPath}")
	private String UPLOADED_FOLDER ;

	@Autowired
	private LanesRepository lanesRepo;
	@Autowired
	private ModeRepository modeRepo;
	@Autowired
	private EquipmentRepository equipmentRepo;
	@Autowired
	private EquipmentLengthRepository equipmentLengthRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BidRepository bidRepo;
	
	@Autowired
	private BidDocumentRepository bidDocumentRepo;
	
	@Autowired
	private BidResponseStatusRepository bidResponseStatusRepository;
	
	@Autowired
	private BidResponseDetailsRepository bidResponseDetailsRepository;
	
/*	public static Map<String, UserDetails> userDetailsMap = new HashMap<>();*/

	/***
	 * @POST
	 * @param bidId
	 * @param uploadfiles
	 * @Document Upload functionality in Define Bid Rules page.
	 */
	@Override
	public List<UploadDocumentResponse> saveUploadedFiles(List<MultipartFile> files, Integer bidId) throws IOException {

		BidEntity bidEntity = bidRepo.findOne(bidId);
		
		List<BidDocumentEntity> bidDocumentEntities = new ArrayList<>();
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			
			BidDocumentEntity  bidDocumentEntity = new BidDocumentEntity();
			bidDocumentEntity.setBidEntity(bidEntity);
			bidDocumentEntity.setDocumentName(file.getOriginalFilename());
			bidDocumentEntity.setDocumentPath(UPLOADED_FOLDER);
			bidDocumentEntities.add(bidDocumentEntity);
		}                                                                                                                                                                                                                                                                                                                                                                             
		List<BidDocumentEntity> documentEntities = bidDocumentRepo.save(bidDocumentEntities);
		List<UploadDocumentResponse> responses = new ArrayList<>();
		for(BidDocumentEntity entity : documentEntities){
			UploadDocumentResponse response = new UploadDocumentResponse();
			response.setBidId(entity.getBidEntity().getBidId());
			response.setDocumentId(entity.getDocumentId());
			response.setDocumentName(entity.getDocumentName());
			response.setDocumentPath(entity.getDocumentPath());
			responses.add(response);
		}
		return responses;
	}

	/***
	 * @POST
	 * @param bidId
	 * @param csvFile
	 * @Document Upload functionality to Upload Lanes(CSV format) in Add Lanes Page.
	 */

	@Override
	public List<CsvUploadErrorResponse> processCsv(Integer bidId, MultipartFile csvFile) throws IOException {
		BaseResponse response = new BaseResponse();
		String fileName = csvFile.getOriginalFilename();
		String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
		CSVParser csvParser = null;
		List<CsvRequest> csvRequestes = new ArrayList<>();
		if (extension.equalsIgnoreCase("csv")) {
			try {
				csvParser = CSVFormat.EXCEL.withHeader().parse(new InputStreamReader(csvFile.getInputStream()));
				Iterable<CSVRecord> csvRecords = csvParser.getRecords();
				BidEntity bidEntity = bidRepo.findOne(bidId);
				validateBidDetails(response, bidEntity);

				csvFileToCsvRequest(csvRequestes, csvRecords, bidEntity);
				List<CsvUploadErrorResponse> csvUploadErrorResponses = new ArrayList<>();
				for(CsvRequest request : csvRequestes){
					EquipmentEntity equipType = new EquipmentEntity();
					CsvUploadErrorResponse csvUploadErrorResponse =new CsvUploadErrorResponse();
					LanesEntity lanesEntity = new LanesEntity();
					equipType = validateCsv(request, equipType, csvUploadErrorResponse, lanesEntity);
					errorCsvResponse(bidId, csvUploadErrorResponses, request, equipType, csvUploadErrorResponse,
							lanesEntity);
					
					saveCsvRequest(request, lanesEntity);
				}
				if(csvRequestes.isEmpty()){
					response.setStatus(404);
					response.setErrorMessage(SmcConstants.NOT_FOUND);
					response.setText(SmcConstants.EMPTY_CSV);
					throw new RecordNotFoundException(SmcConstants.EMPTY_CSV);
				}
				return csvUploadErrorResponses;
			} finally {
				csvParser.close();
			}
		} else {
			response.setStatus(404);
			response.setErrorMessage("NOT_FOUND");
			response.setText(SmcConstants.NOT_VALID_FILE_FORMAT);
			throw new WrongFileFormat(SmcConstants.NOT_VALID_FILE_FORMAT);
		}
	}

	/**
	 * @param request
	 * @param lanesEntity
	 */
	private void saveCsvRequest(CsvRequest request, LanesEntity lanesEntity) {
		if ((request.getLaneNumber() != null && !request.getLaneNumber().equals(""))
				&& (request.getEquipmentSize() != null && !request.getEquipmentSize().equals(""))
				&& (request.getEquipmentType() != null && !request.getEquipmentType().equals(""))
				&& (request.getModeType() != null && !request.getModeType().equals(""))
				&& (request.getWeightinPounds() != null && !request.getWeightinPounds().equals(""))
				&& (request.getCommodityName() != null && !request.getCommodityName().equals(""))
				&& (request.getVolume() != null && !request.getVolume().equals(""))) {
			lanesEntity.setBenchmarkRate(request.getBenchmarkRate());
			lanesEntity.setDestinationAddress(request.getDestinationFacilityAddress());
			lanesEntity.setDestinationCity(request.getDestinationCity());
			lanesEntity.setDestinationName(request.getDestinationFacilityName());
			lanesEntity.setDestinationState(request.getDestinationState());
			lanesEntity.setDestinationZipcode(request.getDestinationPostalCode());
			lanesEntity.setHaul(request.getHaulLength());
			lanesEntity.setIncumbentName(request.getIncumbentName());
			lanesEntity.setIncumbentRate(request.getIncumbentRate());
			lanesEntity.setLaneDescription(request.getLaneDescription());
			lanesEntity.setOriginAddress(request.getOriginFacilityAddress());
			lanesEntity.setOriginCity(request.getOriginCity());
			lanesEntity.setOriginName(request.getOriginFacilityName());
			lanesEntity.setOriginState(request.getOriginState());
			lanesEntity.setOriginZipCode(request.getOriginPostalCode());
			BidEntity bid = bidRepo.findOne(request.getBidId());
			lanesEntity.setBid(bid);
			lanesRepo.save(lanesEntity);
		}
	}

	/**
	 * @param bidId
	 * @param csvUploadErrorResponses
	 * @param request
	 * @param equipType
	 * @param csvUploadErrorResponse
	 * @param lanesEntity
	 */
	private void errorCsvResponse(Integer bidId, List<CsvUploadErrorResponse> csvUploadErrorResponses,
			CsvRequest request, EquipmentEntity equipType, CsvUploadErrorResponse csvUploadErrorResponse,
			LanesEntity lanesEntity) {
		if (lanesEntity.getLane() == null || lanesEntity.getLane().equals("")
				|| lanesEntity.getEquipmentLength() == null || lanesEntity.getEquipmentLength().equals("")
				|| lanesEntity.getEquipmentType() == null || lanesEntity.getEquipmentType().equals("")
				|| lanesEntity.getMode() == null || lanesEntity.getMode().equals("")
				|| lanesEntity.getWeight() == null || lanesEntity.getWeight().equals("")
				|| lanesEntity.getCommodity() == null || lanesEntity.getCommodity().equals("")
				|| lanesEntity.getVolume() == null || lanesEntity.getVolume().equals("")) {
			csvUploadErrorResponse.setDestinationCity(request.getDestinationCity());
			csvUploadErrorResponse.setDestinationFacilityAddress(request.getDestinationFacilityAddress());
			csvUploadErrorResponse.setDestinationFacilityName(request.getDestinationFacilityName());
			csvUploadErrorResponse.setDestinationPostalCode(request.getDestinationPostalCode());
			csvUploadErrorResponse.setDestinationState(request.getDestinationState());
			csvUploadErrorResponse.setHaulLength(request.getHaulLength());
			csvUploadErrorResponse.setIncumbentName(request.getIncumbentName());
			csvUploadErrorResponse.setIncumbentRate(Float.parseFloat(request.getIncumbentRate()));
			csvUploadErrorResponse.setLaneDescription(request.getLaneDescription());
			csvUploadErrorResponse.setOriginCity(request.getOriginCity());
			csvUploadErrorResponse.setOriginFacilityAddress(request.getOriginFacilityAddress());
			csvUploadErrorResponse.setOriginFacilityName(request.getOriginFacilityName());
			csvUploadErrorResponse.setOriginState(request.getOriginState());
			csvUploadErrorResponse.setOriginPostalCode(request.getOriginPostalCode());
			csvUploadErrorResponse.setBenchmarkRate(Float.parseFloat(request.getBenchmarkRate()));
			csvUploadErrorResponse.setBidId(bidId);
			csvUploadErrorResponse.setEquipmentLengthId(lanesEntity.getEquipmentLength());
			csvUploadErrorResponse.setEquipmentTypeId(lanesEntity.getEquipmentType());
			csvUploadErrorResponse.setEquipmentClass(equipType.getEquipmentId());
			csvUploadErrorResponses.add(csvUploadErrorResponse);
		}
	}

	/**
	 * @param request
	 * @param equipType
	 * @param csvUploadErrorResponse
	 * @param lanesEntity
	 * @return
	 */
	private EquipmentEntity validateCsv(CsvRequest request, EquipmentEntity equipType,
			CsvUploadErrorResponse csvUploadErrorResponse, LanesEntity lanesEntity) {
		if(request.getLaneNumber().equals("") || request.getLaneNumber().equals(null)){
			CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
			csvErrorResponse.setValue(request.getLaneNumber());
			csvErrorResponse.setError(SmcConstants.LANES_REQUIRED);
			csvUploadErrorResponse.setLaneNumber(csvErrorResponse);
		}else{
			lanesEntity.setLane(request.getLaneNumber());
		}
		if(request.getEquipmentType().equals("") || request.getEquipmentType().equals(null)){
			CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
			csvErrorResponse.setValue(request.getEquipmentType());
			csvErrorResponse.setError(SmcConstants.EQUIPMENT_REQUIRED);
			csvUploadErrorResponse.setEquipmentType(csvErrorResponse);
		}else{
			equipType = equipmentRepo.getEquipmentByName(request.getEquipmentType());
			if (equipType == null) {
				CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
				csvErrorResponse.setValue(request.getEquipmentType());
				csvErrorResponse.setError(SmcConstants.EQUIPMENTTYPE_NOT_FOUND);
				csvUploadErrorResponse.setEquipmentType(csvErrorResponse);
			} else {
				lanesEntity.setEquipmentType(equipType.getEquipmentId());
			}
		}
		if(request.getVolume().equals("") || request.getVolume().equals(null)){
			CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
			csvErrorResponse.setValue(request.getVolume());
			csvErrorResponse.setError(SmcConstants.VOLUME_REQUIRED);
			csvUploadErrorResponse.setVolume(csvErrorResponse);
		}else{
			lanesEntity.setVolume(request.getVolume());
		}
		if(request.getCommodityName().equals("") || request.getCommodityName().equals(null)){
			CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
			csvErrorResponse.setValue(request.getCommodityName());
			csvErrorResponse.setError(SmcConstants.COMMODITY_REQUIRED);
			csvUploadErrorResponse.setCommodityName(csvErrorResponse);
		}else{
			lanesEntity.setCommodity(request.getCommodityName());
		}
		if(request.getWeightinPounds().equals("") || request.getWeightinPounds().equals(null)){
			CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
			csvErrorResponse.setValue(request.getWeightinPounds());
			csvErrorResponse.setError(SmcConstants.WEIGHT_REQUIRED);
			csvUploadErrorResponse.setWeightinPounds(csvErrorResponse);
		}else{
			lanesEntity.setWeight(request.getWeightinPounds());
		}
		if(request.getModeType().equals("") || request.getModeType().equals(null)){
			CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
			csvErrorResponse.setValue(request.getModeType());
			csvErrorResponse.setError(SmcConstants.MODE_REQUIRED);
			csvUploadErrorResponse.setModeType(csvErrorResponse);
		}else{
			ModeEntity modeEntity = modeRepo.getModeByName(request.getModeType());
			if (modeEntity == null) {
				CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
				csvErrorResponse.setValue(request.getModeType());
				csvErrorResponse.setError(SmcConstants.MODE_NOT_FOUND);
				csvUploadErrorResponse.setModeType(csvErrorResponse);
			} else {
				lanesEntity.setMode(modeEntity.getModeId());
			}
		}
		if (equipType.getParentId() != null) {
			EquipmentLengthEntity lengthEntity = equipmentLengthRepo.getEquipmentByLength(
					request.getEquipmentSize(), equipType.getParentId().getEquipmentId());
			if (lengthEntity == null) {
				CsvErrorResponse csvErrorResponse = new CsvErrorResponse();
				csvErrorResponse.setValue(request.getEquipmentSize());
				csvErrorResponse.setError(SmcConstants.EQUIPMENTLENGTH_NOT_FOUND);
				csvUploadErrorResponse.setModeType(csvErrorResponse);
			} else {
				lanesEntity.setEquipmentLength(lengthEntity.getEquipmentLengthId());
			}
		}
		return equipType;
	}

	/**
	 * @param csvRequestes
	 * @param csvRecords
	 * @param bidEntity
	 */
	private void csvFileToCsvRequest(List<CsvRequest> csvRequestes, Iterable<CSVRecord> csvRecords,
			BidEntity bidEntity) {
		for (CSVRecord csvRecord : csvRecords) {
			CsvRequest csvRequest = new CsvRequest();
			csvRequest.setLaneNumber(csvRecord.get("*Lane Number"));
			csvRequest.setLaneDescription(csvRecord.get("Lane Description"));
			csvRequest.setOriginFacilityName(csvRecord.get("Origin Facility Name"));
			csvRequest.setOriginFacilityAddress(csvRecord.get("Origin Facility Address"));
			csvRequest.setOriginCity(csvRecord.get("Origin City"));
			csvRequest.setOriginState(csvRecord.get("Origin State"));
			csvRequest.setOriginPostalCode(csvRecord.get("Origin Postal Code"));
			csvRequest.setDestinationFacilityName(csvRecord.get("Destination Facility Name"));
			csvRequest.setDestinationFacilityAddress(csvRecord.get("Destination Facility Address"));
			csvRequest.setDestinationCity(csvRecord.get("Destination City"));
			csvRequest.setDestinationState(csvRecord.get("Destination State"));
			csvRequest.setDestinationPostalCode(csvRecord.get("Destination Postal Code"));
			csvRequest.setEquipmentType(csvRecord.get("*Equipment Type"));
			csvRequest.setEquipmentSize(csvRecord.get("Equipment Size"));
			csvRequest.setVolume(csvRecord.get("*Volume"));
			csvRequest.setCommodityName(csvRecord.get("*Commodity Name"));
			csvRequest.setWeightinPounds(csvRecord.get("*Weight in Pounds"));
			csvRequest.setModeType(csvRecord.get("*Mode Type"));
			csvRequest.setHaulLength(csvRecord.get("Haul Length"));
			csvRequest.setIncumbentName(csvRecord.get("Incumbent Name"));
			csvRequest.setIncumbentRate(csvRecord.get("Incumbent Rate"));
			csvRequest.setBenchmarkRate(csvRecord.get("Benchmark Rate"));
			csvRequest.setBidId(bidEntity.getBidId());
			csvRequestes.add(csvRequest);
		}
	}

	/**
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.SmcService#getFileById(int)
	 */
	@Override
	public List<FileUploadResponse> getFileById(int bidId) {
		List<BidDocumentEntity> list =  bidDocumentRepo.findByBidId(bidId);
		
		List<FileUploadResponse> uploadResponses = new ArrayList<>();
		for (BidDocumentEntity bidDocumentEntity : list) {
			FileUploadResponse fileUploadResponse = new FileUploadResponse(); 
			BeanUtils.copyProperties(bidDocumentEntity, fileUploadResponse);	
			fileUploadResponse.setBidEntity(bidDocumentEntity.getBidEntity().getBidId());
			uploadResponses.add(fileUploadResponse);
		}
		return uploadResponses;
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.SmcService#downloadFile(javax.servlet.http.HttpServletResponse, int)
	 */
	@Override
	public void downloadFile(HttpServletResponse response, int fileId) throws IOException {

		BidDocumentEntity fileUpload = bidDocumentRepo.findOne(fileId);
		if (fileUpload != null) {
			Path file = Paths.get(fileUpload.getDocumentPath()+ fileUpload.getDocumentName());
			if (Files.exists(file)) {
				try {
					String contentType = Files.probeContentType(file);
					response.setContentType(contentType);
					response.setHeader("Content-disposition", "attachment; filename=" + fileUpload.getDocumentName());
					Files.copy(file, response.getOutputStream());
					response.getOutputStream().flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				response.sendError(404, new FileNotFoundException().getMessage());
			}
		} else {
			response.sendError(404, new FileNotFoundException().getMessage());
		}
	
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.SmcService#deleteFile(int)
	 */
	@Override
	public void deleteFile(int id) {
		BidDocumentEntity fileUpload = bidDocumentRepo.findOne(id);
		File del = new File(fileUpload.getDocumentPath()+fileUpload.getDocumentName());
		del.delete();
		bidDocumentRepo.delete(fileUpload);
	
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.SmcService#exportCsv(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public void exportCsv(Integer carrierId, Integer bidId) {
		final String COMMA_DELIMITER = ",";
		final String LINE_SEPARATOR = "\n";

		final String HEADER = "Lane Number,Lane Description,Benchmark Rate,Origin Facility Name,Origin Facility Address,Origin City,Origin State,Origin Postal Code,Origin Country Code,Destination Facility Name,Destination Facility Address,Destination City,Destination State,Destination Postal Code,Destination Country Code,Equipment Type,Equipment Size,Volume,Commodity Name,Weight in Lbs,Mode Type,Haul Length,Pricing,Volume,Transit Days,Notes";

		List<Object[]> lanesEntities = lanesRepo.getAllLanesByBidAndCarrierId(carrierId, bidId);
		List<LanesHistoryResponse> laneResponse = new ArrayList<>();
		for (Object[] entitities : lanesEntities) {
			LanesHistoryResponse response = new LanesHistoryResponse();
			BeanUtils.copyProperties(entitities[0], response);
			mapLaneResponse((LanesEntity) entitities[0], response);

			BidResponseStatusEntity bidStatus = (BidResponseStatusEntity) entitities[1];
			response.setStatus(bidStatus.getStatus());
			if (bidStatus.getBidResponseDetails().getDays() != null) {
				response.setDays(String.valueOf(bidStatus.getBidResponseDetails().getDays()));
			}
			if (bidStatus.getBidResponseDetails().getRate() != null) {
				response.setRate(String.valueOf(bidStatus.getBidResponseDetails().getRate()));
			}
			if (bidStatus.getBidResponseDetails().getNotes() != null) {
				response.setNotes(bidStatus.getBidResponseDetails().getNotes());
			}
			laneResponse.add(response);
		}

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("RFPLaneBids_"+bidId+".csv");
			fileWriter.append(HEADER);
			fileWriter.append(LINE_SEPARATOR);
			Iterator<LanesHistoryResponse> it = laneResponse.iterator();
			while (it.hasNext()) {
				writeDataOnCsvFile(COMMA_DELIMITER, LINE_SEPARATOR, fileWriter, it);
			}
			System.out.println("Write to CSV file Succeeded!!!");
		} catch (Exception ee) {
			ee.printStackTrace();
		}
	    	finally
	    	{
	    		try
	    		{
	    			fileWriter.close();
	    		}
	    		catch(IOException ie)
	    		{
	    			System.out.println("Error occured while closing the fileWriter");
	    			ie.printStackTrace();
	    		}
	    	}
		}

	/**
	 * @param COMMA_DELIMITER
	 * @param LINE_SEPARATOR
	 * @param fileWriter
	 * @param it
	 * @throws IOException
	 */
	private void writeDataOnCsvFile(final String COMMA_DELIMITER, final String LINE_SEPARATOR, FileWriter fileWriter,
			Iterator<LanesHistoryResponse> it) throws IOException {
		LanesHistoryResponse e = (LanesHistoryResponse) it.next();
		fileWriter.append(String.valueOf(e.getLane()));
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getLane());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getBenchmarkRate());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(String.valueOf(e.getOriginName()));
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getOriginAddress());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getOriginCity());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getOriginState());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getOriginZipCode());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getOriginState());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getDestinationName());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getDestinationAddress());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getDestinationCity());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getDestinationZipcode());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getDestinationState());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(String.valueOf(e.getEquipmentType()));
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(String.valueOf(e.getEquipmentLength()));
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getVolume());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getCommodity());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getWeight());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getMode());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getHaul());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getRate());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getVolume());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getDays());
		fileWriter.append(COMMA_DELIMITER);
		fileWriter.append(e.getNotes());
		fileWriter.append(LINE_SEPARATOR);
	}
	
	/**
	 * @param entity
	 * @param response
	 */
	private void mapLaneResponse(LanesEntity entity, LanesHistoryResponse response) {
		ModeEntity modeEntity = modeRepo.findOne(entity.getMode());
		
		if (modeEntity != null)
		response.setMode(modeEntity.getModes());
		
		EquipmentEntity equipEntity = equipmentRepo.findOne(entity.getEquipment());
		response.setEquipmentClass(equipEntity.getEquipment());
		
		EquipmentEntity equipType = equipmentRepo.findOne(entity.getEquipmentType());
		response.setEquipmentType(equipType.getEquipment());
		
		EquipmentLengthEntity equipmentLengthEntity = equipmentLengthRepo.findOne(entity.getEquipmentLength());
		response.setEquipmentLength(equipmentLengthEntity.getEquipmentLength());
		
		response.setBidId(entity.getBid().getBidId());
		response.setModeId(entity.getMode());
		if (equipEntity.getParentId() != null) {
			response.setEquipmentClassId(equipEntity.getParentId().getEquipmentId());
		}
		response.setEquipmentLengthId(equipmentLengthEntity.getEquipmentLengthId());
		response.setEquipmentTypeId(equipType.getEquipmentId());
	}

	/* (non-Javadoc)
	 * @see com.bid.smc.service.SmcService#importCsv(java.lang.Integer, java.lang.Integer, org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public void importCsv(Integer bidId, Integer carrierId, MultipartFile csvFile) throws IOException {

		BaseResponse response = new BaseResponse();
		String extension = csvFile.getOriginalFilename().substring(csvFile.getOriginalFilename().lastIndexOf(".") + 1);
		CSVParser csvParser = null;
		if (extension.equalsIgnoreCase("csv")) {
			try {
				csvParser = CSVFormat.EXCEL.withHeader().parse(new InputStreamReader(csvFile.getInputStream()));
				Iterable<CSVRecord> csvRecords = csvParser.getRecords();
				BidEntity bidEntity = bidRepo.findOne(bidId);
				
				validateBidDetails(response, bidEntity);

				HashMap<String, LanesHistoryRequest> map = new HashMap<>();
				
				for (CSVRecord csvRecord : csvRecords) {
					LanesHistoryRequest csvRequest = mapToCSVRequest(bidEntity, csvRecord);
					map.put(csvRequest.getLane(), csvRequest);
				}
				
				List<LanesEntity> lanesEntities = lanesRepo.findLanesByBidId(bidId);

				List<BidResponseDetailsEntity> entities = new ArrayList<>();

				for (LanesEntity lane : lanesEntities) {
					
					if (map.get(lane.getLane()) != null) {
						LanesHistoryRequest csvRequest = map.get(lane.getLane());

						if (csvRequest.getLane().equals(lane.getLane())) {
							BidResponseStatusEntity bidResponseStatusEntity = bidResponseStatusRepository
									.findByBidIdAndLaneId(bidId, lane.getLaneId(), carrierId);

							BidResponseDetailsEntity bidResponseDetailsEntity = updateBidResponse(csvRequest,
									bidResponseStatusEntity);

							entities.add(bidResponseDetailsEntity);
						}
					}
				}
				bidResponseDetailsRepository.save(entities);
			} catch (Exception e) {
				
			}
		}
	}

	private void validateBidDetails(BaseResponse response, BidEntity bidEntity) {
		if (bidEntity == null) {
			response.setStatus(404);
			response.setErrorMessage(SmcConstants.NOT_FOUND);
			response.setText(SmcConstants.RECORD_NOT_FOUND);
			throw new RecordNotFoundException(SmcConstants.RECORD_NOT_FOUND);
		}
	}

	/**
	 * @param csvRequest
	 * @param bidResponseStatusEntity
	 * @return
	 */
	private BidResponseDetailsEntity updateBidResponse(LanesHistoryRequest csvRequest,
			BidResponseStatusEntity bidResponseStatusEntity) {
		BidResponseDetailsEntity bidResponseDetailsEntity = bidResponseStatusEntity.getBidResponseDetails();
		
		bidResponseDetailsEntity.setDays(Integer.parseInt(csvRequest.getDays()));
		bidResponseDetailsEntity.setNotes(csvRequest.getNotes());
		bidResponseDetailsEntity.setVolume(csvRequest.getVolume());
		bidResponseDetailsEntity.setRate(Integer.parseInt(csvRequest.getRate()));
		return bidResponseDetailsEntity;
	}

	/**
	 * @param bidEntity
	 * @param csvRecord
	 * @return
	 */
	private LanesHistoryRequest mapToCSVRequest(BidEntity bidEntity, CSVRecord csvRecord) {
		LanesHistoryRequest csvRequest = new LanesHistoryRequest();
		csvRequest.setLane(csvRecord.get("Lane Number"));
		csvRequest.setLaneDescription(csvRecord.get("Lane Description"));
		csvRequest.setBenchmarkRate(csvRecord.get("Benchmark Rate"));
		csvRequest.setOriginName(csvRecord.get("Origin Facility Name"));
		csvRequest.setOriginAddress(csvRecord.get("Origin Facility Address"));
		csvRequest.setOriginCity(csvRecord.get("Origin City"));
		csvRequest.setOriginState(csvRecord.get("Origin State"));
		csvRequest.setOriginZipCode(csvRecord.get("Origin Postal Code"));
		csvRequest.setOriginState(csvRecord.get("Origin Country Code"));
		csvRequest.setDestinationName(csvRecord.get("Destination Facility Name"));
		csvRequest.setDestinationAddress(csvRecord.get("Destination Facility Address"));
		csvRequest.setDestinationCity(csvRecord.get("Destination City"));
		csvRequest.setDestinationState(csvRecord.get("Destination State"));
		csvRequest.setDestinationZipcode(csvRecord.get("Destination Postal Code"));
		csvRequest.setOriginState(csvRecord.get("Destination Country Code"));
		csvRequest.setEquipmentType(csvRecord.get("Equipment Type"));
		csvRequest.setEquipmentLength(csvRecord.get("Equipment Size"));
		csvRequest.setVolume(csvRecord.get("Volume"));
		csvRequest.setCommodity(csvRecord.get("Commodity Name"));
		csvRequest.setWeight(csvRecord.get("Weight in Lbs"));
		csvRequest.setMode(csvRecord.get("Mode Type"));
		csvRequest.setHaul(csvRecord.get("Haul Length"));
		csvRequest.setRate(csvRecord.get("Pricing"));
		csvRequest.setVolume(csvRecord.get("Responded Volume"));
		csvRequest.setDays(csvRecord.get("Transit Days"));
		csvRequest.setNotes(csvRecord.get("Notes"));
		csvRequest.setBidId(bidEntity.getBidId());
		return csvRequest;
	}
	
	

}
