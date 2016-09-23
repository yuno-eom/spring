package com.dev.spring.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dev.spring.common.DataMap;
import com.dev.spring.common.security.SHA256;
import com.dev.spring.common.util.HttpUtil;
import com.dev.spring.main.service.IndexService;
import com.dev.spring.user.model.UserInfo;
import com.dev.spring.user.service.UserService;

@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired private IndexService indexService;
	@Autowired private UserService userService;
	
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, @CookieValue(value="userId", required=false) String cookieId) {
		
		//ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		//HttpServletRequest request = servletRequestAttribute.getRequest();

		String lang = LocaleContextHolder.getLocale().getLanguage();
		logger.debug("lang: {}", lang);
		
		DataMap paramMap = new DataMap();
		List<DataMap> boardList = indexService.selectMainBoardList(paramMap);
		
		request.setAttribute("cookieId", cookieId);
		request.setAttribute("boardList", boardList);
		
		return "index";
	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:index";
	}
	
	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	@SuppressWarnings("unchecked")
	public String login(HttpServletRequest request, HttpServletResponse response, @CookieValue(value="userId", required=false) String cookieId) {
		
		String userId = request.getParameter("userId");
		String pwd = request.getParameter("pwd");
		String chk = request.getParameter("saveId");
		
		if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(pwd)){
			try {
				DataMap paramMap = new DataMap();
				paramMap.put("userId",userId);
				paramMap.put("pwd", SHA256.encrypt(pwd));
				UserInfo userInfo = userService.selectUserInfo(paramMap);
				
				HttpSession session = request.getSession(true);
				
				if(userInfo != null){
					//사용자 정보 세션 저장
					session.setAttribute("userId", userId);
					session.setAttribute("userNm", userInfo.getUserNm());
					session.setAttribute("grade", userInfo.getGrade());
					
					if(chk != null){ //쿠키 아이디 저장
						HttpUtil.createCookie("userId", userId, 60*60*24*30, "/", response); //30일(2592000)
					}else{	//쿠키 삭제
						HttpUtil.deleteCookie("userId", request.getCookies(), "/", response);
					}
					
					request.setAttribute("SUCCESS", "Login Success");
					
					return "redirect:index";
					
				}else{
					request.setAttribute("ERROR", "Username or password incorrect");
					logger.debug("ERROR :: userId is {} , pwd is {}", userId, SHA256.encrypt(pwd));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(StringUtils.isNotEmpty(userId)){
			request.setAttribute("cookieId", userId);   //POST - login fail..
		}else{
			request.setAttribute("cookieId", cookieId); //GET - page load..
		}
		
		return "login";
	}

}