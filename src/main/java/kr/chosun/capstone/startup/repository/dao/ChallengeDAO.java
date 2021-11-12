package kr.chosun.capstone.startup.repository.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.chosun.capstone.startup.repository.dto.Challenge;

@Repository
public class ChallengeDAO {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert challengeInsertAction;
	private RowMapper<Challenge> challengeMapper = BeanPropertyRowMapper.newInstance(Challenge.class);
	
	public ChallengeDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.challengeInsertAction = new SimpleJdbcInsert(dataSource)
				.usingGeneratedKeyColumns("cpt_seq")
				.withTableName("challenge");
	}
	public List<Challenge> selectChallenge(){
		String sql = "select * from challenge";
		return jdbc.query(sql, Collections.EMPTY_MAP, challengeMapper);
	}
}
