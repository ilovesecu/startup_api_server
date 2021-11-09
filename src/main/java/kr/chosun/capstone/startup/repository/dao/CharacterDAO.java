package kr.chosun.capstone.startup.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.chosun.capstone.startup.repository.dto.Character;
import kr.chosun.capstone.startup.repository.dto.Member;
import kr.chosun.capstone.startup.repository.dto.MemberAwards;
import kr.chosun.capstone.startup.repository.dto.MemberPPLink;
import kr.chosun.capstone.startup.repository.dto.Skill;

@Repository
public class CharacterDAO {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert characterInsertAction;
	private SimpleJdbcInsert ppLinkInsertAction;
	private SimpleJdbcInsert awardInsertAction;
	private SimpleJdbcInsert skillMemberInsertAction; //캐릭터-스킬 관계삽입
	private RowMapper<Character> characteryMapper = BeanPropertyRowMapper.newInstance(Character.class);
	
	public CharacterDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.characterInsertAction = new SimpleJdbcInsert(dataSource)
				.usingColumns("mem_seq","mem_nickname","univ_seq","mem_major","mem_mbti")
				.withTableName("character_");
		this.ppLinkInsertAction = new SimpleJdbcInsert(dataSource)
				.usingGeneratedKeyColumns("mpl_seq")
				.withTableName("member_pp_link");
		this.awardInsertAction = new SimpleJdbcInsert(dataSource)
				.usingGeneratedKeyColumns("award_seq")
				.withTableName("member_awards");
		this.skillMemberInsertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("member_skill");
	}
	//캐릭터 정보 INSERT
	public int insertCharacter(Character character) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(character);
		return characterInsertAction.execute(params);
	}
	//캐릭터 정보-스킬 INSERT (스킬-멤버 테이블은 skillSeq,memSeq가 필요하므로 이렇게 파라미터를 받아서 처리)
	public int insertSkillMember(int skillSeq, int memSeq) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("skill_seq", skillSeq);
		params.put("mem_seq", memSeq);
		return skillMemberInsertAction.execute(params);
	}
	//포트폴리오 링크 INSERT
	public int insertPPLink(MemberPPLink memberPPLink) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(memberPPLink);
		return ppLinkInsertAction.execute(params);
	}
	//수상경력 INSERT
	public int insertAward(MemberAwards memberAwards) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(memberAwards);
		return awardInsertAction.execute(params);
	}
	//포트폴리오 링크 리스트 INSERT
	public int insertPPLink(List<MemberPPLink> list, int memSeq) {
		int resultCnt=0;
		for(MemberPPLink link : list) {
			link.setMemSeq(memSeq);
			resultCnt+=insertPPLink(link);
		}
		return resultCnt;
	}
	//수상경력 리스트 INSERT
	public int insertAward(List<MemberAwards> list, int memSeq) {
		int resultCnt=0;
		for(MemberAwards award : list) {
			award.setMemSeq(memSeq);
			resultCnt+=insertAward(award);
		}
		return resultCnt;
	}
	//스킬 리스트 INSERT
	public int insertSkillMember(List<Skill>list, int memSeq) {
		int resultCnt=0;
		for(Skill skill:list) {
			insertSkillMember(skill.getSkillSeq(), memSeq);
		}
		return resultCnt;
	}
}
