package kr.chosun.capstone.startup.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberAuthEmail {
	private int maeSeq;
	private int memSeq;
	private String maeAuthCode;
	private int maeType; //1. 회원가입 / 2.이메일변경 / 3. 패스워드 분실
	private String maeGenTime; //인증 코드 생성시간
	private String maeAuthTime;//인증 시간
	private int maeExpired; //1:만료, 0:만료X 
}
