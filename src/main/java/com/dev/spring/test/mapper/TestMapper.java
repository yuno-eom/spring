package com.dev.spring.test.mapper;

import java.util.List;

import com.dev.spring.common.DataMap;

public interface TestMapper {

	List<DataMap> selectTestKeyList(DataMap paramMap);

	Integer insertTestKey(DataMap paramMap);

}