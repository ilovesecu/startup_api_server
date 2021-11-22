package kr.chosun.capstone.startup.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Member {
	private int memSeq;
	private String memId;
	private String password;
	private String memNm;
	private String introduce;
	private String memEmail;
	private String memStat;
	private String memPhone;
	private String createDate;
	private String memType;
	private Character character;
	/*@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public class Character {
		private int memSeq;
		private String memNickname;
		private int univSeq;
		private int teamSeq;
		private String memMajor;
		private String memMinor;
		private String memMbti;
		private String profileSeq; //FileInfo
	}*/
}
