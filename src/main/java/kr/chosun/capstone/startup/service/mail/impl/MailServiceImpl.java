package kr.chosun.capstone.startup.service.mail.impl;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.chosun.capstone.startup.config.ApplicationConfig;
import kr.chosun.capstone.startup.service.AuthService;
import kr.chosun.capstone.startup.service.mail.MailService;
import kr.chosun.capstone.startup.service.mail.MailType;
import kr.chosun.capstone.startup.utils.AuthUtil;

@Service
public class MailServiceImpl implements MailService {
	//메일 보내기
	@Override
	public int sendMail(String receiver, String title, String content) throws MessagingException {
		// 이메일 property 설정
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

			// String authCode = authUtil.authCodeGenerator(); //인증코드 생성
			msg.setSubject(title);
			msg.setContent(content, "text/html;charset=euc-kr");

			transport = session.getTransport();
			System.out.println("Sending...");
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients()); // 메일전송
			System.out.println("Email sent!");
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			transport.close();
		}
	}
}
