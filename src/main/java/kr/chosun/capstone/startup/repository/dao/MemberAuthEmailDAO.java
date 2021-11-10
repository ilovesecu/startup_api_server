package kr.chosun.capstone.startup.repository.dao;

import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
				.withTableName("member_auth_email");
	}
	
	//해당 인증코드의 갯수를 반환한다. (중복확인을 위함)
	public int selectAuthcodeCnt(String authCode) {
		String sql = "SELECT COUNT(*) FROM member_auth_email WHERE mae_auth_code = :authCode";
		return jdbc.queryForObject(sql, Collections.singletonMap("authCode", authCode),Integer.class);
	}
}
