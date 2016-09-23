package com.dev.spring.batch.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class MybatisPartitioner implements Partitioner{

	static private Log log = LogFactory.getLog(MybatisPartitioner.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map partition(int gridSize) {
		log.debug("START: Partition");
		
		Map partitionMap = new HashMap();
		String[] boardGrp = {"notice", "inquiry", "faq"};
		
		for(int i=0; i< boardGrp.length; i++){
			ExecutionContext ctxMap = new ExecutionContext();
			ctxMap.putString("boardGrp", boardGrp[i]);
			
			partitionMap.put(boardGrp[i]+":-"+i, ctxMap);
		}
		
		log.debug("END: Created Partitions of size: "+ partitionMap.size());
		return partitionMap;
	}
}