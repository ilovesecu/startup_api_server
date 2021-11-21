package kr.chosun.capstone.startup.config.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import kr.chosun.capstone.startup.config.auth.PrincipalDetails;
import kr.chosun.capstone.startup.repository.dao.MemberDAO;
import kr.chosun.capstone.startup.repository.dto.Member;

/*
    -SecurityFilter 중에 BasicAuthenticationFilter 라는 것이 있다.
	-권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어있다.
	-만약 권한이나 인증이 필요한 주소가 아니라면 위 필터를 타지 않는다.
	-JwtAuthorizationFilter는 BasicAuthenticationFilter를 상속하여서 BasicAuthenticationFilter 역할을 한다.
 * */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	private MemberDAO memberDao;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberDAO memberDao) {
		super(authenticationManager);
		this.memberDao = memberDao;
	}
	
	//인증이나 권한이 필요한 주소요청이 있을 때 해당 메소드가 실행된다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// super.doFilterInternal(request, response, chain); //이걸 지워야한다. (안지우면 응답이 2번돼서 오류남) 세션도 안만들어지고...
		System.out.println("인증이나 권한이 필요한 주소가 요청됨.");
		String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
		System.out.println("jwtHeader:"+jwtHeader);
		
		//header가 있는지 확인, Bearer로 시작하는지 확인
		if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
			System.out.println("여긴아니지?");
			chain.doFilter(request, response); //인가가 안되었으면 그냥 chain타게 하자.
			return ;
		}
		
		//JWT 토큰 검증을 해서 정상적인 사용자인지 확인
		String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, ""); //Bearer 을 제거해준다. (한칸 띄운거 주의)
		//서명해서 username을 가져온다.
		String memId = 
				JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("id").asString(); 

		//서명이 정상적으로 되었다면 null 이 아니다.
		if(memId != null) {
			Member userEntity = memberDao.selectMemberWithoutJoin(memId);
			PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
			//Authentication authentication = authenticationManager.authenticate(authenticationToken);
			//굳이 위 처럼 만들지 않아도 된다. 왜냐면 우리가 지금 로그인 하는 것이 아니라 이미 username이 null이 아니기 때문에 인가는 되었고, 
			//그저 authentication객체가 필요하니까.. 이렇게 만들어야함.
			//JWT토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
			
			//시큐리티 세션 공간에 강제로 Authentication 객체 저장 
			SecurityContextHolder.getContext().setAuthentication(authentication); 
		}
		chain.doFilter(request, response);
	}
}
