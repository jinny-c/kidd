package com.kidd.base.factory.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kidd.base.common.utils.ConfigRef;
import com.kidd.base.common.utils.KiddDateUtils;
import com.kidd.base.factory.cache.dto.KiddPubNumTokenDTO;
import com.kidd.base.factory.cache.service.IKiddRefreshService;
import com.kidd.base.factory.wechat.dto.KiddPubNoInfoDTO;

@Component
public class KiddCacheManager {
	/** 日志 */
	private static Logger log = LoggerFactory.getLogger(KiddCacheManager.class);

	@Autowired
	private IKiddRefreshService kiddRefreshServiceImpl;
	
	private LoadingCache<String, Object> kiddConfigCache;
	
	public static Map<String, Object> cacheMap = new HashMap<String, Object>();

	@PostConstruct
	public void init() {
		kiddConfigCache = CacheBuilder.newBuilder()
				.refreshAfterWrite(4, TimeUnit.HOURS) // 默认4个小时过期
				.build(new CacheLoader<String, Object>() {
					@Override
					public Object load(String key) throws Exception {
						log.debug("LoadingCache start:key={}", key);
						return cacheMap.get(key);
					}
				});
	}
	
	/**
	 * 从缓存中读取信息
	 * @param key
	 * @return
	 */
	public String getCacheConfig(String key) {
		log.debug("getCacheConfig start,key={}", key);
		try {
			return (String)kiddConfigCache.getUnchecked(key);//未声明CacheLoader异常
			//Optional<T> cacheRes = jiddConfigCache.asMap().get(key);
			//return jiddConfigCache.get(key);//已声明CacheLoader.load异常
		} catch (Exception e) {
			log.error("从内存中获取信息失败", e);
		}
		return null;
	}
	
	public void setCacheConfig(String key, String value) {
		log.debug("setCacheConfig start,key={},value={}", key, value);
		try {
			kiddConfigCache.put(key, value);
		} catch (Exception e) {
			log.error("set cache exception", e);
		}
	}

	public void refreshCache(String key) {
		log.debug("refreshCache start,key={}", key);
		try {
			String cacheRes = (String)kiddConfigCache.asMap().get(key);
			String value = "cache refresh";
			if (cacheRes == null) {
				value = "cache is null";
			}
			kiddConfigCache.asMap().put(key, value);
		} catch (Exception e) {
			log.error("refreshCache",e);
		}
	}

	public void refreshCache() {
		log.info("refreshCache start");
		try {
			if (null == cacheMap || cacheMap.isEmpty()) {
				log.error("refreshCache cacheMap is null");
				return;
			}
			log.debug("cacheMap={}", cacheMap);
			for (Map.Entry<String, Object> entry : cacheMap.entrySet()) {
				kiddConfigCache.put(entry.getKey(), entry.getValue());
			}
			
		} catch (Exception e) {
			log.error("refreshCache Exception", e);
		}
	}
	public void clearAllCache() {
		log.info("clearAllCache start");
		kiddConfigCache.invalidateAll();
	}
	
	public KiddPubNoInfoDTO getPubNoConfig(String pubId) {
		log.info("getPubNoConfig start,pubId={}", pubId);
		return null;
	}
	
	public String getAccessToken(String pubNo) {
		log.info("getAccessToken start,pubId={}", pubNo);

		KiddPubNumTokenDTO tokenDto = (KiddPubNumTokenDTO)kiddConfigCache.asMap().get(pubNo);
		if (null != tokenDto
				&& KiddDateUtils.afterCurrDate(KiddDateUtils.yyyyMMddHHmmss,
						tokenDto.getInvalidTime())) {
			log.info("get access_token from cache,tokenDto={}", tokenDto);
			return tokenDto.getAccessToken();
		}
		log.info("微信公众号[{}]access_token过期,重新获取", pubNo);
		tokenDto = kiddRefreshServiceImpl.getAccessToken(cover2Dto(pubNo));
		kiddConfigCache.asMap().put(pubNo, tokenDto);
		
		return tokenDto.getAccessToken();
	}
	
	private KiddPubNumTokenDTO cover2Dto(String pubNo){
		if("gh_d8ca418ebb2b".equals(pubNo)){
			return cover2Dto2();
		}
		return cover2Dto1();
	}
	private KiddPubNumTokenDTO cover2Dto1(){
		KiddPubNumTokenDTO tokenDTO = new KiddPubNumTokenDTO();
		tokenDTO.setPubNo("gh_51790c1ef5c3");
		tokenDTO.setAppId("wxc265b22e9ecff5cc");
		tokenDTO.setAppSecret("473a6a9b85a1e282d0623c71a91a6df5");
		tokenDTO.setAccessTokenUrl(ConfigRef.WX_TOKEN_URI);
		tokenDTO.setMandatory(true); //强制刷新
		return tokenDTO;
	}
	
	private KiddPubNumTokenDTO cover2Dto2(){
		KiddPubNumTokenDTO tokenDTO = new KiddPubNumTokenDTO();
		tokenDTO.setPubNo("gh_d8ca418ebb2b");
		tokenDTO.setAppId("wxb17ce3d03ed8073e");
		tokenDTO.setAppSecret("ea3b47e92bc17d958ea2f168c3f62dad");
		tokenDTO.setAccessTokenUrl(ConfigRef.WX_TOKEN_URI);
		tokenDTO.setMandatory(true); //强制刷新
		return tokenDTO;
	}
}