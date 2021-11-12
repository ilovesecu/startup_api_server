package kr.chosun.capstone.startup.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Skill {
	private int skillSeq;
	private String skillNm;
	private String skillClassification;
	private String fileSeq;
	
}
