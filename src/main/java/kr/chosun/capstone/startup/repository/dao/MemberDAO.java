package kr.chosun.capstone.startup.repository.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.chosun.capstone.startup.repository.dto.Member;
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
}
