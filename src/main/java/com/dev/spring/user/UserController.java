package com.dev.spring.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dev.spring.common.DataMap;
import com.dev.spring.user.model.SearchUser;
import com.dev.spring.user.model.UserInfo;
import com.dev.spring.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired private UserService userService;
	
	@RequestMapping(value = "/pop/{userId}", method = RequestMethod.POST) //개인정보 보안상.. POST만..
	@SuppressWarnings("unchecked")
	public String userInfo(@PathVariable String userId, Model model) throws Exception {
		
		DataMap paramMap = new DataMap();
		paramMap.put("userId", userId);
		
		UserInfo userInfo = userService.selectUserInfo(paramMap);
		model.addAttribute("userInfo", userInfo);
		
		return "user/user_input";
	}
	
	@RequestMapping(value = "/pop/join", method = RequestMethod.GET)
	public String userJoin() throws Exception {
		
		return "user/user_input";
	}
	
	@RequestMapping(value = "/ajaxSubmit", method = RequestMethod.POST)
	@ResponseBody
	public int userAjaxSubmit(@ModelAttribute UserInfo userInfo) throws Exception {
		
		String cmd = userInfo.getCmd();
		int rst = 0;
		
		if("insert".equals(cmd)){
			rst = userService.insertUserInfo(userInfo);
		}else if("update".equals(cmd)){
			rst = userService.updateUserInfo(userInfo);
		}
		
		return rst;
	}
	
	@RequestMapping(value = "/ajaxDeletes", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@ResponseBody
	public int userAjaxDeletes(@RequestParam(value = "args[]") List<String> userId) throws Exception {
		
		logger.debug("delete count : {}", userId.size());
		
		DataMap paramMap = new DataMap();
		paramMap.put("userId", userId);

		return userService.deleteUserInfo(paramMap);
	}
	
	@RequestMapping(value = "/ajaxCheckId", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@ResponseBody
	public boolean checkUserId(@ModelAttribute UserInfo userInfo) throws Exception {
		
		DataMap paramMap = new DataMap();
		paramMap.put("userId", userInfo.getUserId());
		
		userInfo = userService.selectUserInfo(paramMap);
		
		return (userInfo == null) ? true : false;
	}
	
	@RequestMapping(value = "/ajaxJoinCheck", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@ResponseBody
	public List<UserInfo> joinCheck(@RequestParam String email) throws Exception {
	//public UserInfo joinCheck(@RequestParam String email) throws Exception {
		
		DataMap paramMap = new DataMap();
		paramMap.put("email", email);
		
		List<UserInfo> userInfo = userService.selectUserInfoList(paramMap);
		//UserInfo userInfo = userService.selectUserInfo(paramMap);
		
		return userInfo;
	}
	
	/* user/users.json .xml .xls .pdf sample */
	@RequestMapping(value = "/users", method = {RequestMethod.GET, RequestMethod.POST})
	public String getUsers(@ModelAttribute SearchUser searchUser, Model model) throws Exception {
		
		DataMap paramMap = new DataMap();
		
		List<UserInfo> userInfo = userService.selectUserInfoList(paramMap);
		
		model.addAttribute("searchUser", searchUser); //for grid search test (only view)...
		model.addAttribute("users", userInfo);        //for json, xml, excel, pdf
		
		String grid = searchUser.getGrid() == null ? "jqGrid" : searchUser.getGrid();
		return "user/user_"+grid;
	}
	
	// dhtmlxGrid List ajax
	@RequestMapping(value = "/userGrid2", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public Model userGrid2(Model model) throws Exception {
		
		DataMap paramMap = new DataMap();
		
		List<UserInfo> userInfo = userService.selectUserInfoList(paramMap);
		
		//model.addAttribute("total_count", 17);
		//model.addAttribute("pos", 0);
		model.addAttribute("data", userInfo);
		
		ObjectMapper om = new ObjectMapper();
		logger.debug("json: {}", om.writeValueAsString(model));
		
		return model;
	}
	
	//jqGrid List ajax
	@RequestMapping(value = "/userGrid", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@ResponseBody
	public Model userGrid(HttpServletRequest request, Model model) throws Exception {
		
		log4Parameters(request);
		
		int rows = Integer.parseInt(request.getParameter("rows"));
		int page = Integer.parseInt(request.getParameter("page"));
		String sidx = request.getParameter("sidx");
		String sord = request.getParameter("sord");
		String searchString = request.getParameter("searchString");
		
		DataMap paramMap = new DataMap();
		paramMap.put("limit", rows);
		paramMap.put("offset", (page-1)*rows);
		paramMap.put("sidx", sidx);
		paramMap.put("sord", sord.toUpperCase());
		paramMap.put("searchString", searchString);
		
		List<DataMap> userInfo = userService.selectUserGrid(paramMap);
		
		int rowMax = userService.selectUserGridRow(paramMap);
		int pageCnt = rowMax%rows == 0 ? (int)Math.ceil(rowMax/rows) : (int)Math.ceil(rowMax/rows)+1;
		
		model.addAttribute("page", page);
		model.addAttribute("total", pageCnt);
		model.addAttribute("records", rowMax);
		model.addAttribute("rows", userInfo);
		//model.addAttribute("searchString", searchString);
		
		return model;
	}
	
	@RequestMapping(value = "/cellEdit", method = RequestMethod.POST)
	@SuppressWarnings("unchecked")
	@ResponseBody
	public String cellEdit(HttpServletRequest request) {
		
		log4Parameters(request);
		
		String oper = request.getParameter("oper");
		
		UserInfo userInfo = new UserInfo();
		int rst = 0;
		
		//test for jqGrid (parameter : object -> map)
		if("add".equals(oper)){
			String id = request.getParameter("USER_ID");
			userInfo.setUserId(id);
			
			setParamsToUserInfo(request, userInfo);
			
			rst = userService.insertUserInfo(userInfo);
			
		}else if("edit".equals(oper)){
			String id = request.getParameter("id");
			userInfo.setUserId(id);
			
			setParamsToUserInfo(request, userInfo);
			
			rst = userService.updateUserInfo(userInfo);
			
		}else if("del".equals(oper)){
			//String[] id = request.getParameterValues("id");
			String[] id = request.getParameter("id").split(",");
			List<String> userId =new ArrayList<String>();
			userId.addAll(Arrays.asList(id));
			
			DataMap paramMap = new DataMap();
			paramMap.put("userId", userId);
			
			rst = userService.deleteUserInfo(paramMap);
		}
		
		return rst > 0 ? "SUCCESS" : "FAILURE";
	}
	
	private void log4Parameters(HttpServletRequest request) {
		
		for(Enumeration<String> e=request.getParameterNames(); e.hasMoreElements();) {
			String name = e.nextElement();     //input name
			String value = request.getParameter(name); //input value
			
			logger.debug("parameter: {} = {}", name, value);
		}		
	}
	
	private void setParamsToUserInfo(HttpServletRequest request, UserInfo userInfo) {
		
		for(Enumeration<String> e=request.getParameterNames(); e.hasMoreElements();) {
			String name = e.nextElement();     //input name
			String value = request.getParameter(name); //input value
			
			if("USER_PW".equals(name)) userInfo.setPwd(value);
			else if("USER_NM".equals(name)) userInfo.setUserNm(value);
			else if("EMAIL".equals(name)) userInfo.setEmail(value);
			else if("GRADE".equals(name)) userInfo.setGrade(value);
		}		
	}
}