package com.bid.smc.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import com.bid.smc.response.CsvUploadErrorResponse;
import com.bid.smc.response.FileUploadResponse;
import com.bid.smc.response.UploadDocumentResponse;

public interface SmcService extends UserDetailsService {

	public List<UploadDocumentResponse> saveUploadedFiles(List<MultipartFile> files, Integer bidId) throws IOException;
	
	public List<CsvUploadErrorResponse> processCsv(Integer bidId, MultipartFile csvFile) throws IOException;

	public List<FileUploadResponse> getFileById(int bidId);

	public void downloadFile(HttpServletResponse response, int fileId) throws IOException;

	public void deleteFile(int id);
	
	public void exportCsv(Integer carrierId, Integer bidId);
	
	public void importCsv(Integer carrierId, Integer bidId, MultipartFile csvFile)throws IOException;;
	
}
