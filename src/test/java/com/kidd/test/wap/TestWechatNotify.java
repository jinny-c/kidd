package com.kidd.test.wap;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.kidd.base.http.httpclient.KiddHttpBuilder;
import com.kidd.base.http.httpclient.KiddHttpExecutor;

/**
 * 测试HTTP
 *
 * @history
 */
public class TestWechatNotify {

	@Test
	public void testNotify() throws Exception{
		Map<String, Object> bodyValueMap = new HashMap<String, Object>();
		bodyValueMap.put("pubId", "gh_51790c1ef5c3"); //固定值
		//bodyValueMap.put("pubId", "gh_d8ca418ebb2b"); //固定值
		bodyValueMap.put("msgKey", "textNotice"); //固定值
		bodyValueMap.put("openId", "oAD6ev2mkI3Nlz3ZaR2Edii6y-ZQ");  //固定值
		bodyValueMap.put("openId", "oAD6ev2S4SJWcquqUUWuvrfs_zpA");  //固定值

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("textData", "尊敬的用户您好，您尾号1234的银行卡有笔交易打款失败！");
		bodyValueMap.put("jsonData", parameterMap);
		try {
			String url = "http://10.148.21.80:8082/kidd/wap/pubnum/wechatNotify.htm";
			KiddHttpExecutor executor = KiddHttpBuilder.create()
					.loadPool(1, 1)
					.loadTimeOut(3000, 3000)
					.loadIgnoreUrl()
					.build();
			String responseStr = executor.doPostWithUrl(url, JSON.toJSONString(bodyValueMap), null);
			System.out.println("=============" + responseStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
