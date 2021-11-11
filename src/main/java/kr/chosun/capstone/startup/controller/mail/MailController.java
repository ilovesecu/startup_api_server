package kr.chosun.capstone.startup.controller.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.chosun.capstone.startup.repository.dto.UploadFile;
import kr.chosun.capstone.startup.service.AuthService;
import kr.chosun.capstone.startup.service.mail.MailService;
import kr.chosun.capstone.startup.service.mail.MailType;

@RestController
public class MailController {
	@Autowired
	AuthService authService;
	
	@PostMapping("/auth/mail/register")
	public ResponseEntity<Map<String,Object>> sendMail(
			@RequestParam(value="memseq", required = true) int memSeq) 
			throws MessagingException {
		int result = authService.sendAuthMail(memSeq,MailType.REGISTER); //메일 발송
		
		Map<String,Object> response = new HashMap<>();
		if(result==1) {
			response.put("result", "success");
		}else {
			response.put("result", "fail");
		}
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
	}
}
