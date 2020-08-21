package com.lovius.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataUtils {

	public static String currentYYYYMMDD() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String currentYYMMDD() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String currentYYYYMM() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMM");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String currentMMDD() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMdd");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String currentYYYYMMDDHHMMSS() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String currentYYMMDDHHMMSS() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMdd HHmmss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String currentHHMMSS() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmss");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String currentHHMM() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmm");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String currentHHMMSSSSS() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HHmmssSSS");  
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}

}
