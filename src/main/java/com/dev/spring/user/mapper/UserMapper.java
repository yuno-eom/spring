package com.dev.spring.user.mapper;

import java.util.List;

import com.dev.spring.common.DataMap;
import com.dev.spring.user.model.UserInfo;

public interface UserMapper {

	Object selectUserInfo(DataMap paramMap);

	List<UserInfo> selectUserInfoList(DataMap paramMap);

	Integer deleteUserInfo(DataMap paramMap);

	Integer insertUserInfo(UserInfo userInfo);

	Integer updateUserInfo(UserInfo userInfo);

	Integer selectUserGridRow(DataMap paramMap);

	List<DataMap> selectUserGrid(DataMap paramMap);

/*
	@MapKey(value = "staCodGrp")
	Map<String, Map<String, List<DataMap>>> selectStatisticsMst();

	@MapKey(value = "staCod")
	Map<String, Map<String, List<DataMap>>> selectIndicatorMst();

	@MapKey(value = "scmColGrp")
	Map<String, Map<String, List<DataMap>>> selectColumnMst();
*/
}