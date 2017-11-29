package com.kidd.base.factory.wechat;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.kidd.base.common.constant.KiddConstants;
import com.kidd.base.common.utils.ServiceProvider;
import com.kidd.base.factory.message.handler.IWechatPushMsgHandler;

/**
 * 维护消息推送Map集合
 *
 * @history
 */
@SuppressWarnings("rawtypes")
public class KiddWapMapHolderUtil {
    /** SLF4J */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(KiddWapMapHolderUtil.class);

	private static class MsgHandlerMapHolder {
        private final static Map<String, IWechatPushMsgHandler> MSG_HANDLER_MAP = new HashMap<String, IWechatPushMsgHandler>();
        static {
            MSG_HANDLER_MAP.put(KiddConstants.MSG_TYPE_CLICK,
                    (IWechatPushMsgHandler)ServiceProvider.getService("clickMessageHandler"));
            MSG_HANDLER_MAP.put(KiddConstants.MSG_TYPE_TEXT,
                    (IWechatPushMsgHandler)ServiceProvider.getService("contentPushMessageHandler"));
            log.info("TransMapHolder.MSG_HANDLER_MAP initialize finished,size: {}", MSG_HANDLER_MAP.size());
        }
    }


    /**
     * 获取消息处理器
     *
     * @param key
     * @return
     */
    public static IWechatPushMsgHandler getMsgHandler(String key) {
        return MsgHandlerMapHolder.MSG_HANDLER_MAP.get(key);
    }

}