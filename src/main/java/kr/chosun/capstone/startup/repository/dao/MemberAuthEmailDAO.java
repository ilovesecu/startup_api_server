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

import kr.chosun.capstone.startup.repository.dto.MemberAuthEmail;
import kr.chosun.capstone.startup.utils.DateUtil;
import static kr.chosun.capstone.startup.repository.dao.sqls.MemberAuthEmailSqls.*;

@Repository
public class MemberAuthEmailDAO{
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<MemberAuthEmail> rowMapper = BeanPropertyRowMapper.newInstance(MemberAuthEmail.class);
	
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
	
	//인증코드 링크 클릭 시 인증되는 메소드 
	//(mae_expired 값이 1이면 인증만료이므로 인증되지 않도록 구현)
	public int updateAuthCode(MemberAuthEmail memberAuthEmail) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(memberAuthEmail);
		return jdbc.update(UPDATE_AUTH_TIME, params);
	}
	
	//인증코드로 하나의 행을 조회한다.
	public MemberAuthEmail selectWithAuthCode(String authCode) {
		return jdbc.queryForObject(
				SELECT_AUTHINFO_WITH_AUTHCODE, 
				Collections.singletonMap("authCode", authCode), 
				rowMapper);
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
