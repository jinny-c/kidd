package com.kidd.base.spring.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kidd.base.common.constant.KiddConstants;
import com.kidd.base.common.constant.KiddErrorCodes;
import com.kidd.base.common.enums.KiddErrorCodeEnum;
import com.kidd.base.common.exception.KiddGlobalValidException;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.factory.wechat.KiddPubNoClient;
import com.kidd.wap.controller.dto.OAuthUser;

public class KiddCompatibleInterceptor extends HandlerInterceptorAdapter {
	/** SLF4J */
	private static final Logger log = LoggerFactory.getLogger(KiddCompatibleInterceptor.class);
	
	//private static final String HYSFT_PUB_ID = "gh_51790c1ef5c3";
	private static final String HYSFT_PUB_ID = "gh_d8ca418ebb2b";
	
	@Autowired
	private KiddPubNoClient kiddPubNoClient;
	
	private static class MenuSwitch {
		private boolean isValidWechatUser;
		private boolean isValidUser;
		private boolean isUserAccess;

		/** 菜单开关控制 **/
		private static Map<String, MenuSwitch> menuSwitchMap = new HashMap<String, MenuSwitch>();
		static {
			menuSwitchMap.put("/*", new MenuSwitch(false, false, false)); // 默认不拦截
			menuSwitchMap.put("/scanShareCode.htm", new MenuSwitch(true, true, false));  //扫码注册
			menuSwitchMap.put("/payEntry.htm", new MenuSwitch()); // 收款
			menuSwitchMap.put("/creditCardEntry.htm", new MenuSwitch()); // 信用卡管理
			menuSwitchMap.put("/toLogin.htm", new MenuSwitch(true, true, false)); // 切换用户
			menuSwitchMap.put("/userInfo.htm", new MenuSwitch()); // 用户基本信息
			menuSwitchMap.put("/card.htm", new MenuSwitch()); // 修改收款卡
			menuSwitchMap.put("/queryBillsEntry.htm", new MenuSwitch()); // 交易明细
			menuSwitchMap.put("/showRecommend.htm", new MenuSwitch()); // 我的推广码
			menuSwitchMap.put("/showPrizeIndex.htm", new MenuSwitch()); // 抽奖活动
			menuSwitchMap.put("/wechat.htm", new MenuSwitch()); // 微信
		}

		private MenuSwitch() {
			isValidWechatUser = true;
			isValidUser = true;
			isUserAccess = true;
		}

		private MenuSwitch(boolean isValidWechatUser, boolean isValidNoCardUser, boolean isUserAccess) {
			this.isValidWechatUser = isValidWechatUser;
			this.isValidUser = isValidNoCardUser;
			this.isUserAccess = isUserAccess;
		}

		private static boolean isNeedValidWechatUser(String url) {
			return getMenuSwitch(url).isValidWechatUser;
		}

		private static boolean isNeedValidUser(String url) {
			MenuSwitch menuSwitch = getMenuSwitch(url);
			return menuSwitch.isValidWechatUser && menuSwitch.isValidUser;
		}

		private static boolean isNeedCtrlUserAccess(String url) {
			MenuSwitch menuSwitch = getMenuSwitch(url);
			return menuSwitch.isValidWechatUser && menuSwitch.isValidUser && menuSwitch.isUserAccess;
		}

		private static MenuSwitch getMenuSwitch(String url) {
			String key = url.substring(url.lastIndexOf("/"));
			MenuSwitch menuSwitch = menuSwitchMap.get(key);
			if (menuSwitch == null) {
				return menuSwitchMap.get("/*");
			}
			return menuSwitch;
		}
	}
	
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
		
		// 是否验证微信授权
		if (MenuSwitch.isNeedValidWechatUser(request.getRequestURI())) {
			validWechatUser(request);
		}
		// 是否验证用户
		if (MenuSwitch.isNeedValidUser(request.getRequestURI())) {
			log.info("preHandle isNeedValidUser");
		}

		// 是否进入用户访问控制模块
		if (MenuSwitch.isNeedCtrlUserAccess(request.getRequestURI())) {
			log.info("preHandle isNeedCtrlUserAccess");
		}
		return true;
	}

	private void validWechatUser(HttpServletRequest request) throws KiddGlobalValidException {
		//用户点击菜单进入
		String pubId = request.getParameter(KiddConstants.PUB_NO_ID);
		if (KiddStringUtils.isBlank(pubId)) {
			request.setAttribute(KiddConstants.PUB_NO_ID, HYSFT_PUB_ID);
			//pubId = (String)request.getAttribute(KiddConstants.PUB_NO_ID);
			pubId = HYSFT_PUB_ID;
		}
		
		String currentCode = request.getParameter(KiddConstants.OAUTH2_CODE);
		if (KiddStringUtils.isBlank(currentCode)) {
			log.error("微信用户未授权或授权失败");
			throw new KiddGlobalValidException(KiddErrorCodes.E_KIDD_ERROR, "微信用户未授权或授权失败");
		}

		String latestCode = (String)request.getSession().getAttribute(KiddConstants.OAUTH2_CODE);
		//允许用户刷新页面
		if (isNeedRefreshUri(request.getRequestURI()) && KiddStringUtils.isNotBlank(latestCode) && latestCode.equals(currentCode)) {
			log.error("页面刷新获取用户信息");
			refreshNoCardUser(request);
		}
		
		log.info("微信用户开始网页授权,公众号ID:[{}]", pubId);
		
		OAuthUser authUser = kiddPubNoClient.getWXAuthUser(pubId, currentCode, KiddConstants.WX_OAUTH2_BASE_SCOPE);
		if(authUser == null || KiddStringUtils.isBlank(authUser.getOpenid()) && KiddStringUtils
				.isBlank(authUser.getAliPayUserID())){
			log.error("获取微信用户信息失败,当前授权码[{}]无效", currentCode);
			throw new KiddGlobalValidException(KiddErrorCodeEnum.ERROR_CODE_KW0001.getErrorCode(), KiddErrorCodeEnum.ERROR_CODE_KW0001.getErrorMsg());
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