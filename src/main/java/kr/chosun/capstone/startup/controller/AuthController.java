package kr.chosun.capstone.startup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.MemberAuthEmail;
import kr.chosun.capstone.startup.service.AuthService;
import kr.chosun.capstone.startup.utils.DateUtil;

//인증관련 컨트롤러
@RestController
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@GetMapping("/auth/{authCode}")
	public void authenticationAuthCode(@PathVariable(value="authCode") String authCode) {
		String authTime=DateUtil.getYYYYMMddHHmmssStr();
		MemberAuthEmail memberAuthEmail = MemberAuthEmail.builder()
				.maeAuthCode(authCode)
				.maeAuthTime(authTime)
				.build();
		int result = authService.authenticationAuthCode(memberAuthEmail);
		System.out.println(result);
	}
}
