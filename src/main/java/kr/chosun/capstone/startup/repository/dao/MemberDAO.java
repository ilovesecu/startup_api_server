package kr.chosun.capstone.startup.repository.dao;

import java.util.Collections;
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
}
