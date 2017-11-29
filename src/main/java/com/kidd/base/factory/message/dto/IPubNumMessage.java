package com.kidd.base.factory.message.dto;

/**
 * 消息接口
 *
 * @history
 */
public interface IPubNumMessage {

	/**
	 * 消息类型 【详见WapConstants】
	 *
	 * @return
	 */
	String getWechatMsgType();

	/**
	 * 消息键值
	 *
	 * @return
	 */
	String getWechatMsgKey();

	/**
	 * 消息体
	 *
	 * @return
	 */
	String getWechatMsgBody();

	/**
	 * 商户微信openid
	 *
	 * @return
	 */
	String getWechatOpenId();

	/**
	 * 微信公众号Id
	 *
	 * @return
	 */
	String getWechatPubId();

}
