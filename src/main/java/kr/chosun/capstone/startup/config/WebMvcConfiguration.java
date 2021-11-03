package kr.chosun.capstone.startup.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc 
@ComponentScan(basePackages = {"kr.chosun.capstone.startup.controller"})
public class WebMvcConfiguration extends WebMvcConfigurerAdapter{
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//img로 요청이 오는 경우에는 /img 폴더로 다이렉트로 보낸다.
        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
    }
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("main");
	}
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    	// default servlet handler를 사용하게 합니다.
        configurer.enable();
    }
}
