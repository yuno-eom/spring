package com.dev.spring.batch.service;

import org.springframework.batch.item.ItemProcessor;

import com.dev.spring.common.DataMap;

public class MybatisProcessor implements ItemProcessor<DataMap, DataMap> {

	@SuppressWarnings("unchecked")
	@Override
	public DataMap process(DataMap dMap) throws Exception {
		
		System.out.println("Processing..." + dMap);
		
		DataMap paramMap = new DataMap();
		paramMap.put("dbYear", dMap.get("DB_YEAR"));
		
		return dMap;
	}

}