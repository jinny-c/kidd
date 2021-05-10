package com.kidd.base.factory.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
import com.kidd.base.common.utils.KiddTraceLogUtil;
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
	
	private Lock lock;
	
	// 任务执行器
	private ExecutorService executorService;

	// 5线程执行
	private void initThreadPool() {
		if (executorService == null) {
			log.info("initThreadPool start");
			executorService = new ThreadPoolExecutor(5, 5, 0L,
					TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(),
					new ThreadPoolExecutor.DiscardPolicy());
		}
	}
	
	public static Map<String, Object> cacheMap = new HashMap<String, Object>();

	@PostConstruct
	public void init() {
		// 为true表示为公平锁，为fasle为非公平锁。默认情况下，如果使用无参构造器，则是非公平锁
		lock = new ReentrantLock(true);// 公平锁
		// 即便是公平锁，如果通过不带超时时间限制的tryLock()的方式获取锁的话，它也是不公平的
		// 但是带有超时时间限制的tryLock(long timeout, TimeUnit unit)方法则不一样，还是会遵循公平或非公平的原则的
		
		initThreadPool();
		
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
		lock.lock();
		try {
			
			List<Future<String>> results = new ArrayList<Future<String>>();
			for (int i = 0; i < 6; i++) {
				results.add(executorService.submit(new threadProcessor(i+"a")));
			}
			for (Future<String> result : results) {
				try {
					log.info("future get={}",result.get());
				} catch (Exception e) {
					e.printStackTrace();
					log.error("future get exception",e);
				}
			}
			
			String cacheRes = (String)kiddConfigCache.asMap().get(key);
			String value = "cache refresh";
			if (cacheRes == null) {
				value = "cache is null";
			}
			kiddConfigCache.asMap().put(key, value);
		} catch (Exception e) {
			log.error("refreshCache",e);
		} finally {
			lock.unlock();
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
		return getTestDto(pubId);
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
		KiddPubNoInfoDTO pubNoDto = getTestDto(pubNo);
		KiddPubNumTokenDTO tokenDTO = new KiddPubNumTokenDTO();
		tokenDTO.setPubNo(pubNoDto.getPubId());
		tokenDTO.setAppId(pubNoDto.getAppId());
		tokenDTO.setAppSecret(pubNoDto.getAppSecret());
		tokenDTO.setAccessTokenUrl(ConfigRef.WX_TOKEN_URI);
		tokenDTO.setMandatory(true); //强制刷新
		return tokenDTO;
	}
	
	private KiddPubNoInfoDTO getTestDto(String pubNoId){
		KiddPubNoInfoDTO dto = new KiddPubNoInfoDTO();
		if("gh_51790c1ef5c3".equals(pubNoId)){
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
	 * 线程执行的类
	 * 
	 */
	private class threadProcessor implements Callable<String> {
		private String value;

		public threadProcessor(String value) {
			this.value = value;
		}

		@Override
		public String call() throws Exception {
			log.info("id={}",KiddTraceLogUtil.getTraceId());
			try {
				log.info("thread call value={}",value);
			} catch (Exception e) {
				log.error("thread call exception:{}", e);
			}
			return value;
		}
	}

	public static void main(String[] args) {
		System.out.println("ssTT".hashCode());
		System.out.println("sStt".hashCode());

		Map<String,String> stm = new HashMap<>();
		stm.put("st","1");
		stm.put("sT","2");
		stm.put("St","3");
		stm.put("ST","4");
		System.out.println(stm);

		LoadingCache<String, Object> ch = CacheBuilder.newBuilder()
				.refreshAfterWrite(4, TimeUnit.HOURS) // 默认4个小时过期
				.build(new CacheLoader<String, Object>() {
					@Override
					public Object load(String key) throws Exception {
						log.debug("LoadingCache start:key={}", key);
						return cacheMap.get(key);
					}
				});

		ch.put("st","1");
		ch.put("sT","2");
		ch.put("St","3");
		ch.put("ST","4");

		System.out.println(ch.getUnchecked("sT"));
		System.out.println(ch.getUnchecked("ST"));


	}
}