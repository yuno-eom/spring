package com.dev.spring.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dev.spring.system.service.WebLogService;
import com.dev.spring.system.model.WebLogVO;

public class WebLogInterceptor extends HandlerInterceptorAdapter {
	
	//private static final Logger logger = LoggerFactory.getLogger(WebLogInterceptor.class);
	
	@Autowired
	WebLogService webLogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hadler) throws Exception {
		//logger.debug("preHandle");
		
		String remoteAddr = request.getRemoteAddr();
		String url = request.getRequestURL() + "?" + request.getQueryString();
		String httpUserAgent = request.getHeader( "User-Agent" );		
		String httpReferer = request.getHeader( "Referer" );
		
		WebLogVO webLogVO = new WebLogVO();
		webLogVO.setRemoteAddr(remoteAddr);
		webLogVO.setUrl(url);
		webLogVO.setHttpUserAgent(httpUserAgent);
		webLogVO.setHttpReferer(httpReferer);
		
		HttpSession session =  request.getSession(true);
		String userId = (String)session.getAttribute("userId");
		if(userId != null) {
			webLogVO.setUserId(userId);
		}
		
		webLogService.insertWebLog(webLogVO);
		
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
