package kr.chosun.capstone.startup.repository.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
	public List<Challenge> selectChallenge(int id){
		//공모전 분야별 검색
		String sql = "";
		switch (id) {
			case 0 : sql = "select * from challenge"; break;
			case 2 : sql = "select * from challenge where field = 2"; break;
			case 4 : sql = "select * from challenge where field = 4"; break;
			case 5 : sql = "select * from challenge where field = 5"; break;
			case 12 : sql = "select * from challenge where field = 12"; break;
			case 15 : sql = "select * from challenge where field = 15"; break;
			default : System.out.println("switch case 문 확인 필요"); break;
		}
		//String sql = "select * from challenge";
		return jdbc.query(sql, Collections.EMPTY_MAP, challengeMapper);
	}
	public void deleteChallenge(Integer cpt_seq) {
		String sql = "delete from challenge where cpt_seq = :cpt_seq";
		Map<String, Integer> params = Collections.singletonMap("cpt_seq", cpt_seq);
		jdbc.update(sql, params);
	}
}
