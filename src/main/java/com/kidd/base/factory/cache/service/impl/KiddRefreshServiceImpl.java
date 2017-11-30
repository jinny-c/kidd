package com.kidd.base.factory.cache.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.base.common.utils.KiddDateUtils;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.factory.cache.dto.KiddPubNumTokenDTO;
import com.kidd.base.factory.cache.service.IKiddRefreshService;
import com.kidd.base.http.httpclient.KiddHttpBuilder;
import com.kidd.base.http.httpclient.KiddHttpExecutor;

@Service(value = "kiddRefreshService")
public class KiddRefreshServiceImpl implements IKiddRefreshService {

	/** 日志 */
	private static Logger log = LoggerFactory.getLogger(KiddRefreshServiceImpl.class);
	
	private static final String CHECK_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token={}";
	
	private KiddHttpExecutor executor;

	@PostConstruct
	private void initHttpExecutor() {
		try {
			executor = KiddHttpBuilder.create()
					.loadPool(1, 1)
					.loadTimeOut(3000, 3000)
					.loadIgnoreUrl()
					.build();
			log.info("create http executor success");
		} catch (Exception e) {
			log.error("create http executor fail", e);
		}
	}
	
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

	@Override
	public void refreshAccessToken(KiddPubNumTokenDTO req) {
		if (KiddStringUtils.isBlank(req.getAccessTokenUrl())) {
			log.warn("微信公众号[{}]access_token接口地址为空", req.getPubNo());
			return;
		}
		log.info("微信公众号access_token刷新请求参数：{}", req);
		try {
			//强制刷新
			if (req.isMandatory()) {
				log.info("微信公众号[{}]access_token强制刷新", req.getPubNo());
				WechatAccessToken accessToken = requestAccessToken(req);
				store2RedisCache(req.getPubNo(), req.getAppId(), accessToken);
				return;
			}
			KiddPubNumTokenDTO pubNumToken = getValidPubNumTokenByCache(req.getPubNo(), req.getAppId());
			if (null != pubNumToken) {
				log.info("微信公众号[{}]access_token缓存未过期，无需刷新", req.getPubNo());
				return;
			}
			log.info("微信公众号[{}]access_token缓存开始", req.getPubNo());
			WechatAccessToken accessToken = requestAccessToken(req);
			store2RedisCache(req.getPubNo(), req.getAppId(), accessToken);
			log.info("微信公众号[{}]access_token缓存刷新成功", req.getPubNo());
		} catch (Throwable e) {
			log.error("微信公众号[{}]access_token刷新异常", req.getPubNo(), e);
		}
	}

	@Override
	public String getAccessToken(String pubNo, String appId) {
		// TODO Auto-generated method stub
		KiddPubNumTokenDTO cache = null;
		return null;
	}
	
	@Override
	public KiddPubNumTokenDTO getAccessToken(KiddPubNumTokenDTO req) {
		// TODO Auto-generated method stub
		if (KiddStringUtils.isBlank(req.getAccessTokenUrl())) {
			log.warn("微信公众号[{}]access_token接口地址为空", req.getPubNo());
			return null;
		}
		log.info("微信公众号access_token刷新请求参数：{}", req);
		try {
			log.info("微信公众号[{}]access_token开始", req.getPubNo());
			WechatAccessToken accessToken = requestAccessToken(req);
			log.info("微信公众号[{}]access_token成功", req.getPubNo());
			
			return store2RedisCache(req.getPubNo(), req.getAppId(), accessToken);
		} catch (Throwable e) {
			log.error("微信公众号[{}]access_token异常", req.getPubNo(), e);
		}
		return null;
	}
	
	/**
	 * 获取微信公众平台API接口调用的凭证
	 *
	 * @author jiezhang 2017-01-16
	 * @return
	 */
	private WechatAccessToken requestAccessToken(KiddPubNumTokenDTO req) {
		String requestUrl = KiddStringUtils.replace(req.getAccessTokenUrl(), req.getAppId(), req.getAppSecret());
		try {
			log.info("请求access_token: {}", requestUrl);
			String result = executor.doGetWithUrl(requestUrl, null);
			log.info("请求access_token结果: {}", result);
			if(KiddStringUtils.isBlank(result)){
				return null;
			}

			JSONObject jsonObject = JSON.parseObject(result);
			String access_token = jsonObject.getString("access_token");
			if(KiddStringUtils.isBlank(access_token)){
				log.error("获取access_token失败，errcode:{} errmsg:{}", jsonObject.getInteger("errcode"),
						jsonObject.getString("errmsg"));
				return null;
			}
			WechatAccessToken accessToken = new WechatAccessToken();
			accessToken.setAccess_token(access_token);
			accessToken.setExpires_in(jsonObject.getInteger("expires_in"));
			return accessToken;
		} catch (Exception e) {
			log.error("HTTP请求异常", e);
		}
		return null;
	}
	
	private KiddPubNumTokenDTO store2RedisCache(String pubNo, String appId,
			WechatAccessToken accessToken) {
		if (null == accessToken) {
			return null;
		}
		KiddPubNumTokenDTO pubNoToken = new KiddPubNumTokenDTO();
		pubNoToken.setPubNo(pubNo);
		pubNoToken.setAppId(appId);
		pubNoToken.setAccessToken(accessToken.getAccess_token());
		pubNoToken.setCreateTime(KiddDateUtils.getCurrentDate());
		// 计算无效时间，单位为：分
		String nextInvalidateTime = KiddDateUtils.getNextTime(
				KiddDateUtils.yyyyMMddHHmmss, pubNoToken.getCreateTime(),
				accessToken.getExpires_in() / 60);
		pubNoToken.setInvalidTime(nextInvalidateTime);
		return pubNoToken;
	}
	
	
	private KiddPubNumTokenDTO getValidPubNumTokenByCache(String pubNo, String appId) {
		try {
			KiddPubNumTokenDTO cache = null;
			if (cache == null ) {
				return null;
			}
			if (!KiddDateUtils.afterCurrDate(KiddDateUtils.yyyyMMddHHmmss, cache.getInvalidTime())) {
				log.error("微信公众号[{}]access_token过期,当前时间=[{}],会话失效时间=[{}]", pubNo,
						KiddDateUtils.getCurrentDate(), cache.getInvalidTime());
				return null;
			}
			//检查access_token是否有效
			if (!checkAccessToken(cache.getAccessToken())) {
				log.error("微信公众号[{}]access_token无效", pubNo);
				return null;
			}
			return cache;
		} catch (Exception e) {
			log.error("缓存access_token查询失败", e);
		}
		return null;
	}
	private boolean checkAccessToken(String accessToken) {
		String requestUrl = KiddStringUtils.replace(CHECK_URL, accessToken);
		try {
			log.info("检查access_token有效性开始: {}", requestUrl);
			String result = executor.doGetWithUrl(requestUrl, null);
			log.info("检查access_token有效性结束, isContainsErrCode: {}", result.contains("errcode"));
			return KiddStringUtils.isNotBlank(result) && !result.contains("errcode");
		} catch (Exception e) {
			log.error("HTTP请求异常", e);
		}
		return false;
	}
	private class WechatAccessToken {
		private String access_token;
		private Integer expires_in;

		public String getAccess_token() {
			return access_token;
		}

		public void setAccess_token(String access_token) {
			this.access_token = access_token;
		}

		public Integer getExpires_in() {
			return expires_in;
		}

		public void setExpires_in(Integer expires_in) {
			this.expires_in = expires_in;
		}
	}

}
