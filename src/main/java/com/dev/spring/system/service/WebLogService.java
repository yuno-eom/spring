package com.dev.spring.system.service;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.spring.system.mapper.WebLogMapper;
import com.dev.spring.system.model.WebLogVO;

@Service
public class WebLogService {
	
	//private static final Logger logger = LoggerFactory.getLogger(WebLogService.class);
	
	@Autowired
	private WebLogMapper webLogMapper;

	public void insertWebLog(WebLogVO webLogVO) {
		webLogMapper.insertWebLog(webLogVO);
	}
}