package kr.chosun.capstone.startup.controller;

import java.io.File;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.chosun.capstone.startup.repository.dto.UploadFile;
import kr.chosun.capstone.startup.service.FileUploadService;

@RestController
public class FileController {
	@Autowired
	private FileUploadService fileUploadService;
	
	@PostMapping("/uploadFile")
	public ResponseEntity<List<UploadFile>> uploadFile(
			@RequestParam(value="uploadFile",required = true) 
			MultipartFile[] uploadFiles) {
		List<UploadFile> fileInfoList = fileUploadService.uploadFile(uploadFiles);
		return new ResponseEntity<List<UploadFile>>(fileInfoList, HttpStatus.OK);
	}
}
