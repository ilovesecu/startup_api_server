package kr.chosun.capstone.startup.repository.dao;

import java.util.Collections;
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
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import kr.chosun.capstone.startup.repository.dto.Party;

@Repository
public class PartyDAO {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert partyInsertAction;
	private RowMapper<Party> partyMapper = BeanPropertyRowMapper.newInstance(Party.class);
	
	public PartyDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.partyInsertAction = new SimpleJdbcInsert(dataSource)
				.usingGeneratedKeyColumns("prt_seq")
				.withTableName("party");
	}
	
	public List<Party> selectParty(){
		String sql = "";
		sql = "select * from party";
		return jdbc.query(sql, Collections.EMPTY_MAP, partyMapper);
	}

	public int insertParty(Party party) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(party);
		Number key = partyInsertAction.executeAndReturnKey(param);
		return key.intValue();
	}
}
