package com.dev.spring.test.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.spring.common.DataMap;
import com.dev.spring.test.mapper.TestMapper;

@Service
public class TestService {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(TestService.class);
	
	@Autowired private TestMapper testMapper;
	
	public List<DataMap> selectTestKeyList(DataMap paramMap) {
		return testMapper.selectTestKeyList(paramMap);
	}
	
	
	public Integer insertTestKey(DataMap paramMap) {
		return testMapper.insertTestKey(paramMap);
	}

}