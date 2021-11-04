package kr.chosun.capstone.startup.repository.dao;

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

@Repository
public class CharacterDAO {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Character> characteryMapper = BeanPropertyRowMapper.newInstance(Character.class);
	
	public CharacterDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.usingColumns("mem_seq","mem_nickname","mem_major","mem_mbti")
				.withTableName("character");
	}
	
	public int insert(Character member) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		return insertAction.execute(params);
	}
}
