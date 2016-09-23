package com.dev.spring.test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dev.spring.common.DataMap;
import com.dev.spring.test.service.TestService;

@Controller
@RequestMapping("test")
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired private TestService testService;
	
	@RequestMapping(value = "/key", method = {RequestMethod.GET, RequestMethod.POST})
	@SuppressWarnings("unchecked")
	public String selectKeyTest(HttpServletRequest request, Model model) throws Exception {
		logger.debug("before paramMap");
		if("insert".equals(request.getParameter("cmd"))){
			DataMap paramMap = new DataMap();
			paramMap.put("text1", request.getParameter("text1"));
			logger.debug("before paramMap : {}", paramMap);
			
			@SuppressWarnings("unused")
			int rst = testService.insertTestKey(paramMap);
			logger.debug("after paramMap : {}", paramMap);
		}
		
		List<DataMap> testList = testService.selectTestKeyList(null);
		model.addAttribute("testList", testList);
		
		return "test/test_key";
	}

}