package kr.chosun.capstone.startup.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter implements javax.servlet.Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터1");
		//다시 필터 체인에 등록 시켜줘야함. (끝나지 말고 계속 진행하라는 의미)
		chain.doFilter(request, response); 
	}

	@Override
	public void destroy() {
		
	}

}
