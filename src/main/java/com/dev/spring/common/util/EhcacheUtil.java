package com.dev.spring.common.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import net.sf.ehcache.statistics.StatisticsGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class EhcacheUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(EhcacheUtil.class);
	
	@Autowired 
	private CacheManager cacheManager;
	
	public void testCache() {
		
		cacheManager = CacheManager.getInstance();
		//cacheManager = CacheManager.create();
		//EhCache cache = cacheManager.getEhcache("statisticsMst");
		Cache cache = cacheManager.getCache("statisticsMst");
		logger.debug("cache: {}", cache);
		
		for (Object key: cache.getKeys()) {
			Element element = cache.get(key);
			logger.debug("element: {}", element);
			if (element != null) {
			}
		}

		Element element = cache.get("statisticsMst");
		logger.debug("element: {}", element);
		
		Attribute<String> staCodGrp = cache.getSearchAttribute("StaCodGrp");
		logger.debug("attribute: {}", staCodGrp);
		
		Query query = cache.createQuery();
		query.includeKeys();
		//query.includeValues();
		//query.includeAttribute(staCodGrp);
		//query.addCriteria(staCodGrp.eq("101"));
		query.end();

		Results results = query.execute();
		System.out.println(" Size: " + results.size());
		System.out.println("----Results-----\n");
		for (Result result : results.all()) {
			System.out.println("Got: Key[" + result.getKey()
					+ "] Value class [" + result.getValue().getClass()
					+ "] Value [" + result.getValue() + "]");
		}
	}
	
	public static void cacheInfo() {
		
		CacheManager cacheManager = CacheManager.newInstance();
		String[] cacheNames = cacheManager.getCacheNames();
		long totalHeap = 0;
		
		for (String cacheNm : cacheNames) {
			Cache cache = cacheManager.getCache(cacheNm);
			StatisticsGateway cacheStat = cache.getStatistics();
			long cacheSize = cacheStat.getSize();
			long cacheHeap = cacheStat.getLocalHeapSizeInBytes();
			totalHeap += cacheHeap;
			
			logger.debug("{} : size = {} , heap = {}", cacheNm, cacheSize, cacheHeap);
		}
		
		logger.debug("cache = {} , total heap = {}M", cacheNames.length, totalHeap/1024/1024);
	}
}