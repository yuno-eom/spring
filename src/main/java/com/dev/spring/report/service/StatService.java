package com.dev.spring.report.service;

import java.util.List;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.spring.common.DataMap;
import com.dev.spring.report.mapper.StatMapper;
import com.dev.spring.report.model.ReportVO;

@Service
public class StatService {

	//private static final Logger logger = LoggerFactory.getLogger(StatService.class);
	
	@Autowired private StatMapper statMapper;
	
	public List<DataMap> selectBoardStat(ReportVO reportVO) {
		return statMapper.selectBoardStat(reportVO);
	}
}