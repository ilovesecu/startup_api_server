package kr.chosun.capstone.startup.service.impl;

import javax.mail.MessagingException;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.chosun.capstone.startup.config.ApplicationConfig;
import kr.chosun.capstone.startup.repository.dao.MemberAuthEmailDAO;
import kr.chosun.capstone.startup.repository.dto.Member;
import kr.chosun.capstone.startup.repository.dto.MemberAuthEmail;
import kr.chosun.capstone.startup.service.AuthService;
import kr.chosun.capstone.startup.service.MemberService;
import kr.chosun.capstone.startup.service.mail.MailService;
import kr.chosun.capstone.startup.service.mail.MailType;
import kr.chosun.capstone.startup.utils.AuthUtil;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private MemberAuthEmailDAO memberAuthEmailDao;
	@Autowired
	private AuthUtil authUtil;
	@Autowired
	private MailService mailService;
	@Autowired
	private MemberService memberService;

	// DB에 저장되어있는 mail_auth_code의 숫자를 조회 (중복된 것이 있는지 조회하기위함)
	@Override
	public int getAuthCodeCnt(String authCode) {
		return memberAuthEmailDao.selectAuthcodeCnt(authCode);
	}

	// 인증메일 전송
	@Override
	public int sendAuthMail(int memSeq, MailType mailType) throws MessagingException {
		String authCode = authUtil.authCodeGenerator(); // 인증코드 생성
		Member member = memberService.getMember(memSeq);
		if(member==null) return 0; //해당 memSeq의 회원이 없다면 그대로 종료
		
		//인증코드가 DB에 정상적으로 삽입되어야 메일을 보낸다.
		if(authInfoInsert(memSeq,authCode, mailType)==1) {
			MailContent mailContent = mailContentGenerator(mailType, authCode);
			System.out.println("AuthMail Member:"+member);
			return mailService.sendMail(member.getMemEmail(), mailContent.subject, mailContent.content);
		}
		return 0;
	}
	
	//만료되지 않은 인증코드 링크 클릭 시 인증해주는 메소드
	@Override
	@Transactional(readOnly = false)
	public int authenticationAuthCode(MemberAuthEmail memberAuthEmail) {
		int result = memberAuthEmailDao.updateAuthCode(memberAuthEmail);
		/*인증시간을 정상적으로 업데이트 했다면 memSeq를 가져와서
		  Member테이블에서 mem_stat도 AUTH → NORMAL 로업데이트한다.*/
		if(result == 1) { 
			MemberAuthEmail rs=getAuthInfoWithAuthCode(memberAuthEmail.getMaeAuthCode());
			Member member = memberService.getMember(rs.getMemSeq());
			
		}
		return result;
	}
	//인증정보 조회(인증코드로 조회)
	@Override
	public MemberAuthEmail getAuthInfoWithAuthCode(String authCode) {
		return memberAuthEmailDao.selectWithAuthCode(authCode);
	}
	
	//발급된 인증 코드 DB에 insert
	private int authInfoInsert(int memSeq, String authCode, MailType mailType) {
		return memberAuthEmailDao.insertMemberAuthEmail(memSeq, authCode, mailType.value());
	}

	// 인증메일 내용 생성기
	private MailContent mailContentGenerator(MailType type, String authCode) {
		MailContent mailContent = new MailContent();
		switch (type) {
		case REGISTER:
			mailContent.subject = "회원가입 완료를 위해 인증 해주세요.";
			mailContent.content = """
					<h1>스팀 회원가입을 환영합니다!</h1>
					회원 인증을 위해서 아래의 링크를 클릭해주세요.<br/>
					<a href='http://%s:8080/startup/auth/%s'>인증완료하기</a><br/><br/><br/>
					<img src='http://%s:8080/startup/display?file=2021/11/08/logo.png'/>
					""".formatted(ApplicationConfig.SERVER_CHANGER, authCode, ApplicationConfig.SERVER_CHANGER);
			break;
		case CHANGE_MAIL:
			break;
		case PASSWORD:
			break;
		default:
			return null;
		}
		return mailContent;
	}

	class MailContent {
		private String subject;
		private String content;
	}


}
