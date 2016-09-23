package com.dev.spring.report.mapper;

import java.util.List;

import com.dev.spring.common.DataMap;
import com.dev.spring.report.model.ReportVO;

public interface StatMapper {
	
	public abstract List<DataMap> selectBoardStat(ReportVO reportVO);
	
}