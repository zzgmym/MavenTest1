package utils;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

public class IdGenertor {
	public static String genGUID(){
		return new BigInteger(165, new Random()).toString(36).toUpperCase();
	}
	public static String genOrdernum(){
		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String s1 = df.format(now);// 20141026+纳秒
		return s1+System.nanoTime();
	}
	public static boolean isNumeric(String str){
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
