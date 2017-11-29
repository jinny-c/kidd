package com.kidd.base.factory.message.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.base.common.utils.KiddDateUtils;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.factory.message.dto.IPubNumMessage;
import com.kidd.base.factory.wechat.KiddPubNoClient;
import com.kidd.base.factory.wechat.dto.KiddPubNoInfoDTO;

/**
 * 普通消息类型--文本消息/自定义消息
 *
 * @history
 */
@Service("contentPushMessageHandler")
public class ContentMessageHandler implements IWechatPushMsgHandler {

	/** SLF4J */
	private static final Logger log = LoggerFactory.getLogger(ContentMessageHandler.class);

	public static final int RETRIES_TIME = 1;

	@Autowired
	private KiddPubNoClient kiddPubNoClient;

	@Override
	public void doHandler(IPubNumMessage pubNumMessage) {
		//交易通知
		if(KiddPubNoInfoDTO.MSG_KEY_TRANS.equals(pubNumMessage.getWechatMsgKey())){
			String jsonData = pubNumMessage.getWechatMsgBody();
			JSONObject jsonObject = JSON.parseObject(jsonData);

			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("first", "尊敬的用户：您又收到一笔钱！");
			dataMap.put("tradeDateTime", KiddDateUtils.parseToDateAD(jsonObject.getString("tradeDateTime")));
			dataMap.put("tradeType", jsonObject.getString("tradeType"));
			dataMap.put("curAmount", jsonObject.getString("curAmount") + "元");
			dataMap.put("remark", "祝您生活愉快");

			String pubId = pubNumMessage.getWechatPubId();
			String toUser = pubNumMessage.getWechatOpenId(); //商户的openID oSecpwWdgC-4h0nL5rkxWm2TOjLU
			String templateId = kiddPubNoClient.getMsgTemplateId(pubId, KiddPubNoInfoDTO.MSG_KEY_TRANS);
			kiddPubNoClient.pushTemplateMessage(pubId, toUser, templateId, null, dataMap, RETRIES_TIME);
			return;
		}

		//提现通知
		if(KiddPubNoInfoDTO.MSG_KEY_WITHDRAW.equals(pubNumMessage.getWechatMsgKey())){
			String jsonData = pubNumMessage.getWechatMsgBody();
			JSONObject jsonObject = JSON.parseObject(jsonData);

			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("first", "尊敬的用户: \r\n我公司已成功向您的账户划出交易款项，请注意查看您的银行账户信息。");
			dataMap.put("merTab", "商户编号");
			dataMap.put("merId", jsonObject.getString("merchantCode"));
			dataMap.put("remAmt", jsonObject.getString("curAmount") + "元");
			dataMap.put("remTime", KiddDateUtils.parseToDateAD(jsonObject.getString("tradeDateTime")));
			dataMap.put("remBank", jsonObject.getString("bankName"));
			dataMap.put("remAccount", jsonObject.getString("cardNo"));
			dataMap.put("holderDetail", "实际入账时间请以入账行信息为准");
			dataMap.put("remStat", "划款完成");
			dataMap.put("remark", "祝您生活愉快");

			String pubId = pubNumMessage.getWechatPubId();
			String toUser = pubNumMessage.getWechatOpenId(); //商户的openID oSecpwWdgC-4h0nL5rkxWm2TOjLU
			String templateId = kiddPubNoClient.getMsgTemplateId(pubId, KiddPubNoInfoDTO.MSG_KEY_WITHDRAW);
			kiddPubNoClient.pushTemplateMessage(pubId, toUser, templateId, null, dataMap, RETRIES_TIME);
			return;
		}

		//商户审核通知
		if(KiddPubNoInfoDTO.MSG_KEY_AUDIT.equals(pubNumMessage.getWechatMsgKey())){
			String pubId = pubNumMessage.getWechatPubId();
			String toUser = pubNumMessage.getWechatOpenId(); //商户的openID oSecpwWdgC-4h0nL5rkxWm2TOjLU
			String templateId = kiddPubNoClient.getMsgTemplateId(pubId, KiddPubNoInfoDTO.MSG_KEY_AUDIT);

			String jsonData = pubNumMessage.getWechatMsgBody();
			JSONObject jsonObject = JSON.parseObject(jsonData);
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("first", "您好，感谢您申请成为无卡支付商户。");
			dataMap.put("keyword2", KiddDateUtils.parseToDateAD(jsonObject.getString("auditTime")));
			String auditStatus = jsonObject.getString("auditStatus");
			if ("00".equals(auditStatus)) {
				dataMap.put("keyword1", "审核通过！");
				kiddPubNoClient.pushTemplateMessage(pubId, toUser, templateId, null, dataMap, RETRIES_TIME);
				return;
			}
			dataMap.put("keyword1", "审核失败！");
			dataMap.put("remark", "失败原因：" + jsonObject.getString("auditReason") + " 点击“详情”重新提交");
			String appId = kiddPubNoClient.getPubNoAppId(pubId);
			kiddPubNoClient.pushTemplateMessage(pubId, toUser, templateId,
					"getOAuth2UserInfoUrl(pubId, appId)", dataMap, RETRIES_TIME);
			return;
		}
		
		//信用卡还款日提醒
		if(KiddPubNoInfoDTO.MSG_KEY_CREDIT.equals(pubNumMessage.getWechatMsgKey())){
			String jsonData = pubNumMessage.getWechatMsgBody();
			JSONObject jsonObject = JSON.parseObject(jsonData);

			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("first", "尊敬的用户：您有一张信用卡3天后要还款！");
			dataMap.put("keyword1", jsonObject.getString("cardBankName") + "(" + jsonObject.getString("cardNo")
					+ ")" + jsonObject.getString("fullName"));
			dataMap.put("keyword2", jsonObject.getString("repayDate"));
			dataMap.put("remark", "请您在还款日前还清，避免产生息费。如已还款，无需理会。");
			String pubId = pubNumMessage.getWechatPubId();
			String toUser = pubNumMessage.getWechatOpenId(); //商户的openID oSecpwWdgC-4h0nL5rkxWm2TOjLU
			String templateId = kiddPubNoClient.getMsgTemplateId(pubId, KiddPubNoInfoDTO.MSG_KEY_CREDIT);
			kiddPubNoClient.pushTemplateMessage(pubId, toUser, templateId, null, dataMap, RETRIES_TIME);
			return;
		}

		//客服通知
		if(KiddPubNoInfoDTO.MSG_KEY_NOTICE_TEXT.equals(pubNumMessage.getWechatMsgKey())){
			String jsonData = pubNumMessage.getWechatMsgBody();
			JSONObject jsonObject = JSON.parseObject(jsonData);
			kiddPubNoClient.pushCustomMessage(pubNumMessage.getWechatPubId(),
					pubNumMessage.getWechatOpenId(),
					jsonObject.getString("textData"));
			
			return;
		}
		
		
		// 默认回复
		if(KiddStringUtils.isNotBlank(pubNumMessage.getWechatMsgKey())) {
			kiddPubNoClient.pushCustomMessage(pubNumMessage.getWechatPubId(), pubNumMessage.getWechatOpenId(),
					"欢迎使用无卡支付产品，如" + "KiddErrorCode.ERROR_CODE_MW008.getErrorMsg()");
		}
	}
}
