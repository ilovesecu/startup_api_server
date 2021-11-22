package kr.chosun.capstone.startup.config.auth.jwt;


import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.chosun.capstone.startup.config.auth.PrincipalDetails;
import kr.chosun.capstone.startup.repository.dto.Member;

//formLogin이 아닌 JWT로 로그인하기 위한 클래스 [24]
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
	
	// 위의 attemptAuthentication 실행 후 인증이 정상적으로 되었으면 실행되는 메소드
	// JWT 토큰을 만들어서 응답해준다.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication 인증완료!");
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		//토큰 만들 때 기본적으로 Builder패턴.
		String jwtToken = JWT.create() //pom.xml에 java-jwt해놨기 때문에 가능
				.withSubject("startUp토큰")
				.withExpiresAt(new Date(System.currentTimeMillis()+(JwtProperties.EXPIRATION_TIME))) //만료시간
				.withClaim("id", principalDetails.getUsername())
				.withClaim("seq", principalDetails.getMember().getMemSeq())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET)); //내 서버만 아는 고유한 값을 줘야한다.
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken); //이건 고정된 값임. (Bearer하고 한칸 띄워야하는 것 주의)
	}
	
	
}
