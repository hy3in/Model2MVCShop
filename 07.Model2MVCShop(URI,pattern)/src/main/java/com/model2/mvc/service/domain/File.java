package com.model2.mvc.service.domain;

import org.springframework.web.multipart.MultipartFile;

public class File {
	private int fileNo;
	private int prodNo;
	private String fileName;
	private MultipartFile uploadFile;
	
	
	
	public MultipartFile getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public int getProdNo() {
		return prodNo;
	}
	public void setProdNo(int prodNo) {
		this.prodNo = prodNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public String toString() {
		return "File [fileNo=" + fileNo + ", prodNo=" + prodNo + ", fileName=" + fileName + "]";
	}
		
}
