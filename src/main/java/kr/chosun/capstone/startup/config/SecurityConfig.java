package kr.chosun.capstone.startup.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity//스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인(기본필터체인)에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
		.antMatchers("/user/**").authenticated() //인증이 필요
		//.antMatchers("/startup/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
		.anyRequest().permitAll() //모든 요청 허용
		.and()
		.formLogin()
		.loginPage("/login");
		
	}
	
}
