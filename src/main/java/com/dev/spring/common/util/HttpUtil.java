package com.dev.spring.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil {
	
	public static void createCookie(String setName, String setValue, int maxage, String path, HttpServletResponse response) {
		Cookie cookie = new Cookie(setName, setValue);
		cookie.setMaxAge(maxage);
		cookie.setPath(path);
		
		response.addCookie(cookie);
	}
	
	public static void deleteCookie(String getName, Cookie[] cookies, String path, HttpServletResponse response) {
		
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals(getName)) {
					cookies[i].setMaxAge(0);
					cookies[i].setPath(path);
					response.addCookie(cookies[i]);
				}
			}
		}
	}
	
	public static String getValueFromCookie(String getName, HttpServletRequest request) {
		
		String getValue = "";
		Cookie[] cookies = request.getCookies();
		
		if(cookies != null) {
			for(int i=0; i<cookies.length; i++) {
				if(cookies[i].getName().equals(getName)) {
					getValue = cookies[i].getValue();
					break;
				}
			}
		}
		
		return getValue;
	}
}