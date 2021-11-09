package kr.chosun.capstone.startup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.chosun.capstone.startup.repository.dao.CharacterDAO;
import kr.chosun.capstone.startup.repository.dao.MemberDAO;
import kr.chosun.capstone.startup.repository.dto.Character;
import kr.chosun.capstone.startup.repository.dto.Member;
import kr.chosun.capstone.startup.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDao;
	@Autowired
	private CharacterDAO characterDao;
	
	@Override
	public List<Member> getMembers() {
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public int register(Member member) {
		int memSeq=memberDao.insert(member);
		if(member.getMemType().equals("STUDENT")) { //만약에 대학생을 선택했다면 캐릭터까지 DB에 넣어준다.
			Character character =  member.getCharacter();
			character.setMemSeq(memSeq);
			characterDao.insertCharacter(character);
			characterDao.insertAward(character.getMemAwards(), memSeq);
			characterDao.insertPPLink(character.getMemPPLinks(), memSeq);
			characterDao.insertSkillMember(character.getMemSkills(), memSeq);
		}
		return 0;
	}


}
