package com.kidd.base.cache.service;


/**
 *
 * @history
 */

public interface IKiddRefreshService {

	/**
	 * 刷新
	 * @param keyFlag
	 */
	
	void refreshToken(String keyFlag);

	String getToken(String pubNo, String appId);


}