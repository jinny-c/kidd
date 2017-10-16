package com.kidd.base.factory.wechat;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.base.common.enums.KiddSymbolEnum;
import com.kidd.base.common.utils.ConfigRef;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.common.utils.KiddTraceLogUtil;
import com.kidd.base.factory.cache.KiddCacheManager;
import com.kidd.base.factory.wechat.dto.KiddPubNoInfoDTO;
import com.kidd.base.factory.wechat.dto.WechatTemplateMsg;
import com.kidd.base.http.httpclient.KiddHttpExecutor;
import com.kidd.base.http.util.KiddHttpExecutorHolder;
import com.kidd.wap.controller.dto.OAuthUser;
import com.kidd.wap.util.KiddPayBizUtil;

@Component
public class KiddPubNoClient {

	@Autowired
	private KiddCacheManager kiddCacheManager;

	private static Logger log = LoggerFactory.getLogger(KiddPubNoClient.class);

	/** HTTP 执行器 */
	private static KiddHttpExecutor executor = KiddHttpExecutorHolder.getExecutor();

	/**
	 * 清除本地公众号缓存
	 *
	 * @return
	 */
	public void clearCache() {
		kiddCacheManager.clearAllCache();
	}

	/**
	 * 刷新公众号TOKEN缓存
	 *
	 * @return
	 */
	public void refreshToken(String pubId) {
		//kiddCacheManager.refreshToken(pubId);
	}


	/**
	 * 获取微信公众号配置
	 *
	 * @return
	 */
	public String getPubNoAppId(String pubId) {
		KiddPubNoInfoDTO pubNoConfig = kiddCacheManager.getPubNoConfig(pubId);
		if (pubNoConfig == null) {
			return "";
		}
		return pubNoConfig.getAppId();
	}

	/**
	 * 获取微信公众号模板ID
	 *
	 * @return
	 */
	public String getMsgTemplateId(String pubId, String msgType) {
		KiddPubNoInfoDTO pubNoConfig = kiddCacheManager.getPubNoConfig(pubId);
		if (pubNoConfig == null) {
			return "";
		}
		return pubNoConfig.getMsgTempId(msgType);
	}

	/**
	 * 获取微信公众号图片资源URL
	 *
	 * @return
	 */
	public String getPicUrl(String pubId, String picType) {
		KiddPubNoInfoDTO pubNoConfig = kiddCacheManager.getPubNoConfig(pubId);
		if (pubNoConfig == null) {
			return "";
		}
		return pubNoConfig.getPicUrl(picType);
	}

	/**
	 * 获取微信用户信息
	 * @param code
	 * @return
	 */
	public OAuthUser getWXAuthUser(String pubId, String code, String scope) {
		KiddPubNoInfoDTO pubNoConfig = kiddCacheManager.getPubNoConfig(pubId);
		if (pubNoConfig == null) {
			pubNoConfig = getTestDto(pubId);
			//return null;
		}
		return KiddPayBizUtil.getWXAuthUser(pubNoConfig.getAppId(), pubNoConfig.getAppSecret(), code, scope);
	}

	private KiddPubNoInfoDTO getTestDto(String pubId){
		KiddPubNoInfoDTO dto = new KiddPubNoInfoDTO();
		if("gh_51790c1ef5c3".equals(pubId)){
			dto.setPubId("gh_51790c1ef5c3");
			dto.setAppId("wxc265b22e9ecff5cc");
			dto.setAppSecret("473a6a9b85a1e282d0623c71a91a6df5");
			dto.setPubStatus("00");
			dto.setPubName("开发用");	
		}else{
			dto.setPubId("gh_d8ca418ebb2b");
			dto.setAppId("wxb17ce3d03ed8073e");
			dto.setAppSecret("ea3b47e92bc17d958ea2f168c3f62dad");
			dto.setPubStatus("00");
			dto.setPubName("开发用");
		}
		return dto;
	}
		
	/**
	 * 长链接转短链接
	 *
	 */
	public String long2short(String pubId, String url) {
		String requestUrl = KiddStringUtils.replace(ConfigRef.WX_SHORT_TOKEN, kiddCacheManager.getAccessToken(pubId));
		String result = null;
		try {
			JSONObject object = new JSONObject();
			object.put("action", "long2short");
			object.put("long_url", url);
			String requestData = object.toString();
			log.debug("TraceID:{}, start long2short, requestData={}, requestUrl={}", KiddTraceLogUtil.getTraceId(),
					requestData, requestUrl);
			result = executor.doPostWithUrl(requestUrl, requestData, null); //发送JSON格式数据
			log.info("TraceID:{}, finish long2short, result={}", KiddTraceLogUtil.getTraceId(), result);
		} catch (Exception e) {
			log.error("TraceID:{}, 长链接转短链接，HTTP请求异常", KiddTraceLogUtil.getTraceId(), e);
		}
		if (KiddStringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSON.parseObject(result);
			if (jsonObject == null) {
				return url;
			}
			if (isContainsErrorCode(jsonObject.toString())) {
				log.info("TraceID:{}, long2short failed, 强制刷新access_token", KiddTraceLogUtil.getTraceId());
				//kiddCacheManager.refreshToken(pubId);
			}
			return jsonObject.getString("short_url");
		}
		return KiddSymbolEnum.Blank.symbol();
	}


