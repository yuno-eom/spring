package com.dev.spring.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BaseInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hadler) throws Exception {
		//logger.debug("preHandle");
		
		String contextPath = request.getContextPath();
		String requestURI[] = request.getRequestURI().split("/");
		String getMenu = requestURI.length > 2 ? requestURI[2] : "";
		//String cookieLocale = HttpUtil.getValueFromCookie("selectLocale", request); //localeResolver
		//String getLocale = LocaleContextHolder.getLocale().toString();
		
		request.setAttribute("base", contextPath); //struts는 기본 제공..
		request.setAttribute("menu", getMenu);
		//request.setAttribute("locale", cookieLocale);
		//request.setAttribute("locale", getLocale); //--> freemarker에서는 "${.locale}" 사용 
													 //--> decorator에서는 <#setting locale = springMacroRequestContext.getLocale() /> 선언 후 사용
		//logger.debug("url is {}", request.getRequestURL());
		logger.debug("base is {}, menu is {}", contextPath, getMenu);
		
		return true;
	}
/*
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object hadler, ModelAndView mnv) throws Exception {
		//logger.debug("postHandle");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object hadler, Exception ex) throws Exception {
		//logger.debug("afterCompletion");
	}
*/
}
