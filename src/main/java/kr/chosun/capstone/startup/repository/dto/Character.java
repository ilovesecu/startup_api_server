package kr.chosun.capstone.startup.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Character {
	private int memSeq;
	private String memNickname;
	private int univSeq;
	private int teamSeq;
	private String memMajor;
	//private String memMinor;
	private String memMbti;
	private int profileSeq; //FileInfo
}
