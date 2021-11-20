package kr.chosun.capstone.startup.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터1");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		req.setCharacterEncoding("UTF-8");
		
		//토큰을 만들었다고 가정하고 진행.
		if(req.getMethod().equals("POST")) { //POST 메소드일 때만 동작한다.
			String headerAuth = req.getHeader("Authorization");
			System.out.println("headerAuth:"+headerAuth);
			if(headerAuth.equals("cos")) {
				System.out.println("cos확인");
				//다시 필터 체인에 등록 시켜줘야함. (끝나지 말고 계속 진행하라는 의미)
				chain.doFilter(request, response); 
			}else {
				PrintWriter out = res.getWriter();
				out.println("인증안됨.");
				out.flush();
			}
		}
	}

	@Override
	public void destroy() {
		
	}

}
