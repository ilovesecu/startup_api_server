package kr.chosun.capstone.startup.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
	
	//yyyy-MM-dd HH:mm:ss 생성
	public static String currentDateTime() {
		LocalDateTime now = LocalDateTime.now();
		String formatted = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		return formatted;
	}
	
	// 년/월/일 스트링값 반환
	public static String getYYYYMMddStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator);
	}
	
	// 년-월-일 시:분:초 스트링값 반환
	public static String getYYYYMMddHHmmssStr(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String str = sdf.format(date);
		return str;
	}
}
