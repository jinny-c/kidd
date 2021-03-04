package com.kidd.base.http.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidd.base.http.httpclient.KiddHttpBuilder;
import com.kidd.base.http.httpclient.KiddHttpExecutor;
import org.springframework.beans.factory.annotation.Value;

public class KiddHttpExecutorHolder {
	@Value("${poolMaxTotal:30}")
	private static int poolMaxTotal;
	@Value("${maxPerRoute:10}")
	private static int maxPerRoute;
	/** SLF4J */
	private static final Logger log = LoggerFactory.getLogger(KiddHttpExecutorHolder.class);

	private static KiddHttpExecutor executor = null;
	static {
		if (null == executor) {
			try {
				executor = KiddHttpBuilder.create()
						.loadPool(poolMaxTotal, maxPerRoute)
						.loadTimeOut(3000, 3000)
						.loadIgnoreUrl()
						.build();
			} catch (Exception e) {
				log.error("create http executor fail", e);
			}
		}
	}

	public static KiddHttpExecutor getExecutor(){
		return executor;
	}
}