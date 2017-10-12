package com.kidd.wap.util;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.base.common.constant.KiddConstants;
import com.kidd.base.common.enums.KiddPayTypeEnum;
import com.kidd.base.common.serialize.KiddFastJsonUtils;
import com.kidd.base.common.utils.ConfigRef;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.common.utils.KiddTraceLogUtil;
import com.kidd.base.http.httpclient.KiddHttpExecutor;
import com.kidd.wap.controller.dto.OAuthUser;

public class KiddPayBizUtil {
	private static Logger log = LoggerFactory.getLogger(KiddPayBizUtil.class);

	/** HTTP 执行器 */
	private static KiddHttpExecutor executor = KiddHttpExecutorHolder.getExecutor();


	/**
	 * 获取用户信息 【扫码支付】
	 * @param payType
	 * @param authCode
	 * @return
	 */
	public static OAuthUser getOAuthUser(String payType, String authCode) {
		switch (KiddPayTypeEnum.covertByType(payType)){
			case PAY_TYPE_WECHAT_SCAN_PAY:
				//扫码支付使用扫码公众号
				return getWXAuthUser(ConfigRef.WX_APP_ID_SCAN_PAY,
						ConfigRef.WX_APP_SECRET_SCAN_PAY, authCode, KiddConstants.WX_OAUTH2_BASE_SCOPE);
			case PAY_TYPE_ALI_SCAN_PAY:
				//return getAliAuthUser(authCode);
			default:
				return null;
		}
	}

	/**
	 * 获取微信用户信息
	 * @param code
	 * @return
	 */
	public static OAuthUser getWXAuthUser(String appId, String appSecret, String code, String scope) {
		//通过code换取网页授权access_token
		Map<String, String> accessMap = getOAuthAccessToken(appId, appSecret, code);
		if(null == accessMap){
			return null;
		}

		if(KiddConstants.WX_OAUTH2_BASE_SCOPE.equals(scope)){
			OAuthUser oAuthUser = new OAuthUser();
			oAuthUser.setOpenid(accessMap.get("openid"));
			return oAuthUser;
		}

		String requestUrl = KiddStringUtils.replace(ConfigRef.WX_USER_INFO_URI,
				accessMap.get("access_token"), accessMap.get("openid"));
		try {
			log.info("TraceID:{}, 请求授权用户信息: {}", KiddTraceLogUtil.getTraceId(), requestUrl);
			String result = executor.doGetWithUrl(requestUrl, null);
			log.info("TraceID:{}, 请求授权用户信息结束", KiddTraceLogUtil.getTraceId());
			//TODO JiddJacksonUtils
			return KiddFastJsonUtils.toObj(result, OAuthUser.class);
		} catch (Exception e) {
			log.error("TraceID:{}, {}", KiddTraceLogUtil.getTraceId(), e.getMessage(), e);
			return null;
		}
	}


	/**
	 * 通过code换取网页授权access_token 【区别微信公众平台API的access_token】
	 *
	 * @param code
	 * @return
	 */
	private static Map<String, String> getOAuthAccessToken(String appId, String appSecret, String code) {
		try {
			String requestUrl = KiddStringUtils.replace(ConfigRef.WX_OAUTH2_TOKEN_URI, appId, appSecret, code);
			log.info("TraceID:{}, 请求网页授权的AccessToken: {}", KiddTraceLogUtil.getTraceId(), requestUrl);
			String result = executor.doGetWithUrl(requestUrl, null);
			log.info("TraceID:{}, 请求网页授权的AccessToken结果: {}", KiddTraceLogUtil.getTraceId(), result);
			if(KiddStringUtils.isBlank(result)){
				return null;
			}

			JSONObject jsonObject = JSON.parseObject(result);
			String access_token = jsonObject.getString("access_token");
			String openid = jsonObject.getString("openid");
			if(KiddStringUtils.isBlank(access_token) || KiddStringUtils.isBlank(openid)){
				log.error("TraceID:{}, 获取网页授权的AccessToken失败，errcode:{} and errmsg:{}",
						KiddTraceLogUtil.getTraceId(), jsonObject.getInteger("errcode"),
						jsonObject.getString("errmsg"));
				return null;
			}

			//请求成功
			Map<String, String> map = new HashMap<String, String>();
			map.put("access_token", access_token);
			map.put("openid", openid);
			return map;
		} catch (Exception e) {
			log.error("TraceID:{}, HTTP请求异常", KiddTraceLogUtil.getTraceId(), e);
			return null;
		}
	}
}
