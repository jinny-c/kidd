package com.kidd.base.factory.cache.service;

import com.kidd.base.factory.cache.dto.KiddPubNumTokenDTO;


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


	
	/**
	 * 刷新微信公众号接口访问凭证
	 * @param req
	 */
	void refreshAccessToken(KiddPubNumTokenDTO req);

	/**
	 * 查询缓存Token
	 * @param pubNo
	 * @param appId
	 * @return
	 */
	String getAccessToken(String pubNo, String appId);
	
	KiddPubNumTokenDTO getAccessToken(KiddPubNumTokenDTO req);
	
}