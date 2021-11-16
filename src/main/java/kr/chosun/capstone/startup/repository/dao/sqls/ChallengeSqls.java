package kr.chosun.capstone.startup.repository.dao.sqls;

public class ChallengeSqls {
	public static final String SELECT_CHALLENGE = """
			select * from challenge;
			""";
	
	public static final String DELETE_CHALLENGE_BY_ID = """
			delete from challenge where cpt_seq = :cpt_seq;
			""" ;
}
