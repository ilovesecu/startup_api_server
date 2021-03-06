package kr.chosun.capstone.startup.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.chosun.capstone.startup.repository.dao.CharacterDAO;
import kr.chosun.capstone.startup.repository.dao.MemberDAO;
import kr.chosun.capstone.startup.repository.dto.Character;
import kr.chosun.capstone.startup.repository.dto.Member;
import kr.chosun.capstone.startup.service.AuthService;
import kr.chosun.capstone.startup.service.MemberService;
import kr.chosun.capstone.startup.service.mail.MailService;
import kr.chosun.capstone.startup.service.mail.MailType;
import kr.chosun.capstone.startup.utils.AuthUtil;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDao;
	@Autowired
	private CharacterDAO characterDao;
	@Autowired
	private AuthService authService;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	//모든 멤버 조회
	@Override
	public List<Member> getMembers() {
		return null;
	}
	
	//회원가입
	@Override
	@Transactional(readOnly = false)
	public Member register(Member member) {
		//패스워드 암호화
		String rawPassword = member.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		member.setPassword(encPassword);
		
		int memSeq=memberDao.insert(member);
		member.setMemSeq(memSeq);
		if(member.getMemType().equals("STUDENT")) { //만약에 대학생을 선택했다면 캐릭터까지 DB에 넣어준다.
			Character character =  member.getCharacter();
			character.setMemSeq(memSeq);
			characterDao.insertCharacter(character);
			characterDao.insertAward(character.getMemAwards(), memSeq);
			characterDao.insertPPLink(character.getMemPPLinks(), memSeq);
			characterDao.insertSkillMember(character.getMemSkills(), memSeq);
		}
		
		//인증메일 전송 (해당 부분가지 하나의 트랜잭션으로 관리)
		try {
			int sendResult = authService.sendAuthMail(memSeq, MailType.REGISTER);
			if(sendResult==0)return null;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		if(memSeq!=0)
			return member;
		else
			return null;
	}
	
	//특정 멤버조회
	@Override
	public Member getMember(int memSeq) {
		return memberDao.selectMemberWithoutJoin(memSeq);
	}
	
	//특정 멤버에 대한 (수상내역, 스킬, 포트폴리오 조회)
	@Override
	public Member getMemberProfile(int memSeq) {
		return memberDao.selectMemberWithBasicJoin(memSeq);
	}
	
	//멤버 memStat 업데이트
	@Override
	public int updateMemStat(int memSeq, String memStat) {
		return memberDao.updateMemStat(memSeq, memStat);
	}
	
	//멤버 아이디 갯수 확인 (중복확인에 활용)
	@Override
	public int getMemIdCnt(String memId) {
		return memberDao.selectMemId(memId);
	}
	
	//멤버 이메일 갯수 확인(중복확인에 활용)
	@Override
	public int getMemEmailCnt(String memEmail) {
		return memberDao.selectMemEmail(memEmail);
	}
}
