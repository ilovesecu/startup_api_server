package kr.chosun.capstone.startup.config.auth.jwt;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.chosun.capstone.startup.config.auth.PrincipalDetails;
import kr.chosun.capstone.startup.repository.dto.Member;
import lombok.RequiredArgsConstructor;

//formLogin이 아닌 JWT로 로그인하기 위한 클래스 24강
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	//로그인 요청을 하면 로그인 시도를 위해서 실행되는 메소드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter:로그인 시도 중");
		// 1. username, password 받기
		ObjectMapper om = new ObjectMapper();
		try {
			Member member = om.readValue(request.getInputStream(), Member.class);
			// 2. 정상인지 로그인 시도를 해보자. (가장 간단한 방법이 AuthenticationManager로 로그인 시도를 하면 PrincipalDetailsService의 loadUserByUsername가 자동으로 실행됨)
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(member.getMemId(), member.getPassword());
			//로그인 시도 (PrincipalDetailsService의 loadUserByUsername() 메소드가 실행된다. ) 정상이면 authentication이 반환됨.
			Authentication authentication = this.getAuthenticationManager().authenticate(authenticationToken);
			//Authentication authentication = authenticationManager.authenticate(authenticationToken);
			PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
			System.out.println(principalDetails.getMember().getMemId());
			// 3. PrincipalDetails가 반환이 되면 PrincipalDetails를 세션에 담는다. (세션에 있어야 유저별로 권한관리를 할 수 있다. 굳이 권한관리를 안하면 세션에 안담아도 된다.)
			return authentication;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
