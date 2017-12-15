package com.kidd.test.excel;

import java.util.HashMap;
import java.util.Map;

import com.kidd.base.common.utils.readFile.JsonFileUtil;
import com.kidd.base.common.utils.readFile.XmlFileUtil;
import com.kidd.wap.controller.dto.GetValidateCodeReq;


public class ReadFileTest {

	public static void main(String[] args) {
		//method1();
		method2();
	}
	
	private static void method2() {
		GetValidateCodeReq req1 = new GetValidateCodeReq();
		req1.setChannel("channel");
		Map<String, GetValidateCodeReq> hsm = new HashMap<String, GetValidateCodeReq>();
		
		hsm.put("req1", req1);
		System.out.println(req1);
		System.out.println(hsm.get("req1"));
		
		req1.setActionFlag("actionFlag");
		System.out.println(req1);
		System.out.println(hsm.get("req1"));
		
		hsm.get("req1").setClientChannel("clientChannel");
		System.out.println(req1);
		System.out.println(hsm.get("req1"));
		
		req1.setActionFlag("null");
		System.out.println(req1);
		System.out.println(hsm.get("req1"));
		
		System.out.println(req1.getActionFlag().equals(null));
	}
	
	private static void method1() {
		String path ="D:/workSpace/20170531-micro/trunk/hpay/kidd/src/main/resources/analogData/";
		String name1 ="format.xml";
		String name2 ="format.json";
		String name3 ="KiddPubNoMenuDTO.json";
		
		String str1 = XmlFileUtil.readXmlFile(path, name1);
		System.out.println("======"+str1);
		String str2 = JsonFileUtil.readJsonFile(path, name2);
		System.out.println("======"+str2);
		
		//GetValidateCodeReq req1 = XmlFileUtil.readXmlFile(GetValidateCodeReq.class,path, name1);
		//System.out.println("======"+req1);
		GetValidateCodeReq req = new GetValidateCodeReq();
		req.setMobile("mobile");
		req.setChannel("channel");
		String res = XmlFileUtil.convertToXml(req);
		System.out.println("======"+res);
		//GetValidateCodeReq req2 = JsonFileUtil.readJsonFile(GetValidateCodeReq.class,path, name2);
		//System.out.println("======"+req2);
		//List<KiddPubNoMenuDTO> dto = JsonFileUtil.readJsonListFile(KiddPubNoMenuDTO.class,path, name3);
		//System.out.println("======"+dto);
		
		
	}
}
