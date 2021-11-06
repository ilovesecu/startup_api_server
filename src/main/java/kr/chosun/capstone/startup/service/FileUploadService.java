package kr.chosun.capstone.startup.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
	void uploadFile(MultipartFile[] multipartFiles);
	//int deleteFile(String uuid);
}
