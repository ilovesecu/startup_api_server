package kr.chosun.capstone.startup.service.mail;

import javax.mail.MessagingException;

public interface MailService {
	public int sendMail(String receiver, String title, String content) throws MessagingException;
	public final String SENDER = "webmaster.chosun.startup.team@gmail.com";
	public final String SENDER_NAME = "스타트업 프로젝트";
	public final String SMTP_USERNAME = "webmaster.chosun.startup.team@gmail.com";
	public final String SMTP_PASSWORD = "chosun1!";
	public final String HOST = "smtp.gmail.com";
	public final int PORT = 587;
}
