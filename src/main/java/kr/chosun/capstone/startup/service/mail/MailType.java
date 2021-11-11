package kr.chosun.capstone.startup.service.mail;

public enum MailType {
	REGISTER(1), CHANGE_MAIL(2), PASSWORD(3);
	MailType(int value){this.value = value;}
    private final int value;
    public int value() { return value; }
}
