package com.dev.spring.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hadler) throws Exception {
		
		HttpSession session = request.getSession(true);
		
		if(session.getAttribute("userId") == null){
			String contextPath = request.getContextPath();
			String requestURI = request.getRequestURI();
			logger.debug("contextPath is {}, requestURI is {}", contextPath, requestURI);
			
			String ajaxCall = (String) request.getHeader("AJAX");
			if("true".equals(ajaxCall)){
				response.sendError(901); //add error code
			}else{
				response.sendRedirect(contextPath+"/login");
			}
			
			return false;
		}
		
		return true;
	}
/*	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object hadler, ModelAndView mnv) throws Exception {
		logger.debug("postHandle");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object hadler, Exception ex) throws Exception {
		logger.debug("postHandle");
	}
*/
}
