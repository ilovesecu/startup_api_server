package kr.chosun.capstone.startup.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"kr.chosun.capstone.startup.service","kr.chosun.capstone.startup.repository","kr.chosun.capstone.startup.utils"})
@Import({DBConfig.class})
public class ApplicationConfig {
	public static String SERVER_CHANGER="localhost";
}
