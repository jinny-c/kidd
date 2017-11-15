package com.kidd.db.mybatis.umg.mapper;

import java.util.List;

import com.kidd.db.mybatis.umg.mapper.domain.KiddUserInfo;

public interface KiddUserInfoMapper {
	//TODO 不实现
	@Deprecated
    int deleteByPrimaryKey(String userId);

    int insertSelective(KiddUserInfo record);

    KiddUserInfo selectByPrimaryKey(String userId);
    
    KiddUserInfo selectByUserName(String userName);
    
    KiddUserInfo selectByCondition(KiddUserInfo record);

    int updateByPrimaryKeySelective(KiddUserInfo record);
    
    int countRecordsByPaging(KiddUserInfo record);
    
	List<KiddUserInfo> selectByPaging(KiddUserInfo record);

}