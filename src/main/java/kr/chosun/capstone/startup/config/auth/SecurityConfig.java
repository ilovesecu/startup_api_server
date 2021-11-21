package kr.chosun.capstone.startup.config.auth;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.filter.CorsFilter;

import kr.chosun.capstone.startup.config.auth.jwt.JwtAuthenticationFilter;
import kr.chosun.capstone.startup.config.auth.jwt.JwtAuthorizationFilter;
import kr.chosun.capstone.startup.repository.dao.MemberDAO;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity//스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인(기본필터체인)에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private final PrincipalDetailsService principalDetailsService;
	private final CorsFilter corsFilter;
	private final MemberDAO memberDao;
	
	@Bean //패스워드를 암호화해주는 인코더를 IoC에 등록해준다.
	public BCryptPasswordEncoder encoderPwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//JWT 필터에 AuthenticationManager 등록
		AbstractAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
		jwtAuthenticationFilter.setAuthenticationManager(this.authenticationManagerBean());
		
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용X(JWT사용 시 기본)
		.and()
		.addFilter(corsFilter)//cors 정책 설정 (CorsConfig 참고), @CrossOrigin(=인증이없을때 사용), 인증이있을 때는 시큐리티의 filter에 등록을 해줘야함.
		.formLogin().disable()//JWT서버이므로 formLogin 사용X
		.httpBasic().disable()
		.addFilter(jwtAuthenticationFilter) //form disable 대응
		.addFilter(new JwtAuthorizationFilter(this.authenticationManagerBean(), memberDao))
		.authorizeRequests() //인증이 필요함
		.antMatchers("/api/v1/user/**").authenticated()
		.anyRequest().permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailsService).passwordEncoder(encoderPwd());
	}
	
}
