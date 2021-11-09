package kr.chosun.capstone.startup.repository.dao;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.chosun.capstone.startup.repository.dto.Skill;

@Repository
public class SkillDAO {
	private NamedParameterJdbcTemplate jdbc = null;
	private SimpleJdbcInsert insertAction = null;
	private RowMapper<Skill> skillMapper = BeanPropertyRowMapper.newInstance(Skill.class);
	
	public SkillDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.usingGeneratedKeyColumns("skill_seq")
				.withTableName("skill");
	}
	
	public List<Skill> selectSkills() {
		String sql = "select * from skill";
		return jdbc.query(sql, Collections.EMPTY_MAP, skillMapper);
	}
}
