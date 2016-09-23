package com.dev.spring.user.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.dev.spring.common.DataMap;
import com.dev.spring.common.security.SHA256;
import com.dev.spring.user.mapper.UserMapper;
import com.dev.spring.user.model.UserInfo;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired private UserMapper userMapper;
	
	public UserInfo selectUserInfo(DataMap paramMap) {
		return (UserInfo)userMapper.selectUserInfo(paramMap);
	}
	
	@Cacheable(value = "userInfo")
	public List<UserInfo> selectUserInfoList(DataMap paramMap) {
		return userMapper.selectUserInfoList(paramMap);
	}
	
	@CacheEvict(value = "userInfo", allEntries=true)
	public Integer deleteUserInfo(DataMap paramMap) {
		return userMapper.deleteUserInfo(paramMap);
	}
	
	@CacheEvict(value = "userInfo", allEntries=true)
	public Integer insertUserInfo(UserInfo userInfo) {
		setPwdEncrypt(userInfo);
		return userMapper.insertUserInfo(userInfo);
	}
	
	@CacheEvict(value = "userInfo", allEntries=true) //, key = "#userInfo")
	public Integer updateUserInfo(UserInfo userInfo) {
		setPwdEncrypt(userInfo);
		return userMapper.updateUserInfo(userInfo);
	}

	public int selectUserGridRow(DataMap paramMap) {
		return userMapper.selectUserGridRow(paramMap);
	}

	public List<DataMap> selectUserGrid(DataMap paramMap) {
		return userMapper.selectUserGrid(paramMap);
	}
	
	private void setPwdEncrypt(UserInfo userInfo) {
		if(userInfo.getPwd() != null){
			try {
				String pwd = SHA256.encrypt(userInfo.getPwd()); //비밀번호 암호화
				logger.debug("PWD endcrypt: {} >> {}", userInfo.getPwd(), pwd);
				userInfo.setPwd(pwd);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

/*
	@Cacheable(value = "statisticsMst")
	public Map<String, Map<String, List<DataMap>>> selectStatisticsMst() {
		return statisticsMapper.selectStatisticsMst();
	}

	@Cacheable(value = "indicatorMst")
	public Map<String, Map<String, List<DataMap>>> selectIndicatorMst() {
		return statisticsMapper.selectIndicatorMst();
	}

	@Cacheable(value = "columnMst")
	public Map<String, Map<String, List<DataMap>>> selectColumnMst() {
		return statisticsMapper.selectColumnMst();
	}

	@SuppressWarnings({ "unchecked"})
	private Map<String, List<DataMap>> listFromListOfMap (List<DataMap> listOfMap) {
		
		Map<String, List<DataMap>> map = new HashMap<String, List<DataMap>>();
		for(int i = 0; i < listOfMap.size(); i++) {
			String key = (String) listOfMap.get(i).get("key");
			List<DataMap> list = (List<DataMap>) listOfMap.get(i).get("list");
			map.put(key, list);
		}
		
		return map;
	}

	private Map<String, DataMap> dmapFromListOfMap (List<DataMap> listOfMap) {
		
		Map<String, DataMap> map = new HashMap<String, DataMap>();
		for(int i = 0; i < listOfMap.size(); i++) {
			String key = (String) listOfMap.get(i).get("key");
			DataMap dmap = (DataMap) listOfMap.get(i).get("dmap");
			map.put(key, dmap);
		}
		
		return map;
	}
*/
}