package com.kidd.base.factory.message.handler;

import com.kidd.base.factory.message.dto.IPubNumMessage;



/**
 * 消息推送处理
 *
 * @history
 */
public interface IWechatPushMsgHandler {
	void doHandler(IPubNumMessage pubNumMessage);
}