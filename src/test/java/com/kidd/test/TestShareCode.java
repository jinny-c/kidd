package com.kidd.test;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kidd.base.common.utils.KiddStringUtils;
import com.kidd.base.http.httpclient.KiddHttpBuilder;
import com.kidd.base.http.httpclient.KiddHttpExecutor;


public class TestShareCode {

    @Test
    public void toShortUrl() throws Exception{

    	String acctoken = "ZjHTZ-Vu5aCrkXGNQWp_5TdwDZKbgW1izVwqkITK9OkuUCR94n0o_Hq8nO-Facc-LG9s8pMd1f5SHIVx6t4upmyca9SjSw0Fe1TxY2LqlNTCKExhsw35i7FF3ofpwuc_TZMdAFAWCP";
		String url = "http://10.148.21.80:8082/kidd";
		
		String requestUrl = KiddStringUtils.replace("https://api.weixin.qq.com/cgi-bin/shorturl?access_token={}", acctoken);
		JSONObject object = new JSONObject();
		object.put("action", "long2short");
		object.put("long_url", url);
		String requestData = object.toString();
		
		KiddHttpExecutor executor = KiddHttpBuilder.create()
				.loadPool(1, 1)
				.loadTimeOut(3000, 3000)
				.loadIgnoreUrl()
				.build();
		String result = executor.doPostWithUrl(requestUrl, requestData, null);
		
		System.out.println("===========" + result);
		JSONObject jsonObject = JSON.parseObject(result);
		System.out.println("===========" + jsonObject);
		System.out.println("===========" + jsonObject.getString("short_url"));
		
    }

}
