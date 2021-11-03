package kr.chosun.capstone.startup.repository.dao;


import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.chosun.capstone.startup.repository.dto.University;
import static kr.chosun.capstone.startup.repository.dao.sqls.UniversitySqls.*;

@Repository
public class UniversityDAO {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<University> universityMapper = BeanPropertyRowMapper.newInstance(University.class);
	
	public UniversityDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("university")
				.usingColumns("univ_nm","univ_domain")
				.usingGeneratedKeyColumns("univ_seq");
	}
	
	public List<University> selectUniversitys(){
		return jdbc.query(SELECT_UNIVERSITY, Collections.EMPTY_MAP, universityMapper);
	}
}
