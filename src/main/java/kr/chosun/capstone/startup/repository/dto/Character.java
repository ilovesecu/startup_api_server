package kr.chosun.capstone.startup.repository.dto;

import java.util.List;

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
	private List<MemberPPLink> memPPLinks;
	private List<MemberAwards> memAwards;
	
	/*
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public class MemberPPLink{
		private int mplSeq;
		private int memSeq;
		private String mplContent;
	}
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@ToString
	public class MemberAwards{
		private int awardSeq;
		private int memSeq;
		private String awardContent;
		private String awardTime;
	}
	*/
}
