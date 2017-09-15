package com.kidd.base.utils;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.MDC;


/**
 * 日志跟踪 【slf4j】
 *
 */
public class KiddTraceLogUtil {
	private static final String TRACE_KEY = "traceID";
	private static final int TRACE_LENGTH = 20;

	public static void beginTrace() {
		String traceId = RandomStringUtils.randomAlphanumeric(TRACE_LENGTH);
		MDC.put(TRACE_KEY, traceId);
	}

	public static void beginTrace(String traceId) {
		MDC.put(TRACE_KEY, traceId);
	}

	public static void endTrace() {
		MDC.remove(TRACE_KEY);
	}

	public static String getTraceId() {
		return ((String) MDC.get(TRACE_KEY));
	}
}