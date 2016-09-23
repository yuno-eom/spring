package com.dev.spring.main.service;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.spring.common.DataMap;
import com.dev.spring.main.mapper.IndexMapper;

@Service
public class IndexService {
	
	//private static final Logger logger = LoggerFactory.getLogger(IndexService.class);
	
	@Autowired private IndexMapper indexMapper;
	
	public List<DataMap> selectMainBoardList(DataMap paramMap) {
		return indexMapper.selectMainBoardList(paramMap);
	}
}
