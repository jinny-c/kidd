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
import com.kidd.base.factory.cache.dto.KiddPubNumTokenDTO;
import com.kidd.base.factory.cache.service.IKiddRefreshService;
import com.kidd.base.factory.wechat.dto.KiddPubNoInfoDTO;

@Component
public class KiddCacheManager {
	/** 日志 */
	private static Logger log = LoggerFactory.getLogger(KiddCacheManager.class);

	@Autowired
	private IKiddRefreshService kiddRefreshServiceImpl;
	
	private LoadingCache<String, String> kiddConfigCache;
	
	public static Map<String, String> cacheMap = new HashMap<String, String>();

	@PostConstruct
	public void init() {
		kiddConfigCache = CacheBuilder.newBuilder()
				.refreshAfterWrite(4, TimeUnit.HOURS) // 默认4个小时过期
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
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
			return kiddConfigCache.getUnchecked(key);//未声明CacheLoader异常
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
			String cacheRes = kiddConfigCache.asMap().get(key);
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
			for (Map.Entry<String, String> entry : cacheMap.entrySet()) {
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
	
	public String getAccessToken(String pubId) {
		log.info("getAccessToken start,pubId={}", pubId);
		//String cache = this.getCacheConfig(pubId);
		KiddPubNumTokenDTO tokenDTO = new KiddPubNumTokenDTO();
		tokenDTO.setPubNo("gh_51790c1ef5c3");
		tokenDTO.setAppId("wxc265b22e9ecff5cc");
		tokenDTO.setAppSecret("473a6a9b85a1e282d0623c71a91a6df5");
		tokenDTO.setAccessTokenUrl(ConfigRef.WX_TOKEN_URI);
		tokenDTO.setMandatory(true); //强制刷新
		
		return kiddRefreshServiceImpl.getAccessToken(tokenDTO);
	}
	
}