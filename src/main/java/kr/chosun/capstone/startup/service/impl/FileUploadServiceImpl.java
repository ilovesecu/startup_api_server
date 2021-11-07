package kr.chosun.capstone.startup.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.chosun.capstone.startup.repository.dao.FileDAO;
import kr.chosun.capstone.startup.repository.dto.UploadFile;
import kr.chosun.capstone.startup.service.FileUploadService;
import kr.chosun.capstone.startup.utils.DateUtil;
import kr.chosun.capstone.startup.utils.FileUtil;

@Service
public class FileUploadServiceImpl implements FileUploadService {
	@Autowired
	private FileDAO fileDao;
	
	@Override
	@Transactional(readOnly = false)
	public List<UploadFile> uploadFile(MultipartFile[] multipartFiles) {
		List<UploadFile> uploadFiles = new ArrayList<>();
		File uploadPath = new File(UPLOADFOLDER,DateUtil.getYYYYMMddStr());
		System.out.println("uploadPath"+uploadPath);
		
		//디렉토리가 존재하지 않으면 생성
		if(!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
	
		for(MultipartFile multipartFile : multipartFiles) {
			UploadFile dbFile = new UploadFile(); //DB에 저장될 데이터
			dbFile.setFilePath(DateUtil.getYYYYMMddStr()); //저장 디렉토리
			
			String uploadFileName = multipartFile.getOriginalFilename();
			System.out.println("upload File name:"+uploadFileName);
			System.out.println("upload File size:"+multipartFile.getSize());
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\")+1);
			dbFile.setFileRealNm(uploadFileName); //사용자가 업로드한 실제 파일명
			
			//이름중복 방지를 위한 uuid값 
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid+"_"+uploadFileName;
			dbFile.setFileStoredNm(uploadFileName); //서버에 저장된 파일명(중복제거)
			
			System.out.println("upload File name:"+uploadFileName);
			
			//mimeType 확인
			File saveFile = new File(uploadPath, uploadFileName);
			String contentType = FileUtil.checkMimeType(saveFile);
			dbFile.setFileType(contentType);
			
			try {
				multipartFile.transferTo(saveFile); //파일 저장
				int fileSeq=fileDao.insertUploadFile(dbFile); //DB에 파일정보 저장
				dbFile.setFileSeq(fileSeq);
				uploadFiles.add(dbFile);
			}catch(Exception e) {
				e.printStackTrace();
			}		
		}
		return uploadFiles;
	}

}
