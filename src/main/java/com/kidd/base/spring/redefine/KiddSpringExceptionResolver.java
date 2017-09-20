package com.kidd.base.spring.redefine;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.kidd.base.common.enums.KiddErrorCodeEnum;
import com.kidd.base.common.exception.KiddGlobalValidException;
import com.kidd.base.common.utils.KiddResponseUtils;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.common.utils.KiddTraceLogUtil;

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
		
		
        String respMsg = KiddResponseUtils.toErr(request, KiddErrorCodeEnum.ERROR_CODE_KW999.getErrorCode(),
        		KiddErrorCodeEnum.ERROR_CODE_KW999.getErrorMsg());
		String errorMsg = KiddErrorCodeEnum.ERROR_CODE_KW999.getErrorMsg();
		String errorCode = KiddErrorCodeEnum.ERROR_CODE_KW999.getErrorCode();
		
		if (ex instanceof KiddGlobalValidException) {
			KiddGlobalValidException gvEX = (KiddGlobalValidException) ex;
			errorMsg = gvEX.getErrorMsg();
			respMsg = KiddResponseUtils.toErr(request, gvEX.getErrorCode(), gvEX.getErrorMsg());
			log.error("捕获MicroGlobalValidException: {}", ex.toString());
		} else {
			log.error("捕获Exception", ex);
		}
		log.error("return client result={}",respMsg);
		
		if (isAjaxReq(request)) {
			try {
				KiddResponseUtils.writeToResponse(response, respMsg);
			} catch (Exception handlerException) {
				logger.warn("Handling of [" + ex.getClass().getName()
						+ "] resulted in Exception", handlerException);
			}
			return new ModelAndView();
		}
		
		ModelAndView modelAndView = getModelAndView(viewName, ex, request);
		Map<String, String> result = new HashMap<String, String>();
		result.put("responseCode", errorCode);
		result.put("message", errorMsg);
		modelAndView.addObject("result", result);
		modelAndView.addObject("message", errorMsg);
		return modelAndView;
	}

	private boolean isAjaxReq(HttpServletRequest request) {
		// 判断是否为ajax请求
		String requestType = request.getHeader("X-Requested-With");
		log.debug("requestType={}", requestType);
		if (KiddStringUtils.isNotBlank(requestType)) {
			return true;
		}
		return false;
	}
}
