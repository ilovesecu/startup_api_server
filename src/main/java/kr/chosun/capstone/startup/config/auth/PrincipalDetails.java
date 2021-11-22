package kr.chosun.capstone.startup.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.chosun.capstone.startup.repository.dto.Member;
import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{
	private Member member;
	
	public PrincipalDetails(Member member) {
		this.member = member;
	}
 
	//해당 User의 권한을 리턴하는 곳! (우리는 Role이 없음)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		return collect;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getMemId();
	}

	@Override //계정이 만료 안되었니?
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override //계정이 안잠겼니?
	public boolean isAccountNonLocked() {
		return true;
	}
 
	@Override  //비밀번호 기간이 안지났니?
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override  //계정이 활성화되어있니?
	public boolean isEnabled() {
		/*
		 * 언제 사용하느냐?
		 * 우리 사이트에서 1년동안 회원이 로그인하지 않으면 휴먼 계정으로 하려고한다. (물론 User객체에 최종 로그인 날짜가 있어야겠지)
		 * 현재시간 - 로그인 시간 => 1년 초과 시 return false; 이렇게 하면 된다. 
		 */
		if (member.getMemStat().equals("NORMAL")) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
