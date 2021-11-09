package kr.chosun.capstone.startup.service.mail.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import kr.chosun.capstone.startup.config.ApplicationConfig;
import kr.chosun.capstone.startup.service.mail.MailService;
import kr.chosun.capstone.startup.service.mail.MailType;

@Service
public class MailServiceImpl implements MailService{
	
	
	@Override
	public int sendMail(String receiver, MailType mailType) throws MessagingException {
		//이메일 property 설정
		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		Session session = Session.getDefaultInstance(props);
		MimeMessage msg = new MimeMessage(session);
		Transport transport = null;
		try {
			msg.setFrom(new InternetAddress(SENDER, SENDER_NAME));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
			
			MailContent mailContent = mailContentGenerator(mailType,"test");
			msg.setSubject(mailContent.subject);
			msg.setContent(mailContent.content, "text/html;charset=euc-kr");
			
			transport = session.getTransport();
			System.out.println("Sending...");
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
	        transport.sendMessage(msg, msg.getAllRecipients());
	        System.out.println("Email sent!");
	        return 1;
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
			transport.close();
		}
		
	}
	
	private MailContent mailContentGenerator(MailType type, String authCode) {
		MailContent mailContent = new MailContent();
		switch(type) {
			case REGISTER:
				mailContent.subject="회원가입 완료를 위해 인증 해주세요.";
				mailContent.content="""
						<h1>스팀 회원가입을 환영합니다!</h1>
						회원 인증을 위해서 아래의 링크를 클릭해주세요.<br/>
						<a href='http://%s:8080/startup/auth/%s'>인증완료하기</a><br/><br/><br/>
						<img src='http://%s:8080/startup/display?file=2021/11/08/logo.png'/>
						""".formatted(ApplicationConfig.SERVER_CHANGER,authCode,ApplicationConfig.SERVER_CHANGER);
				break;
			case CHANGE_MAIL:
				break;
			case PASSWORD:
				break;
			default:
				return null;
		}
		return mailContent;
	}
	
	class MailContent{
		private String subject;
		private String content;
	}
}
