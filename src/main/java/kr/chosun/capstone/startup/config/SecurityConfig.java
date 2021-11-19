package kr.chosun.capstone.startup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import kr.chosun.capstone.startup.filter.MyFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity//스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인(기본필터체인)에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean //패스워드를 암호화해주는 인코더를 IoC에 등록해준다.
	public BCryptPasswordEncoder encoderPwd() {
		return new BCryptPasswordEncoder();
	}
	private final CorsFilter corsFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//BasicAuthenticationFilter가 동작하기 전에 MyFilter가 동작하도록 설정.
		http.addFilterBefore(new MyFilter(), BasicAuthenticationFilter.class);
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 사용X(JWT사용 시 기본)
		.and()
		.addFilter(corsFilter)//cors 정책 설정 (CorsConfig 참고), @CrossOrigin(=인증이없을때 사용), 인증이있을 때는 시큐리티의 filter에 등록을 해줘야함.
		.formLogin().disable()//JWT서버이므로 formLogin 사용X
		.httpBasic().disable()
		.authorizeRequests() //인증이 필요함
		.antMatchers("/api/v1/user/**").authenticated()
		.anyRequest().permitAll();
		/*
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated() //인증이 필요
		//.antMatchers("/startup/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.anyRequest().permitAll() //모든 요청 허용
		.and()
		.formLogin().disable() //JWT서버니까 formLogin사용X
		.httpBasic().disable()
		.usernameParameter("userId") //username파라미터의 이름 설정
		.passwordParameter("password") //password 파라미터의 이름 설정
		*/
	}
	
}