	/**
	 * 创建菜单
	 *
	 */
	public boolean createMenu(String pubId, String menuJson) {
		String requestUrl = KiddStringUtils.replace(ConfigRef.WX_MENU_CREATE_URI, kiddCacheManager.getAccessToken(pubId));
		try {
			log.info("TraceID:{}, 创建菜单开始, menuJson={}, requestUrl={}", KiddTraceLogUtil.getTraceId(),
					menuJson, requestUrl);
			String result = executor.doPostWithUrl(requestUrl, menuJson, null); //发送JSON格式数据
			log.info("TraceID:{}, 创建菜单完成, result={}", KiddTraceLogUtil.getTraceId(), result);
			if (KiddStringUtils.isNotBlank(result)) {
				JSONObject jsonObject = JSON.parseObject(result);
				return "ok".equals(jsonObject.getString("errmsg"));
			}
		} catch (Exception e) {
			log.error("TraceID:{}, 创建菜单失败，HTTP请求异常", KiddTraceLogUtil.getTraceId(), e);
		}
		return false;
	}

	/**
	 * 推送客服消息
	 *
	 * @param openId
	 * @param text
	 */
	public void pushCustomMessage(String pubId, String openId, String text) {
		JSONObject textObj = new JSONObject();
		textObj.put("content", text);

		JSONObject msgObj = new JSONObject();
		msgObj.put("touser", openId);
		msgObj.put("msgtype", "text");
		msgObj.put("text", textObj);

		String msgContent = msgObj.toString();
		String requestUrl = KiddStringUtils.replace(ConfigRef.WX_MSG_KF_URI, kiddCacheManager.getAccessToken(pubId));
		try {
			log.info("TraceID:{}, 推送客服消息开始, msgContent={}, requestUrl={}", KiddTraceLogUtil.getTraceId(),
					msgContent, requestUrl);
			String result = executor.doPostWithUrl(requestUrl, msgContent, null); //发送JSON格式数据
			log.info("TraceID:{}, 推送客服消息完成, result={}", KiddTraceLogUtil.getTraceId(), result);
		} catch (Exception e) {
			log.error("TraceID:{}, 推送客服消息失败，HTTP请求异常", KiddTraceLogUtil.getTraceId(), e);
		}
	}

	/**
	 * 推送模板消息
	 * 
	 * @param toUser
	 * @param templateID
	 * @param url
	 * @param dataMap
	 * @param count 发送失败时重发次数
	 */
	public void pushTemplateMessage(String pubId, String toUser, String templateID,
	                                String url, Map<String, String> dataMap, Integer count) {
		WechatTemplateMsg templateMsg = new WechatTemplateMsg(toUser, templateID, url);
		for (Map.Entry<String, String> entry : dataMap.entrySet()) {
			templateMsg.putData(entry.getKey(), entry.getValue());
		}
		String msgContent = JSON.toJSONString(templateMsg);
		String requestUrl = KiddStringUtils.replace(ConfigRef.WX_MSG_TMP_URI, kiddCacheManager.getAccessToken(pubId));
		String result = null;
		try {
			log.info("TraceID:{}, 推送模板消息开始, msgContent: {}, requestUrl: {}", KiddTraceLogUtil.getTraceId(),
					msgContent, requestUrl);
			result = executor.doPostWithUrl(requestUrl, msgContent, null);
			log.info("TraceID:{}, 推送模板消息完成, result: {}", KiddTraceLogUtil.getTraceId(), result);
		} catch (Exception e) {
			log.error("TraceID:{}, 推送模板消息失败，HTTP请求异常", KiddTraceLogUtil.getTraceId(), e);
		}
		if (KiddStringUtils.isNotBlank(result)) {
			JSONObject jsonObject = JSON.parseObject(result);
			boolean errorFlag = isContainsErrorCode(jsonObject == null ? "" : jsonObject.toString());
			if (errorFlag && count > 0) {
				//kiddCacheManager.refreshToken(pubId);
				pushTemplateMessage(pubId, toUser, templateID, url, dataMap, count-1);
			}
		}
	}

	private boolean isContainsErrorCode(String result) {
		boolean error40001 = result.indexOf("40001") > 0; //access_token无效，检查AppSecret的正确性
		boolean error42001 = result.indexOf("42001") > 0; //access_token超时
		boolean error40014 = result.indexOf("40014") > 0; //不合法的access_token，检查是否过期
		boolean error40003 = result.indexOf("40003") > 0; //不合法的OpenID
		boolean error45015 = result.indexOf("45015") > 0; //回复时间超时
		return error40014 || error42001 || error40001 || error40003 || error45015;
	}

}
