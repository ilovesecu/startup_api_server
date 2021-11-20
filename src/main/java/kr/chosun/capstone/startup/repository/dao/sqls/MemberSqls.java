package kr.chosun.capstone.startup.repository.dao.sqls;

//멤버 테이블 관련 SQL
public class MemberSqls {
	public static final String UPDATE_MEMSTAT = """
				UPDATE `member` SET mem_stat = :memStat WHERE mem_seq = :memSeq
			""";

	public static final String SELECT_MEMBER_BASIC = """
				SELECT
				M.mem_seq, M.mem_id, M.mem_nm, M.introduce, M.mem_email, M.mem_phone, M.mem_type,
				C.mem_nickname, C.mem_major, C.mem_mbti,
				FINFO.file_seq AS profile_seq, FINFO.file_path AS profile_path,FINFO.file_stored_nm AS profile_nm,
				AWARDS.award_seq, AWARDS.award_content, AWARDS.award_time,
				PP.mpl_seq, PP.mpl_content,
				TSKILL.skill_seq, TSKILL.skill_nm, TSKILL.skill_classification
				FROM `member` M
				LEFT JOIN `character_` C ON M.mem_seq = C.mem_seq
				LEFT JOIN member_awards AWARDS ON C.mem_seq = AWARDS.mem_seq
				LEFT JOIN member_pp_link PP ON C.mem_seq = PP.mem_seq
				LEFT JOIN member_skill SKILL ON C.mem_seq = SKILL.mem_seq
				LEFT JOIN file_info FINFO ON C.profile_seq = FINFO.file_seq
				LEFT JOIN skill TSKILL ON SKILL.skill_seq = TSKILL.skill_seq
				WHERE M.mem_seq = :memSeq
			""";

}
