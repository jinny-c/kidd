package com.kidd.db.mybatis.umg.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kidd.db.mybatis.umg.mapper.KiddMerchRecommendInfoMapper;
import com.kidd.db.mybatis.umg.services.IKiddMgmtUmgService;

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
		return merchRecommendInfoMapper.selectAll();
	}
	
}
