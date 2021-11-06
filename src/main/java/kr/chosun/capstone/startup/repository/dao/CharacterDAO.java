package kr.chosun.capstone.startup.repository.dao;

import java.util.List;

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

@Repository
public class CharacterDAO {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert characterInsertAction;
	private SimpleJdbcInsert ppLinkInsertAction;
	private SimpleJdbcInsert awardInsertAction;
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
	}
	//캐릭터 정보 INSERT
	public int insertCharacter(Character character) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(character);
		return characterInsertAction.execute(params);
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
}
