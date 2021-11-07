package kr.chosun.capstone.startup.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UploadFile {
	private int fileSeq;
	private String fileRealNm;
	private String fileStoredNm;
	private String filePath;
	private String fileType;
	private String deleteFlag;
	private String createDate;
	private String modifyDate;
}
