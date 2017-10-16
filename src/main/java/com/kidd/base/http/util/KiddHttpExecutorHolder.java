package com.kidd.base.http.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidd.base.http.httpclient.KiddHttpBuilder;
import com.kidd.base.http.httpclient.KiddHttpExecutor;

public class KiddHttpExecutorHolder {
	/** SLF4J */
	private static final Logger log = LoggerFactory.getLogger(KiddHttpExecutorHolder.class);

	private static KiddHttpExecutor executor = null;
	static {
		if (null == executor) {
			try {
				executor = KiddHttpBuilder.create()
						.loadPool(30, 10)
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