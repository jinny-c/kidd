package com.kidd.base.factory.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.kidd.base.factory.message.dto.IPubNumMessage;
import com.kidd.base.factory.message.handler.IWechatPushMsgHandler;
import com.kidd.base.factory.wechat.KiddWapMapHolderUtil;

@Service("defaultMsgHandlerDispatcher")
public class DefaultMsgHandlerDispatcher implements IMsgHandlerDispatcher {

	/** SLF4J */
	private static final Logger log = LoggerFactory.getLogger(DefaultMsgHandlerDispatcher.class);

	@Override
	public void dispatch(IPubNumMessage pubNumMessage) {
		IWechatPushMsgHandler wechatPushMsgHandler = KiddWapMapHolderUtil.
				getMsgHandler(pubNumMessage.getWechatMsgType());
		if (wechatPushMsgHandler == null) {
			log.warn("当前事件消息无需推送,wechatMsgType=[{}]", pubNumMessage.getWechatMsgType());
			return;
		}
		wechatPushMsgHandler.doHandler(pubNumMessage);
	}

}
