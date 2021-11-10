package kr.chosun.capstone.startup.utils;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.chosun.capstone.startup.service.AuthService;

@Component
public class AuthUtil {
	@Autowired
	private AuthService authService;
	
	//인증코드 생성기
	public String authCodeGenerator() {
		String code = null;
		
		//중복코드 검사를 최대 1000회 실시한다.
		for(int i=0; i<1000; i++) {
			code = UUID.randomUUID().toString(); //36자
			code+=String.valueOf(System.currentTimeMillis());
			code = code.replace("-", "");
			
			//혹시라도 50자가 넘으면 딱 50자로 줄인다.
			if(code.length()>50) {
				code = code.substring(0,51); //50자 까지 가져온다.
			}
			//생성된 인증코드 중복 검사
			if(duplicateCheckAuthCode(code)) {
				break; //중복검사가 통과된다면 바로 반복문 탈출
			}; 
		}
		
		return code;
	}
	
	//인증코드 중복검사
	public boolean duplicateCheckAuthCode(String authCode) {
		int result = authService.getAuthCodeCnt(authCode);
		if(result == 0)return true; //중복없음
		else return false; //중복있음
	}
}
