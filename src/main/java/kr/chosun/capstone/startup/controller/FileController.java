package kr.chosun.capstone.startup.controller;

import java.io.File;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
	@PostMapping("/uploadFile")
	public String uploadFile(MultipartFile[] uploadFile) {
		String uploadFolder = "C:\\upload\\startup";
		for(MultipartFile multipartFile : uploadFile) {
			System.out.println("upload File name:"+multipartFile.getOriginalFilename());
			System.out.println("upload File size:"+multipartFile.getSize());
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			try {
				multipartFile.transferTo(saveFile);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return "ok";
	}
}
