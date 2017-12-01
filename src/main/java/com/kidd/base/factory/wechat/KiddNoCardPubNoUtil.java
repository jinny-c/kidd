package com.kidd.base.factory.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidd.base.common.constant.KiddConstants;
import com.kidd.base.common.enums.KiddSymbolEnum;
import com.kidd.base.common.utils.ConfigRef;
import com.kidd.base.common.utils.KiddStringUtils;


/**
 * 无卡支付微信公众号管理工具类
 *
 * @history
 */
public class KiddNoCardPubNoUtil {

	private static Logger log = LoggerFactory.getLogger(KiddNoCardPubNoUtil.class);

	/**
	 * 普通链接转微信授权链接
	 *
	 * @history
	 */
	public static String getOAuth2Url(String appId, String url) {
		try {
			String realUrl = URLEncoder.encode(url, KiddConstants.CHARSET_DEF);
			return KiddStringUtils.replace(ConfigRef.WX_OAUTH2_URI, appId, realUrl, KiddConstants.WX_OAUTH2_BASE_SCOPE);
		} catch (UnsupportedEncodingException e) {
			log.error("不支持UTF-8编码", e);
		}
		return KiddSymbolEnum.Blank.symbol();
	}

	/**
	 * 获取用户信息地址
	 *
	 * @return
	 */
	public static String getOAuth2UserInfoUrl(String pubId, String appId){
		String userInfoUrl = KiddStringUtils.replace(ConfigRef.USER_INFO_URL, pubId);
		return getOAuth2Url(appId, userInfoUrl);
	}

	/**
	 * 获取抽奖地址
	 *
	 * @return
	 */
	public static String getOAuth2UserPrizeUrl(String pubId, String appId){
		String userInfoUrl = KiddStringUtils.replace(ConfigRef.USER_PRIZE_INFO_URL, pubId);
		return getOAuth2Url(appId, userInfoUrl);
	}
	
}
