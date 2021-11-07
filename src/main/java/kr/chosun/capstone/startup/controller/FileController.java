package kr.chosun.capstone.startup.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(@RequestParam(value="file",required = true)String fileName){
		File file = new File(FileUploadService.UPLOADFOLDER+"\\",fileName);
		
		//존재하지 않는 파일이면 NOT_FOUND 응답
		if(file.exists()==false) {
			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(
					FileCopyUtils.copyToByteArray(file),
					header,
					HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource>downloadFile(@RequestParam(value="file", required = true) String fileName){
		Resource resource = 
				new FileSystemResource(FileUploadService.UPLOADFOLDER+"\\"+fileName);
		//존재하지 않는 파일이라면 NOT_FOUND 
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		//UUID 분리 후 파일명 구하기
		String resourceName = resource.getFilename().split("_")[1];
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity<Resource> result = null;
		try {
			//다운로드 시 저장이름
			headers.add("Content-Disposition", 
					"attachment; filename="+ new String(resourceName.getBytes("UTF-8"),"ISO-8859-1")); 
			result = new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
