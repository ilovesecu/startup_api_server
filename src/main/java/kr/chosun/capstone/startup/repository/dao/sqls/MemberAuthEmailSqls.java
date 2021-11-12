package kr.chosun.capstone.startup.repository.dao.sqls;

public class MemberAuthEmailSqls {
	public static final String UPDATE_AUTH_TIME = """
			UPDATE member_auth_email SET 
				mae_auth_time=:maeAuthTime 
			WHERE mae_auth_code = :maeAuthCode and mae_expired = 0
			""";
	public static final String SELECT_AUTHINFO_WITH_AUTHCODE = """
			SELECT * FROM member_auth_email WHERE mae_auth_code = :authCode
			""";
}
