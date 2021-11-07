package kr.chosun.capstone.startup.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileUtil {
	public static String checkMimeType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
