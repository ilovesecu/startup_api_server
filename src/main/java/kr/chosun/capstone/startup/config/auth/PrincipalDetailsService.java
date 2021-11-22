package kr.chosun.capstone.startup.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.chosun.capstone.startup.repository.dao.MemberDAO;
import kr.chosun.capstone.startup.repository.dto.Member;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{
	private final MemberDAO memberDao;
	
	//함수 종료 시 @AuthenticationPrincipal 어노테이션 만들어짐.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberDao.selectMemberWithoutJoin(username);
		System.out.println("loadUserByUsername"+member);
		
		//리턴될 때 Authentication내부에 PrincipalDetails(UserDetails)타입이 쏙 들어간다.
		//그리고 시큐리티 세션에 Authentication이 쏙 들어간다.
		if(member!=null) return new PrincipalDetails(member);
		return null;
	}

}
