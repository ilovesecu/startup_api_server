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
import kr.chosun.capstone.startup.repository.dto.UploadFile;

@Repository
public class FileDAO {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert uploadFileInsertAction;
	private RowMapper<UploadFile> uploadFileMapper = BeanPropertyRowMapper.newInstance(UploadFile.class);
	
	public FileDAO(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.uploadFileInsertAction = new SimpleJdbcInsert(dataSource)
				.usingGeneratedKeyColumns("file_seq")
				.usingColumns("file_real_nm","file_stored_nm","file_path","file_type")
				.withTableName("file_info");
	}
	
	public int insertUploadFile(UploadFile uploadFile) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(uploadFile);
		Number seq = uploadFileInsertAction.executeAndReturnKey(params);
		return seq.intValue();
	}
}
