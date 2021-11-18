package kr.chosun.capstone.startup.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
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
import kr.chosun.capstone.startup.repository.dto.UploadFile;

import static kr.chosun.capstone.startup.repository.dao.sqls.MemberSqls.*;

@Repository
public class MemberDAO {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Member> memberMapper = BeanPropertyRowMapper.newInstance(Member.class);

	public MemberDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("member")
				.usingGeneratedKeyColumns("mem_seq");
	}
	
	public int insert(Member member) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		Number newMemSeq=insertAction.executeAndReturnKey(params);
		return newMemSeq.intValue();
	}
	
	// 멤버와 관련된 테이블 조인(member + character + awards + portfolio + skill)
	public Member selectMemberWithBasicJoin(int memSeq) {
		Map<String, Object> params = Collections.singletonMap("memSeq", memSeq);
		return jdbc.queryForObject(SELECT_MEMBER_BASIC, params, new RowMapper<Member>() {
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				Member member = memberPropertyInjection(rs);
				//member를 정상적으로 가져왔고, STUDENT라면 Character와 그 관련된 정보를 모두 가져온다.
				if(member!=null && member.getMemType().equals("STUDENT")) {
					Character character=characterPropertInjection(rs);
					member.setCharacter(character);
				}
				return member;
			}
		});
	}
	
	//멤버와 관련된 테이블 조인없이 Member테이블의 내용만 조회
	public Member selectMemberWithoutJoin(int memSeq) {
		String sql = "SELECT * FROM member WHERE mem_seq=:mem_seq";
		Map<String,Integer> params = Collections.singletonMap("mem_seq", memSeq);
		return (Member) jdbc.queryForObject(sql, params, memberMapper);
	}
	
	//멤버테이블의 memStat 컬럼 업데이트
	public int updateMemStat(int memSeq, String memStat) {
		Map<String,Object> params = new HashMap<>();
		params.put("memSeq", memSeq);
		params.put("memStat", memStat);
		return jdbc.update(UPDATE_MEMSTAT, params);
	}
	
	//멤버 테이블 ID존재 여부 확인 (ID중복확인에 활용)
	public int selectMemId(String memId) {
		Map<String,Object> params = Collections.singletonMap("memId", memId);
		String sql = "SELECT COUNT(*) FROM member WHERE mem_id=:memId";
		return jdbc.queryForObject(sql, params, Integer.class);
	}
	
	//멤버 테이블 email 존재 여부 확인(학생의 경우 이메일 중복이 안되므로)
	public int selectMemEmail(String memEmail) {
		Map<String,Object> params = Collections.singletonMap("memEmail", memEmail);
		String sql = "SELECT COUNT(*) FROM member WHERE mem_email=:memEmail";
		return jdbc.queryForObject(sql, params, Integer.class);
	}
	
	//ResultSet을 직접 제어할 때 Member테이블 -> Member 객체로 맵핑해주는 메소드
	private Member memberPropertyInjection(ResultSet rs) throws SQLException {
		int memSeq = rs.getInt("mem_seq");
		String memId = rs.getString("mem_id");
		String memNm = rs.getString("mem_nm");
		String memType = rs.getString("mem_type");
		String introduce = rs.getString("introduce");
		String memEmail = rs.getString("mem_email");
		String memPhone = rs.getString("mem_phone");
		Member member = Member.builder()
				.memSeq(memSeq).memId(memId).memNm(memNm).introduce(introduce)
				.memType(memType).memEmail(memEmail).memPhone(memPhone).build();
		return member;
	}
	//ResultSet을 직접 제어할 때 Character_테이블 -> Character 객체로 맵핑해주는 메소드
	private Character characterPropertInjection(ResultSet rs) throws SQLException {
		//rs.first();
		int memSeq = rs.getInt("mem_seq");
		String memNickname = rs.getString("mem_nickname");
		String memMajor = rs.getString("mem_major");
		String memMbti = rs.getString("mem_mbti");
		int profileSeq = rs.getInt("profile_seq");
		String profilePath = rs.getString("profile_path");
		String profileNm = rs.getString("profile_nm");
		UploadFile profileImgFile = UploadFile.builder()
				.fileSeq(profileSeq).filePath(profilePath).fileStoredNm(profileNm)
				.build();
		Character character = Character.builder()
				.memSeq(memSeq).memNickname(memNickname).memMajor(memMajor)
				.memMbti(memMbti).memProfileImage(profileImgFile)
				.build();
		getCharacterAdditionalInfo(rs,character);
		return  character;
	}
	//ResultSet을 직접 제어할 때 Character테이블의 1:N 관계 테이블(PP, Skill, Award) 맵핑
	private void getCharacterAdditionalInfo(ResultSet rs, Character character) throws SQLException {
		List<MemberAwards> awards = new ArrayList<>();
		List<MemberPPLink> pps = new ArrayList<>();
		List<Skill> skills = new ArrayList<>();
		int memSeq = character.getMemSeq();
		
		int awardSeq = rs.getInt("award_seq");
		String awardContent = rs.getString("award_content");
		String awardTime = rs.getString("award_time");
		int mplSeq = rs.getInt("mpl_seq");
		String mplContent = rs.getString("mpl_content");
		int skillSeq = rs.getInt("skill_seq");
		String skillNm = rs.getString("skill_nm");
		String skillClassification = rs.getString("skill_classification");
		
		if(awardSeq!=0) 
			awards.add(new MemberAwards(awardSeq,memSeq,awardContent,awardTime));
		if(mplSeq!=0) 
			pps.add(new MemberPPLink(mplSeq,memSeq, mplContent));
		if(skillSeq!=0)
			skills.add(new Skill(skillSeq, skillNm, skillClassification, null));
		
		boolean duplicateFlag = false;
		//1:N 관계의 레코드를 모두 넣어준다.
		while(rs.next()) {
			awardSeq = rs.getInt("award_seq");
			if(awardSeq != 0){
				for(int i=0; i<awards.size(); i++) {
					//리스트에 이미 있는 수상 번호라면 바로 종료한다.
					if(awards.get(i).getAwardSeq() == awardSeq) {
						duplicateFlag = true;
						break;
					}
				}
				if(duplicateFlag==false) {
					awardContent = rs.getString("award_content");
					awardTime = rs.getString("award_time");
					awards.add(new MemberAwards(awardSeq,memSeq,awardContent,awardTime));
				}
				duplicateFlag=false; //플래그 초기화
			}
			
			mplSeq = rs.getInt("mpl_seq");
			if(mplSeq != 0){
				for(int i=0; i<pps.size(); i++) {
					//리스트에 이미 있는 수상 번호라면 바로 종료한다.
					if(pps.get(i).getMplSeq() == mplSeq) {
						duplicateFlag = true;
						break;
					}
				}
				if(duplicateFlag==false) {
					mplContent = rs.getString("mpl_content");
					pps.add(new MemberPPLink(mplSeq,memSeq, mplContent));
				}
				duplicateFlag=false; //플래그 초기화
			}
			
			skillSeq = rs.getInt("skill_seq");
			if(skillSeq != 0){
				for(int i=0; i<skills.size(); i++) {
					//리스트에 이미 있는 수상 번호라면 바로 종료한다.
					if(skills.get(i).getSkillSeq() == skillSeq) {
						duplicateFlag = true;
						break;
					}
				}
				if(duplicateFlag==false) {
					skillNm = rs.getString("skill_nm");
					skillClassification = rs.getString("skill_classification");
					skills.add(new Skill(skillSeq, skillNm, skillClassification, null));
				}
				duplicateFlag=false; //플래그 초기화
			}
		}
		character.setMemAwards(awards);
		character.setMemPPLinks(pps);
		character.setMemSkills(skills);
	}
}
