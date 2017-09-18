package com.kidd.base.cache.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kidd.base.cache.service.IKiddRefreshService;

@Service(value = "kiddRefreshService")
public class KiddRefreshServiceImpl implements IKiddRefreshService {

	/** 日志 */
	private static Logger log = LoggerFactory.getLogger(KiddRefreshServiceImpl.class);
	
	@Override
	public void refreshToken(String keyFlag) {
		// TODO Auto-generated method stub
		log.info("refreshToken start,keyFlag={}", keyFlag);
	}

	@Override
	public String getToken(String pubNo, String appId) {
		// TODO Auto-generated method stub
		log.info("getToken start,pubNo={}", pubNo);
		return null;
	}
	
}
