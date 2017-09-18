package com.kidd.base.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import com.kidd.base.cache.service.IKiddRefreshService;
import com.kidd.base.utils.KiddStringUtils;

/**
 * SpringContext监听器
 *
 * @history
 */
public class SpringContextListener implements ApplicationListener<ApplicationContextEvent> {
	/** SLF4J */
	private static final Logger log = LoggerFactory.getLogger(SpringContextListener.class);

	private static final String BEAN_ID_TOKEN = "kiddRefreshService";

	private KiddRefreshTimer timer = new KiddRefreshTimer();;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if (event.getApplicationContext().getParent() == null) {//root application context
			if (event instanceof ContextRefreshedEvent) {
				IKiddRefreshService kiddRefreshService = event.getApplicationContext()
						.getBean(BEAN_ID_TOKEN, IKiddRefreshService.class);
				if (kiddRefreshService == null) {
					log.error("kiddRefreshService不存在，请检查配置");
					throw new IllegalArgumentException("服务还未注册,请检查配置");
				}

				timer.setKiddRefreshServiceImpl(kiddRefreshService);
				log.info("Start timing refresh thread");
				timer.start();

				// 初始化到内存
				List<String> stList = new ArrayList<String>(Arrays.asList("wap","view"));
				for (int i = 0; i < stList.size(); i++) {
					String alias = KiddStringUtils.join(i,"_",stList.get(i));
					KiddCacheManager.cacheMap.put(stList.get(i), alias);
				}
				log.info("initialization finish,KiddCacheManager.cacheMap:[{}]",
						KiddCacheManager.cacheMap);
			}

			if (event instanceof ContextClosedEvent) {
				log.info("Destroy thread refresh");
				timer.finish();
				timer = null;
			}
		}
	}
}