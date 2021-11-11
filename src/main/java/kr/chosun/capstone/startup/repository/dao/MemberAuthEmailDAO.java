package kr.chosun.capstone.startup.repository.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class MemberAuthEmailDAO{
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	public MemberAuthEmailDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.usingGeneratedKeyColumns("mae_seq")
				.usingColumns("mem_seq","mae_auth_code","mae_type")
				.withTableName("member_auth_email");
	}
	
	//해당 인증코드의 갯수를 반환한다. (중복확인을 위함)
	public int selectAuthcodeCnt(String authCode) {
		String sql = "SELECT COUNT(*) FROM member_auth_email WHERE mae_auth_code = :authCode";
		return jdbc.queryForObject(sql, Collections.singletonMap("authCode", authCode),Integer.class);
	}
	
	//인증코드를 삽입한다
	public int insertMemberAuthEmail(int memSeq, String authCode, int maeType) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("mem_seq", memSeq);
		params.put("mae_auth_code", authCode);
		params.put("mae_type", maeType);
		return insertAction.execute(params);
	}
}
