package com.dev.spring.common;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class Global extends ResourceBundleMessageSource {
	
	public static ResourceBundle resource = ResourceBundle.getBundle("properties/env");
	//PagingTag 등.. Global.resource.getString("CONTEXT_PATH") = request.getContextPath() request로 받기 힘든 경우..

	public static MessageSourceAccessor messageSourceAccessor;
	
	public Map<String, String> getAllMessages(String basename) {
		Locale locale = LocaleContextHolder.getLocale();
		ResourceBundle bundle = getResourceBundle(basename, locale);
		
		TreeMap<String, String> treeMap = new TreeMap<String, String>();
		for (String key : bundle.keySet()) {
			treeMap.put(key, getMessage(key, null, locale));
		}
		
		return treeMap;
	}

	public static String getMessages(String key){
		return messageSourceAccessor.getMessage(key);
	}
	
	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		Global.messageSourceAccessor = messageSourceAccessor;
	}
}
