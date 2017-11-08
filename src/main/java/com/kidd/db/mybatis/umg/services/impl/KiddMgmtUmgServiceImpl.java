package com.kidd.db.mybatis.umg.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kidd.base.common.utils.KiddObjectUtils;
import com.kidd.db.mybatis.umg.mapper.KiddMerchRecommendInfoMapper;
import com.kidd.db.mybatis.umg.mapper.domain.KiddMerchRecommendInfo;
import com.kidd.db.mybatis.umg.services.IKiddMgmtUmgService;
import com.kidd.db.mybatis.umg.services.bean.KiddMerchRecommendBean;

@Service(value = "kiddMgmtUmgService")
public class KiddMgmtUmgServiceImpl implements IKiddMgmtUmgService {
	private static Logger log = LoggerFactory.getLogger(KiddMgmtUmgServiceImpl.class);
    @Autowired
    private KiddMerchRecommendInfoMapper merchRecommendInfoMapper;
    
	@Override
	public Integer queryCount() {
		// TODO Auto-generated method stub
		log.info("queryCount start");
		//merchRecommendInfoMapper.selectAll();
		try {
			return merchRecommendInfoMapper.selectAll();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("query count exception:",e);
		}
		return 0;
	}

	@Override
	public KiddMerchRecommendBean queryByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		log.info("queryByPrimaryKey start,id={}",id);
		KiddMerchRecommendInfo info = merchRecommendInfoMapper.selectByPrimaryKey(id);
		if (null == info) {
			return null;
		}
		return KiddObjectUtils.copyProperty(KiddMerchRecommendBean.class, info);
	}
	
}
