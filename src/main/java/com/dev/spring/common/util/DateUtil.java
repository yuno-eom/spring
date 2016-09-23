package com.dev.spring.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getSimpleDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date());
	}
	
	public static String getSimpleDate(String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		return format.format(new Date());
	}
}