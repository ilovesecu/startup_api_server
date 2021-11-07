package kr.chosun.capstone.startup.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.chosun.capstone.startup.repository.dto.UploadFile;

public interface FileUploadService {
	public static String UPLOADFOLDER = "C:\\upload\\startup";
	public List<UploadFile> uploadFile(MultipartFile[] multipartFiles);
	//int deleteFile(String uuid);
}
