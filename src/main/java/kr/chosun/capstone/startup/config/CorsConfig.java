package kr.chosun.capstone.startup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		/* 
		내 서버가 응답을 할 때 해당 데이터(보통json)를 자바스크립트에서 처리할 수 있게 할지 설정
		js에서 ajax로 서버로 요청을 하면 서버에서 처리된 응답을 보내주는데 만약에 이것이 false라면 응답이 오지 않는다.
		컨트롤러 클래스명 위에 @CrossOrigin 이렇게 어노테이션으로 cors를 설정할 수 도 있지만 
		이건 시큐리티의 인증이 필요하지 않은 것에만 유효하고 인증이 필요한 부분은 모두 막힌다.
		*/
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*"); // 모든 ip에 응답을 허용
		config.addAllowedHeader("*"); // 모든 header에 응답을 허용
		config.addAllowedMethod("*"); // 모든 post,get,put,delete,patch허용 허용
		source.registerCorsConfiguration("/api/**", config); // /api로 들어오는 모든 주소는 해당 config설정을 따라라
		return new CorsFilter(source);
	}
}
