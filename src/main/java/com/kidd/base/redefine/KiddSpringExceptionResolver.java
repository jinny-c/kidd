package com.kidd.base.redefine;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.kidd.base.enums.KiddErrorCodeEnum;
import com.kidd.base.exception.KiddGlobalValidException;
import com.kidd.base.utils.KiddStringUtils;
import com.kidd.base.utils.KiddTraceLogUtil;

public class KiddSpringExceptionResolver extends SimpleMappingExceptionResolver {

	private Logger log = LoggerFactory
			.getLogger(KiddSpringExceptionResolver.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		// Expose ModelAndView for chosen error view.
		String viewName = determineViewName(ex, request);
		if (KiddStringUtils.isBlank(viewName)) {
			return null;
		}

		// Apply HTTP status code for error views, if specified.
		// Only apply it if we're processing a top-level request.
		Integer statusCode = determineStatusCode(request, viewName);
		if (statusCode != null) {
			applyStatusCodeIfPossible(request, response, statusCode);
		}

		String errorMsg = KiddErrorCodeEnum.ERROR_CODE_KW999.getErrorMsg();
		if (ex instanceof KiddGlobalValidException) {
			KiddGlobalValidException microEX = (KiddGlobalValidException) ex;
			errorMsg = microEX.getErrorMsg();
			log.error("捕获MicroGlobalValidException: {}", ex.toString());
		} else {
			log.error("捕获Exception", ex);
		}
		log.error("TraceID:{}, 返回客户端的错误提示：{}", KiddTraceLogUtil.getTraceId(),
				errorMsg);

		ModelAndView modelAndView = getModelAndView(viewName, ex, request);
		Map<String, String> result = new HashMap<String, String>();
		result.put("status", "0");
		result.put("message", errorMsg);
		modelAndView.addObject("result", result);
		modelAndView.addObject("message", errorMsg);
		return modelAndView;
	}

}
