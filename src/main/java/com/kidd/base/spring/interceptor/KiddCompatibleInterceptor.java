package com.kidd.base.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kidd.base.common.constant.KiddConstants;
import com.kidd.base.common.enums.KiddErrorCodeEnum;
import com.kidd.base.common.exception.KiddGlobalValidException;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.wap.controller.dto.OAuthUser;

public class KiddCompatibleInterceptor extends HandlerInterceptorAdapter {
	/** SLF4J */
	private static final Logger log = LoggerFactory.getLogger(KiddCompatibleInterceptor.class);
	
	//private static final String HYSFT_PUB_ID = "gh_51790c1ef5c3";
	private static final String HYSFT_PUB_ID = "gh_d8ca418ebb2b";
	
	/** 需要刷新商户审核状态的URI集合 **/
	private static final String[] needRefreshUris = {
			"/userAccessCtrl.htm",
			"/advertise.htm"
	};

	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
	 * 然后SpringMVC会根据声明的前后顺序一个接一个的执行，
	 * 而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
	 * SpringMVC的这种Interceptor链式结构也是可以进行中断的，
	 * 这种中断方式是令preHandle的返回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
	                         HttpServletResponse response, Object handler) throws Exception {
		log.info("preHandle enter");
		if (request.getRequestURI().endsWith(KiddConstants.SCAN_SHARE_CODE_URI)
				&& KiddStringUtils.isBlank(request.getParameter(KiddConstants.PUB_NO_ID))) {
			request.setAttribute(KiddConstants.PUB_NO_ID, HYSFT_PUB_ID);
		}
		validWechatUser(HYSFT_PUB_ID,request);
		// 刷新商户信息
		if (isNeedRefreshUri(request.getRequestURI())) {
			refreshNoCardUser(request);
		}
		return true;
	}

	private void validWechatUser(String pubId,HttpServletRequest request) throws KiddGlobalValidException {
		//用户点击菜单进入
		String currentCode = request.getParameter(KiddConstants.OAUTH2_CODE);
		if (KiddStringUtils.isBlank(currentCode)) {
			log.info("非微信，无需授权");
			return;
			//throw new KiddGlobalValidException(KiddErrorCode.ERROR_CODE_MW001.getErrorCode(), KiddErrorCode.ERROR_CODE_MW001.getErrorMsg());
		}

		log.info("微信用户开始网页授权,公众号ID:[{}]", pubId);
		//OAuthUser authUser = cacheConfig.getWXAuthUser(pubId, currentCode, KiddConstants.WX_OAUTH2_BASE_SCOPE);
		OAuthUser authUser = null;
		if(authUser == null || KiddStringUtils.isBlank(authUser.getOpenid()) && KiddStringUtils
				.isBlank(authUser.getAliPayUserID())){
			log.error("获取微信用户信息失败,当前授权码[{}]无效", currentCode);
			throw new KiddGlobalValidException(KiddErrorCodeEnum.ERROR_CODE_KW001.getErrorCode(), KiddErrorCodeEnum.ERROR_CODE_KW001.getErrorMsg());
		}

		//存缓存
		request.getSession().setAttribute(KiddConstants.OAUTH2_CODE, currentCode);
		//noCardUserVO.setWechatOpenId(authUser.getOpenid());
	}
	
	private void refreshNoCardUser(HttpServletRequest request) throws KiddGlobalValidException {
		Object userVO = request.getSession().getAttribute(KiddConstants.CURRENT_USER);
		log.info("refreshNoCardUser,userVO=[{}]", userVO);
		if (userVO == null) {
			return;
		}
	}

	private boolean isNeedRefreshUri(String uri) {
		for (String listUri : needRefreshUris) {
			if (uri.endsWith(listUri)) {
				return true;
			}
		}
		return false;
	}

}