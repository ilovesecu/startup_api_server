package kr.chosun.capstone.startup.repository.dao.sqls;

//멤버 테이블 관련 SQL
public class MemberSqls {
	public static final String UPDATE_MEMSTAT = """
		UPDATE `member` SET mem_stat = :memStat WHERE mem_seq = :memSeq
			""";
}
