package kr.chosun.capstone.startup.service;

import javax.mail.MessagingException;

import kr.chosun.capstone.startup.repository.dto.MemberAuthEmail;
import kr.chosun.capstone.startup.service.mail.MailType;

//인증관련 처리
public interface AuthService {
	//DB에 저장되어있는 mail_auth_code의 숫자를 조회 (중복된 것이 있는지 조회하기위함)
	public int getAuthCodeCnt(String authCode); 
	//인증메일 전송
	public int sendAuthMail(int memSeq, MailType mailType) throws MessagingException;
	//인증코드 인증완료
	public int authenticationAuthCode(MemberAuthEmail memberAuthEmail);
	//인증정보 조회(인증코드로 조회)
	public MemberAuthEmail getAuthInfoWithAuthCode(String authCode);
}
