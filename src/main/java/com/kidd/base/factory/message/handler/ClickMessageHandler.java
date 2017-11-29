package com.kidd.base.factory.message.handler;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kidd.base.factory.message.dto.IPubNumMessage;
import com.kidd.base.factory.wechat.KiddPubNoClient;


/**
 * 事件消息类型--CLICK事件消息
 *
 * @history
 */
@Service("clickMessageHandler")
public class ClickMessageHandler implements IWechatPushMsgHandler {
	/** SLF4J */
	private static final Logger log = LoggerFactory.getLogger(ClickMessageHandler.class);

	@Autowired
	private KiddPubNoClient pubNoClient;

	@Override
	public void doHandler(IPubNumMessage pubNumMessage) {

		String wechatOpenId = pubNumMessage.getWechatOpenId();
		String msgKey = pubNumMessage.getWechatMsgKey();

		if ("bill_statistics".equals(msgKey)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd");
			String date = sdf.format(new Date());
			StringBuilder data = new StringBuilder();
			data.append("\n今日总笔数：").append("10笔");
			data.append("\n今日总金额：").append("1989.7元");
			data.append("\n微信交易：").append("6笔").append("，共1000元.");
			data.append("\n支付宝交易：").append("4笔").append("，共989.7元.");

			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("first", "您申请统计的账户数据如下");
			dataMap.put("shopName", "千里香馄饨");
			dataMap.put("statisticsTime", date);
			dataMap.put("statisticsData", data.toString());
			dataMap.put("remark", "需要查看当月信息，请点击“详情”");
			pubNoClient.pushTemplateMessage("", wechatOpenId, "",
					"", dataMap, 1);
		} else if (null == msgKey) {
			pubNoClient.pushCustomMessage("", wechatOpenId, "请咨询在线客服");
		}
	}
}
