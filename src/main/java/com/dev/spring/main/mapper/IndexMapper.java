package com.dev.spring.main.mapper;

import java.util.List;

import com.dev.spring.common.DataMap;

public interface IndexMapper {
	
	List<DataMap> selectMainBoardList(DataMap paramMap);

}