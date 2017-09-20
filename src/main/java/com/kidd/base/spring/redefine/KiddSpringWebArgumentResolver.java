package com.kidd.base.spring.redefine;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.kidd.base.annotation.KiddSecureAnno;
import com.kidd.base.annotation.proc.KiddSecureProcessor;
import com.kidd.base.common.KiddBaseReqDto;
import com.kidd.base.common.exception.KiddGlobalValidException;
import com.kidd.base.params.valid.KiddValidResp;
import com.kidd.base.params.valid.VerifyControllerUtil;

/**
 * web参数处理类
 * 
 * @history
 */
public class KiddSpringWebArgumentResolver implements
		HandlerMethodArgumentResolver {

	private static final String SERIALVERSIONUID = "serialVersionUID";

	private static Logger logger = LoggerFactory.getLogger(KiddSpringWebArgumentResolver.class);
	
	@Autowired
	private KiddSecureProcessor kiddSecureProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.method.support.HandlerMethodArgumentResolver#
	 * supportsParameter(org.springframework.core.MethodParameter)
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 仅作用于添加了该【类注解】的入参
		return parameter.hasParameterAnnotation(KiddSecureAnno.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.method.support.HandlerMethodArgumentResolver#
	 * resolveArgument(org.springframework.core.MethodParameter,
	 * org.springframework.web.method.support.ModelAndViewContainer,
	 * org.springframework.web.context.request.NativeWebRequest,
	 * org.springframework.web.bind.support.WebDataBinderFactory)
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest request,
			WebDataBinderFactory binderFactory) throws Exception {
		logger.info("KiddSpringWebArgumentResolver.resolveArgument start");
		
		if (!kiddSecureProcessor.isSecure(parameter)) {
			return WebArgumentResolver.UNRESOLVED;
		}
		// 参数赋值
		Object target = argToObj(parameter, request, binderFactory);
		// 参数校验
		paramsValid(target, parameter.getParameterType(), request);
		return target;
	}

	/**
	 * 参数校验
	 * 
	 * @param target
	 * @param clazz
	 * @param request
	 * @throws JiddGlobalValidException
	 */
	private void paramsValid(Object target, Class<? extends Object> clazz,
			NativeWebRequest request) throws KiddGlobalValidException {
		// 2017-03-07 microView工程DTO目前继承关系为二层
		if (clazz.getSuperclass() == KiddBaseReqDto.class
				|| clazz.getSuperclass().getSuperclass() == KiddBaseReqDto.class) {
			KiddBaseReqDto req = (KiddBaseReqDto) target;
			KiddValidResp valid = VerifyControllerUtil.validateReqDto(req);
			logger.info("校验参数返回：" + valid);
			if (valid != null && !valid.isSucc()) {
				throw new KiddGlobalValidException(valid.getErrCode(),
						valid.getErrMsg());
			}
		}
	}

	/**
	 * 参数转换
	 * 
	 */
	private Object argToObj(MethodParameter parameter,
			NativeWebRequest request, WebDataBinderFactory binderFactory)
			throws Exception {
		Class<? extends Object> clazz = parameter.getParameterType();
		Object target = BeanUtils.instantiateClass(clazz);

		String shortName = ClassUtils.getShortNameAsProperty(clazz);
		WebDataBinder binder = binderFactory.createBinder(request, null,
				shortName);
		Object arg = null;
		
		// 2016-09-20 处理父类中需要解密的属性字段
		for (; clazz != KiddBaseReqDto.class; clazz = clazz.getSuperclass()) {
			// 参数赋值
			for (Field filed : clazz.getDeclaredFields()) {
				// 字段属性为空跳过
				if (filed == null) {
					continue;
				}
				if (SERIALVERSIONUID.equals(filed.getName())) {
					continue;
				}
				filed.setAccessible(true);
				arg = binder.convertIfNecessary(
						request.getParameter(filed.getName()), filed.getType(),
						parameter);

				// 数据解密
				// TODO
				if (kiddSecureProcessor.isDecr(filed) && null != arg) {
					//logger.info(JiddStringUtils.replace("jiddSecureProcessor decrypt field=[{}]",filed.getName()));
					logger.info("jiddSecureProcessor decrypt field=[{}],value=[{}]",filed.getName(),String.valueOf(arg));
					arg = kiddSecureProcessor.decrypt("key", String.valueOf(arg), null, null);
				}
				filed.set(target, arg);
			}
		}
		return target;
	}
}
