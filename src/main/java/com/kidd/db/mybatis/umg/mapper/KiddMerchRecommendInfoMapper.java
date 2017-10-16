package com.kidd.db.mybatis.umg.mapper;

import com.kidd.db.mybatis.umg.mapper.domain.KiddMerchRecommendInfo;

public interface KiddMerchRecommendInfoMapper {
    int deleteByPrimaryKey(Integer loginUserId);

    int insert(KiddMerchRecommendInfo record);

    KiddMerchRecommendInfo selectByPrimaryKey(Integer loginUserId);

    int updateByPrimaryKeySelective(KiddMerchRecommendInfo record);

    int selectAll();
}